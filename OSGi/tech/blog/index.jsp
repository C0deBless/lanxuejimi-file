<%@ page language="java" import="java.util.*,com.tech.bll.models.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%//if(session.getAttribute(SessionModel.UserName.toString())==null){
	//response.sendRedirect(request.getContextPath()+"/signin.jsp?returnURL="+request.getRequestURL());
//} %>
<html>
  <head>
  	<base href="<%=basePath%>"/>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%out.println(session.getAttribute(SessionModel.UserName.toString()));%>的博客</title>
	<!-- CSS -->
	<link href="style/transdmin.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="style/blogindex.css" rel="stylesheet" type="text/css" media="screen" />
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/xheditor_plugins/ubb.min.js"></script>
	<script type="text/javascript" src="js/xheditor-zh-cn.min.js"></script>
	<script type="text/javascript" src="js/popup.js"></script>
	<script type="text/javascript" src="js/util.js"></script>
	<script language="javascript" type="text/javascript">
	window.onload=function(){
		//var hs=document.location.hash;
		//var method=hs.toString().substr(1,hs.toString().length-1);
		//if(method.length>0){
		//	eval(method+"();");
		//}
		$.getScript("js/login.js", function() {
					initpage();
				});
	}
	function addessay(){
		$("#main").empty();
		$("#main").load("blog/essayeditor",{mode:"addessay"},function(){
			$('#essayeditor').xheditor({wordDeepClean:false,upImgUrl:"../file/upload",upImgExt:"jpg,jpeg,gif,png,bmp"});
		});
	}
	function essaylist(){
		loadEssayList("account1",1,10,"main");
	}
	function editessay(essayid){
		$("#main").empty();
		$("#main").load("blog/essayeditor",{mode:"editessay",essayid:essayid},function(){
			$('#essayeditor').xheditor({wordDeepClean:false,upImgUrl:"../file/upload",upImgExt:"jpg,jpeg,gif,png,bmp"});
		});
	}
	//---------------删除文章
	function deleteEssayConfirm(essayid){
		var div=document.createElement("div");
		$(div).css("margin-top","20px");
		var span1=document.createElement("span");
		$(span1).html("确定要删除这篇文章？");
		$(span1).css("font-size","16px");
		var span2=document.createElement("span");
		input_yes=document.createElement("input");
		input_yes.type="button";
		input_yes.value="确定";
		input_yes.id="yes";
		$(input_yes).addClass("button");
		
		input_no=document.createElement("input");
		input_no.id="no";
		input_no.type="button";
		$(input_no).addClass("button");
		input_no.value="取消";
		
		$(span2).append(input_yes);
		$(span2).append(input_no);
		div.appendChild(span1);
		div.appendChild(span2);
		ShowDom(div,"删除文章",300,80);
		$("#yes").bind("click",{essayid:essayid},function(events){
			var essayid=events.data.essayid;
			//alert(essayid);
			showProgress("正在删除文章，请稍等...");
			$.ajax({
				url:"blog/deleteessay",	
				data:{essayId:essayid},
				dataType:"json",
				success:function(data){
					CloseDialog();
					if(data.status=="success"){
						showProgress("执行成功",function(){window.setTimeout(function(){hideProgress();},1000)});
						$("#essay_"+events.data.essayid).fadeOut(2000,function(){
							$("#essay_"+events.data.essayid).remove();
						});
					}
					else{
						showProgress("操作失敗",function(){window.setTimeout(function(){hideProgress();},1000)});
					}
				}
			});
		});
		$("#no").bind("click",{},function(events){
			CloseDialog();
		});
	}
	</script>
</head>
<body>
	<div id="wrapper">
    	<!-- h1 tag stays for the logo, you can use the a tag for linking the index page -->
    	<h1 class="header"><a href="#"><span>TechCommunity</span></a></h1>       
        <!-- You can name the links with lowercase, they will be transformed to uppercase by CSS, we prefered to name them with uppercase to have the same effect with disabled stylesheet -->
        <div style="margin:8px 7px 20px 7px;background-color:#EEE;padding:6px;height:auto;margin-bottom:20px;"><ul id="mainNav">
        	<li><a href="#" class="active">首页</a></li> <!-- Use the "active" class for the active menu item  -->
        	<li><a href="#">个人主页</a></li>
        	<li><a href="#">问答区</a></li>
        	<li><a href="#">讨论区</a></li>
        	<li class="logout" id="register"><a href="javascript:void(0);" onclick="javascript:void(0);">注册</a></li>
			<li class="logout" id="signin"><a href="javascript:void(0);" onclick="javascript:showLogon();">登陆</a></li>
        </ul></div>
        <!-- // #end mainNav -->     
        <div id="containerHolder">
			<div id="container">
				<div id="left">
				<div id="signinfo" style="height:170px;border-bottom:1px solid #DDD;display:block;width:179px;">
				</div>
        		<div id="sidebar">
                	<ul class="sideNav">
                    	<li><a href="javascript:void(0);">个人资料</a></li>
                    	<li><a href="javascript:void(0);">我的好友</a></li>
                    	<li><a href="javascript:void(0);" onclick="javascript:essaylist();">我的文章</a></li>
                    	<li><a href="javascript:void(0);">我的问答</a></li>
                    	<li><a href="javascript:void(0);">我参与的讨论</a></li>
                    </ul>
                    <!-- // .sideNav -->
                </div>
                
                </div>
                <div id="topnav">
                	<ul id="navList">
						<li><a href="javascript:void(0);" onclick="javascript:addessay();">新随笔</a></li>
						<li><a href="javascript:void(0);">联系</a></li>
						<li><a href="javascript:void(0);">管理</a></li>
						<li><a href="javascript:void(0);">订阅</a></li>
					</ul>
                </div>
                <div id="main">
                
                </div>
            </div>
            <!-- // #container -->
        </div>	
        <!-- // #containerHolder -->        
        <div id="footer">Feel free to use and customize it. </div>
    </div>
    <!-- // #wrapper -->
</body>
</html>
