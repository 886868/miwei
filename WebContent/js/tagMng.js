var tagMng = new function(){
    this.getTagListURL = defaultWebUrl + "bookadmin/getTagList.do";
    this.editTagListURL = defaultWebUrl + "bookadmin/editTagList.do";
};

tagMng.init = function(){
    
    var tagTypeOptions = {
            dataSource : [{text:"根节点" , value:"0"},{text:"子节点",value:"1"}],
            editable: false
    }
    
    $('#tagTable').omGrid({
    //      height:510,
         limit:0,
         colModel : [ {header : '标签id', name : 'tagId', align : 'left', width : 100,editor:{type:"text",editable:true}},
                      {header : '标签名称', name : 'tagName', align : 'left', width : "100",editor:{type:"text",editable:true}},
                      {header : '标签类型', name : 'tagType', align : 'left', width : "100",editor:{type:"omCombo", name:"tagTypeCB" ,options:tagTypeOptions},renderer:function(value){
                          if("0" == value){
                              return "<span>根节点</span>";
                          } else if ("1" == value){
                              return "<span>子节点</span>";
                          }
                      }},
                      {header : '父标签', name : 'parentTagId', align : 'left', width : "100",editor:{type:"text",editable:true}},
                      {header : '标签色值', name : 'tagColor', align : 'left', width : "100",editor:{type:"text",editable:true}},
                    ],
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
    $('#tagTable').omGrid("cancelEdit");
    $('#tagTable').omGrid('setData', this.getTagListURL);
    $('#tagTable').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
    
    $('#add').click(function(){
        $('#tagTable').omGrid('insertRow',0);
    });
    $('#del').click(function(){
        var dels = $('#tagTable').omGrid('getSelections');
        if(dels.length <= 0 ){
            alert('请选择删除的记录！');
            return;
        }
        $('#tagTable').omGrid('deleteRow',dels[0]);
    });
    $('#save').click(function(){
        var data = $('#tagTable').omGrid('getChanges');
        tagMng.tagSave(data);
    });
};
tagMng.getTagList = function() {
    $('#tagTable').omGrid('setData', this.getTagListURL);
};
tagMng.tagSave = function(data){
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
    dataReq.setEventListener("receive", tagMng.tagSaveCallBack);
    dataReq.send(this.editTagListURL, data);
    // 更新
    // $('#tagTable').omGrid('setData', this.systemEnvdoURL+"?data="+$.toJSON(data));
};

tagMng.tagSaveCallBack = function(data) {
    
    if(data.exception == "ok") {
        alert("修改成功。");
    } else {
        alert(data.exception);
        return;
    }
    
    $('#tagTable').omGrid('reload');
}

