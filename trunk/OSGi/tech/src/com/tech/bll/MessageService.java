package com.tech.bll;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.mail.SmtpClient;

public class MessageService {
	public static void sendActiviteEmail(String userName,String userNickName,String userLoginName,String userId) 
				throws AddressException, MessagingException, IOException{
		String smtpHost="smtp.qq.com";
		String name="trnnn";
		String pwd="fadingqingfeng";
		String to=userName;
		String from="trnnn@qq.com";
		String subject="这是一封激活邮件";
		StringBuffer sb=new StringBuffer();
		sb.append("您好，");
		sb.append(userNickName);
		sb.append("<br/>");
		sb.append("您的登录名为：");
		sb.append(userLoginName);
		sb.append("<br/>");
		sb.append("<a href=\"");
		sb.append(ConfigurationService.getConfig("SitePath")+"account/activite?key="+userId);
		sb.append("\" target=\"_blank\">");
		sb.append("请点击此链接激活您的账户");
		sb.append("</a>");
		String body=sb.toString();
		SmtpClient client=new SmtpClient();
		client.setSmtpHost(smtpHost);
		client.setAuthentication(name, pwd);
		client.setFrom(from);
		client.setSubject(subject);
		client.setBody(body);
		client.addTo(to);
		client.send();
	}
}
