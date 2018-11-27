package com.comm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.comm.dto.LoginForm;
import com.comm.model.Administrator;
import com.comm.model.MenuInfo;
import com.comm.service.AdministratorService;
import com.comm.service.MenuInfoService;
import com.comm.util.DataUtil;
import com.comm.util.MsgBundle;
import com.comm.util.RandomValidateCode;

import net.sf.json.JSONObject;

@Controller
public class OtherController extends BaseController {
    
    @Autowired
    private AdministratorService administratorService;
    
    @Autowired
    private MenuInfoService menuInfoService;

    /**
     * 登录画面跳转
     * @author lqq
     * @param response
     * @param request
     */
    @RequestMapping("/user/index.do")
    public ModelAndView userIndex(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("loginup");
    }
    
    /**
     * 验证码变换
     * @author guoyu
     * @param response
     * @param request
     */
    @RequestMapping("/user/im.do")
    public void validationImg(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 验证码验证
     * @author guoyu
     * @param response
     * @param request
     */
    @RequestMapping("/user/checkCode.do")
    public void checkCode(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8"); 
        // 取得 session 中的 code
        String validateC = (String) request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY);
        // 取得页面输入的 code
        String veryCode = request.getParameter("icode");
        // 获得页面输出流
        PrintWriter out;
        try {
            out = response.getWriter();
            // code比较及结果输出
            if(veryCode==null||"".equals(veryCode)){    
                out.println("");    
            }else{    
                if(validateC.equals(veryCode.toUpperCase())){    
                    out.println("success");    
                }else{    
                    out.println("fail"); 
                }    
            }    
            out.flush();    
            out.close();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 用户登录
     * @author guoyu
     * @param loginInf
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/loginup.do") 
      public ModelAndView userLogin(LoginForm loginInf , HttpServletRequest request) throws IOException{
        
        Administrator admin = new Administrator();
        admin.setAdminId(loginInf.getAccount());
        // 密码使用MD5加密后的密文（32个字符）
        admin.setPassword(loginInf.getPasswordcre());
        boolean res = administratorService.login(admin);
          
          if (res == false) {
              request.setAttribute("errLogin", "1");
              return new ModelAndView("loginup");
          } else {
              Administrator adminis =  administratorService.getAdmin(admin.getAdminId());
              request.setAttribute("name", adminis.getName());
              request.setAttribute("adminId", admin.getAdminId());
//              request.setAttribute("adminprovince", adminis.getCity());
              request.setAttribute("rule", adminis.getRule());
              request.setAttribute("menulist", adminis.getMenulist());
              request.setAttribute("mainmenuseq", adminis.getMainmenuseq());
              return new ModelAndView("main");
          }
      }
    
    /**
     * 退出系统
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/logoutup.do")
    public ModelAndView logoutup(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("loginup");
    }

    /**
     * Web后台密码修改
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/user/modifyPassword.do")
    @ResponseBody 
    public String userRevise(HttpServletRequest request) throws IOException{
        
        ServletInputStream input = request.getInputStream();
        String inputstr = new String(DataUtil.read(input),"utf-8");
        //前台传过来的数据 getObj
        JSONObject getObj = JSONObject.fromObject(inputstr);
        JSONObject famObj = new JSONObject();
        //admin 是从数据库中取得的用户数据
        Administrator admin =  administratorService.getAdmin(getObj.getString("adminId"));
        if(null!= admin){
            if (admin.getPassword().equals(getObj.getString("password"))) {
                //getObj.getString("newpassword") 确认密码
                 if (getObj.getString("password").equals(getObj.getString("newpassword"))) {
                     //如果修改的密码和原密码一致，则不做任何处理，后台返回 成功
                     famObj.put("result", MsgBundle.RET_SUC);
                 } else {
                     admin.setPassword(getObj.getString("newpassword"));
                     administratorService.updateAdmin(admin);
                     famObj.put("result", MsgBundle.RET_SUC);
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
     * 修改密码画面跳转
     * 
     * @author guoyu
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/mPassword.do")
    public ModelAndView mPassword(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("modifyPassword");
    }
    
    /**
     * 用户密码修改画面跳转
     * 
     * @author luqq
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/muserPWD.do")
    public ModelAndView muserPWD(HttpServletRequest request)
            throws IOException {
        return new ModelAndView("changePWD");
    }
    
    /**
     * 页面菜单加载
     * 
     * @author lqq
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/menu.do")
    @ResponseBody
    public String userMenu(HttpServletRequest request) throws IOException {
        JSONObject menulist = new JSONObject();
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
        menulist.put("menulist", list2);
        return menulist.toString();
    }
    
    /**
     * 页面菜单加载
     * 
     * @author lqq
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/user/menuByseqs.do")
    @ResponseBody
    public String userMenuByseqs(HttpServletRequest request) throws IOException {
        ServletInputStream input = request.getInputStream();
        String inputstr = new String(DataUtil.read(input),"utf-8");
        JSONObject getObj = JSONObject.fromObject(inputstr);
        String seqs = getObj.getString("seqs");
        
        JSONObject menulist = new JSONObject();
        List<MenuInfo> list1 = menuInfoService.getMenuBySeqs(seqs);
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
        menulist.put("menulist", list2);
        return menulist.toString();
    }

}
