var administrator = new function(){
    this.administratorexURL = defaultWebUrl + "user/administratorex.do";
    this.administratordoURL = defaultWebUrl + "user/administratordo.do";
    this.svpwdURL = defaultWebUrl + "user/updatePWD.do";
    this.adminId= null;
};
administrator.administrator = function(){
    // 设置 dialog
    $("#dialog-updatepwd").omDialog({
        autoOpen : false, 
        resizable: false,
        height:250,
        width:315,
        modal: true,
        closeOnEscape : false,
        onClose:function(event) {            
        }
    });
    this.adminId= null;
    var cond = new administratorCond();
    cond.adminId = $("#adminId").val();
    cond.name = $("#name").val();
    cond.num = $("#num").val();
    if(cond.num != ""){
        if(!isMobilePhone(cond.num)){
            alert("输入的手机号码格式有误，请重新输入。");
            return ;
        }
    }
    $('#grid').omGrid("cancelEdit");
    $('#grid').omGrid('setData', this.administratorexURL+"?adminId="+cond.adminId+"&name="+cond.name+"&num="+cond.num+"&rule="+$("#curAdminarea").val());
    $('#grid').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
             if (data.res != ""){
                 alert("登录名重复，请重新操作！");
             }
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
};
administrator.administratordo = function(data){
    var insertlist = data.insert;
    var updatelist = data.update;
    var deletelist = data.delete;
    
    if(insertlist.length==0 && updatelist.length==0 && deletelist.length == 0){
        alert("没有需要保存的内容。");
        return;
    }
    for(var i=0;i<insertlist.length;i++){ 
        var insertInfo = insertlist[i];
        if (insertInfo.adminId == ""){
            alert("登录名必须输入。");
            return ;
        }else if (insertInfo.num == ""){
            alert("手机号必须输入。");
            return ;
        }else if (insertInfo.city == ""){
            alert("归属地必须选择。");
            return ;
        }else if (insertInfo.rule == ""){
            alert("角色必须选择。");
            return ;
        }else if (insertInfo.pwd == ""){
            alert("密码必须输入。");
            return ;
        // 创建账号密码英数字校验 lqq 2013.3.17
        //}else if(!isPassword(pwd1)){
        }else if(!isPassword(insertInfo.pwd)){
            alert("密码格式不正确，请输入英文或者数字。");
            return ;
        }else if(!isMobilePhone(insertInfo.num)){
            alert("输入的手机号码格式有误，请重新输入。");    
            return ;
        }else{
            //加密
            insertInfo.pwd = hex_md5(insertInfo.pwd);
        }        
    };
    for(var i=0;i<updatelist.length;i++){ 
        var updateInfo = updatelist[i];
        if (updateInfo.num == ""){
            alert("手机号必须输入。");
            return ;
        }else if (updateInfo.city == ""){
            alert("归属地必须选择。");
            return ;
        }else if (updateInfo.rule == ""){
            alert("角色必须选择。");
            return ;
        }else if(!isMobilePhone(updateInfo.num)){
            alert("输入的手机号码格式有误，请重新输入。");    
            return ;
        }    
    };
    
    // 更新后台数据
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", administrator.chgCallback);
    dataReq.send(this.administratordoURL,data);
    
};

administrator.chgCallback = function(data){
    administrator.adminId= null;
    var cond = new administratorCond();
    cond.adminId = $("#adminId").val();
    cond.name = $("#name").val();
    cond.num = $("#num").val();
    if(cond.num != ""){
        if(!isMobilePhone(cond.num)){
            alert("输入的手机号码格式有误，请重新输入。");
            return ;
        }
    }
    $('#grid').omGrid("cancelEdit");
    $('#grid').omGrid('setData', administrator.administratorexURL+"?adminId="+cond.adminId+"&name="+cond.name+"&num="+cond.num+"&rule="+$("#curAdminarea").val());
    $('#grid').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
             if (data.res != ""){
                 alert("登录名重复，请重新操作！");
             }
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
    if(data == "fail"){
        alert("修改失败！");
    } else {
        alert("修改成功！");
    }
};

administrator.editpwd = function(hId){
    administrator.adminId=hId;
    $("#npwd").attr("value","");
    $("#npwdag").attr("value","");
    $("#adminpwd").attr("value","");
    $("#dialog-updatepwd").omDialog("open");
};
administrator.savepwd = function(){    
    var pwd1=$("#npwd").val();
    var pwd2=$("#npwdag").val();
    if(pwd1 == ""){
        alert("新密码不能为空，请输入新密码。");
        $("#npwd").attr("value","");
        return ;
    }else if(pwd2 == ""){
        alert("确认密码不能为空，请输入确认密码。");
        $("#npwdag").attr("value","");
        return ;
    }else if(pwd1 != pwd2){
        alert("两次输入的密码不一致，请重新输入");
        $("#npwd").val("");
        $("#npwdag").val("");
        return ;
    }else if(!isPassword(pwd1)){
        alert("密码格式不正确，请输入英文或者数字。");
        $("#npwd").val("");
        $("#npwdag").val("");
        return ;
    }
    var cond = new administratorCond();
    cond.adminId = administrator.adminId;
    //被修改管理员密码
    //加密
    cond.newpwd = hex_md5($("#npwd").val());
    //超级管理员密码
    //加密
    cond.adminpwd = hex_md5($("#adminpwd").val());
    cond.curAdminId = $("#curAdminId").val();
    var pwdbc = new jsonReq();
    pwdbc.setEventListener("receive", administrator.svpdcallback);
    pwdbc.send(this.svpwdURL, cond);
};
administrator.svpdcallback = function(data){    
    var res = data.result;
    if (res == "0"){
        alert("密码修改失败。");
    }else if(res == "2"){
        alert("管理员密码不正确，密码修改失败。");
    }else{
        administrator.adminId = null;
        alert("密码修改成功。");
        dialogCancelBtn($('#dialog-updatepwd'));
    }
};

function administratorCond() {
    this.adminId = null;
    this.name = null;
    this.num = null;
    this.seq = null;
    this.curAdminId = null;
    this.adminpwd = null;
    this.newpwd = null;
};
