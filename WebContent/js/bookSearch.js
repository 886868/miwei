var bookSearch = new function(){
    this.editBookId = "";
    this.resBookId = "";
    
    this.resAddTagLock = false;
    this.resAddSubLock = false;
    this.resAddBookLock = false;
    
    this.getBookListURL = defaultWebUrl + "bookadmin/getBookList.do";
    this.getBookFirstTagURL = defaultWebUrl + "book/getFirstTags.do";
    this.getTagsByFirstURL = defaultWebUrl + "book/getTagsByFirst.do";
    this.saveStoryInfoURL = defaultWebUrl + "bookadmin/saveStoryInfo.do";
    this.delBookURL = defaultWebUrl + "bookadmin/delBook.do"; 
    this.updStoryInfoURL = defaultWebUrl + "bookadmin/updStoryInfo.do";
    
    this.getResInfoURL = defaultWebUrl + "bookadmin/getResInfo.do";
    this.bookResUploadURL = defaultWebUrl + "bookadmin/bookResUpload.do";
    this.delResURL = defaultWebUrl + "bookadmin/delRes.do"; 
    this.getDirByIdURL = defaultWebUrl + "book/getDirById.do"
    this.previewImgURL = defaultWebUrl + "bookadmin/previewImg.do"
    this.previewTxtURL = defaultWebUrl + "bookadmin/getDirById.do"
    
    this.getSubjectURL = defaultWebUrl + "book/getSubjects.do";
    this.saveResTagURL = defaultWebUrl + "bookadmin/saveResTag.do";
    this.saveResSubURL = defaultWebUrl + "bookadmin/saveResSub.do";
    
};
/**
 * 页面初始化
 */
