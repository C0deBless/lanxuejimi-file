<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script language="javascript" type="text/javascript">
	function signin(){
		var username=document.getElementById("userName").value;
		var userpwd=document.getElementById("userPwd").value;
		var remember=document.getElementById("remember").value;
		login(username,userpwd,remember);
	}
	
</script>
<div id="signinmessage" style="width:280px;height:auto;padding-left:15px;">
	 <div style="margin-top:10px;height:30px;">
	 	<div style="width:70px;float:left;text-align:right;line-height:30px;">登录邮箱：</div>
	 	<div style="width:180px;float:left;"><input type="text" class="text" id="userName"></div>
	 </div>
	 <div style="margin-top:10px;height:30px;">
	 	<div style="width:70px;float:left;text-align:right;line-height:30px;">密码：</div>
	 	<div style="width:180px;float:left;"><input type="password" class="text" id="userPwd"/></div>
	 </div>
	 <div style="margin-top:10px;">
	 	<div style="width:95px;float:left;line-height:30px;margin-left:66px;"><input type="checkbox" id="remember"/>保存登陆状态</div>
	 	<div style="width:80px;float:left;text-align:right;"><input type="button" class="button" onclick="signin();" value="马上登陆"/></div>
	 </div>
</div>