package com.trnnn.osgi.admin.webservice.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trnnn.osgi.admin.properties.PropertiesFactory;
import com.trnnn.osgi.admin.webservice.servlet.model.WebServiceModel;
import com.trnnn.osgi.admin.webservice.web.WebServiceManage;
import com.trnnn.osgi.webservice.WebServiceContext;
import com.trnnn.osgi.webservice.WebServiceSpider;

public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		WebServiceSpider.updateAvailbleClass();
		//WebServiceContext.updateEndpoint();
		WebServiceManage webServiceManage=new WebServiceManage();
		List<WebServiceModel> webServices=webServiceManage.getWebServiceList();
		request.setAttribute("webServices", webServices);
		
		PropertiesFactory properties=new PropertiesFactory("admin.properties");
		int port=Integer.parseInt(properties.getProperty("services.port"));
		String root=properties.getProperty("services.root").toString();
		request.setAttribute("services.port", port);
		request.setAttribute("services.root", root);
		
		request.getRequestDispatcher("/webservice.jsp").forward(request, response);
		
		
		
	}
}
