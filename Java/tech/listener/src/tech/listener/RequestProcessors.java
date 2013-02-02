package tech.listener;

import java.io.DataInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import tech.annotations.After;
import tech.annotations.Before;
import tech.annotations.PathVariable;
import tech.annotations.RequestProcessing;
import tech.annotations.RequestProcessor;
import tech.config.Config;
import tech.convertor.ConvertSupport;
import tech.processor.advice.AfterRequestProcessAdvice;
import tech.processor.advice.BeforeRequestProcessAdvice;
import tech.processor.advice.RequestProcessAdvice;
import tech.processor.advice.RequestProcessAdviceException;
import tech.util.AntPathMatcher;
import tech.util.ReflectHelper;
import tech.util.RegexPathMatcher;

public final class RequestProcessors {
	private static final Logger LOGGER = Logger
			.getLogger(RequestProcessors.class.getName());
	private static Set<ProcessorMethod> processorMethods = new HashSet<ProcessorMethod>();
	private static Map<Method, Object> processors = new HashMap<Method, Object>();
	private static Map<Class<? extends RequestProcessAdvice>, ? extends RequestProcessAdvice> adviceMap = new HashMap<Class<? extends RequestProcessAdvice>, RequestProcessAdvice>();
	private static Map<Class<? extends ConvertSupport>, ConvertSupport> convertMap = new HashMap<Class<? extends ConvertSupport>, ConvertSupport>();

	public static Object invoke(final String requestURI,
			final String contextPath, final String method,
			final HTTPRequestContext context) {

		final ProcessorMethod processMethod = getProcessorMethod(requestURI,
				contextPath, method);

		if (null == processMethod) {
			LOGGER.log(
					Level.WARNING,
					"Can not find process method for request[requestURI={0}, method={1}]",
					new Object[] { requestURI, method });
			return null;
		}

		final Method processorMethod = processMethod.getProcessorMethod();
		Object processorObject = processors.get(processorMethod);

		try {
			if (null == processorObject) {
				final Class<?> processorClass = processMethod
						.getProcessorClass();
				final Object instance = processorClass.newInstance();
				processors.put(processorMethod, instance);
				processorObject = instance;
			}

			final Map<String, Object> args = new LinkedHashMap<String, Object>();

			final Class<?>[] parameterTypes = processorMethod
					.getParameterTypes();
			final String[] parameterName = processMethod.getMethodParamNames();

			// TODO need Optimization
			final Map<String, String> pathVariableValueMap = processMethod
					.pathVariableValueMap(requestURI);
			for (int i = 0; i < parameterTypes.length; i++) {
				final Class<?> paramClass = parameterTypes[i];
				if (paramClass.equals(HTTPRequestContext.class)) {
					args.put(parameterName[i], context);
				} else if (paramClass.equals(HttpServletRequest.class)) {
					args.put(parameterName[i], context.getRequest());
				} else if (paramClass.equals(HttpServletResponse.class)) {
					args.put(parameterName[i], context.getResponse());
				} else if (pathVariableValueMap.containsKey(parameterName[i])) {
					args.put(
							parameterName[i],
							getConerter(processMethod.getConvertClass())
									.convert(
											parameterName[i],
											pathVariableValueMap
													.get(parameterName[i]),
											paramClass));
				} else {
					args.put(parameterName[i], null);
				}
			}

			// before invoke.
			if (processorMethod.isAnnotationPresent(Before.class)) {
				final Before befores = processorMethod
						.getAnnotation(Before.class);
				final Class<? extends BeforeRequestProcessAdvice>[] adviceClass = befores
						.adviceClass();
				BeforeRequestProcessAdvice instance;

				try {
					for (Class<? extends BeforeRequestProcessAdvice> clz : adviceClass) {
						instance = (BeforeRequestProcessAdvice) adviceMap
								.get(clz);
						if (instance == null) {
							instance = clz.newInstance();
						}
						instance.doAdvice(context, args);
					}
				} catch (final RequestProcessAdviceException e) {
					LOGGER.log(
							Level.WARNING,
							"Occurs an exception before request processing [errMsg={0}]",
							e.getMessage());
					return null;
				}

			}

			final Object ret = processorMethod.invoke(processorObject, args
					.values().toArray());

			// after invoke.
			if (processorMethod.isAnnotationPresent(After.class)) {
				final After afters = processorMethod.getAnnotation(After.class);
				final Class<? extends AfterRequestProcessAdvice>[] adviceClass = afters
						.adviceClass();
				AfterRequestProcessAdvice instance;
				for (Class<? extends AfterRequestProcessAdvice> clz : adviceClass) {
					instance = (AfterRequestProcessAdvice) adviceMap.get(clz);
					if (instance == null) {
						instance = clz.newInstance();
					}
					instance.doAdvice(context, ret);
				}
			}
			return ret;

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Invokes processor method failed [method="
					+ processorMethod.getDeclaringClass().getSimpleName() + '#'
					+ processorMethod.getName() + ']', e);

			return null;
		}
	}

