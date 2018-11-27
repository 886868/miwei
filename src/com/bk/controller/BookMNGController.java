package com.bk.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.comm.controller.BaseController;
import com.comm.dto.BKCommRes;
import com.comm.dto.GridDataModel;
import com.comm.model.StoryDirInfo;
import com.comm.model.StoryInfo;
import com.comm.model.StorySubject;
import com.comm.model.StorySubjectDiv;
import com.comm.model.StoryTag;
import com.comm.model.StoryTagDiv;
import com.comm.service.StoryDirInfoService;
import com.comm.service.StoryInfoService;
import com.comm.service.StorySubjectDivService;
import com.comm.service.StoryTagDivService;
import com.comm.util.ClassUtil;
import com.comm.util.Const;
import com.comm.util.DateUtil;
import com.comm.util.HttpUtil;
import com.comm.util.ResMessageConst;
import com.comm.util.StringUtil;
import com.comm.util.UUIDUtil;

import net.sf.json.JSONObject;

@Controller
public class BookMNGController extends BaseController {
    
    /**
     * LOG输出
     */
    private static Logger logger = Logger.getLogger(BookMNGController.class);
    
    @Autowired
    private StoryInfoService storyInfoService;
    
    @Autowired
    private StoryDirInfoService storyDirInfoService;
    
    @Autowired
    private StoryTagDivService storyTagDivService;
    
