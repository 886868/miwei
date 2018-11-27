package com.comm.util;

import java.util.Random;

public class CommUtil {

    /**
     * string null转空字符串
     * 
     * @param str
     * @return
     */
    public static String convertNullToEmpty(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * str 转 int
     * 
     * @param str
     * @param defaultValue
     *            转换失败返回-1
     * @return
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * str 转 int 默认值为 -1
     * 
     * @param str
     * @return
     */
    public static int toInt(String str) {
        return toInt(str, -1);
    }

    /**
     * 
     * @param str
     * @return
     */
    public static float toFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * 生成6位手机短信验证码
     *
     */
    public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

    public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }

}
