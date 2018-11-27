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
import com.comm.model.ScrollbarInfo;
import com.comm.service.ScrollbarInfoService;
import com.comm.util.HttpUtil;
import com.comm.util.ResMessageConst;

import net.sf.json.JSONObject;

@Controller
public class BannerMNGController extends BaseController {
    /**
     * LOG输出
     */
    private static Logger logger = Logger.getLogger(BannerMNGController.class);
    
    @Autowired
    private ScrollbarInfoService scrollbarInfoService;
    /**
     * 
     * 管理员画面初始化
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/bookadmin/bannerMng.do")
    public ModelAndView subjectMng(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("bannerMng");
    }  
    
    /**
     * 获取主题
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/bookadmin/getBannerList.do")
    @ResponseBody
    public String getTagList(HttpServletRequest request) {
        logger.debug(">>>> getTagList() start <<<<");
        
        GridDataModel<ScrollbarInfo> model = new GridDataModel<ScrollbarInfo>();
        
        List<ScrollbarInfo> lst = scrollbarInfoService.getAll();
        
        logger.debug(">>>> getTagList() end <<<<");
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
    @RequestMapping(value = "/bookadmin/editBannerList.do")
    @ResponseBody
    public BKCommRes editTagList(HttpServletRequest request) {
        logger.info(">>>> editTagList() start <<<<");
        JSONObject reqObj = HttpUtil.getJsonObjFromRequest(request);
        scrollbarInfoService.update(reqObj);
//        logger.info(">>>> editTagList() end <<<<");
        return new BKCommRes(ResMessageConst.RES_SUCCESS);
    }
}
