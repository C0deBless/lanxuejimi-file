<%@ page language="java" import="java.util.*,com.tech.pojo.*" pageEncoding="utf-8"%>
<%--<link type="text/css" rel="stylesheet" href="/Demo1/js/syntaxhighlighter/SyntaxHighlighter.css"/>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shCore.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushXml.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushJScript.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushCss.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushPhp.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushCSharp.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushCpp.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushJava.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushPython.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushRuby.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushVb.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushDelphi.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushSql.js"></script>
<script type="text/javascript" src="/Demo1/js/syntaxhighlighter/shBrushPlain.js"></script>--%>
<%String mode=request.getParameter("mode");
	Essay model=null;
	if(mode.equals("editessay")){
		model=(Essay)request.getSession().getAttribute("essay");
	}
%>
<script type="text/javascript" language="javascript">

//window.onload=function(){dp.SyntaxHighlighter.HighlightAll('code');}
function submitadd(){
	var title=$("#title").attr("value");
	var summary=$("#summary").attr("value");
	var content=$("#essayeditor").xheditor().getSource();
	var essay_categorys=document.getElementsByName("essay_category");
	var essay_category;
	for(var i=0;i<essay_categorys.length;i++){
		if(essay_categorys[i].checked.toString()=="true"){
			essay_category=essay_categorys[i].value;
			break;
		}
	}
	showProgress("正在添加文章...");
	$.ajax({
		url:"blog/addessay",
		type:"post",
		data:{title:title,content:content,category:essay_category,summary:summary},
		//dataType:"json",
		success:function(data){
			showProgress("执行成功",function(){window.setTimeout(function(){hideProgress();},500)});
		}
	});
}
function submitedit(essayid){
	var title=$("#title").attr("value");
	var summary=$("#summary").attr("value");
	var content=$("#essayeditor").xheditor().getSource();
	var essay_categorys=document.getElementsByName("essay_category");
	var essay_category;
	for(var i=0;i<essay_categorys.length;i++){
		if(essay_categorys[i].checked.toString()=="true"){
			essay_category=essay_categorys[i].value;
			break;
		}
	}
	showProgress("正在提交更改...");
	$.ajax({
		url:"blog/editessay",
		type:"post",
		data:{essayid:essayid,title:title,content:content,category:essay_category,summary:summary},
		//dataType:"json",
		success:function(data){
				showProgress("执行成功",function(){window.setTimeout(function(){hideProgress();},500)});
		}
	});
}
</script>
<div style="width:50px;font-size:13px;margin:5px;line-height:20px;">标题：</div>
<div style="width:850px;font-size:15px;margin:5px;"><input type="text" name="title" id="title" value="<%if(model!=null){out.print(model.getEssayTitle());} %>" style="width:800px;height:20px;"/></div>
<div style="width:50px;font-size:13px;margin:5px;line-height:20px;">摘要：</div>
<div style="width:850px;font-size:15px;margin:5px;"><textarea name="summary" id="summary" style="width:800px;height:60px;"><%if(model!=null){out.print(model.getEssaySummary());} %></textarea></div>
<div style="width:50px;font-size:13px;margin:5px;line-height:20px;">内容：</div>
<div style="margin:5px;">
	<textarea id="essayeditor" name="essayeditor" class="xheditor-mfull" rows="30" style="width:850px"><%if(model!=null){out.print(model.getEssayText());} %></textarea>
