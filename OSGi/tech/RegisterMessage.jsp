<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>"/>
    
    <title>注册成功</title>
    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	
	<link rel="stylesheet" type="text/css" href="../style/transdmin.css"/>
	
	<style type="text/css">
	.container1{
		width:550px;
		height:350px;
		background-color:#EEE;
		margin:80px auto 0 auto;
		padding:6px;
	}
	.container{
		width:548px;
		height:348px;
		background-color:white;
		border:1px solid #DDD;
		text-align:center;
		padding-top:50px;
	}
	</style>
  </head>
  
  <body>
    <div class="container1">
     	<div class="container">
	     	<span style="font-size:30px;">注册成功，请到邮箱查收您的激活邮件。</span><br/>
	     	<span style=""><a href="javascript:void(0);">立即查看邮箱</a></span>
     	</div>
     </div>
  </body>
</html>
