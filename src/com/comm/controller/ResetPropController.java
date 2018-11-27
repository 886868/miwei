package com.comm.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comm.model.SystemEnv;
import com.comm.service.SystemEnvService;
import com.comm.util.ClassUtil;
import com.comm.util.CommCryptoUtil;
import com.comm.util.Const;


/**
 * [name]<br>
 * ResetPropController<br><br>
 * [function]<br>
 * 重新初始化PropUtil，以及使用到PropUtil的其它类<br><br>
 * [history]<br>
 * 2014/10/08 ver1.00 JiangJusheng<br>
 */
@Controller
public class ResetPropController extends BaseController {

    private static Logger logger = Logger.getLogger(ResetPropController.class);

    @Autowired
    private SystemEnvService systemEnvService;
    
    /**
     * 重新初始化PropUtil
     * @param HttpServletRequest request HTTP请求
     * @return 
     *         {"result":"0"}    设定成功
     *         {"result":"1"}    设定失败
     */
    @RequestMapping(value = "/com/resetprop.do")
    @ResponseBody
    public String reinitProp(HttpServletRequest request) {
        logger.info(">>>> reinitProp() start <<<<");
        
        JSONObject resultJsonObject = new JSONObject();
        
        // 获取加密解密密钥
        SystemEnv envEncKey = systemEnvService.getSystemEnv("PROP_RESET_KEY");
        if (envEncKey == null) {
            try {
                resultJsonObject.put(Const.COMM_CTRL_RST_KEY,
                        Const.COMM_CTRL_RST_VAL_FAIL);
            } catch (JSONException e) {
                logger.error(ClassUtil.getExceptionInfo(e));
            }
            return resultJsonObject.toString();
        }
        String strEncKey = envEncKey.getValue();
        
        // 获取加密解密AP
        SystemEnv envEncAp = systemEnvService.getSystemEnv("PROP_RESET_AP");
        if (envEncAp == null) {
            try {
                resultJsonObject.put(Const.COMM_CTRL_RST_KEY,
                        Const.COMM_CTRL_RST_VAL_FAIL);
            } catch (JSONException e) {
                logger.error(ClassUtil.getExceptionInfo(e));
            }
            return resultJsonObject.toString();
        }
        String strAP = envEncAp.getValue();
        
        String resetType = request.getParameter("resetType");
        String encVal = request.getParameter("encryptValue");
        
        // 参与安全码校验的参数包括：orderno,status
        String[] encParam = new String[1];
        encParam[0] = resetType;
        // 进行安全验证码校验
        boolean isEncryptValid = CommCryptoUtil.isValidEncVal(encParam, encVal, strEncKey, strAP);
        
        logger.info("resetType=" + resetType + ", encVal=" + encVal
                + ", isEncryptValid=" + isEncryptValid);
        
        if (!isEncryptValid) {
            try {
                resultJsonObject.put(Const.COMM_CTRL_RST_KEY,
                        Const.COMM_CTRL_RST_VAL_FAIL);
            } catch (JSONException e) {
                logger.error(ClassUtil.getExceptionInfo(e));
            }
            return resultJsonObject.toString();
        }
        try {
            resultJsonObject.put(Const.COMM_CTRL_RST_KEY,
                    Const.COMM_CTRL_RST_VAL_SUCC);
        } catch (JSONException e) {
            logger.error(ClassUtil.getExceptionInfo(e));
        }
        logger.info(">>>> reinitProp() end <<<<");
        return resultJsonObject.toString();
    }

}