</div>
<div style="border-bottom:1px solid #DDD;padding:5px;font-size:13px;">文章分类:</div>
<div class="site_category">
.NET技术：
    <input type="radio" id="essay_category_net_csharp" name="essay_category" value="net_csharp">
    <label for="essay_category_net_csharp">C#基础</label>
    <input type="radio" id="essay_category_net_vb" name="essay_category" value="net_vb">
    <label for="essay_category_net_vb">Visual Basic基础</label>
   <input type="radio" id="essay_category_net_aspnet" name="essay_category" value="net_aspnet">
    <label for="essay_category_net_aspnet">ASP.NET</label>
    <input type="radio" id="essay_category_net_aspnetmvc" name="essay_category" value="net_aspnetmvc">
    <label for="essay_category_net_aspnetmvc">ASP.NET MVC</label>
    <input type="radio" id="essay_category_net_winform" name="essay_category" value="net_winform">
    <label for="essay_category_net_winform">WinForm</label>
    <input type="radio" id="essay_category_net_wcf" name="essay_category" value="net_wcf">
    <label for="essay_category_net_wcf">WCF</label>
    <input type="radio" id="essay_category_net_wpf" name="essay_category" value="net_wpf">
    <label for="essay_category_net_wpf">WPF</label>
    <input type="radio" id="essay_category_net_silverlight" name="essay_category" value="net_silverlight">
    <label for="essay_category_net_silverlight">Silverlight</label>
    <input type="radio" id="essay_category_net_others" name="essay_category" value="net_others">
    <label for="essay_category_net_others">其它</label>
<br>
JAVA：
<input type="radio" id="essay_category_java_javase" name="essay_category" value="java_javase">
    <label for="essay_category_java_javase">Java SE</label>
    <input type="radio" id="essay_category_java_javaee" name="essay_category" value="java_javaee">
    <label for="essay_category_java_javaee">Java EE</label>
    <input type="radio" id="essay_category_java_jsp" name="essay_category" value="java_jsp">
    <label for="essay_category_java_jsp">JSP</label>
    <input type="radio" id="essay_category_java_struts" name="essay_category" value="java_struts">
    <label for="essay_category_java_struts">Struts</label>
    <input type="radio" id="essay_category_java_spring" name="essay_category" value="java_spring">
    <label for="essay_category_java_spring">Spring</label>
    <input type="radio" id="essay_category_java_hibernate" name="essay_category" value="java_hibernate">
    <label for="essay_category_java_hibernate">Hibernate</label>
    <input type="radio" id="essay_category_java_tomcat" name="essay_category" value="java_tomcat">
    <label for="essay_category_java_tomcat">Tomcat</label>
    <input type="radio" id="essay_category_java_others" name="essay_category" value="java_others">
    <label for="essay_category_java_others">其它</label>