    @Autowired
    private StorySubjectDivService storySubjectDivService;
    
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/saveResTag.do")
    @ResponseBody
    public BKCommRes saveResTag(HttpServletRequest request) {
        logger.debug(">>>> saveResTag() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString("bookId");
        String resId = reqObj.optString("resTag");
        
        StoryTagDiv stdTmp = storyTagDivService.getItem(bookId, resId);
        
        if(stdTmp != null) {
            return new BKCommRes("类型已存在，不可重复添加。");
        }
        
        String nowDate = DateUtil.getNow17();
        String uuid = UUIDUtil.minimizedUUID();
        StoryTagDiv std = new StoryTagDiv();
        std.setBookId(bookId);
        std.setTagId(resId);
        std.setCrDate(nowDate);
        std.setUpdDate(nowDate);
        std.setUuid(uuid);
        
        storyTagDivService.save(std);
        
        logger.info(">>>> saveResTag() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/saveResSub.do")
    @ResponseBody
    public BKCommRes saveResSub(HttpServletRequest request) {
        logger.debug(">>>> saveResSub() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString("bookId");
        String resId = reqObj.optString("resSub");
        
        StorySubjectDiv ssdTmp = storySubjectDivService.getIteam(bookId, resId);
        
        if(ssdTmp != null) {
            return new BKCommRes("主题已存在，不可重复添加。");
        }
        
        String nowDate = DateUtil.getNow17();
        String uuid = UUIDUtil.minimizedUUID();
        
        StorySubjectDiv ssd = new StorySubjectDiv();
        ssd.setBookId(bookId);
        ssd.setSubjectId(resId);
        ssd.setCrDate(nowDate);
        ssd.setUpdDate(nowDate);
        ssd.setUuid(uuid);
        storySubjectDivService.save(ssd);
        logger.info(">>>> saveResSub() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
    /**
     * 通过小说id获取小说目录
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/getDirById.do")
    @ResponseBody
    public String getDirById(@RequestParam(name="bookId")String bookId, HttpServletRequest request) {
        logger.debug(">>>> getDirById() start <<<<");
//        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
//        String bookId = reqObj.optString(JsonConst.REQ_KEY_BOOK_ID);
        
        GridDataModel<StoryDirInfo> model = new GridDataModel<StoryDirInfo>();
        // 小说主题不能为空
        if(StringUtil.isEmpty(bookId)) {
            logger.error("小说编号为空");
            model.setDataStr(ResMessageConst.RES_MSG_BOOK_LIST_SUBJECT_003);
            return JSONObject.fromObject(model).toString();
        }
        List<StoryDirInfo> lst = storyDirInfoService.getFullLstByBookId(bookId);
        logger.debug(">>>> getDirById() end <<<<");
        model.setRows(lst);
        model.setDataStr("查询结果列表显示");
        return JSONObject.fromObject(model).toString();
    }
        
    
    @RequestMapping("bookadmin/previewImg.do")
    @ResponseBody
    public String previewImg(HttpServletRequest request) {
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String resUrl = reqObj.optString("imgUrl");
        JSONObject res = new JSONObject();
        res.put("exception", "ok");
        
        if(StringUtil.isEmpty(resUrl)) {
            res.put("exception", "无效数据。");
        }
        
        File file = new File(Const.LOCAL_PATH + resUrl);
        InputStream is = null;
        ByteArrayOutputStream byteStream = null;
        try {
            is = new FileInputStream(file);
            byte[] by = new byte[is.available()];
            byteStream = new ByteArrayOutputStream();
            byte[] bb = new byte[1024];
            int ch = is.read(bb);
            while (ch != -1) {
                byteStream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = byteStream.toByteArray();
            String img64 = Base64.getEncoder().encodeToString(by);
            res.put("resStr", img64);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            res.put("exception", "系统异常。");
        } catch (IOException e) {
            e.printStackTrace();
            res.put("exception", "系统异常。");
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteStream != null) {
                try {
                    byteStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res.toString();
        
    }
    
    @RequestMapping("bookadmin/delRes.do")
    @ResponseBody
    public BKCommRes delRes(HttpServletRequest request) {
        logger.info(">>>> delRes() begin <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString("bookId");
        String resType = reqObj.optString("resType");
        
        StoryInfo si = storyInfoService.getByBookId(bookId);
        
        if(si == null) {
            return new BKCommRes("小说不存在。");
        }
        
        if(StringUtils.equals("3", resType)) {
            // 删除小说目录及实体
            String fdNM = si.getBookSaveDir();
            File fd = new File(Const.LOCAL_PATH + fdNM);
            
            if(fd.exists()) {
                ClassUtil.deleteDir(fd);
            }
            
            si.setBookSaveDir("");
            si.setChapterCount(0);
            si.setWordCount(0);
            si.setChUpdDate("");
            si.setUpdDate(DateUtil.getNow17());
            
            storyInfoService.delBookDir(si);
            
        }else if(StringUtils.equals("1", resType)) {
            String fdNM = si.getCoverUrl();
            File file = new File(Const.LOCAL_PATH + fdNM);
            
            if(file.exists()) {
                file.delete();
            }
            
            si.setCoverUrl("");
            si.setUpdDate(DateUtil.getNow17());
            storyInfoService.update(si);
        }else if(StringUtils.equals("2", resType)) {
            String fdNM = si.getCoverBannerUrl();
            File file = new File(Const.LOCAL_PATH + fdNM);
            
            if(file.exists()) {
                file.delete();
            }
            
            si.setCoverBannerUrl("");
            si.setUpdDate(DateUtil.getNow17());
            storyInfoService.update(si);
        }else if(StringUtils.equals("4", resType)) {
            String resId = reqObj.optString("resId");
            if(StringUtil.isEmpty(resId)){
                return new BKCommRes("更新的数据为空。");
            }
            storyTagDivService.del(bookId, resId);
        }else if(StringUtils.equals("5", resType)) {
            String resId = reqObj.optString("resId");
            if(StringUtil.isEmpty(resId)){
                return new BKCommRes("更新的数据为空。");
            }
            
            storySubjectDivService.del(bookId, resId);
        }
        
        logger.info(">>>> delRes() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
        
    @RequestMapping("/bookadmin/bookResUpload.do")
    @ResponseBody
    public BKCommRes bookResUpload(@RequestParam(value = "uploadFile", required = false) MultipartFile[] resFiles, 
            HttpServletRequest request) throws IOException, Exception{
        logger.info(">>>> bookResUpload() begin <<<<");
        String bookId = request.getParameter("bookId");
        String resType = request.getParameter("resType");

        if(resFiles == null || resFiles.length < 1) {
            return new BKCommRes("上传的文件为空");
        }
        
        MultipartFile myFile = resFiles[0];
        
        String suffix = myFile.getOriginalFilename().substring(myFile.getOriginalFilename().indexOf("."),
                myFile.getOriginalFilename().length()).toLowerCase();
        
        
        if( !StringUtils.equals(".jpg", suffix) 
                && !StringUtils.equals(".png", suffix) 
                && !StringUtils.equals(".gif", suffix)
                && !StringUtils.equals(".txt", suffix)){
            // 返回结果
            return new BKCommRes("不支持此类型的文件。");
        }
        
        StoryInfo si = storyInfoService.getByBookId(bookId);
        
        if(si == null) {
            return new BKCommRes("小说不存在。");
        }
        
        StringBuffer filePath = new StringBuffer();
        String fileNM = UUIDUtil.minimizedUUID() + suffix;
        
        filePath.append("/content/")
        .append(bookId);
        
        if(StringUtils.equals(Const.RESOURCETYPE_STORY, resType)) {
            filePath.append("/txt/");
            File fileFd = new File(Const.LOCAL_PATH + filePath.toString());
            fileFd.mkdirs();
            // make txt file
           ClassUtil.saveStory(filePath.toString(), myFile.getInputStream(), storyInfoService, si);
           
        } else {
            filePath.append("/img/");
            File fileFd = new File(Const.LOCAL_PATH + filePath.toString());
            fileFd.mkdirs();
            File imgFile = new File(Const.LOCAL_PATH + filePath.toString() + fileNM);
            
            // 写入磁盘
            byte[] data = ClassUtil.readInputStream(myFile.getInputStream());  
            
            FileOutputStream os = new FileOutputStream(imgFile); 
            os.write(data);
            os.close();
            
            String oldFile = "";
            
            if(StringUtils.equals(Const.RESOURCETYPE_COVERIMG, resType)) {
                oldFile = si.getCoverUrl();
                si.setCoverUrl(filePath.toString() + fileNM);
            } else if(StringUtils.equals(Const.RESOURCETYPE_PHOTOIMG, resType)) {
                oldFile = si.getCoverBannerUrl();
                si.setCoverBannerUrl(filePath.toString() + fileNM);
            }
            
            if(!StringUtil.isEmpty(oldFile)){
                File of = new File(Const.LOCAL_PATH + oldFile);
                
                if(of.exists()) {
                    of.delete();
                }
            }
            
            String updDate = DateUtil.getNow17();
            si.setUpdDate(updDate);
            storyInfoService.update(si);
        }
        
        logger.info(">>>> bookResUpload() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
    @RequestMapping(value = "/bookadmin/getResInfo.do")
    @ResponseBody
    public String getResInfo(HttpServletRequest request, @RequestParam(name="bookId")String bookId) {

        logger.debug(">>>> getResInfo() start <<<<");
        
        StoryInfo si = storyInfoService.getByBookId(bookId);
        
        String cover = si.getCoverUrl();
        String banner = si.getCoverBannerUrl();
        String bookDir = si.getBookSaveDir();
        
        List<JSONObject> lst = new ArrayList<JSONObject>();
        if(!StringUtil.isEmpty(cover)) {
            JSONObject obj = new JSONObject();
            obj.put("resType", 1);
            obj.put("resTypeNM", "小说封面");
            obj.put("resUrl", cover);
            obj.put("bookId", bookId);
            lst.add(obj);
        }
        
        if(!StringUtil.isEmpty(banner)) {
            JSONObject obj = new JSONObject();
            obj.put("resType", 2);
            obj.put("resTypeNM", "轮播图");
            obj.put("resUrl", si.getCoverBannerUrl());
            obj.put("bookId", bookId);
            lst.add(obj);
        }
        
        if(!StringUtil.isEmpty(bookDir)) {
            JSONObject obj = new JSONObject();
            obj.put("resType", 3);
            obj.put("resTypeNM", "小说文本");
            obj.put("resUrl", bookDir);
            obj.put("bookId", bookId);
            lst.add(obj);
        }
        
        List<StoryTag> stds = storyTagDivService.getLstByBookId(bookId);
        if(stds != null && stds.size() >0) {
            for(StoryTag std:stds) {
                JSONObject obj = new JSONObject();
                obj.put("resType", 4);
                obj.put("resTypeNM", "小说标签");
                obj.put("resUrl", std.getTagName());
                obj.put("resId", std.getTagId());
                obj.put("bookId", bookId);
                lst.add(obj);
            }
        }
        
        List<StorySubject> sss = storySubjectDivService.getLstByBookId(bookId);
        if(sss != null && sss.size()>0) {
            for(StorySubject ss:sss) {
                JSONObject obj = new JSONObject();
                obj.put("resType", 5);
                obj.put("resTypeNM", "小说主题");
                obj.put("resUrl", ss.getSubjectName());
                obj.put("resId", ss.getSubjectId());
                obj.put("bookId", bookId);
                lst.add(obj);
            }
        }
        
        GridDataModel<JSONObject> model = new GridDataModel<JSONObject>();
        
        model.setRows(lst);
        model.setDataStr("查询结果列表显示");
        logger.debug(">>>> getResInfo() end <<<<");
        
        return JSONObject.fromObject(model).toString();
    
    }
    
    /**
     * 
     * 管理员画面初始化
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/bookadmin/bookSearch.do")
    public ModelAndView administrator(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("bookSearch");
    }    
        
    /**
     * 保存新增的小说数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/updStoryInfo.do")
    @ResponseBody
    public BKCommRes updStoryInfo(HttpServletRequest request) {
        logger.debug(">>>> updStoryInfo() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString("bookId");
        String bookTitle = reqObj.optString("bookTitle");
        String bookAuthor = reqObj.optString("bookAuthor");
        String bookDesc = reqObj.optString("bookDesc");
        String gender = reqObj.optString("gender");
        String bookStatus = reqObj.optString("bookStatus");
        
        StoryInfo si = storyInfoService.getByBookId(bookId);
        
        if(si == null){
            return new BKCommRes("更新数据不存在。");
        }
        
        si.setBookTitle(bookTitle);
        si.setBookAuthor(bookAuthor);
        si.setBookDesc(bookDesc);
        si.setGender(gender);
        si.setBookStatus(bookStatus);
        si.setUpdDate(DateUtil.getNow17());
        
        storyInfoService.update(si);
        
        logger.debug(">>>> updStoryInfo() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
    /**
     * 保存新增的小说数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/saveStoryInfo.do")
    @ResponseBody
    public BKCommRes saveStoryInfo(HttpServletRequest request) {
        logger.debug(">>>> saveStoryInfo() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        
        String nowDate = DateUtil.getNow17();
        String bookTitle = reqObj.optString("bookTitle");
        String bookAuthor = reqObj.optString("bookAuthor");
        String bookDesc = reqObj.optString("bookDesc");
        String gender = reqObj.optString("gender");
        String bookStatus = reqObj.optString("bookStatus");
        String bookTypeSub = reqObj.optString("bookTypeSub");
        String subject = reqObj.optString("subject");
        
        String bookUuid = UUIDUtil.minimizedUUID();
        String stdUuid = UUIDUtil.minimizedUUID();
        String ssdId = UUIDUtil.minimizedUUID();
        
        StoryInfo si = new StoryInfo();
        si.setBookId(bookUuid);
        si.setBookTitle(bookTitle);
        si.setBookAuthor(bookAuthor);
        si.setBookDesc(bookDesc);
        si.setGender(gender);
        si.setBookStatus(bookStatus);
        si.setCrDate(nowDate);
        si.setUpdDate(nowDate);
        
        StorySubjectDiv ssd = new StorySubjectDiv();
        ssd.setCrDate(nowDate);
        ssd.setBookId(bookUuid);
        ssd.setSubjectId(subject);
        ssd.setUpdDate(nowDate);
        ssd.setUuid(ssdId);
        
        // TODO 将支持多个选择
        StoryTagDiv std = new StoryTagDiv();
        std.setUuid(stdUuid);
        std.setBookId(bookUuid);
        std.setTagId(bookTypeSub);
        std.setCrDate(nowDate);
        std.setUpdDate(nowDate);
        
        List<StoryTagDiv> lst = new ArrayList<StoryTagDiv>();
        lst.add(std);
        storyInfoService.save(si, lst, ssd);
        logger.debug(">>>> saveStoryInfo() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
    /**
     * 查询小说
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/getBookList.do")
    @ResponseBody
    public String getBookList(HttpServletRequest request) {
        logger.debug(">>>> getBookList() start <<<<");
        
        int start = Integer.parseInt(request.getParameter("start"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        
        String bookNM = request.getParameter("bookNM");
        String bookID = request.getParameter("bookID");
        
        List<StoryInfo> lst = storyInfoService.getlstByNMID(bookNM, bookID, start, limit);
        int total = storyInfoService.getCntByNMID(bookNM, bookID);
        GridDataModel<StoryInfo> model = new GridDataModel<StoryInfo>();
        
        model.setRows(lst);
        model.setTotal(total);
        model.setDataStr("查询结果列表显示");
        logger.debug(">>>> getBookList() end <<<<");
        
        System.out.println(JSONObject.fromObject(model).toString());
        
        return JSONObject.fromObject(model).toString();
    }
    
    /**
     * 删除小说
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/delBook.do")
    @ResponseBody
    public BKCommRes delBook(HttpServletRequest request) {
        logger.debug(">>>> delBook() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId =  reqObj.optString("bookId");
        
        // TODO 多个表
        StoryInfo si = storyInfoService.getByBookId(bookId);
        
        String bookDir = si.getBookSaveDir();
        String cover = si.getCoverUrl();
        String banner = si.getCoverBannerUrl();
        
        if(!StringUtil.isEmpty(bookDir)) {
            File fd = new File(Const.LOCAL_PATH + bookDir);
            
            if(fd.exists()) {
                ClassUtil.deleteDir(fd);
            }
        }
        
        if(!StringUtil.isEmpty(cover)) {
            File file = new File(Const.LOCAL_PATH + cover);
            
            if(file.exists()) {
                file.delete();
            }
        }
        
        if(!StringUtil.isEmpty(banner)) {
            File file = new File(Const.LOCAL_PATH + banner);
            
            if(file.exists()) {
                file.delete();
            }
        }
        
        storyInfoService.delBookInfo(bookId);
        
        logger.debug(">>>> delBook() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
}
