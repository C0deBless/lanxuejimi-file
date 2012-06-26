package com.tech.bll;

import java.util.Comparator;
import java.util.Date;

import com.tech.pojo.EssayReply;

@SuppressWarnings("unchecked")
public class SimpleSorter implements Comparator {

	@SuppressWarnings("deprecation")
	public int compare(Object arg0, Object arg1) {
		 if(arg0 == null){ 
	            return arg1 == null ? 0 : 1; 
	        } 
	        if(arg1 == null){ 
	            return -1; 
	        } 
	            
	        if(arg0 instanceof EssayReply&&arg1 instanceof EssayReply){ 
	        	Date date0=new Date(Date.parse(((EssayReply)arg0).getEssayReplyTime()));
	        	Date date1=new Date(Date.parse(((EssayReply)arg1).getEssayReplyTime()));
	        	if(date0.compareTo(date1)==0){
	        		return 1;
	        	}
	            return date0.compareTo(date1);
	        } 
	            
	        return 1; 
	}

}
