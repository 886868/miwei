<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>用户登录</title>
<link rel="stylesheet" type="text/css" href="../css/default.css" />
<link rel="stylesheet" type="text/css" href="../css/loginup.css" />

<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.json-2.4.js"></script>
<script type="text/javascript" src="../js/md5.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#codeimg').attr("src",defaultPostUrl + "/user/im.do");
    });

    function onFormSubmitReg(){
        
        var account = $("#account").val();
        if(!account) {
            alert("用户名不能为空。");
            return;
        }
        
        var account = $("#password").val();
        if(!account) {
            alert("密码不能为空。");
            return;
        }
        
        $("#loginTime").val(getFDeviceNumbyDate());
        $("#passwordcre").val(hex_md5($("#password").val()));
        $("#password").val("");
        document.forms[0].action=defaultPostUrl + "/user/loginup.do";
        document.forms[0].submit();
        
        // 判断验证码是否正确
//      var result = isRightcode().replace(/[\n\r]/ig,'');
//      if(result=='success') {
//          $("#loginTime").val(getFDeviceNumbyDate());
//          $("#passwordcre").val(hex_md5($("#password").val()));
//          $("#password").val("");
//          document.forms[0].action=defaultPostUrl + "/user/loginup.do";
//          document.forms[0].submit();
//      } else if(result=='fail') {
//          alert("验证码输入错误！");
//      } else if(result == "") {
//          alert("请输入验证码");
//      } else if(result == "timeout") {
//          alert("验证码已过期，请点击验证码刷新后重试。");
//      }
    }
    function onFormSubmitReg1(){
        // 判断验证码是否正确
        var result = isRightcode().replace(/[\n\r]/ig,'');
        if(result=='success') {
            document.getElementById("showCheckCode").style.visibility='';
            document.getElementById("messageImg").src="../css/images/login/dui2.gif";
        } else if(result=='fail') {
            document.getElementById("showCheckCode").style.visibility='';
            document.getElementById("messageImg").src="../css/images/login/cuo2.gif";
            
        } else if(result == "" || result == 'timeout' ) {
            document.getElementById("showCheckCode").style.visibility='hidden';
        }
    }

    function refresh(obj) {
        obj.src = defaultPostUrl + "/user/im.do?" + Math.random();
        document.getElementById("showCheckCode").style.visibility='hidden';
        document.getElementById("icode").value='';
    }

    /*
     *
     * return 
     *
     *  1，空 表示验证码未输入；
     *  2，success 表示输入验证码正确；
     *  3，fail 表示输入验证码错误； 
     *
     */
    function isRightcode() {
        var code = $("#icode").val();
        var result = "";
        if(code) {
            code = "icode=" + code;
            $.ajax({
                type : "GET",
                url : defaultPostUrl + "/user/checkCode.do",
                async : false,
                data : code,
                success : function(data) {
                    result = data;
                }
            });
        }
        
        return result;
    }
    /**
    * errLogin 
    * 1:手机号或者密码错误
    */
    function showErr() {
        var value = "${requestScope.errLogin}";
        if ("1" == value) {
            // div.innerHTML="手机号或者密码错误";
            alert("用户名或者密码错误");
        } else if("2" == value) {
            alert("登录超时，请重新登录！");
        } else if("3" == value) {
            alert("您的账号在别处登录。如非本人操作，请注意账号安全。");
        } else if("4" == value) {
            alert("您的账号存在登录异常，请重新登录。");
        } else if("999" == value) {
            alert("连接访问错误，请重新登录。");
        } 
        
    }
</script>
<style type="text/css">
* {
    font-family: 'Microsoft Yahei';
}

input::-webkit-input-placeholder {
    /* placeholder颜色  */
    color: #1d71eb;
    /* placeholder字体大小  */
    font-family: "微软雅黑";
    font-size: 14px;
    /* placeholder位置  */
    text-align: left;
/*  font-weight: bold; */
}

input {
    border: 1px solid red;
}
</style>
</head>
<body onload=showErr();>
    <div class='loginup-main-div'>
        <div>
            <form method="post" id="login" id="form1">
                <div class="loginup-form">
                    <div
                        style="margin-top: 150px; margin-left: -365px; background: #fff; height: 200px; width: 250px;">
                        <table class="loginup-form-table" width="90%" border="0">
                            <tr>
                                <td colSpan="2"><input style="border-radius: 0px; background: #fff; width: 183px; height: 25px;"
                                    placeholder="账&nbsp;号" id="account" type="text" name="account" size="25" maxlength='11' /></td>
                            </tr>
                            <tr>
                                <td colSpan="2"><input style="border-radius: 0px; width: 183px; height: 25px;"
                                    placeholder="密&nbsp;码" id="password" type="password" name="password" size="25" maxlength="20" /> 
                                    <input type="hidden" id="passwordcre" name="passwordcre" /></td>
                            </tr>
                            <!--                            <tr> -->
                            <!--                                <td>验证码</td> -->
                            <!--                                <td><input type="text" style="border-radius:5px;" id="icode" size="8" maxlength="4" title="单击验证码输入框，获取验证码" onBlur="onFormSubmitReg1();"/></td> -->
                            <!--                                <td style="cursor:pointer;"><img onclick="refresh(this);" id="codeimg" /></td> -->
                            <!--                                <td id="showCheckCode" style="visibility:hidden;"><img id="messageImg" width='16' height='16' ></td> -->
                            <!--                            </tr> -->
                        </table>
                        <div style="margin-left: -35px; margin-top: 0px; width: 85px; height: 25px;">
                            <div id="btn-login" class="dialog-btn-login-ac" onclick="onFormSubmitReg();"></div>
                        </div>
                    </div>
                    <div class="loginup-form-copyright">© Copyright 2018</div>
                </div>
                <input type="hidden" id="loginTime" name="loginTime" />
            </form>
        </div>
    </div>
</body>
</html>