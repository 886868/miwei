package com.bs.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comm.controller.BaseController;
import com.comm.dto.ADBRes;
import com.comm.dto.BannersRes;
import com.comm.dto.BookListRes;
import com.comm.dto.BookRes;
import com.comm.dto.ChatpersRes;
import com.comm.dto.FirstTagRes;
import com.comm.dto.SubjectsRes;
import com.comm.dto.TagsRes;
import com.comm.model.AdBanner;
import com.comm.model.StoryInfo;
import com.comm.service.AdBannerService;
import com.comm.service.ScrollbarInfoService;
import com.comm.service.StoryDirInfoService;
import com.comm.service.StoryInfoService;
import com.comm.service.StorySubjectService;
import com.comm.service.StoryTagService;
import com.comm.util.DateUtil;
import com.comm.util.HqlConst;
import com.comm.util.HttpUtil;
import com.comm.util.JsonConst;
import com.comm.util.ResMessageConst;
import com.comm.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class BookBSController extends BaseController {
    
    /**
     * LOG输出
     */
    private static Logger logger = Logger.getLogger(BookBSController.class);
    
    @Autowired
    private StoryInfoService storyInfoService;
    
    @Autowired
    private StoryTagService storyTagService;
    
    @Autowired
    private StoryDirInfoService storyDirInfoService;
    
    @Autowired
    private StorySubjectService storySubjectService;
    
    @Autowired
    private ScrollbarInfoService scrollbarInfoService;
    
    @Autowired
    private AdBannerService adBannerService;
    
    /**
     * 获取主页面的内容
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getLibrary.do")
    @ResponseBody
    public String getLibrary(HttpServletRequest request) {
        logger.debug(">>>> getLibrary() start <<<<");
        
        // 获取所有banners
        JSONObject res = new JSONObject();
        JSONArray banners = scrollbarInfoService.getLstByBz("bz001");
        res.put("banners", banners);
        
        List<Map<String, Object>> subjects = storySubjectService.getAllSubjects();
        JSONArray subs = new JSONArray();
        for(Map<String, Object> obj:subjects) {
            JSONObject subObj = new JSONObject();
            subObj.put("subjectId", obj.get("subjectId"));
            subObj.put("subjectName", obj.get("subjectName"));
            List<StoryInfo> lst = storyInfoService.getLstBySubject(obj.get("subjectId").toString(), HqlConst.CR_DATE, "", 0, 5);
            JSONArray books = new JSONArray();
            for(StoryInfo si:lst) {
                JSONObject jsi = JSONObject.fromObject(si);
                
                List<Map<String, Object>> tags = storyTagService.getTagsByBookId(si.getBookId());
                if(tags != null ) {
                    jsi.put("tags", tags);
                } else {
                    jsi.put("tags", "");
                }
                books.add(jsi);
            }
            
            subObj.put("bookList", books);
            subs.add(subObj);
        }
        res.put("subjects", subs);
        res.put("exception", ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getLibrary() end <<<<");
        
        return res.toString();
    }
    
    /**
     * 通过主题获取小说列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getListBySubject.do")
    @ResponseBody
    public BookListRes getBookListBySubject(HttpServletRequest request) {
        logger.debug(">>>> getBookListBySubject() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        // {'subjectId':'xxxxx','sort':'orderNumber|created','duration':'last_seven_days|all' }
        BookListRes res = new BookListRes();
        
        if(reqObj == null) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            logger.error(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            return res;
        }
        
        String subjectId = reqObj.optString(JsonConst.REQ_KEY_SUBJECT_ID);
        // 小说主题不能为空
        if(StringUtil.isEmpty(subjectId)) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SUBJECT_001);
            logger.error("小说主题为空");
            return res;
        }
        
        String sortKey = reqObj.optString(JsonConst.REQ_KEY_SORT);
        
        String durationKey = reqObj.optString(JsonConst.REQ_KEY_DURATION);
        
        int start = reqObj.optInt(JsonConst.REQ_KEY_START);
        int length = reqObj.optInt(JsonConst.REQ_KEY_LENGTH);
        
        logger.debug(">>>> 判断sortkey <<<<");
        if(JsonConst.RES_KEY_SORT_CREATED.equals(sortKey)){
            sortKey = HqlConst.CR_DATE;
        } else if(JsonConst.RES_KEY_SORT_ORDER_NUMBER.equals(sortKey)) {
            // TODO 排序字段需要讨论
            sortKey = HqlConst.CR_DATE;
        }
        
        if(JsonConst.RES_KEY_DUR_LSD.equals(durationKey)){
            durationKey = DateUtil.getNow8();
        } else{
            durationKey = "";
        }
        
        List<StoryInfo> lst = storyInfoService.getLstBySubject(subjectId, sortKey, durationKey
                ,start,length);
        logger.debug(">>>> 获取list <<<<");
        int total = storyInfoService.getCntBySubject(subjectId, sortKey, durationKey);
        logger.debug(">>>> 获取总数 <<<<");
        
        JSONArray jarr = new JSONArray();
        if(lst.size() > 0) {
         // 获取种类
            for(StoryInfo obj:lst) {
                JSONObject jobj = JSONObject.fromObject(obj);
                // 根据图书id获取相应的tags
                List<Map<String, Object>> tags = storyTagService.getTagsByBookId(obj.getBookId());
                if(tags != null ) {
                    jobj.put("tags", tags);
                }else {
                    jobj.put("tags", "");
                }
                jarr.add(jobj);
            }
        }
        
        res.setBookList(jarr);
        res.setException(ResMessageConst.RES_SUCCESS);
        res.setTotal(total);
        logger.debug(">>>> getBookListBySubject() end <<<<");
        return res;
    }
    
    // 通过标签（类型）获取小说列表
    /**
     * 通过标签（类型）获取小说列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getListByTag.do")
    @ResponseBody
    public BookListRes getBookListByTag(HttpServletRequest request) {
        logger.debug(">>>> getBookListByTag() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        BookListRes res = new BookListRes();
        
        if(reqObj == null) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            logger.error(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            return res;
        }
        
        String tagId = reqObj.optString(JsonConst.REQ_KEY_TAG_ID);
        
        // 小说主题不能为空
        if(StringUtil.isEmpty(tagId)) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SUBJECT_002);
            logger.error("小说类型为空");
            return res;
        }
        
        String sortKey = reqObj.optString(JsonConst.REQ_KEY_SORT);
        
        String durationKey = reqObj.optString(JsonConst.REQ_KEY_DURATION);
        
        int start = reqObj.optInt(JsonConst.REQ_KEY_START);
        int length = reqObj.optInt(JsonConst.REQ_KEY_LENGTH);
        
        logger.debug(">>>> 判断sortkey <<<<");
        if(JsonConst.RES_KEY_SORT_CREATED.equals(sortKey)){
            sortKey = HqlConst.CR_DATE;
        } 
        
        if(JsonConst.RES_KEY_DUR_LSD.equals(durationKey)){
            durationKey = DateUtil.getNow8();
        } else{
            durationKey = "";
        }
        
        List<StoryInfo> lst = storyInfoService.getLstByTag(tagId, sortKey, durationKey
                ,start,length);
        logger.debug(">>>> 获取list <<<<");
        int total = storyInfoService.getCntByTag(tagId, sortKey, durationKey);
        logger.debug(">>>> 获取总数 <<<<");
        
        JSONArray jarr = new JSONArray();
        if(lst.size() > 0) {
         // 获取种类
            for(StoryInfo obj:lst) {
                JSONObject jobj = JSONObject.fromObject(obj);
                // 根据图书id获取相应的tags
                List<Map<String, Object>> tags = storyTagService.getTagsByBookId(obj.getBookId());
                if(tags != null ) {
                    jobj.put("tags", tags);
                }else {
                    jobj.put("tags", "");
                }
                jarr.add(jobj);
            }
        }
        
        res.setBookList(jarr);
        res.setException(ResMessageConst.RES_SUCCESS);
        res.setTotal(total);
        logger.debug(">>>> getBookListByTag() end <<<<");
        return res;
    }
    
    // 通过搜索框获取小说列表 TODO
    /**
     * 通过作者名或者书名获取小说列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getListByTxt.do")
    @ResponseBody
    public BookListRes getListByTxt(HttpServletRequest request) {
        logger.debug(">>>> getBookListByTag() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        
        String inputTxt = reqObj.optString(JsonConst.REQ_KEY_INPUT_TXT);
        
        BookListRes res = new BookListRes();
        
        // 小说主题不能为空
        if(StringUtil.isEmpty(inputTxt)) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SUBJECT_002);
            logger.error("小说类型为空");
            return res;
        }
        
        int start = reqObj.optInt(JsonConst.REQ_KEY_START);
        int length = reqObj.optInt(JsonConst.REQ_KEY_LENGTH);
        
        List<StoryInfo> lst = storyInfoService.getLstByInputTxt(inputTxt, start, length);
        logger.debug(">>>> 获取list <<<<");
        int total = storyInfoService.getCntByInputTxt(inputTxt);
        logger.debug(">>>> 获取总数 <<<<");
        
        JSONArray jarr = new JSONArray();
        if(lst.size() > 0) {
         // 获取种类
            for(StoryInfo obj:lst) {
                JSONObject jobj = JSONObject.fromObject(obj);
                // 根据图书id获取相应的tags
                List<Map<String, Object>> tags = storyTagService.getTagsByBookId(obj.getBookId());
                if(tags != null ) {
                    jobj.put("tags", tags);
                }else {
                    jobj.put("tags", "");
                }
                jarr.add(jobj);
            }
        }
        
        res.setBookList(jarr);
        res.setException(ResMessageConst.RES_SUCCESS);
        res.setTotal(total);
        logger.debug(">>>> getBookListByTag() end <<<<");
        return res;
    }
    
    /**
     * 通过小说id获取小说目录
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getDirById.do")
    @ResponseBody
    public ChatpersRes getDirById(HttpServletRequest request) {
        logger.debug(">>>> getDirById() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString(JsonConst.REQ_KEY_BOOK_ID);
        ChatpersRes res = new ChatpersRes();
        
        // 小说主题不能为空
        if(StringUtil.isEmpty(bookId)) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SUBJECT_003);
            logger.error("小说编号为空");
            return res;
        }
        
        List<Map<String,Object>> lst = storyDirInfoService.getLstByBookId(bookId);
        
        res.setChapters(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getDirById() end <<<<");
        return res;
    }
     
    // 获取具体小说内容 通过nginx映射，直接获取静态资源 无对应逻辑 TODO
    
    // 获取主题
    /**
     * 获取所有主题
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getSubjects.do")
    @ResponseBody
    public SubjectsRes getSubjects(HttpServletRequest request) {
        logger.debug(">>>> getSubjects() start <<<<");
        List<Map<String, Object>> lst = storySubjectService.getAllSubjects();
        SubjectsRes res = new SubjectsRes();
        res.setSubjects(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getSubjects() end <<<<");
        return res;
    }
    
    // 获取分类
    /**
     * 获取一级分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getFirstTags.do")
    @ResponseBody
    public FirstTagRes getFirstTags(HttpServletRequest request) {
        logger.debug(">>>> getFirstTags() start <<<<");
        
        List<Map<String, Object>> lst = storyTagService.getFirstTags();
        
        FirstTagRes res = new FirstTagRes();
        res.setTags(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getFirstTags() end <<<<");
        return res;
    }
    /**
     * 根据一级分类获取二级分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getTagsByFirst.do")
    @ResponseBody
    public TagsRes getTagsByFirst(HttpServletRequest request) {
        logger.debug(">>>> getTagsByFirst() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String tagId = reqObj.optString(JsonConst.REQ_KEY_TAG_ID);
        
        List<Map<String, Object>> lst = storyTagService.getTagsByFirst(tagId);
        
        TagsRes res = new TagsRes();
        res.setParentId(tagId);
        res.setTags(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getTagsByFirst() end <<<<");
        return res;
    }
    
    /**
     * 获取所有分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getAllTags.do")
    @ResponseBody
    public FirstTagRes getAllTags(HttpServletRequest request) {
        logger.debug(">>>> getAllTags() start <<<<");
        
        // 获取所有一级标签
        List<Map<String, Object>> allLst = storyTagService.getFirstTags();
        FirstTagRes res = new FirstTagRes();
        JSONArray jarr = new JSONArray();
        // tags的json格式为 
        // {'tags':[{'tagId':'xxxx','tagName':'xxxx','childTags':[{'tagId':'xxxx','tagName':'xxxx'},{'tagId':'xxxx','tagName':'xxxx'}]}],'exception':'ok'}
        if(allLst != null && allLst.size() > 0) {
            for(Map<String, Object> map:allLst) {
                JSONObject jobj = JSONObject.fromObject(map);
                String tagId = map.get("tagId").toString();
                // 通过一级标签 获取其子标签的所有数据
                List<Map<String, Object>> lst = storyTagService.getTagsByFirst(tagId);
                
                if(lst!=null) {
                    jobj.put("childTags", lst);
                }
                
                jarr.add(jobj);
            }
        } else {
            res.setException("标签数据为空");
        }
        
        res.setTags(jarr);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getAllTags() end <<<<");
        return res;
    }
    
    // 获取banner列表
    /**
     * 根据业务类型 获取banner列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getBanners.do")
    @ResponseBody
    public BannersRes getBanners(HttpServletRequest request) {
        logger.debug(">>>> getAllTags() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String divBz = reqObj.optString(JsonConst.REQ_KEY_DIV_BZ);
        BannersRes res = new BannersRes();
        
        if(StringUtil.isEmpty(divBz)) {
            res.setException("业务类型不能为空。");
            Log.error("业务类型不能为空。");
            return res;
        }
            
        JSONArray lst = scrollbarInfoService.getLstByBz(divBz);
        
        res.setBanners(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getBanners() end <<<<");
        return res;
    }
    /**
     * 根据 book_id 获取小说详情页内容
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getBookInfo.do")
    @ResponseBody
    public BookRes getBookInfo(HttpServletRequest request) {
        logger.debug(">>>> getBookInfo() start <<<<");
        
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        String bookId = reqObj.optString(JsonConst.REQ_KEY_BOOK_ID);
        BookRes res = new BookRes();
        
        if(StringUtil.isEmpty(bookId)) {
            res.setException("小说编号不能为空。");
            Log.error("小说编号不能为空。");
            return res;
        }
        
        StoryInfo si = storyInfoService.getByBookId(bookId);
        if(si == null) {
            res.setException("查询的小说不存在。");
            logger.error("查询的小说不存在。");
        }
        
        JSONObject jobj = JSONObject.fromObject(si);
        // 根据图书id获取相应的tags
        List<Map<String, Object>> tags = storyTagService.getTagsByBookId(si.getBookId());
        if(tags != null ) {
            jobj.put("tags", tags);
        }else {
            jobj.put("tags", "");
        }
        
        res.setBook(jobj);
        res.setException(ResMessageConst.RES_SUCCESS);
        logger.debug(">>>> getBookInfo() end <<<<");
        return res;
    }
    
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getAdbs.do")
    @ResponseBody
    public ADBRes getAdbs(HttpServletRequest request) {
        ADBRes res = new ADBRes();
        List<AdBanner> lst = adBannerService.getAdBanners();
        res.setAds(lst);
        res.setException(ResMessageConst.RES_SUCCESS);
        return res;
    }
    
    /**
     * 通过查询条件，分页获取数据
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/book/getList4Main.do")
    @ResponseBody
    public BookListRes getList4Main(HttpServletRequest request) {
        logger.debug(">>>> getList4Main() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        // {'subjectId':'xxxxx','sort':'orderNumber|created','duration':'last_seven_days|all' }
        BookListRes res = new BookListRes();
        
        if(reqObj == null) {
            res.setException(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            logger.error(ResMessageConst.RES_MSG_BOOK_LIST_SYS_001);
            return res;
        }
        
        String inputTxt = reqObj.optString(JsonConst.REQ_KEY_INPUT_TXT);
        String tag = reqObj.optString(JsonConst.REQ_KEY_TAG_ID);
        // 0-20,20-100,100-0;
        String wordCnt = reqObj.optString(JsonConst.REQ_KEY_WORD_CNT);
        String status = reqObj.optString(JsonConst.REQ_KEY_BOOK_STATUS);
        
        int start = reqObj.optInt(JsonConst.REQ_KEY_START);
        int length = reqObj.optInt(JsonConst.REQ_KEY_LENGTH);
        
        int ltWords = 0;
        int gtWords = 0;
        
        if("all".equalsIgnoreCase(wordCnt)){
            wordCnt = "";
        }
        
        if("all".equalsIgnoreCase(status)){
            status = "";
        }
        
        if("all".equalsIgnoreCase(tag)){
            tag = "";
        }
        
        if(!StringUtil.isEmpty(wordCnt)) {
            
            String[] cnts = wordCnt.split("-");
            gtWords= Integer.valueOf(cnts[0]) * 10000;
            ltWords= Integer.valueOf(cnts[1]) * 10000;
        }
        
        List<StoryInfo> lst = storyInfoService.getList4Main(inputTxt, tag, status, ltWords, gtWords, start, length);
        
        logger.debug(">>>> 获取list <<<<");
        int total = storyInfoService.getCnt4Main(inputTxt, tag, status, ltWords, gtWords);
        logger.debug(">>>> 获取总数 <<<<");
        
        JSONArray jarr = new JSONArray();
        if(lst.size() > 0) {
         // 获取种类
            for(StoryInfo obj:lst) {
                JSONObject jobj = JSONObject.fromObject(obj);
                // 根据图书id获取相应的tags
                List<Map<String, Object>> tags = storyTagService.getTagsByBookId(obj.getBookId());
                if(tags != null ) {
                    jobj.put("tags", tags);
                }else {
                    jobj.put("tags", "");
                }
                jarr.add(jobj);
            }
        }
        
        res.setBookList(jarr);
        res.setException(ResMessageConst.RES_SUCCESS);
        res.setTotal(total);
        logger.debug(">>>> getList4Main() end <<<<");
        return res;
    }
}
