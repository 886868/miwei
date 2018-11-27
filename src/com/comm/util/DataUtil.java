package com.comm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataUtil {
	public static byte[] read(InputStream inputSream) {
        ByteArrayOutputStream outStream = null;
        int len = -1;
        byte[] buffer = new byte[1024];
        byte[] bRet = null;
        try {
        	outStream = new ByteArrayOutputStream();
			while ((len = inputSream.read(buffer)) != -1) {
			    outStream.write(buffer, 0, len);
			}
			bRet = outStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(outStream != null) {
					outStream.close();
				}
				if(inputSream != null) {
					inputSream.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
		}
        return bRet;
    }
}
