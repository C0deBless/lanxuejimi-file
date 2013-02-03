<div>
<div>用户名：<input type="text" id="userName"/></div>
<div>密码：<input type="password" id="password"/></div>
<div><input type="checkbox" id="remember"/>记住我</div>
<div><input type="button" value="登陆" id="password" onclick="processLogin();"/></div>
<script language="javascript">
	function processLogin(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		var remember=document.getElementById("remember").value;
		login(userName,password,remember);
	}
</script>
</div>