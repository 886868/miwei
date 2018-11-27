var accessInitURL = defaultWebUrl + "user/accessCountInit.do";


var modifyPassword = new function(){
	this.mpURL = defaultWebUrl + "user/modifyPassword.do";
	this.mupURL = defaultWebUrl + "user/modifyuserPassword.do";
};


/**
 *初始化
 */
modifyPassword.certain = function(){
	var pwdbc = new jsonReq();
	pwdbc.setEventListener("receive", modifyPassword.callback);
	var cond = new svpdCond();
	cond.adminId = $("#curAdminId").val();;
	cond.password = $("input[name='pwd']").val();
	cond.newpassword = $("input[name='npwd']").val();
	if (cond.password == ""){
		alert("原密码不能为空！");
		return;
	}
	if(cond.newpassword == ""){
		alert("新密码不能为空！");
		return;
	}
	if (!isPassword(cond.password)
			||!isPassword(cond.newpassword) ){
		alert("输入的密码格式有误，请重新输入。");
		return;
	}
	//二次输入的密码不一致
	if ($.trim(cond.newpassword) != $.trim($("input[name='npwdag']").val())){
		$("input[name='npwd']").val("");
		$("input[name='npwdag']").val("");
		alert("两次输入的密码不一致，请重新输入。");
		return;
	}
	cond.password = hex_md5(cond.password);
	cond.newpassword = hex_md5(cond.newpassword);
	pwdbc.send(this.mpURL, cond);
};
/**
 * 
 */
modifyPassword.callback = function(data){
	if (data.result=="1"){
		alert("密码修改成功。");
		$("input[name='npwd']").val("");
		$("input[name='npwdag']").val("");
		$("input[name='pwd']").val("");
	}else if (data.result=="2"){
		alert("原密码不正确，请重新输入。");
		return;
	} else {
		alert("密码修改失败，请稍候再试。");
	}
};
/**
 * 重写
 */
modifyPassword.reset = function(){
	$("input[name='npwd']").val("");
	$("input[name='npwdag']").val("");
	$("input[name='pwd']").val("");
};


/**
 *初始化
 */
modifyPassword.modifyuserPWD = function(){
	var upwdbc = new jsonReq();
	upwdbc.setEventListener("receive", modifyPassword.usercallback);
	var cond = new svpdCond();
	cond.adminId = $("#tel_num1").val();
	if ($("#tel_num1").val() == ""){
		alert("用户手机号码不能为空！");
		return;
	}
	if($("#tel_num2").val() == ""){
		alert("确认手机号不能为空！");
		return;
	}
	//二次输入的密码不一致
	if ($.trim($("#tel_num1").val()) != $.trim($("#tel_num2").val())){
		$("input[name='tel_num1']").val("");
		$("input[name='tel_num2']").val("");
		alert("两次输入的手机号码不一致，请重新输入。");
		return;
	}
	if (!isMobilePhone($("#tel_num1").val())){
		alert("输入的手机号码格式有误，请重新输入。");
		return;
	}
	
	upwdbc.send(this.mupURL, cond);
};
/**
 * 
 */
modifyPassword.usercallback = function(data){
	if (data.result=="1"){
		alert("密码修改成功。");
		$("input[name='tel_num1']").val("");
		$("input[name='tel_num2']").val("");
	} else {
		alert("用户不存在，密码修改失败。");
	}
};
/**
 * 重写
 */
modifyPassword.userreset = function(){
	$("input[name='tel_num1']").val("");
	$("input[name='tel_num2']").val("");
};
/**
 * 
 * @returns
 */
function svpdCond() {
	this.adminId = null;
	this.password = null;
	this.newpassword = null;
};





