package com.tech.servlets;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.tech.bll.AccountService;
import com.tech.pojo.Essay;

public class test {
	public static void main(String[] args){
		SessionFactory factory=new Configuration().configure().buildSessionFactory();
		Session session=factory.openSession();
		for(int i=1;i<=120;i++){
			Essay essay=new Essay();
			essay.setAccount(new AccountService().getAccountModelByName("trnnn@qq.com"));
			essay.setEssayBrowse(0);
			essay.setEssayEnableReply("true");
			essay.setEssayIsEssence("false");
			essay.setEssayIsRecommend("false");
			essay.setEssayPublishTime(new Date().toString());
			essay.setEssayStep(0);
			essay.setEssayText("<span style='font-family: Arial; white-space: normal; font-size: 14px; line-height: 25px; '><h2 style='margin-top: 0px; font-size: 14px; margin-right: 0px; margin-left: 0px; margin-bottom: 4px; '><a id='ctl01_TitleUrl' href='http://www.cnblogs.com/zhoujg/archive/2011/01/17/1937647.html' style='color: rgb(34, 51, 85); text-decoration: none; '>介绍一个基于ASP.NET MVC的框架Catharsis----------"+i+"</a></h2></span><div class='postbody'><p style='margin-top: 5px; margin-right: auto; margin-left: auto; margin-bottom: 5px; text-indent: 0px; '><span style='font-family: 宋体; '><span style='font-size: 12pt; '>&nbsp;&nbsp;&nbsp;&nbsp;Catharsis是一个基于ASP.Net MVC的一个开源框架，之前在codeproject上看到的。在<a href='http://www.cnblogs.com/zhoujg/archive/2011/01/01/1923688.html' style='color: rgb(29, 88, 209); text-decoration: none; '></a></span><strong>我的</strong></span><strong>2011生活看板</strong>中也说到今年准备给<a href='http://openexpressapp.codeplex.com/' style='color: rgb(29, 88, 209); text-decoration: none; '><strong>OpenExpressApp</strong></a>增加B/S支持，所以最近花了几天时间看了一下Catharsis，以下简单介绍一下。</p><h3 style='font-size: 14px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(170, 170, 170); font-family: Arial; padding-left: 10px; margin-left: 10px; margin-right: 10px; '>Catharsis介绍</h3><p style='margin-top: 5px; margin-right: auto; margin-left: auto; margin-bottom: 5px; text-indent: 0px; '>Web-application framework, multi-tier (5-tier) Architecture (SQL or XML is persitence, DAO as the only access point to get/store data -NHibernate 3.0, Facades as the only Business rule validation place, MVC pattern (Views on the RAZOR only) and UI (HTML, CSS and JQuery).</p><p style='margin-top: 5px; margin-right: auto; margin-left: auto; margin-bottom: 5px; text-indent: 0px; '><strong>ASP.NET MVC 3.0 RC - Razor,<br />The Web-application framework gathering the best practices and design patterns.&nbsp;<br />Strongly OOP, multi-tier Architecture, NHibernate 3.0, XML, ADO.NET<br />VS 2010, .NET 4.0 (Contracts, Covariance, Named and Default parameters)</strong></p></div>");
			
			essay.setEssayTop(0);
			String category;
			int j=i%4;
			switch(j){
			case 0:category="net_csharp";break;
			case 1:category="net_vb";break;
			case 2:category="net_aspnet";break;
			case 3:category="net_aspnetmvc";break;
			default:category="";
			}
			essay.setEssayTitle("this is title---- "+i+" for"+category);
			essay.setEssayType(category);
			session.save(essay);
			Transaction trans=session.beginTransaction();
			trans.commit();
		}
		System.out.println("success");
	}
}
