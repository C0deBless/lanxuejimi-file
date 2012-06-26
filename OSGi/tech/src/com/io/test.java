package com.io;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class test {
	public static void main(String[] args){
		Date date=new Date();
		String strdate=date.toString();
		System.out.println(strdate);
		long ldate=date.getTime();
		System.out.println(ldate);
		//String df=DateFormat.getDateInstance(DateFormat.DATE_FIELD,Locale.US).format(date);
		//System.out.println(df);
		SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.US);
		System.out.println(sdf.format(date));
	}
}
