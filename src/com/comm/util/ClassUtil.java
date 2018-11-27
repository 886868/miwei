package com.comm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.comm.model.StoryDirInfo;
import com.comm.model.StoryInfo;
import com.comm.service.StoryInfoService;

public class ClassUtil {

    
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
    
    public static int cntHZ(String str){
        int cnt = 0;
        Pattern pattern =  Pattern.compile("([\u4e00-\u9fa5]{1})"); //定义匹配模式:1个汉字
        
        //汉字匹配，统计字数
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) cnt++;
        return cnt;
    }
    
    public static void saveStory(String filePath, InputStream fs, StoryInfoService storyInfoService, StoryInfo si) {
        int line = 1;
        String lineStr = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        OutputStreamWriter isw = null;
        BufferedWriter bw = null;
        
        try {
            List<StoryDirInfo> dirs = new ArrayList<StoryDirInfo>();
            isr = new InputStreamReader(fs,"GB2312");
            br = new BufferedReader(isr);
//            String pattern = ".*第.*章(\\s+){1,}.*";
//            String pattern = ".*第.*章{1,}.*";
//            String pattern_1 = "^(\\s{0,})第.*章\\s.*";
            String pattern_1 = "^(\\s{0,})第.*章(\\s|\\:|\\：).*";
            String pattern_2 = "^(\\s{0,})\\d{3,}\\s.*";
            String chLink = filePath + UUIDUtil.minimizedUUID() + ".txt";
            isw = new OutputStreamWriter(new FileOutputStream(Const.LOCAL_PATH + chLink), "GB2312");
            bw = new BufferedWriter(isw);
            String nowDate = DateUtil.getNow17();
            StoryDirInfo dir = new StoryDirInfo();
            dir.setBookId(si.getBookId());
            dir.setChLink(chLink);
            dir.setChNo(line-1);
            dir.setChTitle("前言");
            dir.setCrDate(nowDate);
            dir.setUpdDate(nowDate);
            dirs.add(dir);
            
            int cntHZ = 0;
            while((lineStr=br.readLine())!=null) {
//                boolean isMatch = Pattern.matches(pattern, lineStr);
                
                boolean isMatch = Pattern.matches(pattern_1, lineStr) || Pattern.matches(pattern_2, lineStr);
                cntHZ += cntHZ(lineStr);
//                if(isMatch && lineStr.length() <= 50) {
                if(isMatch) {
                    bw.flush();
                    chLink = filePath + UUIDUtil.minimizedUUID() + ".txt";
                    isw = new OutputStreamWriter(new FileOutputStream(Const.LOCAL_PATH + chLink), "GB2312");
                    bw = new BufferedWriter(isw);
                    bw.write(lineStr);
                    bw.newLine();
                    StoryDirInfo dirTmp = new StoryDirInfo();
                    dirTmp.setBookId(si.getBookId());
                    dirTmp.setChLink(chLink);
                    dirTmp.setChNo(line);
                    dirTmp.setChTitle(lineStr);
                    dirTmp.setCrDate(nowDate);
                    dirTmp.setUpdDate(nowDate);
                    dirs.add(dirTmp);
                    line++;
                } else {
                    bw.write(lineStr);
                    bw.newLine();
                }
            }
            si.setBookSaveDir(filePath.toString());
            si.setChapterCount(line);
            si.setChUpdDate(nowDate);
            si.setUpdDate(nowDate);
            si.setWordCount(cntHZ);
            storyInfoService.saveUpdBook(si, dirs);
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
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
}