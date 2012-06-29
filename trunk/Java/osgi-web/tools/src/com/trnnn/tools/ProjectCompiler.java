package com.trnnn.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Manifest;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ProjectCompiler {
	private String projectName;
	private String projectPath;
	private List<File> sourceFiles;
	@SuppressWarnings("unused")
	private List<File> configs;
	@SuppressWarnings("unused")
	private Manifest manifest;
	@SuppressWarnings("unused")
	private boolean outputJar;
	private String targetPath;
	private FileParser fileParser = new FileParser();
	private String classpath;

	public ProjectCompiler(String projectPath, String projectName) {
		this.projectPath = projectPath;
		this.projectName = projectName;
		this.targetPath = this.projectPath + "bin";
		this.sourceFiles = this.fileParser.getFiles(new File(projectPath),
				new ArrayList<File>(), "java");
		// init classpath
		this.classpath = "";
		List<File> tmpfiles = this.fileParser.getFiles(new File(projectPath),
				new ArrayList<File>(), "jar");
		for (File item : tmpfiles) {
			this.classpath += item.getPath() + ";";
		}
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public void compile(boolean outputJar) throws Exception {
		String fullTargetPath = this.targetPath;// +this.projectName+File.separator;
		File targetDir = new File(fullTargetPath);
		if (!targetDir.exists()) {
			boolean iii = targetDir.mkdirs();
			System.out.println("Making Dir " + fullTargetPath + ":::" + iii);
		}
		// System.setProperty("java.home",
		// "C:\\Program Files\\Java\\jdk1.6.0_24\\");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// 建立DiagnosticCollector对象
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);
		// 建立用于保存被编译文件名的对象
		// 每个文件被保存在一个从JavaFileObject继承的类中
		Iterable<String> options = Arrays.asList("-d", fullTargetPath,
				"-classpath", "D:\\Java EE 5\\javaee.jar;" + this.classpath);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjectsFromStrings(this
						.prepareSourceFilePath(sourceFiles));// Arrays.asList("E:\\开发项目\\Java\\CompileTest\\src\\com\\trnnn\\compile\\Main.java"));
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				diagnostics, options, null, compilationUnits);
		// 编译源程序
		boolean success = task.call();
		fileManager.close();
		System.out.println((success) ? "编译成功" : "编译失败");
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
				.getDiagnostics()) {

			System.out.printf("Code: %s%n" + "Kind: %s%n" + "Position: %s%n"
					+ "Start Position: %s%n" + "End Position: %s%n"
					+ "Source: %s%n" + "Message: %s%n", diagnostic.getCode(),
					diagnostic.getKind(), diagnostic.getPosition(),
					diagnostic.getStartPosition(), diagnostic.getEndPosition(),
					diagnostic.getSource(), diagnostic.getMessage(null));
		}

	}

	public static void main(String[] args) throws Exception {
		ProjectCompiler compiler = new ProjectCompiler("D:\\webtest\\",
				"b2cccc");
		compiler.compile(false);
		// System.out.println(System.getProperty("java.class.path"));
	}

	private List<String> prepareSourceFilePath(List<File> sourceFiles) {
		List<String> sourceFilePaths = new ArrayList<String>();
		for (File item : sourceFiles) {
			sourceFilePaths.add(item.getPath());
		}
		return sourceFilePaths;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}
}
