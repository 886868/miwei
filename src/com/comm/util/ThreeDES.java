package com.comm.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ThreeDES {
    
    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    /**
     * 
     * 3DES 加密
     * @param src   待加密内容
     * @param key   秘钥
     * @return
     * @throws Exception
     */
    public static String encryptThreeDESECB( String src,  byte[] key) throws Exception {
         DESedeKeySpec dks = new DESedeKeySpec(key);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
         SecretKey securekey = keyFactory.generateSecret(dks);

         Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
         byte[] b = cipher.doFinal(src.getBytes());

         BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

    }

    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    /**
     * 3DES 解密
     * @param src   待解密内容
     * @param key   秘钥
     * @return
     * @throws Exception
     */
    public static String decryptThreeDESECB( String src,  byte[] key) throws Exception {
        // --通过base64,将字符串转成byte数组
         BASE64Decoder decoder = new BASE64Decoder();
         byte[] bytesrc = decoder.decodeBuffer(src);
        // --解密的key
         DESedeKeySpec dks = new DESedeKeySpec(key);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
         SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
         Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
         byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }
}
