package com.comm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comm.model.Rule;
import com.comm.service.RuleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class InitController extends BaseController {
    
    @Autowired
    private RuleService ruleService;
    
    @RequestMapping("/user/getRuleAdmin.do")
    @ResponseBody
    public String getRuleAdmin(HttpServletRequest request) throws IOException {
        JSONObject userAppQ = new JSONObject();
        JSONArray userAPPA = new JSONArray();
//        JSONObject json = new JSONObject();
        List<Rule> lstProvince = ruleService.getRule();
        for(Rule pmInf : lstProvince){
            userAppQ.put("value", pmInf.getRule());
            userAppQ.put("text", pmInf.getRuleName());
            userAPPA.add(userAppQ);
        }
        return userAPPA.toString();
    }
    
    @RequestMapping("/user/getRuleAdminSel.do")
    @ResponseBody
    public String getRuleAdminSel(HttpServletRequest request) throws IOException {
        JSONObject userAppQ = new JSONObject();
        JSONArray userAPPA = new JSONArray();
//        JSONObject json = new JSONObject();
        String rule = request.getParameter("rule");
        List<Rule> lstProvince = ruleService.getRule();
        for(Rule pmInf : lstProvince){
            if (!rule.equals("99") && pmInf.getRule().equals("99")){
                continue;
            }
            userAppQ.put("value", pmInf.getRule());
            userAppQ.put("text", pmInf.getRuleName());
            userAPPA.add(userAppQ);
        }
        return userAPPA.toString();
    }
    
    @RequestMapping("/user/showRuleAdmin.do")
    @ResponseBody
    public String showRuleAdmin(HttpServletRequest request) throws IOException {
        JSONObject userAppQ = new JSONObject();
        JSONArray userAPPA = new JSONArray();
//        JSONObject json = new JSONObject();
        List<Rule> lstProvince = ruleService.getRule();
        for(Rule pmInf : lstProvince){
            userAppQ.put("value", pmInf.getRule());
            userAppQ.put("text", pmInf.getRuleName());
            userAPPA.add(userAppQ);
        }
        return userAPPA.toString();
    }
    
}
