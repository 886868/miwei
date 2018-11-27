package com.comm.util;

import java.io.UnsupportedEncodingException;

import com.comm.web.common.util.PropUtil;

public class AESHelper {

//	public static final String strAESKey = "hKrBL1SrZm7Pwqg8Z2kIyA==";
	public static final String strAESKey = PropUtil.getProperty("WEIXIN_IF_AES_KEY");
	
	public static final byte[] byteAESKey = TranscodeUtil.base64StrToByteArray(strAESKey);

//	public static final String algorithmParameter = "1234567890abcdef";
	public static final String algorithmParameter = PropUtil.getProperty("WEIXIN_IF_AP");
	
	/**
	 * 解密
	 */
	public static String AESDecrypt (String input) throws UnsupportedEncodingException {

		byte[] byteDecValue = TranscodeUtil.hexStrToByteArray(input);
		byteDecValue = CipherUtil.AESDecrypt(byteDecValue, byteAESKey, algorithmParameter);
		String strDecValue = new String(byteDecValue, "UTF-8");
			
		return strDecValue;
	}
	
	/**
	 * 加密
	 */
	public static String AESEncrypt (String input) throws UnsupportedEncodingException {
	
		byte[] byteEncValue = CipherUtil.AESEncrypt(input.getBytes("UTF-8"), byteAESKey, algorithmParameter);
		String strEncValue = TranscodeUtil.byteArrayToHexStr(byteEncValue);
				
		return strEncValue;
	}
	
	public static String getReturnStr(String input) {
		StringBuilder sbRet = new StringBuilder();
		sbRet.append("value=").append(input);
		return sbRet.toString();
	}

}
