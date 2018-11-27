var subjectMng = new function(){
    this.getSubListURL = defaultWebUrl + "bookadmin/getSubList.do";
    this.editSubListURL = defaultWebUrl + "bookadmin/editSubList.do";
};

subjectMng.init = function(){
    $('#subTable').omGrid({
    //      height:510,
         limit:0,
         colModel : [ {header : '主题id', name : 'subjectId', align : 'left', width : 200,editor:{type:"text",editable:true}},
                      {header : '主题名称', name : 'subjectName', align : 'left', width : "200",editor:{type:"text",editable:true}}],
         dataSource : "",
         onBeforeEdit : function(rowIndex, rowData){
             $('#modify_btn >:button').attr("disabled",true);
         },
         onAfterEdit : function(index,rowdata){
             $('#modify_btn >:button').removeAttr("disabled");
         },
         onCancelEdit : function(){
             $('#modify_btn >:button').removeAttr("disabled");
         }
     });
    $('#subTable').omGrid("cancelEdit");
    $('#subTable').omGrid('setData', this.getSubListURL);
    $('#subTable').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
    
    $('#add').click(function(){
        $('#subTable').omGrid('insertRow',0);
    });
    $('#del').click(function(){
        var dels = $('#subTable').omGrid('getSelections');
        if(dels.length <= 0 ){
            alert('请选择删除的记录！');
            return;
        }
        $('#subTable').omGrid('deleteRow',dels[0]);
    });
    $('#save').click(function(){
        var data = $('#subTable').omGrid('getChanges');
        subjectMng.subSave(data);
    });
};
subjectMng.getSubList = function() {
    $('#subTable').omGrid('setData', this.getSubListURL);
};
subjectMng.subSave = function(data){
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
    
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", subjectMng.subSaveCallBack);
    dataReq.send(this.editSubListURL, data);
    // 更新
    // $('#subTable').omGrid('setData', this.systemEnvdoURL+"?data="+$.toJSON(data));
};

subjectMng.subSaveCallBack = function(data) {
    
    if(data.exception == "ok") {
        alert("修改成功。");
    } else {
        alert(data.exception);
        return;
    }
    
    $('#subTable').omGrid('reload');
}

