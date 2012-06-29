package com.trnnn.osgi.admin.bundle.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleException;

import com.trnnn.osgi.admin.BundleFactory;

public class StartBundle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = Long.parseLong(request.getParameter("bundleId"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			BundleFactory.start(id);
			out.print(true);
		} catch (BundleException e) {
			out.print(false);
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
