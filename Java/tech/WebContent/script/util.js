//--------------设置cookie
function getCookie(name) {
	var str = document.cookie;
	var cookies = str.split("; ");
	for ( var i = 0; i < cookies.length; i++) {
		// var model=cookies[i].split("=");
		// alert(model[1]);
		// if(model[0].toString().trim()==name.toString().trim()){
		// return model[1];
		// }
		var temp = cookies[i].split("=");
		if (temp[0] == name)
			return unescape(temp[1]);
	}
}
function setCookie(name, value, expires) {
	var date = new Date();
	var date1 = date.valueOf();
	date1 += expires;
	var str = document.cookie;
	str = str + ";" + name + "=" + value;
	if (expires != null && expires >= 0) {
		str += ";path=/;expires=" + new Date(date1);
	}
	document.cookie = str;
}
function delCookie(name) {
	var date = new Date();
	var date1 = date.valueOf();
	date1 -= 1000000;
	document.cookie = name + "=;path=/;expires=" + new Date(date1);
	// document.cookie="";
}
function clearcookie() {
	var keys = document.cookie.match(/[^ =;]+(?==)/g);
	if (keys) {
		for ( var i = keys.length; i--;)
			document.cookie = keys[i] + '=0;path=/;expires='
					+ new Date(0).toUTCString();
	}
}
// ------------------------
// -----------设置缓存数据
function addData(key, value) {
	$(document).data(key, value);
}
function getData(key) {
	return $(document).data(key);
}
function removeData(key) {
	$(document).removeData(key);
}
// -------------------------
// --------------操作进度提示栏
function showProgress(content, callback1) {
	if (!callback1) {
		callback1 = function() {
		}
	}
	var tmp = document.getElementById("progress");
	// alert("this is show method");
	if (tmp) {
		hideProgress(function() {
			showProgress(content, callback1);
		});
	} else {
		var div = document.createElement("div");
		document.body.insertBefore(div, document.body.firstChild);
		$(div).addClass("progress");
		$(div).attr("id", "progress");
		document.body.appendChild(div);
		$(div).animate({
			height : "30"
		}, 500, 'linear', callback1);
		$(div).html(content);
	}
}
function hideProgress(callback) {
	// alert("this is hide method");
	if (!callback) {
		callback = function() {
		}
	}
	var div = document.getElementById("progress");
	$(div).animate({
		height : "0"
	}, 500, 'linear', function() {
		$("#progress").remove();
		callback();
	});
}
// -------------------------
// ------------------显示右键菜单
function showContextMenu() {
	var posX = 0, posY = 0;
	var event = event || window.event;
	if (event.pageX || event.pageY) {
		posX = event.pageX;
		posY = event.pageY;
	} else if (event.clientX || event.clientY) {
		posX = event.clientX + document.documentElement.scrollLeft
				+ document.body.scrollLeft;
		posY = event.clientY + document.documentElement.scrollTop
				+ document.body.scrollTop;
	}
	var div = document.createElement("div");
	$(div).addClass("contextmenu");
	$(div).css("left", posX);
	$(div).css("top", posY);
	document.body.appendChild(div);
	window.event.returnValue = false;
	return false;
}
// -------------------------
// -------------加载文章列表
function loadEssayList(accountLoginName, pageIndex, pageCapacity, targetId) {
	// addData("targetId",targetId)
	targetId = "#" + targetId;

	$
			.ajax({
				url : "blog/essaylist",
				type : "post",
				data : {
					accountLoginName : accountLoginName,
					pageIndex : pageIndex,
					pageCapacity : pageCapacity
				},
				dataType : "json",
				success : function(data) {

					$(targetId).empty();
					var container = document.createElement("div");
					container.id = "ec";
					$(targetId).append(container);

					var pageIndex = data.model.pageIndex;
					var totalPage = data.model.totalPage;
					var pageCapacity = data.model.pageCapacity;
					var sourceDiv = document.getElementById("ec");
					var pagerDiv = document.createElement("div");
					pagerDiv.id = "pager";
					for ( var i = 0; i < data.model.list.length; i++) {
						var essay = data.model.list[i];
						var div = document.createElement("div");
						div.id = "essay_" + essay.essayId;
						;
						// 显示文章标题的DIV
						var titleDiv = document.createElement("div");
						$(titleDiv).addClass("essaylist_title");
						// TODO:做查看文章模块
						$(titleDiv)
								.html(
										"<a href='blog/essay_"
												+ essay.essayId
												+ ".html' style='font:inherit;color:inherit;' target='_blank'>"
												+ essay.essayTitle + "</a>");
						var span_edit = document.createElement("span");
						$(span_edit)
								.html(
										"<a href='javascript:editessay(\""
												+ essay.essayId
												+ "\");' style='color: #c66653;'>编辑</a>");
						$(span_edit).css("margin-left", "40px");
						$(span_edit).css("font-size", "12px");
						var span_delete = document.createElement("span");
						$(span_delete).html(
								"<a href='javascript:deleteEssayConfirm(\""
										+ essay.essayId + "\");'>删除</a>");
						$(span_delete).css("margin-left", "20px");
						$(span_delete).css("font-size", "12px");
						// $(span_edit).css("color","#c66653");
						$(titleDiv).append(span_edit);
						$(titleDiv).append(span_delete);
						// 显示文章摘要的DIV
						var contentDiv = document.createElement("div");
						$(contentDiv).addClass("essaylist_content");
						$(contentDiv).html(essay.essaySummary);
						// 显示文章信息的DIV
						var summaryDiv = document.createElement("div");
						$(summaryDiv).addClass("essaylist_summary");
						var span_step = document.createElement("span");
						$(span_step).html("反对：" + essay.essayStep);
						var span_top = document.createElement("span");
						$(span_top).html("支持：" + essay.essayTop);
						var span_browser = document.createElement("span");
						$(span_browser).html("浏览：" + essay.essayBrowse);
						var span_lastModifiedDate = document
								.createElement("span");
						$(span_lastModifiedDate)
								.html(
										"最近修改时间："
												+ formatDateString(essay.essayLastModifiedDate));
						var span_publishDate = document.createElement("span");
						// alert(new
						// Date(Date.parse(essay.essayPublishTime)).toTimeString());
						$(span_publishDate)
								.html(
										"发布时间："
												+ formatDateString(essay.essayPublishTime));
						var span_reply = document.createElement("span");
						$(span_reply).html(
								"<a href='javascript:void(0);'>评论</a>："
										+ essay.replyCount);
						// TODO:添加显示评论模块
						$(summaryDiv).append(span_browser);
						$(summaryDiv).append(span_top);
						$(summaryDiv).append(span_step);
						$(summaryDiv).append(span_reply);
						$(summaryDiv).append(span_publishDate);
						$(summaryDiv).append(span_lastModifiedDate);

						$(div).append(titleDiv);
						$(div).append(contentDiv);
						$(div).append(summaryDiv);
						$(sourceDiv).append(div);
					}
					$("#ec").append(pagerDiv);
					$("#pager")
							.load(
									"partialpage/pager.jsp",
									{
										pageIndex : pageIndex,
										totalPage : totalPage,
										pagerMethod : "loadEssayList('account1',pageIndex,"
												+ pageCapacity + ",'main')"
									});
				}
			});
}
// -------------------------
// 日期相关函数-------------
function formatDateString(source) {
	var str;
	var date = new Date(Date.parse(source));
	str = date.getFullYear() + "/";
	str += (date.getMonth() + 1) + "/";
	str += date.getDate() + " ";
	str += date.getHours() + ":";
	str += date.getMinutes();
	// str+=date.getSeconds();
	return str;
}

// -----------添加回复
function addEssayReply(essayId, replyText, toAccount, toReply) {
	$.ajax({
		url : "blog/addreply",
		data : {
			essayId : essayId,
			replyText : replyText
		},
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.err == "logoff") {

			} else {

			}
		}
	});
}
