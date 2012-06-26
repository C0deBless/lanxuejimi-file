package com.trnnn.cxf.webservice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

@WebService(endpointInterface="com.trnnn.cxf.webservice.HelloWorldService",serviceName="HelloWorld")
public class HelloWorldServiceImpl implements HelloWorldService {
	
	Map<Integer, User> users = new LinkedHashMap<Integer, User>();
	
	@Override
	public String sayHi(String text) {
		return "Hello "+text;
	}

	@Override
	public String sayHiToUser(User user) {
		users.put(users.size()+1, user);
		return "Hello "+user.getName();
	}

	@Override
	public String[] sayHiToUserList(List<User> userList) {
		String[] result = new String[userList.size()];
        int i=0;
        for(User u:userList){
             result[i] = "Hello " + u.getName();
             i++;
        }
        return result;
	}
	
}
