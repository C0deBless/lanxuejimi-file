package com.trnnn.osgi.admin.bundle.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.trnnn.osgi.admin.BundleFactory;
import com.trnnn.osgi.admin.bundle.model.BundleInfoModel;

public class BundleInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = Long.parseLong(request.getParameter("bundleId"));
		BundleInfoModel model = BundleFactory.getBundleInfo(id);
		String strJson;
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			strJson = JSONMapper.toJSON(model).render(true);
			out.print(strJson);
			out.flush();
			out.close();
			
		} catch (MapperException e) {
			e.printStackTrace();
		}

	}

}
