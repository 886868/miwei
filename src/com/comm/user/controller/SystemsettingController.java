package com.comm.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.util.CipherUtil;
import com.comm.util.CommCryptoUtil;
import com.comm.util.DateUtil;
import com.comm.util.ExceptionUtil;
import com.comm.web.common.util.PropUtil;

import net.sf.json.JSONObject;

@Controller
public class SystemsettingController {
	static Logger logger = Logger.getLogger(SystemsettingController.class);
	
	/**
     * 配置重置类型：全部载入
     */
    public static final String PROP_RESET_TYPE_ALL = "9";
    /**
     * 配置重置类型：系统配置
     */
    public static final String PROP_RESET_TYPE_ENV = "1";
    /**
     * 配置重置类型：仅MongoDB配置
     */
    public static final String PROP_RESET_TYPE_MONGODB = "2";
    /**
     * 配置重置类型：仅异网短信配置
     */
    public static final String PROP_RESET_TYPE_OTHERSMS = "3";
    /**
     * 配置重置类型：仅常量
     */
    public static final String PROP_RESET_TYPE_CONST = "4";
    /**
     * 配置重置类型：线程池再启动
     */
    public static final String PROP_RESET_TYPE_THREADPOOL = "5";
    
    private final static int HTTP_CONNECT_TIMEOUT = 60000;
	private final static int HTTP_READ_TIMEOUT = 60000;

	private final static String Z_CODE = "utf-8";
	
	private final static String URLDO = "/com/resetprop.do";

	
	/**
	 * 系统设置画面跳转
	 * 
	 * @author lqq
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user/systemsetting.do")
	public ModelAndView useraddstart(HttpServletRequest request)
			throws IOException {
		return new ModelAndView("systemsetting");
	}
	
	@RequestMapping("/user/dosetting.do")
	@ResponseBody
	public String dosetting(HttpServletRequest request) throws IOException, JSONException {
		logger.info(">>>> SystemsettingController.dosetting start <<<<");
		String systemname = request.getParameter("systemname");
		
		if (systemname.equals("sms")){
			org.json.JSONObject telmodel = new org.json.JSONObject();
//			telmodel = SmsOtherUtil.getBalance();
			telmodel.put("systemname", systemname);
			return telmodel.toString();
		}else{
			String resetType = request.getParameter("cq-key");
			// 获取加密解密密钥
	        String strEncKey = PropUtil.getProperty("PROP_RESET_KEY");
	        // 获取加密解密AP
	        String strAP = PropUtil.getProperty("PROP_RESET_AP");
	
	        // 获取时间戳
	        String timestamp = DateFormatUtils.format(new Date(), DateUtil.FMT_sdf17);
	
	        // 构造digest
	        StringBuilder sbToDigest = new StringBuilder();
	        sbToDigest.append(timestamp).append(resetType);
	        String digest = CipherUtil.MD5Encode(sbToDigest.toString());
	        System.out.println("testGdmcCrypto -> sbToDigest=" + sbToDigest.toString() + ", digest=" + digest);
	
	        // 构造安全验证串
	        String params[] = new String[3];
	        params[0] = timestamp;
	        params[1] = resetType;
	        params[2] = digest;
	        String encryptValue = CommCryptoUtil.getEncValue(params, strEncKey, strAP);
	        //String url = "http://172.60.100.114:8080/timer/com/resetprop.do?timestamp="+timestamp+"&resetType="+resetType+"&encryptValue="+encryptValue;
	        String url = "";
	        String content = "?timestamp="+timestamp+"&resetType="+resetType+"&encryptValue="+encryptValue;
			if (systemname.equals("qinzaina")){
				url = PropUtil.getProperty("PROP_RESET_URL_DW");
			}else if (systemname.equals("timer")){
				url = PropUtil.getProperty("PROP_RESET_URL_TM");
			}else if (systemname.equals("gdmc")){
				url = PropUtil.getProperty("PROP_RESET_URL_GDMC");
			}else if (systemname.equals("backup")){
				url = PropUtil.getProperty("PROP_RESET_URL_BK");
			}else if (systemname.equals("device")){
				url = PropUtil.getProperty("PROP_RESET_URL_DVC");
			}
			StringBuilder out = new StringBuilder();
			int res = HttpRequestGet(url+URLDO,content,out);
			logger.info(">>>> SystemsettingController.HttpRequestGet url,content: "+url+URLDO+content+"<<<<");
			logger.info(">>>> SystemsettingController.HttpRequestGet result: "+res+"<<<<");
			System.out.println("result:" + res);
			logger.info(">>>> SystemsettingController.HttpRequestGet out: "+JSONObject.fromObject(out.toString())+"<<<<");
			logger.info(">>>> SystemsettingController.dosetting end <<<<");
			JSONObject telmodel = new JSONObject();
			if (JSONObject.fromObject(out.toString()).get("result").equals("0")){
				telmodel.put("res", "sucess");
			}else{
				telmodel.put("res", "fail");
			}
	    	return telmodel.toString();
		}
	}
	
	protected int HttpRequestGet(String url, String content,StringBuilder out) {
		URL urlobj;
		HttpURLConnection connection = null;
		int result = 0;
		InputStream is = null;
		BufferedReader rd = null;

		try {
//			System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
//			System.setProperty("sun.net.client.defaultReadTimeout", "60000");
			urlobj = new URL(url + content);
			connection = (HttpURLConnection) urlobj.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);

			connection.setConnectTimeout(HTTP_CONNECT_TIMEOUT * 12);
			connection.setReadTimeout(HTTP_READ_TIMEOUT * 12);

			// Send request
			connection.connect();
			connection.getOutputStream().flush();

			// Get Response
			is = connection.getInputStream();
			rd = new BufferedReader(new InputStreamReader(is,
					Z_CODE));
			String line;
			while ((line = rd.readLine()) != null) {
				out.append(line);
				out.append('\r');
			}
//			rd.close();
			
		} catch (Exception e) {
			result = 1;
//			logger.error(tag, e);
			logger.error(ExceptionUtil.getExceptionInfo(e));
//			e.printStackTrace();
		} finally {
			try {
				if (rd != null) {
					rd.close();
				}
				if (is != null) {
					is.close();
				}
				
				if (connection != null){
					connection.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
