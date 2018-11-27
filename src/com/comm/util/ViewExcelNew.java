package com.comm.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class ViewExcelNew extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
          String fileNameFirst = (String)model.get("fileName");
          String excelFileName=new String(fileNameFirst.getBytes("GB2312"),"ISO8859-1") + DateUtil.longtoDate(System.currentTimeMillis())+ ".xls";
          response.setHeader("Content-disposition", "attachment;filename="+excelFileName);
    
          Properties prop = new Properties();
          // properties文件读取
          prop.load(new InputStreamReader(new FileInputStream("/var/webapp/backserver/excel/"+fileNameFirst+".properties"), "UTF-8"));
          // 获取键值
          String[] keys = prop.getProperty("keys").split(",");
          // 获取excel头
          String[] heads = prop.getProperty("heads").split(",");

          HSSFSheet sheet = (HSSFSheet) workbook.createSheet(fileNameFirst);

          HSSFRow row = null;
          HSSFCell cell = null;
    
          int rowIndex = 1;
    
          List<Map<String,Object>> excels= (List<Map<String,Object>>)model.get("contents");
    
          row = sheet.createRow(rowIndex++);
          // 设置表头
          for(int i = 0; i < heads.length; i++) {
              cell = row.createCell(i);
              cell.setCellValue(heads[i]);
          }
          // 设置表体
          for(Map<String,Object> obj : excels) {
              row = sheet.createRow(rowIndex++);
              for(int i = 0; i < keys.length; i++){
                  cell = row.createCell(i);
                  cell.setCellValue(String.valueOf(obj.get(keys[i])));
              }
          }
      }
}
