package com.tech.bll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
	public static String getGMTTime(){
		SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.US);
		return sdf.format(new Date());
	}
	public static String getGMTTime(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.US);
		return sdf.format(date);
	}
	public static String getFormatedTime(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/d HH:mm:ss",Locale.US);
		return sdf.format(date);
	}
	@SuppressWarnings("deprecation")
	public static String getFormatedTime(String strDate){
		Date date=new Date(Date.parse(strDate));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/d HH:mm",Locale.US);
		return sdf.format(date);
	}
	/*public static void main(String[] args){
		System.out.println(getFormatedTime("Sun, 23 Jan 2011 13:56:05 +0800"));
	}*/
}
