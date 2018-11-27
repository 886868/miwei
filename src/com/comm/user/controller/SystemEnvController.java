package com.comm.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.dto.GridDataModel;
import com.comm.model.SystemEnv;
import com.comm.service.SystemEnvService;

import net.sf.json.JSONObject;

@Controller
public class SystemEnvController {

    @Resource
    private SystemEnvService systemEnvService;

    /**
     * 
     * 系统环境管理画面初始化
     * 
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/systemEnv.do")
    public ModelAndView systemEnv(HttpServletRequest request) throws IOException {
        return new ModelAndView("systemEnv");
    }

    /**
     * 系统环境管理画面初始化
     * 
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/systemEnvinit.do")
    @ResponseBody
    public String systemEnvinit() throws IOException {

        List<SystemEnv> systemEnvs = systemEnvService.getAllSystemEnv();

        List<JSONObject> systemEnvArray = new ArrayList<JSONObject>();

        for (SystemEnv systemEnv : systemEnvs) {
            JSONObject systemEnvObj = new JSONObject();
            systemEnvObj.put("key", systemEnv.getItem());
            systemEnvObj.put("value", systemEnv.getValue());
            systemEnvArray.add(systemEnvObj);
        }

        GridDataModel<JSONObject> model = new GridDataModel<JSONObject>();
        model.setRows(systemEnvArray);
        model.setDataStr("查询结果列表显示");
        return JSONObject.fromObject(model).toString();
    }

    /**
     * 系统环境管理数据增删改
     * 
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/systemEnvdo.do")
    @ResponseBody
    public String systemEnvdo(HttpServletRequest request) throws IOException {

        String json = request.getParameter("data");
        String res = systemEnvService.systemEnvdo(json);
        return getSystemEnv(res);
    }

    private String getSystemEnv(String res) {
        List<SystemEnv> systemEnvs = systemEnvService.getAllSystemEnv();

        List<JSONObject> systemEnvArray = new ArrayList<JSONObject>();

        for (SystemEnv systemEnv : systemEnvs) {
            JSONObject systemEnvObj = new JSONObject();
            systemEnvObj.put("key", systemEnv.getItem());
            systemEnvObj.put("value", systemEnv.getValue());
            systemEnvArray.add(systemEnvObj);
        }

        GridDataModel<JSONObject> model = new GridDataModel<JSONObject>();
        model.setRows(systemEnvArray);
        model.setDataStr("查询结果列表显示");
        model.setRes(res);
        return JSONObject.fromObject(model).toString();
    }

}
