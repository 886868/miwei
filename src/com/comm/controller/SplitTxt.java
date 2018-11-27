package com.comm.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Pattern;

import org.junit.Test;

import com.comm.util.DateUtil;

public class SplitTxt {

    @Test
    public void m1() {
        String fileNM = "D:/work/mw/血猎纵横.txt";
        String outFix = "D:/work/mw/sub/";
        FileInputStream  file = null;
        int line = 1;
        String lineStr = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        OutputStreamWriter isw = null;
        BufferedWriter bw = null;
        try {
            file = new FileInputStream(fileNM);
//            isr = new InputStreamReader(file,"UTF-8");
            isr = new InputStreamReader(file,"GB2312");
            br = new BufferedReader(isr);
            String pattern = ".*第.*章(\\s+){1,}.*";
            isw = new OutputStreamWriter(new FileOutputStream(outFix + (line-1) + ".txt"), "GB2312");
            bw = new BufferedWriter(isw);
            while((lineStr=br.readLine())!=null) {
                boolean isMatch = Pattern.matches(pattern, lineStr);
                if(isMatch) {
                    bw.flush();
                    System.out.println("time:" + DateUtil.getNowLast9() + " ,chapter:" + line + " ,content:" + lineStr.trim());    
                    isw = new OutputStreamWriter(new FileOutputStream(outFix + line + ".txt"), "GB2312");
                    bw = new BufferedWriter(isw);
                    bw.write(lineStr);
                    bw.newLine();
                    line++;
                } else {
                    bw.write(lineStr);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
