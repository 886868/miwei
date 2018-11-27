
function jsonReq() {
	
	this._handler = {};
}



jsonReq.prototype.send = function (reqUrl, param, form) {
	var req = this;
	var sendStr = JSON.stringify(param);
	if(form){
		sendStr = $.toJSON(form);
	}
    $.ajax({
        url : reqUrl,
        type : "POST",
        data : sendStr,
        contentType : 'application/json',
        dataType : 'json',
        cache : false,
        beforeSend : function () {
        },
        success : function(data, status) {
        	if(data) {
        		if(data.timeout) {
        			// 	
        			// openAD("登录超时，请重新登录！");
        			top.location=defaultWebUrl + "user/errUrl.do?errLogin=2";
        			return;
        		} else if(data.doubleLogin) {
        			// openAD("您的账号在别处登录.如非本人操作，请注意账号安全。");
        			top.location=defaultWebUrl + "user/errUrl.do?errLogin=3";
        			return;
        		} else if(data.loginException) {
        			// openAD("您的账号存在登录异常，请重新登录。");
        			top.location=defaultWebUrl + "user/errUrl.do?errLogin=4";
        			return;
        		}
        	}

        	
            var sucFunc = req._handler['receive'];
            
            if (sucFunc) {
                sucFunc.call(this, data, status);//处理 回调函数，data 由json传递回来
            }
        },
        error : function(msg) {
            var errFunc = req._handler['failed'];
                if (errFunc) {
                    errFunc.call(this, msg);
                } 
        },
        complete : function(data, status) {
        }
    });
};

jsonReq.prototype.setEventListener = function(eventName, func) {
    this._handler[eventName] = func;
};
