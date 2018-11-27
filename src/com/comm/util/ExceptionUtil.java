package com.comm.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    /**
     * 把异常中的堆栈信息转化为文字列
     * 
     * @param ex 异常
     * @return 异常信息
     */
    public static String getExceptionInfo(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
    }

}
