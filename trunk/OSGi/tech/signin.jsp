<%@ page language="java" import="java.util.*,com.tech.bll.*,com.tech.bll.models.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>"/>
    <title>TechCommunity</title>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
    <script language="javascript" type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<link href="<%=request.getContextPath()+"/style/transdmin.css"%>" rel="stylesheet" type="text/css" media="screen" />
	<style type="text/css">
	.container1{
		width:500px;
		height:200px;
		background-color:#EEE;
		margin:80px auto 0 auto;
		padding:6px;
	}
	.container{
		width:498px;
		height:198px;
		background-color:white;
		border:1px solid #DDD;
	}
	.disbale1{
		background-color:Gray;
	}
	#smbtn{
		width:100px;
	}
	</style>
  </head>
  
  <body style="text-align:center;font-size:18px;">
  
  <form action="" method="post">
     <div class="container1">
     	<div class="container">
	     	<div style="height:35px;margin-top:70px;">
	     		<div style="width:170px;text-align:right;float:left;">登录邮箱：</div>
	     		<div style="float:left;width:300px;text-align:left;"><input type="text" id="username" name="username" value=""/></div>
	     	</div>
	     	<div style="height:35px;">
	     		<div style="width:170px;text-align:right;float:left;">密码：</div>
	     		<div style="float:left;width:300px;text-align:left;"><input type="password" id="password" name="password" value=""/></div>
	     	</div>
	     	<div style="height:35px;">
	     		<div style="width:270px;text-align:right;float:left;">
	     			<input id="smbtn" class="button" type="submit" value="登录"/>
	     		</div>
	     		<div id="signinmessage" style="font-size:13px;text-align:left;color:#C66653;">
	     		
	     		<%	
	     				if(request.getSession().getAttribute(SessionModel.UserName.toString())!=null){
	     					response.sendRedirect("home");
	     				}
	     				String username=request.getParameter("username");
				  		String password=request.getParameter("password");
				  		if(username!=null&&password!=null){
				  			AccountService as=new AccountService();
				  			SigninStateModel state=as.logon(username,password,"");
				  			if(state.equals(SigninStateModel.succeed)||state.equals(SigninStateModel.hasLogon)){
				  				response.sendRedirect("home");
				  			}
				  			else if(state.equals(SigninStateModel.disabled)){
				  				out.print("您登陆的账户已被禁用");
				  			}
							else if(state.equals(SigninStateModel.failed)){
								out.print("用户名或密码错误，请重试");
				  			}
							else{
								response.sendRedirect("error.jsp");
							}
				  		}
					%>
	     		</div>
	     	</div>
     	</div>
     </div>	
     </form>
  </body>
  <script language="javascript" type="text/javascript"><%--
  function signin(){
		var username=document.getElementById("username").value;
		var userpwd=document.getElementById("password").value;
		var remember=true;//document.getElementById("remember").value;
		$.ajax({
			url:"account/login",
			type:"post",
			dataType:"json",
			data:{userName:username,userPwd:userpwd,remember:remember},
			success:function(data){
				if(data.status=="succeed"){
					window.location.href="home";
				}
			}
		});}--%></script>
</html>