bookSearch.init = function(){
    // 表格设置
    // 大景点表格初始化
    $('#bookTable').omGrid({
        height:510,
        colModel : [
                     {header : '编号', name : 'bookId', align : 'left', width : 50},
                     {header : '名称', name : 'bookTitle', align : 'left', width : 80},
                     {header : '作者', name : 'bookAuthor', align : 'left', width : 50},
                     {header : '封面', name : 'coverUrl', align : 'left', width : 200},
                     {header : '滚动条图', name : 'coverBannerUrl', align : 'left', width : 200},
                     {header : '简介', name : 'bookDesc', align : 'left', width : 200},
                     {header : '总字数', name : 'wordCount', align : 'left', width : 50},
                     {header : '适读性', name : 'gender', align : 'left', width : 30,renderer:function(value,rowData,rowIndex){ 
                         if(value == "male"){
                             return "男性";
                         } else if(value == "female") {
                             return "女性";
                         } else {
                             return value;
                         }
                     }},
                     {header : '章节总数', name : 'chapterCount', align : 'left', width : 30},
                     {header : '章节更新时间', name : 'chUpdDate', align : 'left', width : 120, renderer:function(value,rowData,rowIndex){ 
                         return strDateFtt(value);
                     }},
                     {header : '状态', name : 'bookStatus', align : 'left', width : 30, renderer:function(value,rowData,rowIndex){ 
                         if(value == "1"){
                             return "连载";
                         } else if(value == "2") {
                             return "完结";
                         } else {
                             return value;
                         }
                     }},
                     {header : '创建时间', name : 'crDate', align : 'left', width : 120, renderer:function(value,rowData,rowIndex){ 
                         return strDateFtt(value);
                     }},
                     {header : '更新时间 ', name : 'updDate', align : 'left', width : 120, renderer:function(value,rowData,rowIndex){ 
                         return strDateFtt(value);
                     }},
                     {header : '操作', name : 'operating', align : 'left', width : 140, renderer:function(colValue, rowData, rowIndex){
                         return "<button onclick = \"bookSearch.openEdit('" + rowIndex + "')\">编辑</button>"
                         +"<button style = \"margin-left: 5px;\" onclick = \"bookSearch.delBook('" + rowData.bookId + "')\">删除</button>"
                         +"<button style = \"margin-left: 5px;\" onclick = \"bookSearch.openMore('" + rowData.bookId +  "')\">更多</button>";
                     }}
                     ],
        dataSource : ''
    });
    
    $('#bookMoreTable').omGrid({
        height:300,
        limit:0,
        colModel : [ {header : '资源类型', name : 'resTypeNM', align : 'left', width : 50},
                     {header : '资源内容', name : 'resUrl', align : 'left', width : 450},
                     {header : '操作', name : 'operating', align : 'left', width : 150, renderer:function(colValue, rowData, rowIndex){
                         var str = "";
                         if(rowData.resType == 1 || rowData.resType == 2 || rowData.resType == 3) {
                             str = str + "<button style = \"margin-left: 5px;\" onclick = \"bookSearch.preview('" + rowIndex + "')\">预览</button>";
                         }
                         str = str + "<button style = \"margin-left: 5px;\" onclick = \"bookSearch.delRes('" + rowIndex + "')\">删除</button>"
                         return str;
                     }} ],
        dataSource : ''
    });
    
    $('#bookDirTable').omGrid({
        height:300,
        limit:0,
        colModel : [ {header : '标题', name : 'chTitle', align : 'left', width : 150},
                     {header : 'URL', name : 'chLink', align : 'left', width : 400},
                     {header : '章节数', name : 'chNo', align : 'left', width : 30},
                     {header : '创建时间', name : 'crDate', align : 'left', width : 120, renderer:function(value,rowData,rowIndex){ 
                         return strDateFtt(value);
                     }},
                   ],
        dataSource : ''
    });
    // 获取数据回调
    $('#bookTable').omGrid({
        // 获取数据成功
        onSuccess:function(data,testStatus,XMLHttpRequest,event){
            $('#bookTable_String').html(data.dataStr);
        },
        // 获取数据失败
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#bookTable_String').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
        }
    });
    
    $('#bookMoreTable').omGrid({
        // 获取数据成功
        onSuccess:function(data,testStatus,XMLHttpRequest,event){
            $('#bookMore_String').html(data.dataStr);
        },
        // 获取数据失败
        onError:function(XMLHttpRequest,textStatus,errorThrown,event){
            $('#bookMore_String').html('取得数据失败,请检查登录名是否重复，网络连接是否正常');
        }
    });
    
    $( "#bookSearchModal").omDialog({
        title: '小说基本信息添加',
        autoOpen: false,
        height: 560,
        width : 800,
        modal: true
    });
    
    $( "#bookMoreModal").omDialog({
        title: '小说资源管理',
        autoOpen: false,
        height: 410,
        width : 800,
        modal: true,
        onClose : function(event) {
            $('#bookTable').omGrid("reload");
        }
    });
    $("#resModal").omDialog({
        title: '资源添加',
        autoOpen: false,
        height: 150,
        width : 220,
        modal: true
    });
    
    $("#previewImg").omDialog({
        title: '图片预览',
        autoOpen: false,
        height: 'auto',
        width : 'auto',
        modal: true
    });
    
    $("#previewDir").omDialog({
        title: '目录预览',
        autoOpen: false,
        height: 'auto',
        width : 'auto',
        modal: true
    });
    
    // 获取一级标签内容
    $("#res_book_type").change(function(){
        $('#res_book_type_sub option').not(":first").remove();
        if($("#res_book_type").val()==""){
            return;
        }
        // 根据一级类型，获取相对应的二级类型
        var tagsReq = new jsonReq();
        var param = {"tagId" : $("#res_book_type").val()};
        tagsReq.setEventListener("receive", bookSearch.addResSubTagsCallback);
        tagsReq.send(bookSearch.getTagsByFirstURL, param);
    });
    
    $("#resType").change(function(){
        var value = $(this).children('option:selected').val();
        
        $('#res_book_subject option').not(":first").remove();
        $('#res_book_type option').not(":first").remove();
        $('#res_book_type_sub option').not(":first").remove();
        
        if(value == "201"||value=="202"||value=="101"){
            $('#file_up_area').show();
            $('#tag_seclect_area').hide();
            $('#subject_seclect_area').hide();
        } else if(value == "301"){
            $('#file_up_area').hide();
            $('#tag_seclect_area').show();
            $('#subject_seclect_area').hide();
            
            // 后台获取小说类型
            var dataReq = new jsonReq();
            dataReq.setEventListener("receive", bookSearch.addResTagsCallback);
            dataReq.send(bookSearch.getBookFirstTagURL, "");
            
        }else if(value == "401"){
            $('#file_up_area').hide();
            $('#tag_seclect_area').hide();
            $('#subject_seclect_area').show();
            
            // 获取小说主题
            var subReq = new jsonReq();
            subReq.setEventListener("receive", bookSearch.addResSubjectCallback);
            subReq.send(bookSearch.getSubjectURL, "");
            
        }
    });
    
    $('#resAddTag_btn').click(function(){
        if(bookSearch.resAddTagLock){
            return;
        }
        bookSearch.resAddTagLock = true;
        var resType = $('#resType').val();
        var resTag = $('#res_book_type_sub').val();
        if(resTag=="") {
            alert("请选择小说类型。");
            return;
        }
        var params = {"bookId" : bookSearch.resBookId, "resType" : resType, "resTag":resTag};
        var tagReq = new jsonReq();
        tagReq.setEventListener("receive", bookSearch.saveResTagCallback);
        tagReq.send(bookSearch.saveResTagURL, params);
    });
    
    $('#resAddSub_btn').click(function(){
        if(bookSearch.resAddSubLock ){
            return;
        }
        bookSearch.resAddSubLock  = true;
        var resType = $('#resType').val();
        var resSub = $('#res_book_subject').val();
        if(resSub=="") {
            alert("请选择小说类型。");
            return;
        }
        var params = {"bookId" : bookSearch.resBookId, "resType" : resType, "resSub":resSub};
        var subReq = new jsonReq();
        subReq.setEventListener("receive", bookSearch.saveResSubCallback);
        subReq.send(bookSearch.saveResSubURL, params);
    });
};

