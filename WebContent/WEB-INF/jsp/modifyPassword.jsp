<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>密码修改</title>

<script type="text/javascript" src="../js/md5.js"></script>
<script type="text/javascript">

</script>
<style type="text/css">
		*{font-family:'Microsoft Yahei';}
</style>
</head>
<body>
    <!-- view_source_begin -->
    <div style="margin-top: 50px;">
    <table align="center" style="border-spacing:0px 10px;">
    	<tr>
        	<td align="right">原密码:</td>
        	<td  colspan="2"><input type="password" name="pwd" size='13' maxlength='12' /></td>
        </tr>
        <tr>
        	<td align="right">新密码:</td>
        	<td  colspan="2"><input type="password" name="npwd" size='13' maxlength='12' /></td>
        </tr>
        <tr>
        	<td align="right">确认新密码:</td>
        	<td  colspan="2"><input type="password" name="npwdag" size='13' maxlength='12' /></td>
        </tr>
        <tr>
        	<td>&nbsp;</td>
        	<td><input type="button" onclick='modifyPassword.certain();' value="&nbsp;修&nbsp;改&nbsp;" /></td>
        	<td><input type="button" onclick='modifyPassword.reset();' value="&nbsp;重&nbsp;写&nbsp;" /></td>
        </tr>
    </table>
    </div>
    <!-- view_source_end -->   
</body>
</html>