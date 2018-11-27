<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/md5.js"></script>
<link rel="stylesheet" type="text/css" href="../css/bannerMng.css" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>滚动栏管理功能</title>
<script type="text/javascript">

     $(document).ready(function() {
         bannerMng.init();
    }); 
</script>
</head>
<body>
    <div class="search-area" style = "margin-top: 10px;">
        <div class='search-line1'>滚动栏管理：<button id = "search_btn" onclick = "bannerMng.getbannerList();" >搜索</button></div>
    </div>
    <div class="modify_btn" style = "margin-top: 10px;">
        <input type="button" id="add" value="新增"/>
        <input type="button" id="del" value="删除"/>
        <input type="button" id="save" value="保存修改"/>
    </div>
    <!-- table area begin -->
    <div style = "margin-top: 10px;">
        <div class="bannerTable_String" id='bannerTable_String'></div>
        <table id = "bannerTable"></table>
    </div>
</body>
</html>