bookSearch.saveResTagCallback = function(data) {
    if(data.exception == "ok") {
        $('#bookMoreTable').omGrid("reload");
        alert("添加成功");
        $("#resModal").omDialog("close");
    } else {
        alert(data.exception);
    }
    this.resAddTagLock = false;
};

bookSearch.saveResSubCallback = function(data) {
    if(data.exception == "ok") {
        $('#bookMoreTable').omGrid("reload");
        alert("添加成功");
        $("#resModal").omDialog("close");
    } else {
        alert(data.exception);
    }
    this.resAddSubLock = false;
};

//获取小说二级类型的回调
bookSearch.addResSubTagsCallback = function(data) {
    var options = data.tags;
    $('#res_book_type_sub option').not(":first").remove();
    if(options){
        for(var i=0;i<options.length;i++){
            $("#res_book_type_sub").append("<option value='"+ options[i].tagId + "'>" +  options[i].tagName + "</option>");
        }
    }
};

bookSearch.addResTagsCallback = function(data){
    var options = data.tags;
    // 加载小说一级类型内容
    $('#res_book_type option').not(":first").remove();
    $('#res_book_type_sub option').not(":first").remove();
    if(options){
        for(var i=0;i<options.length;i++){
            $("#res_book_type").append("<option value='"+ options[i].tagId + "'>" +  options[i].tagName + "</option>");
        }
    }
};

bookSearch.addResSubjectCallback = function(data) {
    var options = data.subjects;
    $('#res_book_subject option').not(":first").remove();
    if(options){
        for(var i=0;i<options.length;i++){
            $("#res_book_subject").append("<option value='"+ options[i].subjectId + "'>" +  options[i].subjectName + "</option>");
        }
    }
};

bookSearch.preview = function(rowIndex) {
    var data = $("#bookMoreTable").omGrid("getData").rows[rowIndex];
    var resType = data.resType;
    
    // 小说目录预览
    if(resType == 3) {
        $('#bookDirTable').omGrid('setData', bookSearch.previewTxtURL + "?bookId=" + data.bookId);
        $( "#previewDir").omDialog('open');
    } else{
        // 小说图片预览
        var param = {"imgUrl":data.resUrl};
        var dataReq = new jsonReq();
        dataReq.setEventListener("receive", bookSearch.previewImgCallback);
        dataReq.send(this.previewImgURL, param);
    }
};

bookSearch.previewImgCallback = function(data) {
    $("#previewModal_img").attr('src', "");
    if(data.exception == "ok"){
        var baseStr = "data:image/gif;base64,";
        $("#previewModal_img").attr('src', baseStr + data.resStr);
        $("#previewImg").omDialog("open");
    } else{
        alert(data.exception);
    }
};
bookSearch.delRes = function(rowIndex) {
    if(!confirm("确认删除该记录？")){
        return;
    }
    var data = $("#bookMoreTable").omGrid("getData").rows[rowIndex];
    var resId = "";
    if(data.resId != "") {
        resId = data.resId
    }
    var param = {"bookId":data.bookId,"resType":data.resType,"resId":resId};
    
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", bookSearch.delResCallback);
    dataReq.send(this.delResURL, param);
}

bookSearch.delResCallback = function(data) {
    if(data.exception == "ok") {
        alert("删除成功");
    } else {
        alert(data.exception);
    }
    bookSearch.openMore(bookSearch.resBookId);
}

