<%@ page language="java"
	import="java.util.*,com.tech.bll.models.*,com.tech.pojo.*,com.tech.bll.*"
	pageEncoding="utf-8"%>
<%
	Object obj_loginId = request.getSession().getAttribute(
			SessionModel.UserId.toString());
	Essay essay = (Essay) request.getAttribute("essay");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//重定向到错误页
	if (essay == null) {

	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%
	//if(session.getAttribute(SessionModel.UserName.toString())==null){
	//response.sendRedirect(request.getContextPath()+"/signin.jsp?returnURL="+request.getRequestURL());
	//}
%>
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%
				out.println(session.getAttribute(SessionModel.UserName.toString()));
			%>的博客</title>
		<!-- CSS -->
		<link href="style/transdmin.css" rel="stylesheet" type="text/css"
			media="screen" />
		<link href="style/blogindex.css" rel="stylesheet" type="text/css"
			media="screen" />
		<script type="text/javascript" src="js/jquery-1.5.1.min.js" ></script>
		<script type="text/javascript" src="js/xheditor_plugins/ubb.min.js"  ></script>
		<script type="text/javascript" src="js/xheditor-zh-cn.min.js"  ></script>
		<script type="text/javascript" src="js/popup.js" ></script>
		<script type="text/javascript" src="js/util.js"  ></script>
		<script language="javascript" type="text/javascript">
			window.onload = function() {
				$.getScript("js/login.js", function() {
					initpage();
				});
			}
			//dom：要操作的dom对象
			//essayId：要回复的文章Id
			//toAccount：要回复的用户
			//toReply：要回复的回复
			function showAddReply() {
				
			}
		</script>
	</head>
	<body>
		<div id="wrapper">
			<!-- h1 tag stays for the logo, you can use the a tag for linking the index page -->
			<h1 class="header">
				<a href="#"><span>TechCommunity</span> </a>
			</h1>
			<!-- You can name the links with lowercase, they will be transformed to 
				uppercase by CSS, we prefered to name them with uppercase to have the same effect with disabled 
				stylesheet -->
			<div
				style="margin: 8px 7px 20px 7px; background-color: #EEE; padding: 6px; height: auto; margin-bottom: 20px;">
				<ul id="mainNav">
					<li>
						<a href="#" class="active">首页</a>
					</li>
					<!-- Use the "active" class for the active menu item  -->
					<li>
						<a href="#">个人主页</a>
					</li>
					<li>
						<a href="#">问答区</a>
					</li>
					<li>
						<a href="#">讨论区</a>
					</li>
					<li class="logout" id="register">
						<a href="javascript:void(0);" onclick="javascript:void(0);">注册</a>
					</li>
					<li class="logout" id="signin">
						<a href="javascript:void(0);" onclick="javascript:showLogon();">登陆</a>
					</li>
				</ul>
			</div>
			<!-- // #end mainNav -->
			<div id="containerHolder">
				<div id="container">
					<div id="left">
						<div id="signinfo"
							style="height: 170px; border-bottom: 1px solid #DDD; display: block; width: 179px;">
						</div>
						<div id="sidebar">
							<%
								if (obj_loginId != null) {
							%><ul class="sideNav">
								<li>
									<a href="javascript:void(0);">个人资料</a>
								</li>
								<li>
									<a href="javascript:void(0);">我的好友</a>
								</li>
								<li>
									<a href="javascript:void(0);" onclick="javascript:essaylist();">我的文章</a>
								</li>
								<li>
									<a href="javascript:void(0);">我的问答</a>
								</li>
								<li>
									<a href="javascript:void(0);">我参与的讨论</a>
								</li>
							</ul>
							<%
								}
							%>


							<!-- // .sideNav -->
						</div>

					</div>
					<div id="topnav">
						<ul id="navList">
							<li>
								<a href="javascript:void(0);" onclick="javascript:addessay();">新随笔</a>
							</li>
							<li>
								<a href="javascript:void(0);"> 联系</a>
							</li>
							<li>
								<a href="javascript:void(0);"> 管理</a>
							</li>
							<li>
								<a href="javascript:void(0);"> 订阅</a>
							</li>
						</ul>
					</div>
					<div id="main">
						<div class="essay">
							<div class="essay_title">
								<%=essay.getEssayTitle()%>
							</div>
							<div class="essay_text">
								<%=essay.getEssayText()%>
							</div>
							<div class="essay_summary">
								<span>类别：<%=essay.getEssayType()%></span>
								<span>发表时间：<%=DateTimeUtil.getFormatedTime(essay.getEssayPublishTime())%></span>
								<span>最后修改时间：<%=DateTimeUtil.getFormatedTime(essay.getEssayLastModifiedDate())%></span>
								<span><a href="javascript:void(0);">支持</a>-<%=essay.getEssayTop()%></span>
								<span><a href="javascript:void(0);">反对</a>-<%=essay.getEssayStep()%></span>
								<span><a href="javascript:void(0);">评论</a>-<%=essay.getEssayReplies().size()%></span>
							</div>
							<%
								if (essay.getEssayReplies().size() > 0) {
							%><div class="essay_reply">
								<%
									Set<EssayReply> set = essay.getEssayReplies();
										int i = 1;
										Iterator<EssayReply> replies = set.iterator();

										while (replies.hasNext()) {
											EssayReply reply = replies.next();
								%>
								<div class="essay_reply_item">
									<%--<div class="replyitem_head"></div>
											    --%>
									<div class="replyitem_avatar">

										<img src="files/avatar/<%=reply.getAccount().getAccountAvatarPath()%>"
											width="40px" height="40px" />
									</div>
									<div class="replyitem_text">
										<div>
											<%=reply.getAccountByEssayReplyAuthorId().getAccountNickName()%>
											&nbsp;&nbsp;&nbsp;
											<%=DateTimeUtil.getFormatedTime(reply.getEssayReplyTime())%>
										</div>
										<div><%if(reply.getAccountByEssayReplyToAccountId()!=null){
											out.print("回复"+reply.getAccountByEssayReplyToAccountId().getAccountNickName()+"：");
										}%><%=reply.getEssayReplyText()%>&nbsp;&nbsp;&nbsp;
										<a href="javascript:void(0);"
											onclick="showAddReply(this,'<%=essay.getEssayId()%>','<%=reply.getAccount().getAccountId()%>','<%=reply.getEssayReplyId() %>');">回复</a></div>
									</div>
								</div>

								<%
												i++;
										}
								%>
							</div>
							<%
								}
							%>

							<div class="essay_addreply">
								<textarea id="replyText" rows="5" cols="60"></textarea>
								<br />
								<input type="button" value="提交回复"
									onclick="javascript:addEssayReply(<%=essay.getEssayId()%>,document.getElementById('replyText').value,'','');"
									class="button" />
							</div>
						</div>
					</div>
				</div>
				<!-- // #container -->
			</div>
			<!-- // #containerHolder -->
			<div id="footer">
				Feel free to use and customize it.
			</div>
		</div>
		<!-- // #wrapper -->
	</body>
</html>
