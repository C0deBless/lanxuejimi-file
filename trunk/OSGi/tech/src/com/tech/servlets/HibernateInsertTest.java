package com.tech.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.*;
import org.hibernate.cfg.*;

import com.tech.pojo.*;

public class HibernateInsertTest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public HibernateInsertTest() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,HibernateException {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Account account=new Account();
		account.setAccountId("account3");
		account.setAccountAvatarPath("test");
		account.setAccountCompetence("normal");
		account.setAccountDisable("false");
		account.setAccountFriendList("null");
		account.setAccountGender("male");
		account.setAccountIsActive("true");
		account.setAccountLastLoginTime("xx-xx-xx");
		account.setAccountLoginName("trnnn");
		account.setAccountName("trnnn@qq.com");
		account.setAccountNickName("lqf");
		account.setAccountPassword("lan");
		account.setAccountTechType(".NET");
		account.setAccountVisits(0);
	
		Session session=sessionFactory.openSession();
		Transaction trans=session.beginTransaction();
		session.save(account);
		trans.commit();
		session.close();
		sessionFactory.close();
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("fasdfasd");
		out.println("  <BODY>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
