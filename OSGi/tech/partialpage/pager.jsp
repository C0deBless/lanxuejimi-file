<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
	Integer totalPage = Integer.parseInt(request.getParameter("totalPage"));
	String pagerMethod=request.getParameter("pagerMethod");
%>
<style type="text/css">
.pager {
	height:30px;
	width:auto;
	text-align:center;
	font-size:12px;
}
.pageritem{
	width:20px;
	height:20px;
	border:1px solid #363636;
	margin:3px;
	float:left;
}
.pi_current{
	background-color:#363636;
}
.pi_current a{
	color:#FFF;
}
.pageritem a{
	line-height:20px;
}
</style>
<div class="pager">

	<%
		if (pageIndex <= totalPage && pageIndex >= 1) {
	%><div class="pageritem" style="width:40px;">
		<a href="javascript:<%Integer p1=pageIndex-1;out.print(pagerMethod.replace("pageIndex",p1.toString())); %>;">Prev</a>
	</div>
	<%
		if (pageIndex <= 6 && pageIndex >= 1) {
				for (int i = 1; i <= pageIndex; i++) {
	%><div class="pageritem <%if(i==pageIndex){out.print("pi_current");} %>">
	<a href="javascript:<%Integer p2=i;out.print(pagerMethod.replace("pageIndex",p2.toString())); %>;"><%out.print(i); %></a></div>
	<%
		}
				if (totalPage - pageIndex > 6) {
					for (int i = pageIndex+1; i <= pageIndex + 5; i++) {
	%><div class="pageritem"><a href="javascript:<%Integer p2=i;out.print(pagerMethod.replace("pageIndex",p2.toString())); %>;"><%out.print(i); %></a></div>
	<%
		}%>
				<div class="pageritem" style="width:30px;border:0px;">...</div>
				<div class="pageritem"><a href="javascript:<%Integer p3=totalPage;out.print(pagerMethod.replace("pageIndex",p3.toString())); %>;"><%out.print(totalPage); %></a></div>
				<%
				} else {
					for (int i = pageIndex+1; i < totalPage; i++) {
	%><div class="pageritem"><a href="javascript:<%Integer p4=i;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;"><%out.print(i); %></a></div>
	<%
		}
				}
			} else if (pageIndex > 6 && pageIndex <= totalPage) {
				%>
				<div class="pageritem"><a href="javascript:<%out.print(pagerMethod.replace("pageIndex","1")); %>;">1</a></div>
				<div class="pageritem" style="width:30px;border:0px;">...</div>
				<%
				for (int i = pageIndex-5; i <= pageIndex ; i++) {
					%><div class="pageritem <%if(i==pageIndex){out.print("pi_current");} %>" ><a href="javascript:<%Integer p4=i;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;"><%out.print(i); %></a></div>
					<%
				}
				if (totalPage - pageIndex > 6) {
					
					for (int i = pageIndex+1; i <= pageIndex+5 ; i++) {
						%><div class="pageritem"><a href="javascript:<%Integer p4=i;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;"><%out.print(i); %></a></div>
						<%
							}%>
				<div class="pageritem" style="width:30px;border:0px;">...</div>
				<div class="pageritem"><a href="javascript:<%Integer p4=totalPage;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;"><%out.print(totalPage); %></a></div>
				<%
				} else {
					for (int i = pageIndex+1; i <= totalPage; i++) {
	%><div class="pageritem"><a href="javascript:<%Integer p4=i;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;"><%out.print(i); %></a></div>
	<%
		}
				}
			}
	%><div class="pageritem" style="width:40px;">
		<a href="javascript:<%Integer p4=pageIndex+1;out.print(pagerMethod.replace("pageIndex",p4.toString())); %>;">Next</a>
	</div>
	<%
		}
	%>

</div>