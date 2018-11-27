<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/systemEnv.js"></script>
<script type="text/javascript" src="../js/md5.js"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>系统环境管理列表</title>
<script type="text/javascript">

	 $(document).ready(function() {

		$('#grid').omGrid({
			height:510,
			limit:0,
	        colModel : [ {header : 'key', name : 'key', align : 'left', width : 200,editor:{type:"text",editable:true}},
	                     {header : 'value', name : 'value', align : 'left', width : "autoExpand",editor:{type:"text",editable:true}}],
	        dataSource : "",
	        onBeforeEdit : function(rowIndex, rowData){
	        	$('#demo >:button').attr("disabled",true);
	        },
	        onAfterEdit : function(index,rowdata){
	        	$('#demo >:button').removeAttr("disabled");
	        },
	        onCancelEdit : function(){
	        	$('#demo >:button').removeAttr("disabled");
	        }
	    });
		
		systemEnv.init();
		$('#add').click(function(){
			$('#grid').omGrid('insertRow',0);
		});
		$('#del').click(function(){
			var dels = $('#grid').omGrid('getSelections');
			if(dels.length <= 0 ){
				alert('请选择删除的记录！');
				return;
			}
			$('#grid').omGrid('deleteRow',dels[0]);
		});
		$('#save').click(function(){
			var data = $('#grid').omGrid('getChanges');
			/*****此处传递data到后台并处理*******/
			/*****保存成功之后执行如下操作********/
			systemEnv.systemEnvdo(data);
			//$('#grid').omGrid('saveChanges');
			/******或者执行$('#grid').omGrid('reload');***/
		});
	}); 
</script>
<style type="text/css">
		*{font-family:'Microsoft Yahei';}
		.conListH TD {
			padding-bottom: 5px;
			background-color: #addaf9;
			padding-left: 5px;
			padding-right: 5px;
			color: #000000;
			font-size: 14px;
			font-weight: bold;
			padding-top: 5px;
			padding-bottom: 5px;
			margin-top: 4px;
			margin-bottom: 10px;
		}
</style>
</head>
<body>
	系统环境管理：<input type="button" onclick='systemEnv.init()' value="查询" />
    <div id="demo" >
        <input type="button" id="add" value="新增"/>
        <input type="button" id="del" value="删除"/>
        <input type="button" id="save" value="保存修改"/>
        <TABLE class="conListH" border="0" cellSpacing="0" cellPadding="0"
		width="100%">
		<TBODY>
			<TR>
				<TD><div id='infofailsearch'></div></TD>
			</TR>
		</TBODY>
	</TABLE>
    	<table id="grid"></table>
    </div>
    
</body>
</html>