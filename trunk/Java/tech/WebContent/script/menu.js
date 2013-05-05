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

}