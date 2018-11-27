package com.comm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.comm.controller.AdministratorController;

import net.sf.json.JSONObject;

public class ChartsUtil {

    private static Logger logger = Logger.getLogger(AdministratorController.class);
    // 生成图片保存路径
    private static String IMGPATH = "E:\\test\\img_";
    // json文件保存路径
    private static String JSONPATH = "E:\\test\\jsonData_";
    /**
     * 折线图
     */
    public static String LINECHART = "lineChart";
    /**
     * 面积图
     */
    public static String AREACHART = "areaChart";
    /**
     * 条形图
     */
    public static String BARCHART = "barChart";
    /**
     * 雷达图
     */
    public static String RADARCHART = "radarChart";

    /**
     * 生成图表
     * @param chartsFlag 生成图表类型
     * @param dataList 数据
     * @return 路径/图片名.png
     */
    public static String generateCharts(String chartsFlag, List<Object> dataList){

        String jsonString = null;
        // 判断生成图表类型并构造
        if(LINECHART.equals(chartsFlag)){
            jsonString = lineChart(dataList);
        } else if(AREACHART.equals(chartsFlag)){
            jsonString = areaChart(dataList);
        } else if(BARCHART.equals(chartsFlag)){
            jsonString = barChart(dataList);
        } else if(RADARCHART.equals(chartsFlag)){
            jsonString = radarChart(dataList);
        } else {
            logger.error("");
            return "";
        }
        // 图片名称
        String img = IMGPATH + DateUtil.getSystemStr("","") + ".png";

        try {
            // 生成图表图片
            Process process = Runtime.getRuntime()
                    .exec("phantomjs.exe WebContent/js/highcharts/highcharts-convert.js -infile " + jsonStringToJsonFile(jsonString)
                            + " -outfile " + img + " -resources WebContent/js/highcharts/highcharts.js,WebContent/js/highcharts/highcharts-more.js");
            return img;
        } catch (Exception e) {  
            logger.error(e);
            return "";
        }
    }
    /**
     * json字符串写入文件
     * @param jsonString
     * @return 路径/文件名.json
     */
    private static String jsonStringToJsonFile(String jsonString){
        // json文件名
        String jsonFile = JSONPATH + DateUtil.getSystemStr("","") + ".json";

        StringBuffer buffer = new StringBuffer();
        buffer.append(jsonString);

        File file = new File(jsonFile);
        PrintWriter pw = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            // 创建文件
            if(!file.exists()){
                file.createNewFile();
            }
            // 写入文件
            pw.write(buffer.toString().toCharArray());
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if(pw != null){
                pw.close();
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
        return jsonFile;
    }

    /**
     * 构造折线图
     * @param dataList
     * @return 图表json字符串
     */
    private static String lineChart(List<Object> dataList){
        // 构造图表
        String jsonString = "{"
                + "\"chart\": {\"height\":400, \"width\":1600},"
                + "\"title\": {\"text\": \"\"},"
                + "\"xAxis\": {\"categories\": ['0:00', '2:00', '4:00', '6:00', '8:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00']},"
                + "\"yAxis\": {\"title\": {text: ''},\"plotLines\": [{\"value\": 0,\"width\": 1,\"color\": '#808080'}]},"
                + "\"credits\": {\"enabled\": false},"
                + "\"legend\": {\"enabled\": false}"
                + "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        List<Map<String,Object>> seriesList = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("data", dataList);

        seriesList.add(map);
        // 填充数据
        jsonObject.put("series", seriesList);

        return jsonObject.toString();
    }
    /**
     * 构造面积图
     * @param dataList
     * @return 图表json字符串
     */
    private static String areaChart(List<Object> dataList){
        // 构造图表
        String jsonString = "{"
                + "\"chart\":{\"type\": \"area\",\"height\":400, \"width\":1600},"
                + "\"title\":{\"text\": \"\"},"
                + "\"xAxis\":{\"allowDecimals\": false,\"labels\":{\"formatter\":function(){return this.value;}}},"
                + "\"yAxis\":{\"title\":{\"text\":\"\"},\"labels\":{\"formatter\": function(){return this.value/1000 + 'k';}}},"
                + "\"plotOptions\":{\"area\":{\"pointStart\":1940,\"marker\":{\"enabled\":false,\"symbol\":'circle',\"radius\":2,\"states\":{\"hover\":{\"enabled\":true}}}}},"
                + "\"credits\":{\"enabled\": false},"
                + "\"legend\": {\"enabled\": false}"
                + "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        List<Map<String,Object>> seriesList = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("data", dataList);

        seriesList.add(map);
        // 填充数据
        jsonObject.put("series", seriesList);

        return jsonObject.toString();
    }
    /**
     * 构造条形图
     * @param dataList
     * @return 图表json字符串
     */
    private static String barChart(List<Object> dataList){
        // 构造图表
        String jsonString = "{"
                + "\"title\": {\"text\": \"\",\"height\":400, \"width\":1600},"
                + "\"xAxis\": {\"categories\": ['0:00', '2:00', '4:00', '6:00', '8:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00']},"
                + "\"yAxis\": {\"title\": {text: ''},\"plotLines\": [{\"value\": 0,\"width\": 1,\"color\": '#808080'}]},"
                + "\"credits\": {\"enabled\": false},"
                + "\"legend\": {\"enabled\": false}"
                + "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        List<Map<String,Object>> seriesList = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("data", dataList);

        seriesList.add(map);
        // 填充数据
        jsonObject.put("series", seriesList);

        return jsonObject.toString();
    }
    /**
     * 构造雷达图
     * @param dataList
     * @return 图表json字符串
     */
    private static String radarChart(List<Object> dataList){
        // 构造图表
        String jsonString = "{"
                + "\"chart\": {\"polar\": true,\"type\": \"line\",\"height\":400, \"width\":400},"
                + "\"title\": {\"text\": \"\"},"
                + "\"xAxis\": {\"categories\": ['销售', '市场营销', '发展', '客户支持','信息技术', '行政管理'],\"tickmarkPlacement\": 'on',\"lineWidth\": 0},"
                + "\"yAxis\": {\"gridLineInterpolation\": \"polygon\",\"lineWidth\": 0,\"min\": 0},"
                + "\"tooltip\": {\"shared\": true,\"pointFormat\": '<span style=\"color:{series.color}\">{series.name}: <b>${point.y:,.0f}</b><br/>'},"
                + "\"legend\": {\"enabled\": false},"
                + "\"credits\": {\"enabled\": false}"
                + "}";

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        List<Map<String,Object>> seriesList = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        map.put("data", dataList);

        seriesList.add(map);
        // 填充数据
        jsonObject.put("series", seriesList);

        return jsonObject.toString();
    }
}