	private static ConvertSupport getConerter(
			final Class<? extends ConvertSupport> convertClass)
			throws Exception {

		ConvertSupport convertSupport = convertMap.get(convertClass);
		if (convertSupport == null) {
			convertSupport = convertClass.newInstance();
			convertMap.put(convertClass, convertSupport);
		}

		return convertSupport;
	}

	public static void discover() throws Exception {
		discoverFromClassesDir();
		discoverFromLibDir();
	}

	private static void discoverFromClassesDir() {
		final String webRoot = Config.webRoot();
		final File classesDir = new File(webRoot + "WEB-INF" + File.separator
				+ "classes" + File.separator);
		@SuppressWarnings("unchecked")
		final Collection<File> classes = FileUtils.listFiles(classesDir,
				new String[] { "class" }, true);
		final ClassLoader classLoader = RequestProcessors.class
				.getClassLoader();

		try {
			for (final File classFile : classes) {
				final String path = classFile.getPath();
				final String className = StringUtils
						.substringBetween(
								path,
								"WEB-INF" + File.separator + "classes"
										+ File.separator, ".class")
						.replaceAll("\\/", ".").replaceAll("\\\\", ".");
				final Class<?> clz = classLoader.loadClass(className);

				if (clz.isAnnotationPresent(RequestProcessor.class)) {
					LOGGER.log(Level.FINER,
							"Found a request processor[className={0}]",
							className);
					final Method[] declaredMethods = clz.getDeclaredMethods();
					for (int i = 0; i < declaredMethods.length; i++) {
						final Method mthd = declaredMethods[i];
						final RequestProcessing annotation = mthd
								.getAnnotation(RequestProcessing.class);

						if (null == annotation) {
							continue;
						}

						addProcessorMethod(annotation, clz, mthd);
					}
				}
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,
					"Scans classpath (classes directory) failed", e);
		}
	}