bookSearch.addRes = function() {
    $('#imgUpload_btn').unbind("click");
    $("#resType").val("201");
    $('#upLoading_img').hide();
    $('#res_upload').val("");
    
    $('#file_up_area').show();
    $('#tag_seclect_area').hide();
    $('#subject_seclect_area').hide();
    
    $('#resUpload_btn').click(function(){
        if(bookSearch.resAddBookLock){
            console.log("重复上传。");
            return;
        }
        
        bookSearch.resAddBookLock = true;
        var val = $("#res_upload").val();
        if(val == null || val === ''){
//            alert('请选择要上传的文件。');
            console.log("1");
            return;
        };
        
        var resType = $('#resType').val();
        var params = {"bookId" : bookSearch.resBookId, "resType" : resType};

        $('#resUpload_btn').attr('disabled',true);
        $('#upLoading_img').show();
        //执行上传文件操作的函数 
        $.ajaxFileUpload({
            url:bookSearch.bookResUploadURL,   //处理文件上传操作的服务器端地址
            data:params,
            secureuri:false,                   //是否启用安全提交,默认为false  
            fileElementId:'res_upload',        //文件选择框的id属性 
            dataType:'json',                   //服务器返回的格式,可以是json或xml或 text等
            success:function(data, status){    //服务器响应成功时的处理函数 

                $('#resUpload_btn').attr('disabled',false);
                $('#upLoading_img').hide();

                if( data.exception == "ok"){
                    bookSearch.openMore(bookSearch.resBookId);
                    alert("上传成功!");
                    $("#resModal").omDialog("close");
                } else {
                    alert(data.exception);
                }
                $('#res_upload').val("");
                bookSearch.resAddBookLock = false;
            }, 
            error:function(data, status, e){ //服务器响应失败时的处理函数 
                $('#resUpload_btn').attr('disabled',false);
                $('#upLoading_img').hide();
                alert("服务器响应失败。");
                $('#res_upload').val("");
                bookSearch.resAddBookLock = false;
            } 
        });
    });
    bookSearch.resAddTagLock = false;
    bookSearch.resAddSubLock = false;
    $("#resModal").omDialog("open");
    
}

bookSearch.openMore = function(bookId) {
    
    if(!bookId){
        alert("无效数据，请刷新页面再试。");
        return;
    }
    
    $('#bookMoreTable').omGrid('setData', bookSearch.getResInfoURL + "?bookId=" + bookId);
    bookSearch.resBookId = bookId;
    $( "#bookMoreModal").omDialog('open');
    
}

bookSearch.delBook = function(bookId){
    if(!bookId){
        alert("需删除的记录不存在。");
        return;      
    }
    
    if(!confirm("确认删除该记录？")){
        return;
    }
    var param = {"bookId":bookId};
    
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", bookSearch.delBookCallback);
    dataReq.send(this.delBookURL, param);
};

bookSearch.delBookCallback = function(data){
    if(data.exception == "ok") {
        alert("删除成功");
    } else {
        alert(data.exception);
    }
    bookSearch.getBookList();
};

bookSearch.openEdit = function(rowIndex) {
    var data = $("#bookTable").omGrid("getData").rows[rowIndex];
    
    this.editBookId = data.bookId;
    $('#book_title').val(data.bookTitle);
    $('#book_author').val(data.bookAuthor);
    $('#book_desc').val(data.bookDesc);
    $("#gender").val(data.gender);
    $("#book_status").val(data.bookStatus);
    // TODO
    $("#book_type_area").hide();
    $("#book_subject_area").hide();
//    var tagsReq = new jsonReq();
//    var param = {"tagId" : data.get};
//    tagsReq.setEventListener("receive", bookSearch.getTagsByFirstURLCallback);
//    tagsReq.send(bookSearch.getTagsByFirstURL, param);
    $('#bookSave').hide();
    $('#bookUpd').show();
    $( "#bookSearchModal").omDialog('open');
};

