package tech.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech.renderer.StaticResources;

public class StaticResourceFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (StaticResources.isStaticPath(request.getServletContext()
				.getContextPath(), ((HttpServletRequest) request)
				.getRequestURI())) {
			StaticResources.processStaticResourceRequeset(
					(HttpServletRequest) request,
					(HttpServletResponse) response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
