<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../css/bookSearch.css" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>图书查询</title>
<script type="text/javascript">
    $(document).ready(function() {
        bookSearch.init();
    });
</script>
</head>
<body>
    <!-- search area begin -->
    <div class="search-area">
        <div class='search-line1'>小说名称: <input style="border-radius:5px;" id="bookNM" type="text" name="bookNM" size="30" maxlength='20' /></div>
        <div class='search-line1'>小说ID: <input style="border-radius:5px;" id="bookID" type="text" name="bookID" size="22" maxlength='11' /></div>
        <div class='search-line1'><button id = "search_btn" onclick = "bookSearch.getBookList()" >搜索</button></div>
    </div>
    <!-- search area end -->
<!--     <div style = "margin-top: 10px;"> -->
<!--         <button onclick = "featureSpotMgt.saveEditFeatureSpot()">保存修改</button> -->
<!--     </div> -->
    <!-- table area begin -->
    <div style = "margin-top: 10px;">
        <div class="bookTable_String" id='bookTable_String'></div>
        <div style = "margin-top: 5px;">
            <button onclick = "bookSearch.openBookSearchModal()">添加小说</button>
        </div>
        <table id = "bookTable"></table>
    </div>
   
    <!-- table area end -->

    <!-- feature spot modal begin -->
    <div id="bookSearchModal">
<!--    只展示      -->
        <div>
            <!--    只展示      -->
<!--             <div class=''>编号:  <input style="border-radius:5px;" id="book_id" type="text" name="book_id" size="30" maxlength='20' disabled/></div> -->
            <div class=''>书名:  <input style="border-radius:5px;" id="book_title" type="text" name="book_title" size="30" maxlength='20' /> </div>
        </div>
<!--         封面url   cover_url -->
<!--         滚动条用图片url   cover_banner_url -->
        <div>
            <div class=''>作者:  <input style="border-radius:5px;" id="book_author" type="text" name="book_author" size="30" maxlength='20' /></div>
        </div>
        <div>
            <div>简介:</div>
            <div><textarea id="book_desc" style = "resize: none;height:365px;width:770px;margin-top: 5px;"></textarea></div>
        </div>
<!--         总字数 word_count -->
        <div>
            <a>适合阅读的性别：</a>
            <select id = "gender" >
                <option value = "male">男性向</option>
                <option value = "female">女性向</option>
            </select>
            <a>小说状态：</a>
            <select id = "book_status" >
                <option value = "1">连载</option>
                <option value = "2">完结</option>
            </select>
        </div>
        <div id="book_type_area">
            <a>小说主要分类：</a>
            <select id = "book_type" >
                <option value = "">请选择</option>
            </select>
            <select id = "book_type_sub" >
                <option value = "">请选择</option>
            </select>
        </div>
        
        <div id="book_subject_area">
            <a>小说主题：</a>
            <select id = "book_subject" >
                <option value = "">请选择</option>
            </select>
        </div>
        
        <div style = "margin-top: 5px;">
            <button id="bookSave" onclick = "bookSearch.bookSave(1)">保存</button>
            <button id="bookUpd" onclick = "bookSearch.bookSave(2)">更新</button>
        </div>
    </div>
    
    <div id="bookMoreModal">
        <!-- resource tab begin -->
<!--         <button id = "resourcesTable_del">批量删除</button> -->
        <div style = "margin-top: 10px;">
            <div class="bookTable_String" id='bookMore_String'></div>
            <table id = "bookMoreTable"></table>
        </div>
        <div style = "margin-top: 5px;">
            <button onclick = "bookSearch.addRes()">添加资源</button>
        </div>
        <!-- resource tab end -->
    </div>
    
     <!-- resources modal begin -->
    <div id = "resModal">
        <div>
            <div style = "margin-top: 10px;">
            <a>资源类型</a>
            <select id = "resType" style = "margin-top: 5px;">
                <option value = "201">封面图</option>
                <option value = "202">相册图</option>
                <option value = "101">小说</option>
                <option value = "301">小说标签</option>
                <option value = "401">小说主题</option>
            </select>
            </div>
            <div id="file_up_area">
                <div>
                    <input type="file" name="uploadFile" id="res_upload" style = "margin-top: 10px;width:170px;" accept=".gif,.jpg,.png,.txt" />
                </div>
                <div>
                    <button id = "resUpload_btn">上传</button>
                    <img id="upLoading_img" src="../css/images/custome/icon_custom_process_small.gif" hidden/>
                </div>
            </div>
            <div id="tag_seclect_area">
                <div>小说主要分类：</div>
                <div>
                   <select id = "res_book_type" >
                        <option value = "">请选择</option>
                    </select>
                    <select id = "res_book_type_sub" >
                        <option value = "">请选择</option>
                    </select>
                </div>
                <div>
                    <button id = "resAddTag_btn">添加</button>
                </div>
            </div>
            <div id="subject_seclect_area">
                <div>
                    <a>小说主题：</a>
                    <select id = "res_book_subject" >
                        <option value = "">请选择</option>
                    </select>
                </div>
                <div>
                    <button id = "resAddSub_btn">添加</button>
                </div>
            </div>
            
        </div>
    </div>
    <!-- resources modal end -->

    <!-- preview modal begin -->
    <!--     img -->
    <div id = "previewImg" style="text-align:center;">
        <img id="previewModal_img" src="" alt="Base64 encoded image"  style="margin: 0 auto;" />
    </div>
    <!--     dir -->
    <div id = "previewDir" style="text-align:center;">
            <div style = "margin-top: 10px;">
            <div class="bookDirTable_String" id='bookDirMore_String'></div>
            <table id = "bookDirTable"></table>
        </div>
    </div>
    <!-- preview modal end -->
</body>
</html>