package com.comm.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class StringUtil {

    /**
     * 常量：添加到前端
     */
    public static final int ADD_TO_FRONT = 0;

    /**
     * 常量：添加到尾部
     */
    public static final int ADD_TO_TAIL = 1;

    static Logger logger = Logger.getLogger(StringUtil.class);

    public static String strArrayToString(String[] array) {
        StringBuffer sb = new StringBuffer();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static String[] spiltString(String str, String regex) {
        String[] result = str.split(regex);

        return result;
    }

    public static boolean eq(String Conststr, String str2) {
        if (Conststr.equals(str2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkBlank(String str) {

        if ("".equals(str) || str == null) {
            return true;
        } else {
            return false;
        }
    }
    // public static void systemOutput(String tag,String output){
    // logger.info(new StringBuffer(tag).append(" ").append(output).toString());
    // }

    // public static void systemOutput(String tag, String methodName, String
    // output) {
    // logger.info(new StringBuffer(tag).append(" ").append(methodName)
    // .append(" ").append(output).toString());
    // }

    public static boolean isEmpty(String str) {
        if ("".equals(str) || str == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStrEmpty(String str) {
        if ("".equals(str) || str == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStrNotEmpty(String str) {
        if ("".equals(str) || str == null) {
            return false;
        }
        return true;
    }

    public static String null2Str(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static JSONObject getRetJsonObject(String ret) {
        JSONObject retJsonObject = new JSONObject();
        try {
            retJsonObject.put(MsgBundle.RESULT, ret);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retJsonObject;
    }

    public static final String getPicName0(String account, String faccount, long timeStamp) {
        return account + "_" + faccount + "_0_" + timeStamp + ".jpg";
    }

    public static final String getPicName1(String account, String faccount, long timeStamp) {
        return account + "_" + faccount + "_1_" + timeStamp + ".jpg";
    }

    public static final boolean isObjEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        return false;
    }

    public static final boolean isObjNotEmpty(Object obj) {
        if (null != obj) {
            return true;
        }
        return false;
    }

    public static final boolean isFileExists(File file) {
        if (null == file) {
            return false;
        }
        return file.exists();
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>int</code>, returning
     * <code>zero</code> if the conversion fails.
     * </p>
     *
     * <p>
     * If the string is <code>null</code>, <code>zero</code> is returned.
     * </p>
     *
     * <pre>
     *   StringUtil.parseInt(null) = 0
     *   StringUtil.parseInt("")   = 0
     *   StringUtil.parseInt("1")  = 1
     * </pre>
     *
     * @param str
     *            the string to convert, may be null
     * @return the int represented by the string, or <code>zero</code> if
     *         conversion fails
     */
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    /**
     * <p>
     * Convert a <code>String</code> to an <code>int</code>, returning a default
     * value if the conversion fails.
     * </p>
     *
     * <p>
     * If the string is <code>null</code>, the default value is returned.
     * </p>
     *
     * <pre>
     *   StringUtil.parseInt(null, 1) = 1
     *   StringUtil.parseInt("", 1)   = 1
     *   StringUtil.parseInt("1", 0)  = 1
     * </pre>
     *
     * @param str
     *            the string to convert, may be null
     * @param defaultValue
     *            the default value
     * @return the int represented by the string, or the default if conversion
     *         fails
     */
    public static int parseInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        str = str.trim();
        if (str.length() == 0) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            logger.error(str, nfe);
            return defaultValue;
        }
    }

    public static final String getNotNull(String source, String defaultStr) {
        if (isStrNotEmpty(source)) {
            return source;
        }
        return defaultStr;
    }

    public static String md5s(String plainText) {
        String str;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();

            // logger.debug("结果1 : " + buf.toString());// 32位的加密
            // logger.debug("结果2 : " + buf.toString().substring(8, 24));//
            // 16位的加密
            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串前补0
     *
     * @param str
     *            字符串
     * @param strLength
     *            指定长度
     *
     * @return String 补0后的字符串
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            StringBuffer rslt = new StringBuffer();
            while (strLen < strLength) {
                rslt.append("0");
                strLen++;
            }
            rslt.append(str);
            str = rslt.toString();
        }
        return str;
    }

    /**
     * 字符串填充补全
     *
     * @param str
     *            字符串
     * @param length
     *            指定长度
     * @param fitStr
     *            补全字符串
     * @param option
     *            指定补空格的方向 (0：向前补 1：向后补)
     *
     * @return String 填充补全后的字符串
     */
    public static String addFitString(String str, int length, String fitStr, int option) {
        int strLen = str.length();
        if (strLen < length) {
            StringBuffer rslt = new StringBuffer();
            switch (option) {
            case ADD_TO_FRONT:
                while (strLen < length) {
                    rslt.append(fitStr);
                    strLen++;
                }
                rslt.append(str);
                break;
            case ADD_TO_TAIL:
                rslt.append(str);
                while (strLen < length) {
                    rslt.append(fitStr);
                    strLen++;
                }
                break;
            }
            str = rslt.toString();
        }
        return str;
    }

}
