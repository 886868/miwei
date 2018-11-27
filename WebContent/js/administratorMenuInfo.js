
var administratorMenuInfo = new function(){
	this.administratorMenuInfoURL=defaultWebUrl + "user/getadministratorMenuInfo.do";
	this.savemenulistURL=defaultWebUrl + "user/savemenulist.do";	
	this.mainseq = null;
	this.adminId = null;
};

/**
 *初始化
 */

administratorMenuInfo.init = function(){	
	selectIds = [];
	administratorMenuInfo.mainseq="";
	administratorMenuInfo.adminId="";
	$("#savemenulist").attr("disabled",true);
	$('#infofailsearch').html('操作员菜单结果显示');
	
	$('#grid').omGrid({
		limit:0,
		height:510,
		showIndex : false,
		singleSelect : false,
        dataSource :'',
        colModel : [ 
                     {header : '菜单编号', name : 'menuId', align : 'center', width : 160},
                     {header : '菜单名称', name : 'menuName', align : 'center', width : 160},
                     {header : '父菜单名称', name : 'parent', align : 'center', width : 160},
                     {header : '是否作为主画面', name : 'seq', align : 'center', width : 120, renderer : mainmenuRenderer}],
                     onRowSelect : function(index, data){
                    	if($.inArray(data.seq, selectIds) == -1){ 
                    		selectIds.push(data.seq);
                    	}
         	        	$("#savemenulist").attr("disabled",false);
         	        },
         	        onRowDeselect : function(index, data){
         	        	var i = $.inArray(data.seq, selectIds);
         		        selectIds.splice(i,1);
         		        if (selectIds.length == 0){
         		        	$("#savemenulist").attr("disabled",true);
         		        }
         	        },
         	        //还原过去选中的记录
         	        onRefresh : function(nowpage, records){
         	        	var len = selectIds.length;
         	        	var indexs = [], index =-1;
         	        	for(var i=0; i<len; i++){
         	        	   $.each(records, function(n,item){
         	        		if(item.seq == selectIds[i]){
         			           indexs.push(n);
         			        } 
         	           	   });
         	        	}
         	        	$("#grid").omGrid("setSelections", indexs);
         	        }
		});
	function mainmenuRenderer(value){
		if (value == administratorMenuInfo.mainseq){
			return "<input type='radio' value="+value+" name='radiomainmenuseq' id='radio_"+value+"' checked>";	
		}else{
			return "<input type='radio' value="+value+" name='radiomainmenuseq' id='radio_"+value+"'>";
		}
    }
	
};

/**
 * 查询条件的默认值设定
 * @returns
 */

administratorMenuInfo.menuInfofind=function(){
	var cond = new administratorMenuInfoCond();
	cond.adminId=$("#adminId").val();
	if (cond.adminId == ""){
		alert("请输入管理员id");
		return;
	}
	administratorMenuInfo.adminId=cond.adminId;
	selectIds = [];
	$('#grid').omGrid('setData', this.administratorMenuInfoURL+"?adminId="+cond.adminId);
	
	 $('#grid').omGrid({
	     onSuccess:function(data,testStatus,XMLHttpRequest,event){
            if (data.res == "fail"){
            	alert(data.msg);
            }
            if (data.menulist != ""){
            	var tempselectIds = data.menulist.split(",");
                $.each(tempselectIds, function(idx, obj) {
                	selectIds.push(parseInt(obj));
                });
            }
            administratorMenuInfo.mainseq=data.mainmenuseq;
	     },
	 
	    onError:function(XMLHttpRequest,textStatus,errorThrown,event){
	    	$('#infofailsearch').html('取得数据失败');
	    }
	 });
};

administratorMenuInfo.savemenulist = function(){
	var cond = new administratorMenuInfoCond();
	cond.adminId=administratorMenuInfo.adminId;
	cond.menulist=selectIds;
	cond.mainmenu=$('input:radio[name=radiomainmenuseq]:checked').val();
	var trReq = new jsonReq();
	trReq.setEventListener("receive", administratorMenuInfo.getCallback);
	trReq.send(this.savemenulistURL,cond);
};

administratorMenuInfo.getCallback = function(data){
	if (data.result == "1"){
		alert("设置成功");
	}else{
		alert("设置失败");
	}
};

function administratorMenuInfoCond() {
	this.adminId=null;
	this.menulist=null;
	this.mainmenu=null;
};
