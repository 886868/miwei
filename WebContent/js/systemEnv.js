var systemEnv = new function(){
	this.systemEnvinitURL = defaultWebUrl + "user/systemEnvinit.do";
	this.systemEnvdoURL = defaultWebUrl + "user/systemEnvdo.do";
	this.svpwdURL = defaultWebUrl + "user/updatePWD.do";
	this.adminId= null;
};

systemEnv.init = function(){
	$('#grid').omGrid("cancelEdit");
	$('#grid').omGrid('setData', this.systemEnvinitURL);
	$('#grid').omGrid({
	     onSuccess:function(data,testStatus,XMLHttpRequest,event){
	    	 $('#infofailsearch').html(data.dataStr);
	     },
	    onError:function(XMLHttpRequest,textStatus,errorThrown,event){
	    	$('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
	     }
	 });
};

systemEnv.systemEnvdo = function(data){
	var insertlist = data.insert;
	var updatelist = data.update;
	for(var i=0;i<insertlist.length;i++){ 
		var insertInfo = insertlist[i];
		if (insertInfo.key == ""){
			alert("key必须输入。");
			return ;
		}else if (insertInfo.value == ""){
			alert("value必须输入。");
			return ;
		}
	};
	for(var i=0;i<updatelist.length;i++){ 
		var updateInfo = updatelist[i];
		if (updateInfo.key == ""){
			alert("key必须输入。");
			return ;
		}else if (updateInfo.value == ""){
			alert("value必须输入。");
			return ;
		}
	};
	$('#grid').omGrid('setData', this.systemEnvdoURL+"?data="+$.toJSON(data));
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