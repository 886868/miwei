package com.comm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.comm.util.ViewExcel;

@Controller
public class ExcelViewController extends BaseController {

    private ViewExcel viewExcel;

    @Autowired
    public void setViewExcel(ViewExcel viewExcel) {
        this.viewExcel = viewExcel;
    }

//    @RequestMapping("/user/excDemo.do")
//    public ModelAndView excDemo(HttpServletRequest request)
//            throws IOException {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        
//        List<String> heads = new ArrayList<String>();
//        heads.add("head1");
//        heads.add("head2");
//        heads.add("head3");
//
//        map.put("heads", heads);
//        
//        List<String[]> contents = new ArrayList<String[]>();
//        String[]strs1 = {"10","11","12"};
//        String[]strs2 = {"20","21","22"};
//        String[]strs3 = {"30","31","32"};
//        
//        contents.add(strs1);
//        contents.add(strs2);
//        contents.add(strs3);
//        
//        map.put("heads", heads);
//        map.put("contents", contents);
//        map.put("fileName", "test");
//        
//        return new ModelAndView(viewExcel, map);
//
//    }
}
