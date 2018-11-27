var editorMng = new function(){
    this.getbannerListURL = defaultWebUrl + "bookadmin/getBannerList.do";
    this.editbannerListURL = defaultWebUrl + "bookadmin/editBannerList.do";
};

editorMng.init = function(){
    var ue = UE.getEditor('container');
};
