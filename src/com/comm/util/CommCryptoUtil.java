package com.comm.util;


import java.io.UnsupportedEncodingException;

/**
 * 《号百移动互联用户管理系统建议书》对应加密解密工具类(亲在哪专用)
 * @author lukai
 *
 */
public class CommCryptoUtil {

	
	/**
	 * 分割符
	 */
	public final static String SPLIT = "\\$";
	
	/**
	 * 分割符(无反斜杠)
	 */
	public final static String SPLIT_NOSLASH = "$";
	
	/**
	 * 默认编码
	 */
	private final static String DEFAULT_ENCODING = "UTF-8";
	

	
	
	// 以下专供异网短信平台使用
	/**
	 * 获取加密结果
	 * @param params	参与加密的参数
	 * @return 			密文
	 */
	public static String getEncValue(String[] params, String encKey, String ap) {
		String strEncVal = null;
		
		if (params != null && params.length > 2) {
			// 构造安全验证串
			StringBuilder sbSource = new StringBuilder();
			
			for (int i = 0; i < params.length; i++) {
				if (i < params.length - 1) {
					sbSource.append(params[i]).append(CommCryptoUtil.SPLIT_NOSLASH);
				} else {
					sbSource.append(params[i]);
				}
			}
			byte[] byteEncVal = CipherUtil.AESEncrypt(sbSource.toString()
					.getBytes(), TranscodeUtil.hexStrToByteArray(encKey), ap);
			
			strEncVal = TranscodeUtil.byteArrayToHexStr(byteEncVal).toUpperCase();
		}

		return strEncVal;
	}
	
	/**
	 * 获取解密结果
	 * @param encValue 	密文
	 * @param key		密钥
	 * @param ap		算法参数
	 * @return
	 */
	public static String getDecValue(String encValue, byte[] key, String ap) {
		String strDecVal = null;
		
		if (encValue != null && !"".equals(encValue)) {
			
			byte[] byteDecValBefore = TranscodeUtil.hexStrToByteArray(encValue.toLowerCase());
			byte[] byteDecValAfter = CipherUtil.AESDecrypt(byteDecValBefore, key, ap);
			if (byteDecValAfter == null) {
				return strDecVal;
			} else {
				try {
					strDecVal = new String(byteDecValAfter, DEFAULT_ENCODING);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return strDecVal;
	}
	
	/**
	 * 验证解密结果有效性
	 * @param inputParams	参数数组（不包含timestamp）
	 * @param encValue		密文
	 * @return
	 */
	public static boolean isValidEncVal(String[] inputParams, String encValue, String encKey, String ap) {
		boolean isValid = false;
		
		// STEP 1： 根据密文获得Digest值
		String strDecryptedDigest = "";
		String strDecryptedTimestamp = "";

		// 解密
		String decryptoText = getDecValue(encValue,
				TranscodeUtil.hexStrToByteArray(encKey), ap);

		// 解密结果为空，则解密失败
		if (decryptoText == null) {
			return isValid;
		}

		// 分割解密后的明文到字符串数组
		String[] decryptoParams = decryptoText.split(SPLIT);

		if (decryptoParams != null && decryptoParams.length > 0) {
			// 获取解密后数组的digest值
			strDecryptedDigest = decryptoParams[decryptoParams.length - 1];
			strDecryptedTimestamp = decryptoParams[0];

			// 将解密后的timestamp值与原有参数组成新的数组
			String[] tempParams = new String[inputParams.length + 1];
			for (int i = 0; i <= tempParams.length - 1; i++) {
				// timestamp占据新数组的第0项
				if (i == 0) {
					tempParams[i] = strDecryptedTimestamp;

					// 其余内容复制自原参数数组
				} else {
					tempParams[i] = inputParams[i - 1];
				}
			}

			// STEP 2: 根据参数数组计算Digest值
			// 计算digest值
			StringBuilder sbCalcSource = new StringBuilder();
			sbCalcSource.append(strDecryptedTimestamp);
			for (int i = 0; i < inputParams.length; i++) {
				sbCalcSource.append(inputParams[i]);
			}
			String strCalcDigest = CipherUtil
					.MD5Encode(sbCalcSource.toString());

			// 计算获得的digest值与解密后的degist值一致时，判断为“安全验证串”有效
			if (strCalcDigest.equals(strDecryptedDigest)) {
				isValid = true;
			}
		}
		
		return isValid;
	}
}
