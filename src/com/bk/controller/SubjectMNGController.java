package com.bk.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.controller.BaseController;
import com.comm.dto.BKCommRes;
import com.comm.dto.GridDataModel;
import com.comm.model.StorySubject;
import com.comm.service.StorySubjectService;
import com.comm.util.HttpUtil;
import com.comm.util.ResMessageConst;

import net.sf.json.JSONObject;

@Controller
public class SubjectMNGController extends BaseController {
    
    /**
     * LOG输出
     */
    private static Logger logger = Logger.getLogger(SubjectMNGController.class);
    
    
    @Autowired
    private StorySubjectService storySubjectService;
    
    /**
     * 
     * 管理员画面初始化
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/bookadmin/subjectMng.do")
    public ModelAndView subjectMng(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("subjectMng");
    }    
    
    /**
     * 获取主题
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/getSubList.do")
    @ResponseBody
    public String getSubList(HttpServletRequest request) {
        logger.debug(">>>> getSubList() start <<<<");
        
        GridDataModel<StorySubject> model = new GridDataModel<StorySubject>();
        
        List<StorySubject> lst = storySubjectService.getAllSubjectsEnt();
        
        logger.debug(">>>> getSubList() end <<<<");
        model.setRows(lst);
        model.setDataStr("查询结果列表显示");
        
        return JSONObject.fromObject(model).toString();
    }
    
    /**
     * 更新主题
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/editSubList.do")
    @ResponseBody
    public BKCommRes editSubList(HttpServletRequest request) {
        logger.info(">>>> editSubList() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        storySubjectService.update(reqObj);
        logger.info(">>>> editSubList() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
    
}
