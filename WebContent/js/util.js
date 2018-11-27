// web服务器访问URL
var defaultWebUrl = getWebPath() + "/";
var defaultDearwhereUrl = getprePath() + "/";
var defaultPostUrl = getPostPath();
// 图片服务器的URL，运行本地Tomcat时不可用
// 若也要使用，则本地需配置Apache+Tomcat，Apache中配置代理指向实际的图片服务器URL(http://58.32.233.235/......)
// 本机Tomcat调试时，可直接把pictureUrl的值设为 "http://58.32.233.235:7001/dearwhere/dw/pics/"
// 服务器使用  defaultWebUrl 替换  "http://58.32.233.235:7001/dearwhere/"
//var pictureUrl = defaultWebUrl + "dw/pics/";
//var imgProfileDefault = defaultWebUrl + "css/images/profile/profile_default.png";
//var customepictureUrl = defaultWebUrl + "css/images/custome/";

function findElId(id){
	return document.getElementById(id);
	
	
}

function strDateFtt(date){
    if(!date){
        return "";
    }
    
    var str1 = "/";
    var str2 = ":";
    var size = date.length;
    
    var year = date.substring(0, 4);
    var month = date.substring(4, 6);
    var day = date.substring(6, 8);
    
    var hh = "";
    var mm = "";
    var ss = "";
    if(size == 17 || size == 14) {
        hh = date.substring(8, 10);
        mm = date.substring(10, 12);
        ss = date.substring(12, 14);
        return year + str1 + month + str1 + day + str1 + " " + hh + str2 + mm + str2 + ss;
    } 
    return year + str1 + month + str1 + day;
}


function dateFtt(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

// 获得日期，在添加亲友时，充当亲友设备号 返回的格式 yyyymmdd
function getFDeviceNumbyDate() {
	var d, s = "";
	d = new Date();
	s += d.getFullYear(); 
	s += formateDateAddZero(d.getMonth() + 1);
	s += formateDateAddZero(d.getDate());
	s += formateDateAddZero(d.getHours());
	s += formateDateAddZero(d.getMinutes());
	s += formateDateAddZero(d.getSeconds());
	s += formateDateAddZero(d.getMilliseconds(),1);
   return s;
}

function getFDate(d) {
	var s = "";
	d = new Date();
	s += d.getFullYear() + "/"; 
	s += formateDateAddZero(d.getMonth() + 1) + "/";
	s += formateDateAddZero(d.getDate()) + " ";
	s += formateDateAddZero(d.getHours()) + ":";
	s += formateDateAddZero(d.getMinutes()) + ":";
	s += formateDateAddZero(d.getSeconds());
   return s;
}
// 小于10添加0
function formateDateAddZero(obj,flg) {
	if(obj < 10) {
		if(flg == 1){
			return "00" + obj;
		} else {
			return "0" + obj;
		}
	}
	
	if(obj < 100 && flg == 1) {
		return "0" + obj;
	}
	return obj;
}


// 显示指定panel
function showWestPanel(obj){
	obj.show();
}


// 通用警告栏初始化
function initAlertDialog() {
	$( "#dialog-alert-comm" ).omDialog({
        autoOpen : false, 
        resizable: false,
        height:182,
		width:256,
        modal: true,
        closeOnEscape : false
        // 设置窗口位置[left，top]
		// position: [120,220],
    });
}

// 通用警告栏弹出 openCustomAlertDialog
function openCAD(content,title) {
	var strTitle = "提示";
	if(title) {
		strTitle = title;
	}
	
	$("#dialog-alert-comm-custom-content").html(content);
	$("#dialog-alert-comm-custom").omDialog({title:strTitle});
	$("#dialog-alert-comm-custom").omDialog('open');
}

//通用警告栏弹出 openAlertDialog
function openAD(content,title) {
	var strTitle = "提示";
	if(title) {
		strTitle = title;
	}
	
	$("#dialog-alert-comm-content").html(content);
	$("#dialog-alert-comm").omDialog({title:strTitle});
	$("#dialog-alert-comm").omDialog('open');
}

function getPath() {
	var strPath = window.document.location.pathname;
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return postPath;
}

function getWebPath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return (prePath + postPath);
}

function getprePath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	return prePath;
}

function getPostPath() {
	var strPath = window.document.location.pathname;
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return postPath;
}

// 关闭对象dialog
function dialogCancelBtn(obj) {
    obj.omDialog("close");
}
//打开 dialog
function openDialog(obj) {
    obj.omDialog("open");
}

function quit(obj) {
	obj.submit();
}

function trim(str){ //删除左右两端的空格
	if (str==null) return "";
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

//邮箱验证
function isEmail(email){
	if(email.search(/^([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+\.(?:com|cn|net)$/)!=-1){
	//if  (email.search(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.+[c][o][m]$|[c][n]$/) != -1){
	//if  (email.search(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/) != -1){ 
	//if  (email.search(/^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/) != -1){
	//if  (email.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1){ 
	return true;	 
	}
	else{	
		return false; 	 
	}  
}

//验证手机号码
function isMobilePhone(tel){
	if(tel.search(/^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/) != -1){     
		return true;
	}
	else{		
		return	false;
	}
}
//验证电话号码
function istelPhone(tel,menu){
	// Check类型：最小长度为3位，最大长度为15位，数字类型
	if(tel.search(/^\d+$/) != -1) 
	{ 
		if (fucCheckLength(tel) >= 3 && fucCheckLength(tel) <=15) {
			return true;
		} else {
			return false;
		}
	} 
	else
	{
		return false;
	}
}

//文字长度check
function fucCheckLength(strTemp) { 
	  var i,sum;
	  sum=0;
	  for(i=0;i<strTemp.length;i++) { 
	    if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)) {
	      sum=sum+1;
	    }else {
	      sum=sum+2;
	    }
	  }
	  return sum; 
	}

//英数字校验
function isPassword(password){
	if(password.search(/^[A-Za-z0-9]+$/) != -1){     
		return true;
	}
	else{		
		return	false;
	}
}

