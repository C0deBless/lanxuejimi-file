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
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/*" })
public class PermissionFilter implements Filter {

	static Logger logger = LoggerFactory.getLogger(PermissionFilter.class);

	public PermissionFilter() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		logger.info("PermissionFilter.doFilter, contextPath:{} url:{}",
				((HttpServletRequest) request).getContextPath(),
				((HttpServletRequest) request).getRequestURI());
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
