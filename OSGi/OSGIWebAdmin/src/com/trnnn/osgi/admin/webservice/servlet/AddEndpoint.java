package com.trnnn.osgi.admin.webservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;

import com.trnnn.osgi.admin.properties.PropertiesFactory;
import com.trnnn.osgi.admin.start.Activator;
import com.trnnn.osgi.webservice.WebServiceFactory;

public class AddEndpoint extends HttpServlet {

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
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path=request.getParameter("path");
		String implementor=request.getParameter("implementor");
		String bundleID=request.getParameter("bundleID");
		PropertiesFactory properties=new PropertiesFactory("admin.properties");
		String port=properties.getProperty("services.port");
		String root=properties.getProperty("services.root");
		String url="http://localhost:"+port+"/"+root+"/"+path;
		
		Bundle bundle=Activator.bundleContext.getBundle(Long.parseLong(bundleID));

		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		//WebServiceContext.addEndpoint(name,port,root,path,implementor,bundle);
		Class<? extends Object> implementorClass;
		try {
			implementorClass = bundle.loadClass(implementor);
			WebService webServiceAnnot=implementorClass.getAnnotation(WebService.class);
			Class<? extends Object> interfaceClass=bundle.loadClass(webServiceAnnot.endpointInterface());
			WebServiceFactory.publishService(url,interfaceClass, implementorClass);
			out.print(true);
		} catch (ClassNotFoundException e) {
			out.print(false);
			e.printStackTrace();
		}
		catch(Exception e){
			out.print(false);
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
	}

}
