package com.comm.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.dto.GridDataModel;
import com.comm.model.MenuInfo;
import com.comm.service.MenuInfoService;
import com.comm.util.HttpUtil;

import net.sf.json.JSONObject;

@Controller
public class MenuInfoController {

    static Logger logger = Logger.getLogger(MenuInfoController.class);

    @Autowired
    private MenuInfoService menuInfoService;

    /**
     * 
     * 菜单管理画面初始化
     * 
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/menuInfo.do")
    public ModelAndView administrator(HttpServletRequest request) throws IOException {
        return new ModelAndView("menuInfo");
    }

    /**
     * 菜单管理数据初始化
     * 
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/menuInfoinit.do")
    @ResponseBody
    public String menuInfoinit() throws IOException {
        return getMenuInfo("");
    }

    /**
     * 菜单管理数据增删改
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/menuInfodo.do")
    @ResponseBody
    public String menuInfodo(HttpServletRequest request) throws IOException {
        logger.debug(">>>> MenuInfoController.menuInfodo() start <<<<");
        String json = HttpUtil.getStringFromRequest(request);
        logger.debug("request json:[" + json + "]");
        return menuInfoService.menuInfodo(json);
    }
    
    private String getMenuInfo(String res) {
        List<MenuInfo> menuInfos = menuInfoService.getAllMenu();

        List<JSONObject> menuInfoArray = new ArrayList<JSONObject>();

        for (MenuInfo menuInfo : menuInfos) {
            JSONObject menuInfoObj = new JSONObject();
            menuInfoObj.put("menuId", menuInfo.getmenuId());
            menuInfoObj.put("menuName", menuInfo.getmenuName());
            menuInfoObj.put("menuUrl", menuInfo.getmenuUrl());
            menuInfoObj.put("parent", menuInfo.getParent());
            menuInfoObj.put("node", menuInfo.getNode());
//            menuInfoObj.put("authority", menuInfo.getAuthority());
            menuInfoArray.add( menuInfoObj);
        }
        
        GridDataModel<JSONObject> model = new GridDataModel<JSONObject>();
        model.setRows(menuInfoArray);
        model.setDataStr("查询结果列表显示");
        model.setRes(res);
        return JSONObject.fromObject(model).toString();
    }
}
