<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
	<base href="${contextPath}/"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<title>Main Page</title>
	<link href="style/transdmin.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="style/menu.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="style/layout.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="script/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="script/popup.js"></script>
	<script type="text/javascript" src="script/menu.js"></script>
	<script type="text/javascript" src="script/util.js"></script>
	<script language="javascript" type="text/javascript">
		window.onload = function iniPage() {
			//var hs=document.location.hash;
			//var method=hs.toString().substr(1,hs.toString().length-1);
			//if(method.length>0){
			//	eval(method+"();");
			//}
			$.getScript("script/login.js", function() {
				initpage();
			});
		}
		
		function showRegister(){
			var div = document.createElement("div");
			// div.innerHTML="This is logon page";
			div.id = "registerdiv";
			ShowDom(div, "登录", 350, 160);
			$("#registerdiv").load("component/register.html");
		}
	</script>
</head>
<body>
	<div id="wrapper">
		<!-- h1 tag stays for the logo, you can use the a tag for linking the index page -->
		<h1 class="header">
			<a href="#"><span>TechCommunity</span></a>
		</h1>
		<!-- You can name the links with lowercase, they will be transformed to uppercase by CSS, we prefered to name them with uppercase to have the same effect with disabled stylesheet -->
		<div id="header">
			<ul id="mainNav">
				<li><a href="#" class="active">Home</a></li>
				<!-- Use the "active" class for the active menu item  -->
				<li><a href="#">My Page</a></li>
				<li><a href="#">Questions</a></li>
				<li><a href="#">Disguss</a></li>
				<li class="logout" id="register"><a href="javascript:void(0);" onclick="javascript:showRegister();">Create An Account</a></li>
				<li class="logout" id="signin"><a href="javascript:void(0);"
					onclick="javascript:showLogon();">Signin</a></li>
			</ul>
		</div>
		<!-- // #end mainNav -->
		<div id="containerHolder">
			<div id="container">
				<div id="left">
					<div id="sidebar">
						
					</div>
				</div>
				<h2 style="display: none;">
					<a href="javascript:void(0);">Dashboard</a> &raquo; <a href="#"
						class="active">Print resources</a>
				</h2>

				<div id="main"></div>
				<div id="right"></div>

			</div>
			<!-- // #container -->
		</div>
		<!-- // #containerHolder -->
		<p id="footer">Feel free to use and customize it. Credit is
			appreciated.</p>
	</div>
	<!--
	<div class="menu_group" id="submenu_net" onmouseover="showMenu('net');"
		onmouseout="hideMenu('net');">
		<ul>
			<li><a href="javascript:void(0);">C#基础</a></li>
			<li><a href="javascript:void(0);">Visual basic 基础</a></li>
			<li><a href="javascript:void(0);">Asp.net</a></li>
			<li><a href="javascript:void(0);">Asp.net MVC</a></li>
			<li><a href="javascript:void(0);">WinForm</a></li>
			<li><a href="javascript:void(0);">WCF</a></li>
			<li><a href="javascript:void(0);">WPF</a></li>
			<li><a href="javascript:void(0);">Silverlight</a></li>
			<li><a href="javascript:void(0);">其它</a></li>
		</ul>
	</div>
	<div class="menu_group" id="submenu_java"
		onmouseover="showMenu('java');" onmouseout="hideMenu('java');">
		<ul>
			<li><a href="javascript:void(0);">Java SE</a></li>
			<li><a href="javascript:void(0);">Java EE</a></li>
			<li><a href="javascript:void(0);">JSP</a></li>
			<li><a href="javascript:void(0);">Struts</a></li>
			<li><a href="javascript:void(0);">Spring</a></li>
			<li><a href="javascript:void(0);">Hibernate</a></li>
			<li><a href="javascript:void(0);">Tomcat</a></li>
			<li><a href="javascript:void(0);">其它</a></li>
		</ul>
	</div>
	<div class="menu_group" id="submenu_flash"
		onmouseover="showMenu('flash');" onmouseout="hideMenu('flash');">
		<ul>
			<li><a href="javascript:void(0);">Flash基础</a></li>
			<li><a href="javascript:void(0);">ActionScript 3讨论</a></li>
			<li><a href="javascript:void(0);">Flex 框架</a></li>
			<li><a href="javascript:void(0);">Adobe AIR 专区</a></li>
			<li><a href="javascript:void(0);">游戏编程/3D/算法</a></li>
			<li><a href="javascript:void(0);">Flash 后台技术</a></li>
			<li><a href="javascript:void(0);">其它</a></li>
		</ul>
	</div>
	<div class="menu_group" id="submenu_webfront"
		onmouseover="showMenu('webfront');" onmouseout="hideMenu('webfront');">
		<ul>
			<li><a href="javascript:void(0);">Html/Css</a></li>
			<li><a href="javascript:void(0);">Javascript</a></li>
			<li><a href="javascript:void(0);">jQuery</a></li>
			<li><a href="javascript:void(0);">HTML5</a></li>
		</ul>
	</div>
	<div class="menu_group" id="submenu_mobile"
		onmouseover="showMenu('mobile');" onmouseout="hideMenu('mobile');">
		<ul>
			<li><a href="javascript:void(0);">Android开发</a></li>
			<li><a href="javascript:void(0);">iPhone开发</a></li>
			<li><a href="javascript:void(0);">Windows Mobile</a></li>
			<li><a href="javascript:void(0);">Windows Phone</a></li>
			<li><a href="javascript:void(0);">Symbian开发</a></li>
			<li><a href="javascript:void(0);">其他移动开发</a></li>
		</ul>
	</div>
	<div class="menu_group" id="submenu_database"
		onmouseover="showMenu('database');" onmouseout="hideMenu('database');">
		<ul>
			<li><a href="javascript:void(0);">MS SQL Server</a></li>
			<li><a href="javascript:void(0);">Oracle</a></li>
			<li><a href="javascript:void(0);">MySql</a></li>
			<li><a href="javascript:void(0);">其他数据库</a></li>
		</ul>
	</div>
	-->
</body>
</html>

