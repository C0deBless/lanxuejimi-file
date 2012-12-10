package tech.listener;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "encoding", value = "UTF-8") })
public class PermissionFilter implements Filter {

	static Logger logger = LoggerFactory.getLogger(PermissionFilter.class);

	public PermissionFilter() {
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String contextPath = ((HttpServletRequest) request).getContextPath();
		String requestUrl = ((HttpServletRequest) request).getRequestURI();
		String cuttedUrl = requestUrl.replace(contextPath, "");
		logger.info("PermissionFilter.doFilter, url:{}", cuttedUrl);
		if (cuttedUrl.equals("/")) {
			((HttpServletResponse) response).sendRedirect("index");
		} else
			chain.doFilter(request, response);

	}

	public void destroy() {
	}
}
