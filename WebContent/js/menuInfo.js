var menuInfo = new function(){
    this.menuInfoinitURL = defaultWebUrl + "user/menuInfoinit.do";
    this.menuInfodoURL = defaultWebUrl + "user/menuInfodo.do";
    this.svpwdURL = defaultWebUrl + "user/updatePWD.do";
    this.adminId= null;
};

menuInfo.init = function(){
    $('#grid').omGrid("cancelEdit");
    $('#grid').omGrid('setData', this.menuInfoinitURL);
    $('#grid').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
};

menuInfo.menuInfodo = function(data){
    var insertlist = data.insert;
    var updatelist = data.update;
    var deletelist = data.delete;
    
    if(insertlist.length==0 && updatelist.length==0 && deletelist.length == 0){
        alert("没有需要保存的内容。");
        return;
    }
    
    for(var i=0;i<insertlist.length;i++){ 
        var insertInfo = insertlist[i];
        if (insertInfo.menuId == ""){
            alert("菜单id必须输入。");
            return ;
        }else if (insertInfo.menuName == ""){
            alert("菜单名称必须输入。");
            return ;
        }else if (insertInfo.node == ""){
            alert("节点必须输入。");
            return ;
        }else if (!(insertInfo.node == "0" || insertInfo.node == "1")){
            alert("输入的节点必须是0或1。");
            return ;
        }
    };
    for(var i=0;i<updatelist.length;i++){ 
        var updateInfo = updatelist[i];
        if (updateInfo.menuName == ""){
            alert("菜单名称必须输入。");
            return ;
        }else if (updateInfo.menuName == ""){
            alert("菜单名称必须输入。");
            return ;
        }else if (updateInfo.node == ""){
            alert("节点必须输入。");
            return ;
        }else if (!(updateInfo.node == "0" || updateInfo.node == "1")){
            alert("输入的节点必须是0或1。");
            return ;
        }
    };
    
    // 更新后台数据
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", menuInfo.menuInfodoCallback);
    dataReq.send(this.menuInfodoURL,data);
};

menuInfo.menuInfodoCallback = function(data){
    
    $('#grid').omGrid("cancelEdit");
    $('#grid').omGrid('setData', menuInfo.menuInfoinitURL);
    $('#grid').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
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