<br/>
Flash：
<input type="radio" id="essay_category_flash_basic" name="essay_category" value="flash_basic">
    <label for="essay_category_flash_basic">Flash基础</label>
    <input type="radio" id="essay_category_flash_actionscript3" name="essay_category" value="flash_actionscript3">
    <label for="essay_category_flash_actionscript3">ActionScript3</label>
    <input type="radio" id="essay_category_flash_flex" name="essay_category" value="flash_flex">
    <label for="essay_category_flash_flex">Flex</label>
    <input type="radio" id="essay_category_flash_air" name="essay_category" value="flash_air">
    <label for="essay_category_flash_air">Adobe AIR 专区</label>
    <input type="radio" id="essay_category_flash_game3d" name="essay_category" value="flash_game3d">
    <label for="essay_category_flash_game3d">游戏编程/3D/算法</label>
    <input type="radio" id="essay_category_flash_server" name="essay_category" value="flash_server">
    <label for="essay_category_flash_server">Flash后台技术</label>
    <input type="radio" id="essay_category_flash_others" name="essay_category" value="flash_others">
    <label for="essay_category_flash_others">其它</label>
  <br/>  
  Web前端：
  <input type="radio" id="essay_category_web_htmlcss" name="essay_category" value="web_htmlcss">
    <label for="essay_category_web_htmlcss">Html/Css</label>
    <input type="radio" id="essay_category_web_javascript" name="essay_category" value="web_javascript">
    <label for="essay_category_web_javascript">Javascript</label>
    <input type="radio" id="essay_category_web_jquery" name="essay_category" value="web_jquery">
    <label for="essay_category_web_jquery">jQuery</label>
    <input type="radio" id="essay_category_web_html5" name="essay_category" value="web_html5" >
    <label for="essay_category_web_html5">Html5</label>
    <input type="radio" id="essay_category_web_others" name="essay_category" value="web_others">
    <label for="essay_category_web_others">其它</label><br/>
    移动应用：
    <input type="radio" id="essay_category_mobile_android" name="essay_category" value="mobile_android">
    <label for="essay_category_mobile_android">Android开发</label>
    <input type="radio" id="essay_category_mobile_iphone" name="essay_category" value="mobile_iphone">
    <label for="essay_category_mobile_iphone">iPhone开发</label>
    <input type="radio" id="essay_category_mobile_winmobile" name="essay_category" value="mobile_winmobile">
    <label for="essay_category_mobile_winmobile">Windows Mobile</label>
    <input type="radio" id="essay_category_mobile_winphone" name="essay_category" value="mobile_winphone">
    <label for="essay_category_mobile_winphone">Windows Phone</label>
    <input type="radio" id="essay_category_mobile_symbian" name="essay_category" value="mobile_symbian">
    <label for="essay_category_mobile_symbian">Symbian开发</label>
    <input type="radio" id="essay_category_mobile_others" name="essay_category" value="mobile_others">
    <label for="essay_category_mobile_others">其它</label><br/>
    数据库：
    <input type="radio" id="essay_category_db_sqlserver" name="essay_category" value="db_sqlserver">
    <label for="essay_category_db_sqlserver">MS SQL Server</label>
    <input type="radio" id="essay_category_db_oracle" name="essay_category" value="db_oracle">
    <label for="essay_category_db_oracle">Oracle</label>
    <input type="radio" id="essay_category_db_mysql" name="essay_category" value="db_mysql">
    <label for="essay_category_db_mysql">MySql</label>
    <input type="radio" id="essay_category_db_others" name="essay_category" value="db_others">
    <label for="essay_category_db_others">其它</label><br/>
    其它分类：
    <input type="radio" id="essay_category_others_php" name="essay_category" value="others_php">
    <label for="essay_category_others_php">PHP</label>
    <input type="radio" id="essay_category_others_designpatterns" name="essay_category" value="others_php">
    <label for="essay_category_others_designpatterns">设计模式</label>
    <input type="radio" id="essay_category_others_softwareengineering" name="essay_category" value="others_php">
    <label for="essay_category_others_softwareengineering">软件工程</label>
    <input type="radio" id="essay_category_others_others" name="essay_category" value="others_php">
    <label for="essay_category_others_others">其它</label>
 </div>
 <div style="border-bottom:1px solid #DDD;padding:5px;font-size:13px;">选项</div>
 <div style="margin:10px;">
 <label for="enreply">允许评论</label><input type="checkbox" name="enreply" id="enreply"/>
 <label for="isEssance">发到精华区</label> <input type="checkbox" name="isEssance" id="isEssance"/>   
 <label for="isRecommend">发到推荐区</label><input type="checkbox" name="isRecommend" id="isRecommend"/> </div>
<div>
<%--<input type="button" class="button" value="content" onclick="javascript:$('#test').html($('#essayeditor').xheditor().getSource());dp.SyntaxHighlighter.HighlightAll('code');"/>--%>
<input type="button" class="button" value="发表文章" onclick="javascript:
<%
		if(mode.equals("addessay")){
			out.print("submitadd();");
		}else if(mode.equals("editessay")){
			out.print("submitedit("+model.getEssayId()+");");
		}else{
			request.getRequestDispatcher(request.getContextPath()+"/error.jsp").forward(request,response);
		}%>"/>
<span id="essaymessage"></span>
</div>
<div></div>
<%if(mode.equals("editessay")){
	out.print("<script language='javascript'>document.getElementById('essay_category_"+model.getEssayType()+"').setAttribute('checked','true');</script>");
}%>