package com.trnnn.osgi.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.trnnn.osgi.admin.BundleFactory;

public class AdminIndex extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BundleContext bundleContext = BundleFactory.getBundleContext();
		Bundle[] bundles = bundleContext.getBundles();
		request.setAttribute("bundles", bundles);
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		request.getRequestDispatcher("/admin/index.jsp").forward(request,
				response);
	}

}
