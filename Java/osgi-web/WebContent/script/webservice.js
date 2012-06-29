var sourceDom;
function showAddEndpoint(obj,implementor,bundleID) {
	var port = $(document).data("services.port");
	var root = $(document).data("services.root");
	$(obj).hide();
	sourceDom=obj;
	var sourceTr = $(obj).parent().parent();
	var tr = document.createElement("tr");
	$(tr).height(0);
	var td = document.createElement("td");
	td.colSpan = 4;
	$(td).addClass("addendpoint");
	//td.innerHTML="";
	//var div = document.createElement("div");
	//$(div).addClass("addendpointdiv");
	var span_address = document.createElement("span");
	span_address.innerHTML = "Address : http://localhost:" + port + "/" + root
			+ "/" + "<input type='text'/>";
	var span_operation = document.createElement("span");
	span_operation.innerHTML = "<a href='javascript:void(0);' onclick='submitAddEndpoint(this,\""+implementor+"\","+bundleID+");'>Add</a>|<a href='javascript:void(0);' onclick='cancelAdd(this);'>Cancel</a>";

	$(td).append(span_address);
	$(td).append(span_operation);
	//$(td).append(div);

	$(td).hide();
	tr.appendChild(td);
	$(sourceTr).after(tr);
	$(tr).animate( {
		height:37
	}, 500, "swing", function() {
		$(td).fadeIn(200);
	});

}
function submitAddEndpoint(obj,implementor,bundleID) {
	
	var td=$(obj).parent().parent();
	var spans=$(td).children("span");
	var path=spans.eq(0).children("input").eq(0).val();
	
	$.ajax({
		url:"servlet/webservice/addendpoint",
		type:"post",
		data:{path:path,implementor:implementor,bundleID:bundleID},
		success:function(msg){
			if(msg=="true"){
				ShowMessage("服务已经发布，1秒后重新加载页面。",300,70,1000,function(){
					window.location.reload();
				});
			}
			else{
				ShowAlert("服务发布失败！！",300,70,1000)
			}
		}
	});
}

function cancelAdd(obj) {
	var td = $(obj).parent().parent();
	var tr = td.parent();
	$(td).fadeOut(200,function(){
		tr.animate({
			height:0
		},300,"swing",function(){
			tr.remove();
			$(sourceDom).show();
		});
	});
}

function removeService(serviceId){
	ShowConfirm("确定要删除服务id="+serviceId+"？？？","删除服务",function(){
		$.ajax({
			url:"servlet/webservice/removeendpoint",
			type:"post",
			data:{serviceId:serviceId},
			success:function(msg){
				if(msg=="true"){
					ShowMessage("服务删除成功，1秒后重新加载页面。",300,70,1000,function(){
						window.location.reload();
					});
				}
				else{
					ShowAlert("服务发布失败！！",300,70,1000)
				}
			}
		});
	},200,70);
}