bookSearch.openBookSearchModal = function() {
 // 重置模态窗
//    $('#book_id').val("");
    
    $('#bookSave').show();
    $('#bookUpd').hide();
    $('#book_title').val("");
    $('#book_author').val("");
    $('#book_desc').val("");
    
    // 重置下拉框
    $("#gender").val("1");
    $("#book_status").val("1");
    
    // TODO
    $("#book_type_area").show();
    $("#book_subject_area").show();
    $('#book_subject option').not(":first").remove();
    $('#book_type option').not(":first").remove();
    $('#book_type_sub option').not(":first").remove();
    
    $("#book_type").change(function(){
        $("#book_type_sub option").not(":first").remove();
        if($("#book_type").val()==""){
            return;
        }
        // 根据一级类型，获取相对应的二级类型
        var tagsReq = new jsonReq();
        var param = {"tagId" : $("#book_type").val()};
        tagsReq.setEventListener("receive", bookSearch.getTagsByFirstURLCallback);
        tagsReq.send(bookSearch.getTagsByFirstURL, param);
    });
    
    // 后台获取小说类型
    var dataReq = new jsonReq();
    dataReq.setEventListener("receive", bookSearch.openBookSearchModalCallback);
    dataReq.send(this.getBookFirstTagURL, "");
    
    // 获取小说主题
    var subReq = new jsonReq();
    subReq.setEventListener("receive", bookSearch.openSubjectCallback);
    subReq.send(this.getSubjectURL, "");
};

bookSearch.openBookSearchModalCallback = function(data){
    var options = data.tags;
    // 加载小说一级类型内容
    $("#book_type_sub option").not(":first").remove();
    if(options){
        for(var i=0;i<options.length;i++){
            $("#book_type").append("<option value='"+ options[i].tagId + "'>" +  options[i].tagName + "</option>");
        }
    }
    // 打开对话框
    $( "#bookSearchModal").omDialog('open');
};

bookSearch.openSubjectCallback = function(data) {
    var options = data.subjects;
    
    if(options){
        for(var i=0;i<options.length;i++){
            $("#book_subject").append("<option value='"+ options[i].subjectId + "'>" +  options[i].subjectName + "</option>");
        }
    }
};

// 获取小说二级类型的回调
bookSearch.getTagsByFirstURLCallback = function(data) {
    $("#book_type_sub option").not(":first").remove();
    var options = data.tags;
    if(options){
        for(var i=0;i<options.length;i++){
            $("#book_type_sub").append("<option value='"+ options[i].tagId + "'>" +  options[i].tagName + "</option>");
        }
    }
};

bookSearch.bookSave = function(route) {
    var bookId = this.editBookId;
    var bookTitle = $('#book_title').val();
    var bookAuthor = $('#book_author').val();
    var bookDesc = $('#book_desc').val();
    var gender = $("#gender").val();
    var bookStatus = $("#book_status").val();
    var bookTypeSub = "";
    var subject = "";
    if(route == 1){
        bookTypeSub = $("#book_type_sub").val();
        subject = $("#book_subject").val();
    }
    
    if(!bookTitle){
        alert("请输入书名。")
        return;
    }
    
    if(!bookAuthor){
        alert("请输入作者。")
        return;
    }
    if(route == 1){
        if(!bookTypeSub){
            alert("请选择小说类型。")
            return;
        }
    }
    
    var param;
    if(route == 1){
        param = {"bookTitle":bookTitle, "bookAuthor":bookAuthor, "bookDesc":bookDesc, "gender":gender, "bookStatus":bookStatus, "subject":subject, "bookTypeSub":bookTypeSub};
    } else {
        param = {"bookId":bookId, "bookTitle":bookTitle, "bookAuthor":bookAuthor, "bookDesc":bookDesc, "gender":gender, "bookStatus":bookStatus};
    }
    
    var dataReq = new jsonReq();
    if(route == 1){
        dataReq.setEventListener("receive", bookSearch.saveStoryInfoURLCallback);
        dataReq.send(this.saveStoryInfoURL, param);
    } else {
        dataReq.setEventListener("receive", bookSearch.updStoryInfoCallback);
        dataReq.send(this.updStoryInfoURL, param);
    }
};

bookSearch.updStoryInfoCallback = function(data) {
    $( "#bookSearchModal").omDialog('close');
    if(data.exception == "ok") {
        alert("更新成功");
    } else {
        alert(data.exception);
    }
    bookSearch.getBookList();
};

bookSearch.saveStoryInfoURLCallback = function(data) {
    $( "#bookSearchModal").omDialog('close');
    if(data.exception == "ok") {
        alert("保存成功");
    } else {
        alert(data.exception);
    }
    bookSearch.getBookList();
};

bookSearch.getBookList = function() {
    var bookNM = $('#bookNM').val();
    var bookID = $('#bookID').val();
    // 获取数据
    $('#bookTable').omGrid('setData', bookSearch.getBookListURL + "?bookNM=" + bookNM + "&bookID=" + bookID);
};
