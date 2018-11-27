<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/administrator.js"></script>
<script type="text/javascript" src="../js/md5.js"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>管理员列表</title>
<script type="text/javascript">

     $(document).ready(function() {
         $.validator.addMethod("num", function(value) {
             return /^\d{11}$/.test(value);
          }, '请输入11位手机号');
        var ruleOptions = {
            dataSource : defaultWebUrl + "user/getRuleAdminSel.do?rule="+$("#curAdminarea").val(),
            editable: false
        };
        
        
        $('#grid').omGrid({
            height:510,
            colModel : [ {header : '登录名', name : 'adminId', width : 80, align : 'left'},
                         {header : '姓名', name : 'name', align : 'left', width : 80,editor:{type:"text",editable:true}},
                         {header : '手机', name : 'num', align : 'left', width : 80,editor:{type:"text",editable:true}},
//                          {header : '归属地', name : 'city', align : 'left', width : 80,editor:{type:"omCombo", name:"city" ,options:cityOptions},renderer:cityRenderer},
                         {header : '角色', name : 'rule', align : 'left', width : 80,editor:{type:"omCombo", name:"rule" ,options:ruleOptions},renderer:ruleRenderer},
                         {header : '密码' , name : 'pwd', align : 'left', width : 'autoExpand', renderer : pwdedit}],
            dataSource : "",
            onBeforeEdit : function(rowIndex, rowData){
                $('#demo >:button').attr("disabled",true);
                pwdedit("", rowData, rowIndex);
            },
            onAfterEdit : function(index,rowdata){
                $('#demo >:button').removeAttr("disabled");
            },
            onCancelEdit : function(){
                $('#demo >:button').removeAttr("disabled");
            }
        });
        
        function pwdedit(colValue, rowData, rowIndex){
            if (rowData.hId == undefined){    
                $("input[name='pwd']").removeAttr("readonly");
                $("input[name='adminId']").removeAttr("readonly");
                $("input[name='adminId']").removeClass("grid-edit-text readonly-text");
                $("input[name='pwd']").removeClass("grid-edit-text readonly-text");
                $("input[name='adminId']").addClass("grid-edit-text");
                $("input[name='pwd']").addClass("grid-edit-text"); 
                return "******";
            }else{
                $("input[name='pwd']").attr("readonly","readonly");
                $("input[name='adminId']").attr("readonly","readonly");
                $("input[name='adminId']").addClass("grid-edit-text readonly-text");
                $("input[name='pwd']").addClass("grid-edit-text readonly-text"); 
                return "<input type='button' id='"+rowData.hId+"' value='重置密码' onclick=administrator.editpwd('"+rowData.hId+"');>";
            }
        }
        function ruleRenderer(value){
            if(!value){
                return m_r[0].text;
            }
            for(var i = 0; i < m_r.length; i++) {
                if(m_r[i].value == value) {
                    return m_r[i].text;
                }
            }
        }

        administrator.administrator();
        $('#add').click(function(){
            $('#grid').omGrid('insertRow',0);
            $("input[name='pwd']").removeAttr("readonly");
            $("input[name='adminId']").removeAttr("readonly");
            $("input[name='adminId']").removeClass("grid-edit-text readonly-text");
            $("input[name='pwd']").removeClass("grid-edit-text readonly-text");
            $("input[name='adminId']").addClass("grid-edit-text");
            $("input[name='pwd']").addClass("grid-edit-text"); 
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
            administrator.administratordo(data);
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
    <!-- view_source_begin -->
    <table>
        <tr>
            <td>登录名:<input style="border-radius:5px;" id="adminId" type="text" size="22" maxlength='11' />
            <td>姓名:<input style="border-radius:5px;" id="name" type="text" name="name" size="22" maxlength='11' />
            <td>手机:<input style="border-radius:5px;" id="num" type="text" name="num" size="22" maxlength='11' />
            <td><input type="button" onclick='administrator.administrator()' value="查询" />
        </tr>
    </table>
    <br>
    
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
    
    <div id="dialog-updatepwd" title="密码修改" style="display: none;">
        <!-- content div  -->
        <form>
            <div class="dialog-setting-content-pwd">
                <table>                    
                    <tr>
                        <td align="right">新密码:</td>
                        <td><input type="password" name="npwd" id="npwd" size='13' maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">确认新密码:</td>
                        <td><input type="password" name="npwdag" id="npwdag" size='13' maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td align="right">管理员密码:</td>
                        <td><input type="password" name="adminpwd" id="adminpwd" size='13' maxlength="20"/></td>
                    </tr>
                </table>
            </div>
        </form>
        <!-- /content div  -->
        
        <!-- button div  -->
        <div class="dialog-btn-div">
            <div class="dialog-btn-ok" onclick="administrator.savepwd();"></div>
        </div>
    </div>
    <!-- view_source_end -->   
</body>
</html>