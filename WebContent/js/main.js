var menuURL = defaultWebUrl + "user/menu.do";
var menuByseqsURL = defaultWebUrl + "user/menuByseqs.do";
var Main = new function(){
	this.rules = null;
	this.mainseq = null;
};
Main.menuload = function(rule,menulist,mainmenuseq){
	var cond = new menuCond();
	cond.seqs = menulist;
	var trReq = new jsonReq();
	Main.rules = rule;
	Main.mainseq = mainmenuseq;
	trReq.setEventListener("receive", Main.getCallback);
	trReq.send(menuByseqsURL,cond);
};

Main.getCallback = function(data){
	var menulist1 = data.menulist;
	var mainmenu = "user/administrator.do";
	var titletext = "管理员列表";
	for (var i = 0; i < menulist1.length; i++) {
//		var authoritylist = new Array();
//		authoritylist = menulist1[i].authority.split(",");
//		var pos = $.inArray(Main.rules+"", authoritylist);
		if (menulist1[i].node == "0"){  
			$("#menuaccordiontab").append("<div style='background:#d0e9ee;padding-left:10px;font-weight:bold;'>"
					+menulist1[i].menuName+"</div>");
		}else if (menulist1[i].node == "1" ){
			if(menulist1[i].menuId == "logout"){
				$("#menuaccordiontab").append("<div id='menu_"+menulist1[i].menuId+"' name='menulist' onclick=Main.menucolerchange('menu_"+menulist1[i].menuId+"')><p><ul><a target='_top' href='"+defaultWebUrl + menulist1[i].menuUrl+"'>"+menulist1[i].menuName+"</a></ul></p></div>");
			} else if ((menulist1[i].seq == Main.mainseq) || (0 == Main.mainseq && menulist1[i].menuId == "useradmini")){
				$("#menuaccordiontab").append("<div id='menu_"+menulist1[i].menuId+"' style='background:#96daf6' name='menulist' onclick=Main.menucolerchange('menu_"+menulist1[i].menuId+"')><p><ul><a href='#' url='"+defaultWebUrl + menulist1[i].menuUrl+"?curAdminarea="+Main.rules+"'>"+menulist1[i].menuName+"</a></ul></p></div>");
				mainmenu = menulist1[i].menuUrl;
				titletext = menulist1[i].menuName;
			}else {
				$("#menuaccordiontab").append("<div id='menu_"+menulist1[i].menuId+"' name='menulist' onclick=Main.menucolerchange('menu_"+menulist1[i].menuId+"')><p><ul><a href='#' url='"+defaultWebUrl + menulist1[i].menuUrl+"'>"+menulist1[i].menuName+"</a></ul></p></div>");
			}
		}
	}
	
	$('a[href="#"]').click(function(){
		$("#panel").omPanel('setTitle',$(this).html());
		$("#panel").omPanel('reload',$(this).attr('url'));
	});
	
	$("#panel").omPanel({
        width:'100%',
        height:'100%',
        header: true,
        collapsed: false,
        collapsible: false,
        closable: false,
        url:defaultWebUrl+mainmenu,
        title : titletext,
    });
};
Main.menucolerchange = function(id){
	$("div[id^='menu_']").css("background",""); 
	$("#"+id+"").css("background","#96daf6"); 
};

function menuCond() {
	this.seqs = null;
}


