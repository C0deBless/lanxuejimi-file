package com.trnnn.osgi.admin.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.ServiceRegistration;

import com.trnnn.osgi.webservice.WebServiceContext;
import com.trnnn.osgi.webservice.WebServiceFactory;

public class RemoveEndpoint extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String serviceId=request.getParameter("serviceId");
		ServiceRegistration service=WebServiceContext.getServiceRegistration(serviceId);
		if(service!=null){
			WebServiceFactory.unpublishService(service);
			out.print(true);
		}
		else{
			out.print(false);
		}
		out.flush();
		out.close();
	}

}
