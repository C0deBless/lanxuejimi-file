<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>激活结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()+"/style/transdmin.css"%>">
	
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
		margin:0px;
	}
	</style>
  </head>
  
  <body>
    <div class="container1">
     	<div class="container"><%if(request.getSession().getAttribute("activitestate")!=null&&request.getSession().getAttribute("activitestate").equals("succeed")){
     		%><div style="font-size:30px;margin-top:70px;">激活成功，2秒钟后自动跳转到登陆页</div>
     		<script language="javascript" type="text/javascript">
     		window.onload=function(){
     			window.setTimeout(function(){
     				window.location.href="<%=request.getContextPath()+"/signin.jsp"%>";
     			},2000)
     		}
     		</script>
     		<br/><% 
     	}else{
     		%><div style="font-size:30px;margin-top:70px;color:red;">激活链接已经失效！！！！！</div><br/><%
     	} %>
     	</div>
     </div>
  </body>
</html>
