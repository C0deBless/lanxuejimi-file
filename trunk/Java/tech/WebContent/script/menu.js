var menuflag = 0;
function showMenu(index) {
	menuflag = 1;
	var id = "#submenu_" + index;
	$(id).css("display", "block");
}
function hideMenu(index) {
	menuflag = 0;
	var id = "submenu_" + index;
	var dom = document.getElementById(id);
	dom.style.display = "none";
}
function loadMenu() {
	var dom = document.getElementById("sidebar");
	
}

// <ul class="sideNav">
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('net');">.NET</a></li>
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('java');">JAVA</a></li>
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('flash');">Flash</a></li>
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('webfront');">Web前端</a></li>
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('mobile');">移动应用</a></li>
// <li><a href="javascript:void(0);"
// onmouseover="showMenu('database');">数据库</a></li>
// <li><a href="javascript:void(0);">PHP</a></li>
// <li><a href="javascript:void(0);">设计模式</a></li>
// <li><a href="javascript:void(0);">软件工程</a></li>
// <li><a href="javascript:void(0);">其他分类</a></li>
// </ul>
