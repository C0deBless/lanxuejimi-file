<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.osgi.framework.*"%>
<%@ page import="com.trnnn.osgi.admin.*" %>
<%
	response.setCharacterEncoding("utf-8");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Bundle[] bundles = (Bundle[]) request.getAttribute("bundles");
	List<String> systemBundles = Arrays.asList(
			"com.springsource.org.aopalliance", "commons_fileupload",
			"Compiler", "CompressUtil", "CXFLib", "IOUtil", "javax.servlet",
			"javax.servlet.jsp", "json_plugin", "org.apache.commons.el",
			"org.apache.commons.logging", "org.apache.jasper",
			"org.apache.log4j", "org.eclipse.osgi", "org.eclipse.osgi.services",
			"org.springframework.aop",
			"org.springframework.beans", "org.springframework.context",
			"org.springframework.core","org.osgi.compendium",
			"org.springframework.osgi.catalina.osgi",
			"org.springframework.osgi.catalina.start.osgi",
			"org.springframework.osgi.core",
			"org.springframework.osgi.extender", "org.springframework.osgi.io",
			"org.springframework.osgi.web",
			"org.springframework.osgi.web.extender", "org.springframework.web",
			"org.springframework.web.servlet", "OSGIWebAdmin","cxf-dosgi-ri-singlebundle-distribution",
			"WebServiceFactory");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>OSGI Web Admin</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="script/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="script/popup.js"></script>
		<script type="text/javascript" src="script/bundle.js"></script>
		<script type="text/javascript" src="script/jquery.form.js"></script>
		<link rel="stylesheet" href="style/site.css" type="text/css"></link>
		<link rel="stylesheet" href="style/index.css" type="text/css"></link>
		<script type="text/javascript">
			window.onload=function(){
				$("#bundles tbody tr:even").css("background-color","#EEE");
				$("#bundles tbody tr").each(function(){
					var old;
					$(this).mouseover(function(){
						old=$(this).css("background-color");
						$(this).css("background-color","#DDD");
					});
					$(this).mouseout(function(){
						$(this).css("background-color",old);
					});
				});
			}
			function showFileUpload(obj){
				obj.disabled=true;
				var form=document.createElement("form");
				form.id="fileupload";
				form.action="bundle/install";
				form.method="post";
				form.enctype="multipart/form-data";
				var file=document.createElement("input");
				file.type="file";
				file.id="file1";
				file.name="file1";
				var button=document.createElement("input");
				button.type="button";
				button.value="上传";
				button.onclick=installBundle;
				var cancel=document.createElement("input");
				cancel.type="button";
				cancel.value="取消";
				cancel.onclick=function(){
					$(form).remove();
					obj.disabled=false;
				}
				form.appendChild(file);
				form.appendChild(button);
				form.appendChild(cancel);
				$(form).hide();
				$(obj).after(form);
				$(form).fadeIn(300);
				
			}
			function showUpdateBundle(obj,bundleId){
				obj.disabled=true;
				
				var div=document.createElement("div");
				var updateButton=document.createElement("input");
				updateButton.type="button";
				updateButton.value="Update Now";
				updateButton.onclick=function(){
					$.ajax({
						url:"bundle/update",
						data:{bundleId:bundleId},
						type:"post",
						success:function(msg){
							if(msg=="true"){
								alert("Update successfully!!!");
								window.location.reload();
							}
							else{
								alert("Update failed!!!");
							}
						}
					});
				}
				var uploadFile=document.createElement("input");
				uploadFile.type="button";
				uploadFile.value="Uplaod New Bundle File";
				uploadFile.onclick=function(){
					upload();
				}
				var cancel=document.createElement("input");
				cancel.type="button";
				cancel.value="取消";
				cancel.onclick=function(){
					$(div).remove();
					obj.disabled=false;
				}
				$(div).append(updateButton);
				$(div).append(uploadFile);
				$(div).append(cancel);
				
				$(obj).parent().append(div);
				
				function upload(){
				
					var form=document.createElement("form");
					form.id="fileupload";
					form.action="bundle/update?bundleId="+bundleId;
					form.method="post";
					form.enctype="multipart/form-data";
					var file=document.createElement("input");
					file.type="file";
					file.id="file1";
					file.name="file1";
					var button=document.createElement("input");
					button.type="button";
					button.value="上传";
					button.onclick=updateBundle;
					
					
					form.appendChild(file);
					form.appendChild(button);
					
					
					$(form).hide();
					$(div).append(form);
					$(form).fadeIn(300);
					function updateBundle() {
						$(form).ajaxSubmit({
							success:function(msg){
								if(msg=="true"){
									window.alert("Update successfully");
									window.location.reload();
								}
								else{
									window.alert("Update failed");
									$(form).remove();
									obj.disabled=false;
								}
							}
						});
					}
				}
				
			}
		</script>
	</head>

	<body>
		<div class="head">
			<span>基于OSGI的动态组件管理平台</span>&nbsp;&nbsp;&nbsp;<span><a href="webservice/index" target="_blank" style="font-size:15px;color:#FFF;">Web服务管理页</a></span>
		</div>
		
		<div id="uploaddiv">
			<%--<form id="fileupload" action="bundle/install" method="post" enctype="multipart/form-data">
				<input type="file" name="file1" id="file1"/><input type="button" value="Upload" onclick="installBundle();"/><span id="message"></span>
			</form>--%>
			<div style="width:auto;float:right;">
				<input type="button" id="uploadbutton" value="安装新的插件" onmouseover="$(this).css('background-color','#900');" onmouseout="$(this).css('background-color','#222');" onclick="showFileUpload(this);"/>
			</div>
		</div>
		<div id="bundlediv">
		<table class="table" id="bundles">
			<thead>
				<tr>
					<td>
						Bundle ID
					</td>
					<td>
						Bundle Name
					</td>
					<td>
						Bundle Status
					</td>
					<td>
						Bundle Version
					</td>
					<td>
						Operations
					</td>
				</tr>
			</thead>
			<tbody>
				<%
					for (Bundle bundle : bundles) {
						//out.print(bundle.getBundleId() + bundle.getLocation()
						//+ bundle.getVersion() + "<br/>");
						long bundleId = bundle.getBundleId();
						String strState=BundleFactory.getBundleState(bundleId);
						int iState=bundle.getState();
				%>
				<tr>
					<td><span><%=bundleId%></span></td>
					<td><span class="bundlename"><%=bundle.getSymbolicName()%></span>&nbsp;&nbsp;&nbsp;<img width="14px" src="image/find.png" onclick="loadBundleInfo(this,<%=bundleId%>);"/></td>
					<td><span><%=strState%></span></td>
					<td><span><%=bundle.getVersion()%></span></td>
					<td>
						<%
							if (iState == Bundle.UNINSTALLED) {
						%><a href="javascript:void(0);"
							onclick="installBundle(<%=bundleId%>);">Install</a>
						<%
							} else {
								if(iState==Bundle.ACTIVE){
									%><a href="javascript:void(0);" onclick="stopBundle(<%=bundleId%>);">Stop</a>|<%
								}
								else if(iState==Bundle.RESOLVED||iState==Bundle.INSTALLED){
									%><a href="javascript:void(0);" onclick="startBundle(<%=bundleId%>);">Start</a>|<%
								}
									%><a href="javascript:void(0);" onclick="showUpdateBundle(this,<%=bundleId%>);">Update</a>
									<%
									if(!systemBundles.contains(bundle.getSymbolicName())){
										%>|<a href="javascript:void(0);" onclick="uninstallBundle(<%=bundleId%>);">Uninstall</a><%
									}
								}
						%>
						
						
						
					</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		</div>
	</body>
</html>
