package com.comm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.dto.GridDataModel;
import com.comm.model.Administrator;
import com.comm.model.MenuInfo;
import com.comm.service.AdministratorService;
import com.comm.service.MenuInfoService;
import com.comm.util.DataUtil;
import com.comm.util.HttpUtil;
import com.comm.util.MsgBundle;

import net.sf.json.JSONObject;

@Controller
public class AdministratorController extends BaseController {
    static Logger logger = Logger.getLogger(AdministratorController.class);
    
    @Autowired
    private AdministratorService administratorService;
    
    @Autowired
    private MenuInfoService menuInfoService;
    /**
     * 
     * 管理员画面初始化
     * 
     * @author LiLiang
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/administrator.do")
    public ModelAndView administrator(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("administrator");
    }
    /**
     * 
     * 管理员查询
     *  
     * @author LiLiang
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/administratorex.do")
    @ResponseBody
    public String administratorex(HttpServletRequest request) throws IOException {
        return getAdminList(request,"");
    }
    /**
     * 
     * 管理员增删改
     * 
     * @author LiLiang
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/administratordo.do")
    @ResponseBody
    public String administratordo(HttpServletRequest request) throws IOException {
        String json = HttpUtil.getStringFromRequest(request);
        System.out.println(json);
        return administratorService.administratordo(json);
    }
    
    private String getAdminList(HttpServletRequest request, String res) {
        String startStr = request.getParameter("start");
        String limitStr = request.getParameter("limit");
        String rule = request.getParameter("rule");
        String city = request.getParameter("city");

        int start = Integer.parseInt(StringUtils.isBlank(startStr) ? "0"
                : startStr);
        int limit = Integer.parseInt(StringUtils.isBlank(limitStr) ? "20"
                : limitStr);
        if (limit == 0) {
            limit = Integer.MAX_VALUE;
        }
        Map<String, Object> map = administratorService.getDetailsByAdminId(request
                .getParameter("adminId"),request
                .getParameter("name"),request
                .getParameter("num"),city,rule,start,limit);
        List<Administrator> administrators = (List<Administrator>) map.get("list");
        int total = Integer.parseInt(map.get("total").toString());

        
        List<JSONObject> administratorArray = new ArrayList<JSONObject>();
        
        for (Administrator administrator : administrators) {
            JSONObject administratorObj = new JSONObject();
            administratorObj.put("adminId", administrator.getAdminId());
            administratorObj.put("hId", administrator.getAdminId());
            administratorObj.put("name", administrator.getName());
            administratorObj.put("num", administrator.getNum());
//            administratorObj.put("city", administrator.getCity());
            administratorObj.put("rule", administrator.getRule());
            administratorArray.add(administratorObj);
        }

        GridDataModel<JSONObject> model = new GridDataModel<JSONObject>();
        int end = start + limit;

        end = end > total ? total : end;
        if (start <= end) {
            model.setRows(administratorArray);
        }
        model.setTotal(total);
        model.setDataStr("查询结果列表显示");
        model.setRes(res);
        return JSONObject.fromObject(model).toString();
    }
    
    /**
     * 密码修改
     * @author lqq
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/user/updatePWD.do")
    @ResponseBody 
    public String userRevise(HttpServletRequest request) throws IOException{
        
        ServletInputStream input = request.getInputStream();
        String inputstr = new String(DataUtil.read(input),"utf-8");
        System.out.println(inputstr);
        JSONObject getObj = JSONObject.fromObject(inputstr);
        JSONObject famObj = new JSONObject();
        Administrator admin = administratorService.getAdmin(getObj.getString("curAdminId"));        
        if(null!= admin){
            if (admin.getPassword().equals(getObj.getString("adminpwd"))) {
                admin = administratorService.getAdmin(getObj.getString("adminId"));
                if(null!= admin){
                     admin.setPassword(getObj.getString("newpwd"));
                     administratorService.updateAdmin(admin);
                     famObj.put("result", MsgBundle.RET_SUC);
                 }else{
                     famObj.put("result", MsgBundle.RET_FAILURE);
                 }
             } else {
                 famObj.put("result", MsgBundle.RET_PWD_ERR);
             }
        }else{
            famObj.put("result", MsgBundle.RET_FAILURE);
        }
        return famObj.toString();
    }
    
    /**
     * 
     * 管理员画面初始化
     * 
     * @author LiLiang
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/administratorMenuInfo.do")
    public ModelAndView administratorMenuInfo(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("administratorMenuInfo");
    }
    
    @RequestMapping("/user/getadministratorMenuInfo.do")
    @ResponseBody
    public <T>String getadministratorMenuInfo(HttpServletRequest request)
            throws IOException {
        logger.info(">>>> AdministratorController.getadministratorMenuInfo() start <<<<");
        String adminId = request.getParameter("adminId");
        JSONObject menulist = new JSONObject();
        Administrator admin = administratorService.getAdmin(adminId);
        if (null == admin){
            menulist.put("res", "fail");
            menulist.put("msg", "该操作号不存在");
            return menulist.toString();
        }
        
        List<MenuInfo> list1 = menuInfoService.getAllMenu();
        List<MenuInfo> list2 = new ArrayList<MenuInfo>();
        for(MenuInfo menu : list1){            
            if(menu.getNode().equals("0")){
                MenuInfo mInfo = new MenuInfo();
                mInfo.setSeq(menu.getSeq());
                mInfo.setmenuId(menu.getmenuId());
                mInfo.setmenuName(menu.getmenuName());
                mInfo.setNode(menu.getNode());
//                mInfo.setAuthority(menu.getAuthority());
                list2.add(mInfo);
                for(MenuInfo submenu : list1){
                    if(submenu.getNode().equals("1") && submenu.getParent().equals(menu.getmenuId()) ){
                        MenuInfo submInfo = new MenuInfo();
                        submInfo.setSeq(submenu.getSeq());
                        submInfo.setmenuId(submenu.getmenuId());
                        submInfo.setmenuName(submenu.getmenuName());
                        submInfo.setNode(submenu.getNode());
                        submInfo.setParent(submenu.getParent());
                        submInfo.setmenuUrl(submenu.getmenuUrl());
//                        submInfo.setAuthority(submenu.getAuthority());
                        list2.add(submInfo);
                    }
                }
            }
        };
        menulist.put("rows", list2);
        menulist.put("menulist", admin.getMenulist());
        menulist.put("mainmenuseq", admin.getMainmenuseq());
        logger.info(">>>> AdministratorController.getadministratorMenuInfo() end <<<<");
        return menulist.toString();
    }
    
    /**
     * 保存管理员菜单信息
     * @author lqq
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/user/savemenulist.do")
    @ResponseBody 
    public String savemenulist(HttpServletRequest request) throws IOException{
        
        ServletInputStream input = request.getInputStream();
        String inputstr = new String(DataUtil.read(input),"utf-8");
        System.out.println(inputstr);
        JSONObject getObj = JSONObject.fromObject(inputstr);
        JSONObject famObj = new JSONObject();
        Administrator admin = administratorService.getAdmin(getObj.getString("adminId"));        
        if(null!= admin){
            String menulist = getObj.optString("menulist","").replace("[", "").replace("]", "");
             admin.setMainmenuseq(Long.parseLong(getObj.optString("mainmenu","0")));
             admin.setMenulist(menulist);
             administratorService.updateAdmin(admin);
             famObj.put("result", MsgBundle.RET_SUC);
        }else{
            famObj.put("result", MsgBundle.RET_FAILURE);
        }
        return famObj.toString();
    }
}
