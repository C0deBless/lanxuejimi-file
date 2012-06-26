package com.tech.bll;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

public class ConfigurationService {
	@SuppressWarnings("deprecation")
	public static String filePath=ServletActionContext.getRequest().getRealPath("/")+"WEB-INF\\classes\\site.propertities";
	public static String getConfig(String key){
		Properties props = new Properties();
        try {
         InputStream in = new BufferedInputStream (new FileInputStream(filePath));
         props.load(in);
         String value = props.getProperty (key);
            return value;
        } catch (Exception e) {
         e.printStackTrace();
         return null;
        }
	}
	public static String getSmtpHost(){
		return getConfig("SmtpHost");
	}
	public static String getSitePath(){
		return getConfig("SitePath");
	}
	public static String getSmtpName(){
		return getConfig("SmtpName");
	}
	public static String getSmtpPwd(){
		return getConfig("SmtpPwd");
	}
}
