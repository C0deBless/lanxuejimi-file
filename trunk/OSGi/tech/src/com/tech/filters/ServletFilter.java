package com.tech.filters;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
public class ServletFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest req, ServletResponse resp,
				FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String target = request.getRequestURI();
		
		//target = target.lastIndexOf("?") > 0 ? target.substring(target
		//.lastIndexOf("/") + 1, target.lastIndexOf("?")- target.lastIndexOf("/")) : target.substring(target.lastIndexOf("/") + 1);
		//System.out.println(target);
		ArrayList<String> temparray=new ArrayList<String>();
		temparray.addAll(Arrays.asList(target.split("/")));
		
		//if (this.includes.contains(target)) {
		if (temparray.contains("servlet")) {
			target="/"+target.substring(target.lastIndexOf("servlet"));
			RequestDispatcher rdsp = request.getRequestDispatcher(target);
			//System.out.println("go..............." + rdsp);
			req.setCharacterEncoding("utf-8");
			rdsp.forward(req, resp);
		} else
			chain.doFilter(req, resp);
	}
	
	public void init(FilterConfig config) throws ServletException {
		/*this.includes.addAll(Arrays.asList(config.getInitParameter(
		"includeServlets").split(",")));*/
	}
}

