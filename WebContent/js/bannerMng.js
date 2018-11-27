var bannerMng = new function(){
    this.getbannerListURL = defaultWebUrl + "bookadmin/getBannerList.do";
    this.editbannerListURL = defaultWebUrl + "bookadmin/editBannerList.do";
};

bannerMng.init = function(){
    
    var bannerBZTypeOptions = {
            dataSource : [{text:"首页滚动条" , value:"bz001"},{text:"其他",value:""}],
            editable: false
    }
    
    var bannerTypeOptions = {
            dataSource : [{text:"小说" , value:"1"},{text:"广告",value:"2"},{text:"其他",value:"9"}],
            editable: false
    }
    
    
    
    $('#bannerTable').omGrid({
    //      height:510,
         limit:0,
         colModel : [ {header : '对象id', name : 'itemId', align : 'left', width : 100,editor:{type:"text",editable:true}},
                      {header : '对象类型', name : 'itemType', align : 'left', width : "100",editor:{type:"omCombo", name:"bannerTypeCB" ,options:bannerTypeOptions},renderer:function(value){
                          if("1" == value){
                              return "<span>小说</span>";
                          } else if("2" == value){
                              return "<span>广告</span>";
                          } else if("9" == value){
                              return "<span>其他</span>";
                          }
                      }},
                      {header : '业务类型', name : 'divBz', align : 'left', width : "100",editor:{type:"omCombo", name:"bannerBZTypeCB" ,options:bannerBZTypeOptions},renderer:function(value){
                          if("bz001" == value){
                              return "<span>首页滚动条</span>";
                          } else {
                              return "<span>其他</span>";
                          }
                      }},
                      {header : '创建时间', name : 'crDate', align : 'left', width : "100",editor:{type:"text",editable:false}},
                      {header : '更新时间', name : 'updDate', align : 'left', width : "100",editor:{type:"text",editable:false}},
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
    $('#bannerTable').omGrid("cancelEdit");
    $('#bannerTable').omGrid('setData', this.getbannerListURL);
    $('#bannerTable').omGrid({
         onSuccess:function(data,testStatus,XMLHttpRequest,event){
             $('#infofailsearch').html(data.dataStr);
         },
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#infofailsearch').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
         }
     });
    
    $('#add').click(function(){
        $('#bannerTable').omGrid('insertRow',0);
    });
    $('#del').click(function(){
        var dels = $('#bannerTable').omGrid('getSelections');
        if(dels.length <= 0 ){
            alert('请选择删除的记录！');
            return;
        }
        $('#bannerTable').omGrid('deleteRow',dels[0]);
    });
    $('#save').click(function(){
        var data = $('#bannerTable').omGrid('getChanges');
        bannerMng.bannerSave(data);
    });
};
bannerMng.getbannerList = function() {
    $('#bannerTable').omGrid('setData', this.getbannerListURL);
};
bannerMng.bannerSave = function(data){
    var insertlist = data.insert;
    var updatelist = data.update;
//    for(var i=0;i<insertlist.length;i++){ 
//        var insertInfo = insertlist[i];
//        if (insertInfo.key == ""){
//            alert("key必须输入。");
//            return ;
//        }else if (insertInfo.value == ""){
//            alert("value必须输入。");
//            return ;
//        }
//    };
//    for(var i=0;i<updatelist.length;i++){ 
//        var updateInfo = updatelist[i];
//        if (updateInfo.key == ""){
//            alert("key必须输入。");
//            return ;
//        }else if (updateInfo.value == ""){
//            alert("value必须输入。");
//            return ;
//        }
//    };
    
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", bannerMng.bannerSaveCallBack);
    dataReq.send(this.editbannerListURL, data);
};

bannerMng.bannerSaveCallBack = function(data) {
    
    if(data.exception == "ok") {
        alert("修改成功。");
    } else {
        alert(data.exception);
        return;
    }
    
    $('#bannerTable').omGrid('reload');
}

