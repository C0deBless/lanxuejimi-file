<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.trnnn.osgi.admin.webservice.servlet.model.*,org.osgi.framework.*,com.trnnn.osgi.webservice.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<WebServiceModel> webServices=(List<WebServiceModel>)request.getAttribute("webServices");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Web Service Admin</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="style/site.css" type="text/css"></link>
		<link rel="stylesheet" href="style/webservice.css" type="text/css"></link>
		<script type="text/javascript" src="script/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="script/popup.js"></script>
		<script type="text/javascript" src="script/webservice.js"></script>
		<script language="javascript" type="text/javascript">
		<%
			if(request.getAttribute("services.port")!=null){
				%>
					$(document).data("services.port","<%=request.getAttribute("services.port")%>")
				<%
			}
			else{
				%>
				$(document).data("services.port","5555")
				<%
			}
			
			if(request.getAttribute("services.root")!=null){
				%>
					$(document).data("services.root","<%=request.getAttribute("services.root")%>")
				<%
			}
			else{
				%>
				$(document).data("services.root","services1")
				<%
			}
		%>
		</script>
	</head>

	<body>
		<div>
			<div class="head">
				<span>基于OSGI的WebService 动态发布管理</span>
			</div>
			<div class="center">
				<table class="table" id="webservices">
					<thead>
						<tr>
							<td>Endpoint Class</td>
							<td>Interfaces</td>
							<td>ServiceRegistrations</td>
							<td>Operation</td>
						</tr>
					</thead>
					<tbody>
						<%
						for(WebServiceModel model:webServices){
							%>
								<tr>
									<td><%=model.getImplementor() %></td>
									<td><%=model.getEndpointInterface() %></td>
									<td>
										<%
										if(model.getPublishedServices()!=null&&model.getPublishedServices().size()>0){
											String strEndpoint="";
											for(ServiceRegistration service:model.getPublishedServices()){
												StringBuffer sb=new StringBuffer();
												String serviceId=WebServiceFactory.getServiceId(service);
												String serviceAddress=WebServiceFactory.getServiceAddress(service);
												sb.append("{"+serviceId+"}=");
												sb.append("{"+serviceAddress+"}");
												sb.append("<a href='"+serviceAddress+"?wsdl' target='blank'>WSDL</a>|<a href='javascript:void(0);' onclick='removeService("+serviceId+");'>Remove</a><br/>");
												strEndpoint+=sb.toString();
											}
											out.print(strEndpoint);
										}
										else{
											out.print("<span style='color:red;'>None Endpoint</span>");
										}
										
										%>
									</td>
									<td>
										<a href="javascript:void(0);" onclick="showAddEndpoint(this,'<%=model.getImplementor() %>','<%=model.getBundle().getBundleId() %>');">Add a New Endpoint</a>
									</td>
								</tr>
							<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