	private static void discoverFromLibDir() {
		final String webRoot = Config.webRoot();
		final File libDir = new File(webRoot + "WEB-INF" + File.separator
				+ "lib" + File.separator);
		@SuppressWarnings("unchecked")
		final Collection<File> files = FileUtils.listFiles(libDir,
				new String[] { "jar" }, true);

		final ClassLoader classLoader = RequestProcessors.class
				.getClassLoader();

		try {
			for (final File file : files) {
				if (file.getName().contains("appengine-api")
						|| file.getName().startsWith("freemarker")
						|| file.getName().startsWith("javassist")
						|| file.getName().startsWith("commons")
						|| file.getName().startsWith("mail")
						|| file.getName().startsWith("activation")
						|| file.getName().startsWith("slf4j")
						|| file.getName().startsWith("bonecp")
						|| file.getName().startsWith("jsoup")
						|| file.getName().startsWith("guava")
						|| file.getName().startsWith("markdown")
						|| file.getName().startsWith("mysql")
						|| file.getName().startsWith("c3p0")) {
					// Just skips some known dependencies hardly....
					LOGGER.log(
							Level.INFO,
							"Skipped request processing discovery[jarName={0}]",
							file.getName());

					continue;
				}

				final JarFile jarFile = new JarFile(file.getPath());

				final Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					final JarEntry jarEntry = entries.nextElement();
					final String classFileName = jarEntry.getName();

					if (classFileName.contains("$") // Skips inner class
							|| !classFileName.endsWith(".class")) {
						continue;
					}

					final DataInputStream classInputStream = new DataInputStream(
							jarFile.getInputStream(jarEntry));

					final ClassFile classFile = new ClassFile(classInputStream);
					final AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) classFile
							.getAttribute(AnnotationsAttribute.visibleTag);
					if (null == annotationsAttribute) {
						continue;
					}

					for (Annotation annotation : annotationsAttribute
							.getAnnotations()) {
						if ((annotation.getTypeName())
								.equals(RequestProcessor.class.getName())) {
							// Found a request processor class, loads it
							final String className = classFile.getName();
							final Class<?> clz = classLoader
									.loadClass(className);

							LOGGER.log(Level.FINER,
									"Found a request processor[className={0}]",
									className);
							final Method[] declaredMethods = clz
									.getDeclaredMethods();
							for (int i = 0; i < declaredMethods.length; i++) {
								final Method mthd = declaredMethods[i];
								final RequestProcessing requestProcessingMethodAnn = mthd
										.getAnnotation(RequestProcessing.class);

								if (null == requestProcessingMethodAnn) {
									continue;
								}

								addProcessorMethod(requestProcessingMethodAnn,
										clz, mthd);
							}
						}
					}
				}
				jarFile.close();
			}

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Scans classpath (lib directory) failed",
					e);

		}
	}

	public static void discoverFromClass(final Class<?> clazz) {

		final RequestProcessor requestProcessor = clazz
				.getAnnotation(RequestProcessor.class);

		if (null == requestProcessor) {
			return;
		}

		final Method[] declaredMethods = clazz.getDeclaredMethods();
		for (int i = 0; i < declaredMethods.length; i++) {
			final Method mthd = declaredMethods[i];
			final RequestProcessing requestProcessingMethodAnn = mthd
					.getAnnotation(RequestProcessing.class);

			if (null == requestProcessingMethodAnn) {
				continue;
			}

			addProcessorMethod(requestProcessingMethodAnn, clazz, mthd);
		}
	}

	private static ProcessorMethod getProcessorMethod(final String requestURI,
			final String contextPath, final String method) {
		LOGGER.log(
				Level.FINEST,
				"Gets processor method[requestURI={0}, contextPath={1}, method={2}]",
				new Object[] { requestURI, contextPath, method });

		final List<ProcessorMethod> matches = new ArrayList<ProcessorMethod>();
		int i = 0;
		for (final ProcessorMethod processorMethod : processorMethods) {
			// TODO: 88250, sort, binary-search
			if (method.equals(processorMethod.getMethod())) {
				// String uriPattern = processorMethod.getURIPattern();
				String uriPattern = processorMethod.getMappingString();

				if (processorMethod.isWithContextPath()) {
					uriPattern = contextPath + uriPattern;
				}

				if (requestURI.equals(uriPattern)) {
					return processorMethod;
				}

				boolean found = false;

				switch (processorMethod.getURIPatternMode()) {
				case ANT_PATH:
					found = AntPathMatcher.match(uriPattern, requestURI);
					break;
				case REGEX:
					found = RegexPathMatcher.match(uriPattern, requestURI);
					break;
				default:
					throw new IllegalStateException(
							"Can not process URI pattern[uriPattern="
									+ processorMethod.getURIPattern()
									+ ", mode="
									+ processorMethod.getURIPatternMode() + "]");
				}

				if (found) {
					i++;
					matches.add(processorMethod);
				}
			}
		}

		if (matches.isEmpty()) {
			return null;
		}

		if (i > 1) {
			final StringBuilder stringBuilder = new StringBuilder(
					"Can not determine request method for configured methods[");
			final Iterator<ProcessorMethod> iterator = matches.iterator();
			while (iterator.hasNext()) {
				final ProcessorMethod processMethod = iterator.next();

				stringBuilder.append("[className=");
				stringBuilder.append(processMethod.getProcessorMethod()
						.getDeclaringClass().getSimpleName());
				stringBuilder.append(", methodName=");
				stringBuilder.append(processMethod.getProcessorMethod()
						.getName());
				stringBuilder.append(", patterns=");
				stringBuilder.append(processMethod.getURIPattern());
				stringBuilder.append("]");

				if (iterator.hasNext()) {
					stringBuilder.append(", ");
				}
			}
			stringBuilder.append("]");

			LOGGER.warning(stringBuilder.toString());
		}

		return matches.get(0);
	}

	private static void addProcessorMethod(
			final RequestProcessing requestProcessing, final Class<?> clz,
			final Method method) {
		final String[] uriPatterns = requestProcessing.value();
		final URIPatternMode uriPatternsMode = requestProcessing
				.uriPatternsMode();
		final boolean isWithContextPath = requestProcessing.isWithContextPath();

		for (int i = 0; i < uriPatterns.length; i++) {
			final String uriPattern = uriPatterns[i];
			final HTTPRequestMethod[] requestMethods = requestProcessing
					.method();

			for (int j = 0; j < requestMethods.length; j++) {
				final HTTPRequestMethod requestMethod = requestMethods[j];

				final ProcessorMethod processorMethod = new ProcessorMethod();
				processorMethods.add(processorMethod);

				processorMethod.setMethod(requestMethod.name());
				processorMethod.setURIPattern(uriPattern);
				processorMethod.setWithContextPath(isWithContextPath);
				processorMethod.setProcessorClass(clz);
				processorMethod.setProcessorMethod(method);
				processorMethod.setURIPatternModel(uriPatternsMode);
				processorMethod.setConvertClass(requestProcessing
						.convertClass());

				processorMethod.analysis();
			}
		}
	}

	private RequestProcessors() {
	}

	private static final class ProcessorMethod {

		private String uriPattern;
		private boolean withContextPath;
		private URIPatternMode uriPatternMode;
		private String method;
		private Class<?> processorClass;
		private Method processorMethod;
		private Class<? extends ConvertSupport> convertClass;

		public void setURIPatternModel(final URIPatternMode uriPatternMode) {
			this.uriPatternMode = uriPatternMode;
		}

		public URIPatternMode getURIPatternMode() {
			return uriPatternMode;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(final String method) {
			this.method = method;
		}

		public Class<?> getProcessorClass() {
			return processorClass;
		}

		public void setProcessorClass(final Class<?> processorClass) {
			this.processorClass = processorClass;
		}

		public Method getProcessorMethod() {
			return processorMethod;
		}

		public void setProcessorMethod(final Method processorMethod) {
			this.processorMethod = processorMethod;
		}

		public String getURIPattern() {
			return uriPattern;
		}

		public void setURIPattern(final String uriPattern) {
			this.uriPattern = uriPattern;
		}

		public boolean isWithContextPath() {
			return withContextPath;
		}

		public void setWithContextPath(final boolean withContextPath) {
			this.withContextPath = withContextPath;
		}

		public Class<? extends ConvertSupport> getConvertClass() {
			return convertClass;
		}

		public void setConvertClass(
				final Class<? extends ConvertSupport> convertClass) {
			this.convertClass = convertClass;
		}

		private String mappingString;

		public String getMappingString() {
			return mappingString;
		}

		public void analysis() {
			mappingString = handleMappingString();

			methodParamNames = ReflectHelper.getMethodVariableNames(
					processorClass, processorMethod.getName(),
					processorMethod.getParameterTypes());
			int i = 0;
			for (java.lang.annotation.Annotation[] annotations : processorMethod
					.getParameterAnnotations()) {
				for (java.lang.annotation.Annotation annotation : annotations) {
					if (annotation instanceof PathVariable) {
						methodParamNames[i] = ((PathVariable) annotation)
								.value();
					}
				}
				i++;
			}

		}

		private List<String> paramNames = new ArrayList<String>();
		private List<Integer> posSpan = new ArrayList<Integer>();
		private List<Character> afertCharacters = new ArrayList<Character>();
		private String[] methodParamNames;

		public String[] getMethodParamNames() {
			return methodParamNames;
		}

		private String handleMappingString() {
			final Pattern pattern = Pattern.compile("\\{[^}]+\\}");
			final Matcher matcher = pattern.matcher(uriPattern);
			final StringBuilder uriMapping = new StringBuilder(uriPattern);
			int fixPos = 0;
			char[] tem;
			int lastEnd = 0;
			while (matcher.find()) {
				tem = new char[matcher.end() - matcher.start() - 2];
				uriMapping.getChars(matcher.start() - fixPos + 1, matcher.end()
						- fixPos - 1, tem, 0);
				paramNames.add(new String(tem));
				if (lastEnd == 0) {
					posSpan.add(matcher.start());
				} else {
					posSpan.add(matcher.start() - lastEnd);
				}

				uriMapping.replace(matcher.start() - fixPos, matcher.end()
						- fixPos, "*");
				fixPos = fixPos + matcher.end() - matcher.start() - 1;
				lastEnd = matcher.end();

				if (matcher.end() - fixPos < uriMapping.length()) {
					afertCharacters.add(uriMapping.charAt(matcher.end()
							- fixPos));
				} else {
					afertCharacters.add(null);
				}
			}

			return uriMapping.toString();
		}

		public Map<String, String> pathVariableValueMap(final String requestURI)
				throws Exception {

			final Map<String, String> ret = new HashMap<String, String>();

			final int length = requestURI.length();
			int i = 0;
			StringBuilder chars;
			for (int j = 0; j < paramNames.size(); j++) {
				int step = 0;
				while (step < posSpan.get(j)) {
					i++;
					step++;
				}
				chars = new StringBuilder();
				while (i < length
						&& Character.valueOf(requestURI.charAt(i)) != afertCharacters
								.get(j)) {
					chars.append(requestURI.charAt(i));
					i++;
				}

				ret.put(paramNames.get(j),
						URLDecoder.decode(chars.toString(), "UTF-8"));
			}

			return ret;
		}
	}
}
