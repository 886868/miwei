package com.comm.util;

import java.util.List;

import org.apache.log4j.Logger;

public class ObjUtil {
	private static final String tag = ObjUtil.class.getSimpleName();
    static Logger logger = Logger.getLogger(ObjUtil.class);
    public static final void logInfo(String str){
    	logger.info(str);
    	System.out.println(str);
    }
    
    public static final void logInfo(String... str){
    	StringBuilder sb = new StringBuilder();
    	String[] strings = (String[])str;
    	for (String string : strings) {
    		sb.append(" ").append(string);
		}
    	logger.info(sb.toString());
    	System.out.println(sb.toString());
    }
    
	public static final void logError(String str,Exception e){
		logger.error(str, e);
		e.printStackTrace();
	}
	
	public static final void logError(String str){
		logger.error(str);
		System.out.println(str);
	}
	
	public static final boolean isListZero(List list){
		if(null == list || 0 == list.size()){
			return true;
		}
		return false;
	}
	
	public static final boolean isListNotZero(List list){
		if(null == list || 0 == list.size()){
			return false;
		}
		return true;
	}
	
	public static final boolean isObjNull(Object obj){
		if(null == obj){
			return true;
		}
		return false;
	}
	
	public static final boolean isObjNotNull(Object obj){
		if(null == obj){
			return false;
		}
		return true;
	}
	
	public static final String obj2str(Object obj){
		if(null == obj){
			return "";
		}
		return String.valueOf(obj);
	}
}
