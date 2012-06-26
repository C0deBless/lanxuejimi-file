package com.trnnn.cxf.webservice;

import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface="com.trnnn.cxf.webservice.HelloWorldService",serviceName="HelloWorld")
public class WebService2 implements HelloWorldService {

	@Override
	public String sayHi(String text) {
		return "message from WebService2 "+text;
	}

	@Override
	public String sayHiToUser(User user) {
		return null;
	}

	@Override
	public String[] sayHiToUserList(List<User> users) {
		return null;
	}

}
