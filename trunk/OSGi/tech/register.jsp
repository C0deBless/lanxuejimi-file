<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>"/>
    <title>注册新用户</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
    <script language="javascript" type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
    <script language="javascript" type="text/javascript" src="js/FormValidator.js"></script>
	<link rel="stylesheet" href="style/transdmin.css"/>
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
	}
	.disbale1{
		background-color:Gray;
	}
	#smbtn{
		width:100px;
	}
	.message{float:left;width:120px;text-align:left;margin-left:5px;}
	.inputdiv{float:left;width:180px;text-align:left;}
	.titlediv{width:170px;text-align:right;float:left;}
	</style>
	<script language="javascript" type="text/javascript">
		function register(){
			
		}
		function validateName(){
			return $("#username").validate({type:'required'},{errorMessage:'请填写姓名'},'nameMessage');
		}
		function validateLoginName(){
			
		}
		function validateNickName(){
			
		}
		function validateTechType(){
			var techtype=getTechType("techtype");
			if(techtype==""){
				return false;
			}
			else{
				return true;
			}
		}
		function validatePwd(){
			
		}
		function validateConfirmPwd(){
			
		}
		function getTechType(name){
			var radios=document.getElementsByName(name);
			var length=radios.length;
			var value="";
			for(var i=0;i<length;i++){
				if(radios[i].checked){
					value=radios[i].value;
				}
			}
			return value;
		}
	</script>
  </head>
  <body style="text-align:center;font-size:15px;">
  <form action="account/addTempUser" id="form1" onsubmit="javascript:register();" method="post">
	     <div class="container1">
	     	<div class="container">
		     	<div style="height:35px;margin-top:50px;">
		     		<div class="titlediv">登录邮箱：&nbsp;</div>
		     		<div class="inputdiv"><input type="text" id="username" name="username" value="" onchange="javascript:validateName();"/></div>
		     		<div class="message" id="nameMessage"></div>
		     	</div>
		     	<div style="height:35px;">
		     		<div class="titlediv">登陆名：&nbsp;</div>
		     		<div class="inputdiv"><input type="text" id="loginname" name="loginname" value=""/></div>
		     		<div class="message"></div>
		     	</div>
		     	<div style="height:35px;">
		     		<div class="titlediv">昵称：&nbsp;</div>
		     		<div class="inputdiv"><input type="text" id="nickname" name="nickname" value=""/></div>
		     		<div class="message"></div>
		     	</div>
		     	<div style="height:50px;">
		     		<div class="titlediv">技术类型：&nbsp;</div>
		     		<div style="float:left;width:200px;text-align:left;font-size:12px;">
			     		<input type="radio" name="techtype" value="net"/>.NET
			     		<input type="radio" name="techtype" value="java"/>Java
			     		<input type="radio" name="techtype" value="flash"/>Flash<br/>
			     		<input type="radio" name="techtype" value="php"/>PHP
			     		<input type="radio" name="techtype" value="pattern"/>设计模式
			     		<input type="radio" name="techtype" value="engineering"/>软件工程
		     		</div>
		     		<div class="message"></div>
		     	</div>
		     	<div style="height:35px;">
		     		<div class="titlediv">密码：&nbsp;</div>
		     		<div class="inputdiv"><input type="password" id="password" name="password" value=""/></div>
		     		<div class="message"></div>
		     	</div>
		     	<div style="height:35px;">
		     		<div class="titlediv">确认密码：</div>
		     		<div class="inputdiv"><input type="password" id="confirmpassword" value=""/></div>
		     		<div class="message"></div>
		     	</div>
		     	<div style="height:35px;">
		     		<div style="width:270px;text-align:right;float:left;">
		     			<input id="smbtn" class="button" type="submit" value="马上注册"/>
		     		</div>
		     	</div>
	     	</div>
	     </div>
     </form>
  </body>
</html>
