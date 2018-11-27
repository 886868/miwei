<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
body, html {width: 100%;height: 100%;;margin:0;}
</style>

<title>后台管理平台</title>
<style type="text/css">
        *{font-family:'Microsoft Yahei';}
    </style>
    <link rel="stylesheet" type="text/css" href="../css/default.css" />
    <link rel="stylesheet" type="text/css" href="../css/themes/default/om-all.css" />

    <script type="text/javascript" src="../js/util.js"></script>
    <script type="text/javascript" src="../js/jquery.js"></script>
    <script type="text/javascript" src="../js/jquery.json-2.4.js"></script>

    <script type="text/javascript" src="../js/ui/om-core.js"></script>    
    <script type="text/javascript" src="../js/ui/om-dialog.js"></script>
    <script type="text/javascript" src="../js/ui/om-tooltip.js"></script>
    <script type="text/javascript" src="../js/ui/om-tabs.js"></script>       
    <script type="text/javascript" src="../js/ui/om-calendar.js"></script>
    <script type="text/javascript" src="../js/ui/om-mouse.js"></script>
    <script type="text/javascript" src="../js/ui/om-draggable.js"></script>
    <script type="text/javascript" src="../js/ui/om-position.js"></script>
    <script type="text/javascript" src="../js/ui/om-messagebox.js"></script>
    <script type="text/javascript" src="../js/ui/om-button.js"></script>
    <script type="text/javascript" src="../js/ui/om-dialog.js"></script>
    <script type="text/javascript" src="../js/ui/om-resizable.js"></script>
    <script type="text/javascript" src="../js/ui/om-panel.js"></script>
    <script type="text/javascript" src="../js/ui/om-messagetip.js"></script>
    <script type="text/javascript" src="../js/ui/om-validate.js"></script>
    <script type="text/javascript" src="../js/ui/om-borderlayout.js"></script>    
    <script type="text/javascript" src="../js/ui/om-accordion.js"></script>  
    <!-- <script type="text/javascript" src="../js/ui/themeloader.js"></script> -->
    <script type="text/javascript" src="../js/ui/om-grid.js"></script>
    <script type="text/javascript" src="../js/ui/om-grid-roweditor.js"></script>
    <script type="text/javascript" src="../js/ui/om-combo.js"></script>
    <script type="text/javascript" src="../js/ui/om-grid-rowexpander.js"></script>
    <script type="text/javascript" src="../js/ui/om-progressbar.js"></script>
    <script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../js/ui/om-tree.js"></script>
    <script type="text/javascript" src="../js/ui/om-fileupload.js"></script>

    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/request.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../js/modifyPassword.js"></script>
    <script type="text/javascript" src="../js/md5.js"></script>
    <script type="text/javascript" src="../js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../js/timePicker.js"></script>
    <script type="text/javascript" src="../js/bookSearch.js"></script>
    <script type="text/javascript" src="../js/subjectMng.js"></script>
    <script type="text/javascript" src="../js/tagMng.js"></script>
    <script type="text/javascript" src="../js/bannerMng.js"></script>
    <script type="text/javascript">
        var m_r;
        var selectIds = [];
        var pagelist = [];
        $(document).ready(function() {
            
            //administrator.jsp准备用
            
            var trReqRule = new jsonReq();
            var rule = "${requestScope.rule}";
            var menulist = "${requestScope.menulist}";
            var mainmenuseq = "${requestScope.mainmenuseq}";
                    
            Main.menuload(rule,menulist,mainmenuseq);
            
            trReqRule.setEventListener("receive", adminCallbackGetRule);
            trReqRule.send(defaultWebUrl + "user/getRuleAdmin.do");
            
            function adminCallbackGetRule(data) {
                m_r = data;
            }
            
            $('body').omBorderLayout({
                panels:[{
                    id:"north-panel",
                    header:false,
                    height:50,
                    region:"north",
                 },{
                    id:"center-panel",
                         header:false,
                       region:"center",
                    },{
                     id:"west-panel",
                     resizable:false,
                     collapsible:false,
                      header:false,
                     region:"west",
                     width:225,
                 }]
             });
                        
            $("#id_menu_pwd").html("[设  置]");
            $("#id_menu_exit").html("[退  出]");
        });
    
        

    </script>
</head>
<body>

    <!-- north-panel -->        
        <div id="north-panel" style="white-space: nowrap;" >
            <div class="menu-background" style="height: 48px;" >
                <div class="menu-bg" style="height: 48px;" >
                    <div style="position: absolute;left: 240px;top: 15px;">                   
                        <div style="color:#fff">
                            ${requestScope.name}，欢迎您！
                        </div>
                    </div> 
                </div>               
            </div>
            
            
        </div>
    <!-- /north-panel -->
    <div id="center-panel">
         <div id="panel"></div>
     </div>
     
     <div id="west-panel" style="white-space: nowrap;" >
         <div id="menuaccordion" >              
             <div id="menuaccordiontab" style="height：100%; margin-left:20px;margin-top:10px; margin-right:20px;background:#F5F5F5;border-style: solid;border-color: #dddddd;border-width: 2px 2px 2px 2px;"></div>                 
        </div>
     </div>
     

<input id="curAdminId" type=hidden name="adminId" value="${requestScope.adminId}" />
<input id="curAdminarea" type=hidden name="curAdminarea" value="${requestScope.rule}" />
</body>
</html>

