function stopBundle(id) {
	$.ajax( {
		url : "bundle/stop",
		type : "post",
		data : {
			bundleId : id
		},
		success : function(msg) {
			if (msg == "true") {
				alert("Stop Successfully");
				window.location.reload();
			} else {
				alert("Stop failed");
			}
		}
	});
}
function startBundle(id) {
	$.ajax( {
		url : "bundle/start",
		type : "post",
		data : {
			bundleId : id
		},
		success : function(msg) {
			if (msg == "true") {
				alert("Start Successfully");
				window.location.reload();
			} else {
				alert("Start failed");
			}
		}
	});
}

function installBundle() {
	$("#message").html("正在上传....").css( {color : "red"});
	$("#fileupload").ajaxSubmit( {
		success : function(msg) {
			$("#message").html("上传成功").css( {color : "green"});
			alert("Bundle 上传成功！！！");
			document.getElementById("uploadbutton").disabled=false;
			//window.setTimeout(function(){
				window.location.reload();				
			//},500);
		}
	});
}
function uninstallBundle(id) {
	$.ajax( {
		url : "bundle/uninstall",
		type : "post",
		data : {
			bundleId : id
		},
		success : function(msg) {
			if (msg == "true") {
				alert("Uninstall Successfully");
				window.location.reload();
			} else {
				alert("Uninstall failed");
			}
		}
	});
}
function loadBundleInfo(source, id) {
	var sourceTr = $(source).parent().parent();
	$(source).hide();
	$.ajax( {
		url : "bundle/info",
		type : "post",
		data : {
			bundleId : id
		},
		dataType : "json",
		success : function(data) {
			var table = document.createElement("table");
			$(table).addClass("bundleinfo").addClass("table");
			var tbody=document.createElement("tbody");
			$(table).append(tbody);
			for ( var item in data) {
				var tr = document.createElement("tr");
				var td1 = document.createElement("td");
				$(td1).addClass("key");
				var td2 = document.createElement("td");
				$(td2).addClass("value");
				td1.innerHTML = item;
				var value = eval("data." + item);
				if (value != null && value != "") {
					if (typeof (value) == "object") {
						if (value.length && value.length > 0) {
							var tmpstr = "";
							for ( var i = 0; i < value.length; i++) {
								
								var table2=document.createElement("table");
								var tbody2=document.createElement("tbody");
								$(table2).addClass("servicereferences").append(tbody2);
								
								var tmp = "";
								if (typeof (value[i]) == "object") {
									for ( var elem in value[i]) {
										var tmp_key=elem;
										var tmp_obj = eval("value[i]." + elem);
										if(tmp_obj!=null&&typeof(tmp_obj)=="object"){
											if(tmp_obj.length&&tmp_obj.length>0){
												var tmp_value=tmp_obj.join("~");
												tbody2.appendChild(prepareTR(tmp_key,tmp_value.toString().replace(/~/g,",<br/>")));
											}
											else{
												for(var elem1 in tmp_obj){
													var tmp_key=elem1;
													var tmp_value=eval("tmp_obj."+tmp_key);
													if (tmp_value!=null&&typeof (tmp_value) == "object" && tmp_value.length
														&& tmp_value.length > 0) {
														tmp_value = tmp_value.join("~");
													}
													tbody2.appendChild(prepareTR(tmp_key,tmp_value.toString().replace(/~/g,",<br/>")));
												}
											}
										}
										else{
											tbody2.appendChild(prepareTR(tmp_key,tmp_obj));
										}
									}
								} else if (typeof (value[i]) == "string") {
									tbody2.appendChild(prepareTR(tmp_key,tmp_obj));
								}
								
								td2.appendChild(table2);
							}
							
						}
					}
					else{
						value = value.toString().replace(/([a-zA-z]+)\s*,\s*([a-zA-z]+)/g, "$1,<br/>$2");
						td2.innerHTML = value;
					}
					tr.appendChild(td1);
					tr.appendChild(td2);
					tbody.appendChild(tr);
				}
				else{
					
				}
				
			}
			
			
			var tr = document.createElement("tr");
			var td = document.createElement("td");
			var td1 = document.createElement("td");
			td.colSpan = 4;
			var div=document.createElement("div");
			$(div).css({height:0,width:"auto",padding:2});
			div.appendChild(table);
			
			var closeDiv=document.createElement("div");
			var closeA=document.createElement("a");
			closeA.href="javascript:void(0);";
			closeA.onclick=function(){
				$(table).fadeOut(100,function(){
					$(div).animate({
						height:0
					},300,"swing",function(){
						$(tr).remove();
					});
				});
				$(source).show();
			}
			$(closeDiv).addClass("closeDiv").append(closeA);
			$(td).append(closeDiv);
			
			td.appendChild(div);
			$(tr).append(td1);
			$(tr).append(td);
			$(sourceTr).after(tr);
			$(div).animate( {
				height : $(table).height()
			}, 500, "swing", function() {
				
				$(table).fadeIn(200);
			});
		}
	});
}
function prepareTR(tmp_key,tmp_obj){
	var tr1=document.createElement("tr");
	var td_1=document.createElement("td");
	var td_2=document.createElement("td");
	tr1.appendChild(td_1);
	tr1.appendChild(td_2);
	td_1.innerHTML=tmp_key;
	td_2.innerHTML=tmp_obj;
	return tr1;
}