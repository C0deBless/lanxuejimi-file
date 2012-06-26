package com.trnnn.cxf.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorldService {
	String sayHi(@WebParam(name="text")String text);
	String sayHiToUser(User user);
	String[] sayHiToUserList(List<User> users);
}
