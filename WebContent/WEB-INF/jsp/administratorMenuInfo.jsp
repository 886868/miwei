<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/administratorMenuInfo.js"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>用户菜单设置</title>

<script type="text/javascript">
//设置框件为disable
    $(document).ready(function() {
        administratorMenuInfo.init();
        
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
 
    <div style="margin-top: 30px;">
                  操作号&nbsp;&nbsp;<input style="border-radius:5px;"  id="adminId" type="text" name="adminId" size="20"/>&nbsp;&nbsp;
                &nbsp;&nbsp;                 
                <input type="button" onClick="administratorMenuInfo.menuInfofind();" value="查询" /><br><br>
                
    </div>
    <input type="button" id="savemenulist" onClick="administratorMenuInfo.savemenulist();" value="保存修改" />
    
    <TABLE class="conListH" border="0" cellSpacing="0" cellPadding="0"
        width="100%">
        <TBODY>
            <TR>
                <TD><div id='infofailsearch'></div></TD>
            </TR>
        </TBODY>
    </TABLE>
    <table id="grid"></table><br>
    <!-- view_source_end -->   
</body>
</html>