package com.comm.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;


@Component
public class ViewExcel extends AbstractExcelView { 
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
            HSSFWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        String fileNameFirst = (String)model.get("fileName");
        String excelFileName=new String(fileNameFirst.getBytes("GB2312"),"ISO8859-1") + DateUtil.longtoDate(System.currentTimeMillis())+ ".xls";
        response.setHeader("Content-disposition", "attachment;filename="+excelFileName);

        //get sheet object
        // HSSFSheet sheet=workbook.getSheetAt(0);
        HSSFSheet sheet = workbook.createSheet(fileNameFirst);

        HSSFRow row = null;
        HSSFCell cell = null;

        // set head
        // get head
        List<String> heads = (List<String>)model.get("heads");
        int rowIndex = 1;

        row = sheet.createRow(rowIndex++);
        for(int i = 0; i<heads.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(heads.get(i));
        }

        List<String[]> contents = (List<String[]>)model.get("contents");

        for(String[] strs:contents) {
            row = sheet.createRow(rowIndex++);
            for(int i = 0; i<strs.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(strs[i]);
            }
        }
    }
}
