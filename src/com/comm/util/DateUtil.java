package com.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

public class DateUtil {
    static Logger logger = Logger.getLogger(DateUtil.class);
    public static final SimpleDateFormat sdf17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    public static final SimpleDateFormat sdf14 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");

    public static final String FMT_sdf17 = "yyyyMMddHHmmssSSS";
    public static final String FMT_sdf14 = "yyyyMMddHHmmss";
    public static final String FMT_sdf14_L = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_sdf8 = "yyyyMMdd";
    public static final String FMT_sdf8_L = "yyyy-MM-dd";
    public static final String FMT_sdf6 = "yyMMdd";
    public static final String FMT_sdf9 = "HH:mm:ss.SSS";

    public static final String STARTTIME_HHmmssSSS = "000000000";
    public static final String ENDTIME_HHmmssSSS = "235959999";

    /**
     * String convert to Calendar
     * 
     * @param time
     * @return Calendar
     */
    public static Calendar stringToCal(String time) {
        Calendar result = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        result.setTime(d);
        return result;
    }

    /**
     * String convert to Calendar
     * 
     * @param time
     * @return Calendar
     */
    public static Calendar stringToCal2(String date) {
        Calendar result = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            logger.error(ClassUtil.getExceptionInfo(e));
            // e.printStackTrace();
            return null;
        }
        result.setTime(d);
        return result;
    }

    /**
     * Calendar convert to String
     * 
     * @param time
     * @return String
     */
    public static String calToString(Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = sdf.format(time.getTime());
        return result;
    }

    /**
     * 判断星期几
     * 
     * @param time
     *            yyyyMMdd
     * @return
     */
    public static int getDayOfWeek(String dayofweek) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dayofweek.substring(0, 4)), Integer.valueOf(dayofweek.substring(4, 6)) - 1,
                Integer.valueOf(dayofweek.substring(6, 8)));

        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            return 7;
        } else {
            return day - 1;
        }
    }

    /**
     * 判断时间差是否大于间隔
     * 
     * @param laststr1
     * @param nowstr2
     * @param period
     * @return
     */
    public static boolean compareTimingPeriod(String laststr1, String nowstr2, String period) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Integer.valueOf(laststr1.substring(0, 4)), Integer.valueOf(laststr1.substring(4, 6)) - 1,
                Integer.valueOf(laststr1.substring(6, 8)));
        cal1.set(Calendar.HOUR, Integer.valueOf(laststr1.substring(8, 10)));
        cal1.set(Calendar.MINUTE, Integer.valueOf(laststr1.substring(10, 12)));
        cal1.set(Calendar.SECOND, Integer.valueOf(laststr1.substring(12, 14)));

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Integer.valueOf(nowstr2.substring(0, 4)), Integer.valueOf(nowstr2.substring(4, 6)) - 1,
                Integer.valueOf(nowstr2.substring(6, 8)));
        cal2.set(Calendar.HOUR, Integer.valueOf(nowstr2.substring(8, 10)));
        cal2.set(Calendar.MINUTE, Integer.valueOf(nowstr2.substring(10, 12)));
        cal2.set(Calendar.SECOND, Integer.valueOf(nowstr2.substring(12, 14)));

        cal1.add(Calendar.MINUTE, Integer.valueOf(period));
        int result = cal2.compareTo(cal1);
        return result >= 0 ? true : false;
    }

    /**
     * 取得系统时间yyyyMMddHHmmss
     * 
     * @param period
     * @param ab
     * @return
     */
    public static String getSystemStr(String period, String ab) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String result = "";
        if ("".equals(period) || "".equals(ab)) {
            result = df.format(cal.getTime());
        } else {
            if ("+".equals(ab)) {
                result = df.format(new Date(cal.getTime().getTime() + Integer.valueOf(period) * 60 * 1000));
            }
            if ("-".equals(ab)) {
                result = df.format(new Date(cal.getTime().getTime() - Integer.valueOf(period) * 60 * 1000));
            }
        }
        return result;
    }
    
    /**
     * 取得系统时间yyyyMMddHHmmssSSS
     * 
     * @param period
     * @param ab
     * @return
     */
    public static String date2Str17(Date date) {
        return sdf17.format(date);
    }

    /**
     * 取得系统时间yyyyMMddHHmmss
     * 
     * @param period
     * @param ab
     * @return
     */
    public static String date2Str14(Date date) {
        return sdf14.format(date);
    }

    /**
     * 取得系统时间yyyyMMddHHmmss
     * 
     * @param period
     * @param ab
     * @return
     */
    public static String date2Str14(long dateLong) {
        Date date = new Date(dateLong);
        return sdf14.format(date);
    }

    /**
     * 取得系统时间yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static String date2Str8(Date date) {
        return sdf8.format(date);
    }

    /**
     * 系统时间 （long)到 yyyy/MM/dd/ HH:mm:ss转换
     */
    public static String longtoDate(long dateLong) {
        Date date = new Date(dateLong);
        return sdf17.format(date);
    }

    /**
     * 系统时间 （long)到 yyyyMMddHHmmssSSS
     */
    public static String longtoDateStr17(long dateLong) {
        Date date = new Date(dateLong);
        return fomatDateHyphen(sdf14.format(date));
    }

    /**
     * yyyyMMddHHmmss ---> yyyy年MM月dd日 HH时mm分ss秒
     * 
     * @return
     */
    public static String fomatDate(String dateStr) {
        if (null == dateStr || "".equals(dateStr) || dateStr.length() < 14) {
            return "";
        }
        String result = "";
        result = dateStr.substring(0, 4) + "年" + dateStr.substring(4, 6) + "月" + dateStr.substring(6, 8) + "日" + " "
                + dateStr.substring(8, 10) + "时" + dateStr.substring(10, 12) + "分" + dateStr.substring(12, 14) + "秒";
        return result;
    }

    /**
     * yyyyMMddHHmmss ---> yyyy/MM/dd/ HH:mm:ss
     * 
     * @return
     */
    public static String fomatDateHyphen(String dateStr) {
        if (null == dateStr || "".equals(dateStr) || dateStr.length() < 14) {
            return "";
        }
        String result = "";
        result = dateStr.substring(0, 4) + "/" + dateStr.substring(4, 6) + "/" + dateStr.substring(6, 8) + " "
                + dateStr.substring(8, 10) + ":" + dateStr.substring(10, 12) + ":" + dateStr.substring(12, 14);
        return result;
    }

    /**
     * yyyy/MM/dd/ HH:mm:ss ---> yyyyMMddHHmmss
     * 
     * @return
     */
    public static String fomatDateClearHyphen(String dateStr) {
        if (null == dateStr || "".equals(dateStr) || dateStr.length() != 19) {
            return "";
        }
        dateStr = dateStr.replace("/", "");
        dateStr = dateStr.replace(":", "");
        dateStr = dateStr.replace(" ", "");

        return dateStr;
    }

    /**
     * yyyyMMddHHmmss ---> yyyy-MM-dd- HH:mm:ss
     * 
     * @return
     */
    public static String fomatDateHyphen2(String dateStr) {
        if (null == dateStr || "".equals(dateStr) || dateStr.length() < 14) {
            return "";
        }
        String result = "";
        result = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8) + " "
                + dateStr.substring(8, 10) + ":" + dateStr.substring(10, 12) + ":" + dateStr.substring(12, 14);
        return result;
    }

    /**
     * 计算下次支付时间
     * 
     * @param paytime
     * @param npaytime
     * @return
     */
    public static String getNextPaytime(String paytime, String npaytime) {
        if (paytime == null || "".equals(paytime) || paytime.length() < 14) {
            return "20010101000000";
        }
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf14.parse(paytime));
        } catch (ParseException e) {
            logger.error(ClassUtil.getExceptionInfo(e));
        }
        cal.add(Calendar.DATE, Integer.valueOf(npaytime));
        return calToString(cal);
    }

    /**
     * 计算下次支付时间
     * 
     * @param paytimeNow
     *            本次支付时间
     * @param intervalMonth
     *            支付间隔
     * @return
     */
    public static Date getNextPaytime(Date paytimeNow, int intervalMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paytimeNow);
        calendar.add(Calendar.MONTH, intervalMonth);
        return calendar.getTime();
    }
    
    /**
     * 计算下次间隔时间
     * @param paytimeNow        当前时间
     * @param intervalMonth     间隔分钟
     * @return
     */
    public static Date getNextTimeMin(Date paytimeNow, int intervalMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paytimeNow);
        calendar.add(Calendar.MINUTE, intervalMonth);
        return calendar.getTime();
    }
    
    /**
     * 根据 Date 类型 返回 Unix时间戳
     * @param date
     * @return
     */
    public static long getUnixTime(Date date) {
        
        if(date == null) {
            return 0;
        }
        
        long dateTime = date.getTime();
        
        return (dateTime/1000L);
    }
    

    /**
     * 根据当前日期，获取下周的周一的日期
     * 
     * @param date
     *            当前日期
     * @return 下周一的日期
     */
    public static String getMonday(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day != Calendar.SUNDAY) {
                cal.add(Calendar.WEEK_OF_MONTH, 1);
            }
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 比较日期
     * 
     * @strDate1 日期1
     * @strDate2 日期2
     * @return -1:日期1<日期2 0：日期1=日期2 1：日期1>日期2
     * @throws ParseException
     **/
    public static int compareDate(String strDate1, String strDate2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(strDate1);
        Date date2 = sdf.parse(strDate2);

        int compareRlt;

        if (date1.getTime() > date2.getTime()) {
            compareRlt = 1;
        } else if (date1.getTime() < date2.getTime()) {
            compareRlt = -1;
        } else {
            compareRlt = 0;
        }

        return compareRlt;
    }

    /**
     * 根据当前日期，获取下个月一号的日期
     * 
     * @param strDate
     *            当前日期
     * @return 下个月一号的日期
     * @throws ParseException
     */
    public static String getNextMonthFirst(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String nextMonthFirstDate = "";
            cal.add(Calendar.MONTH, 1);// 加一个月
            cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
            nextMonthFirstDate = sdf.format(cal.getTime());
            return nextMonthFirstDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
    
    /**
     * 获取前几天作者后几天的日期
     * @param strDate 需要计算的日期
     * @param sdf   日期格式，输入输出格式
     * @param dayLength 加减的天数
     * @return
     */
    public static String getSomeDay(String strDate, SimpleDateFormat sdf, int dayLength) {
        try {
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String nextMonthFirstDate = "";
            cal.add(Calendar.DATE, dayLength); // 增加或减少相应天数
            nextMonthFirstDate = sdf.format(cal.getTime());
            return nextMonthFirstDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据当前日期，获取第二天日期
     * 
     * @param strDate
     *            当前日期
     * @return 第二天的日期
     * @throws ParseException
     */
    public static String getNextDay(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String nextMonthFirstDate = "";
            cal.add(Calendar.DAY_OF_YEAR, 1);// 加1天
            nextMonthFirstDate = sdf.format(cal.getTime());
            return nextMonthFirstDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取现在时间
     * 
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取两个时刻的时间间隔（秒）
     * 
     * @return 返回间隔（秒）
     */
    public static long getTimeSpan(String strFrom, String strTo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        long timeSpan;
        try {
            Date dateFrom = sdf.parse(strFrom);
            Date dateTo = sdf.parse(strTo);
            timeSpan = Math.abs((dateTo.getTime() - dateFrom.getTime())) / 1000;
            return timeSpan;
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getDateFromFileName(String date) {
        if (StringUtil.isStrNotEmpty(date)) {
            int start = date.indexOf("2");
            int end = date.indexOf(".");
            if (start > 0 && end > 0 && end > start) {
                return date.substring(start, end);
            }
        }
        return null;
    }

    /**
     * String convert to Calendar
     * 
     * @param time
     * @return Calendar
     */
    public static Calendar string14ToCal(String time) {
        Calendar result = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = null;
        try {
            d = sdf.parse(time);
        } catch (ParseException e) {
            logger.error(ClassUtil.getExceptionInfo(e));
            return null;
        }
        result.setTime(d);
        return result;
    }

    /**
     * 计算两个日期间的天数
     * 
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return
     * @throws ParseException
     */
    public static int dateDiff(String fromDate, String toDate) throws ParseException {
        int days = 0;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date from = df.parse(fromDate);
        Date to = df.parse(toDate);
        days = (int) Math.abs((to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000)) + 1;

        return days;
    }

    /**
     * 根据日期区间，获取所有日期
     * 
     * @param strDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 日期区间list
     * @throws ParseException
     */
    public static List<String> getDayList(String strDate, String endDate) {
        try {
            List<String> dayList = new ArrayList<String>();
            int days = dateDiff(strDate, endDate);
            for (int i = 0; i < days; i++) {
                String nextMonthFirstDate = "";
                if (i == 0) {
                    nextMonthFirstDate = strDate;
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    Date date = sdf.parse(strDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DAY_OF_YEAR, 1);// 加1天
                    nextMonthFirstDate = sdf.format(cal.getTime());
                    strDate = nextMonthFirstDate;
                }
                dayList.add(nextMonthFirstDate);
            }
            return dayList;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 返回星期中文名
     * 
     * @param fromDate
     *            起始日期
     * @param toDate
     *            结束日期
     * @return
     * @throws ParseException
     */
    public static String getDayOfWeekName(int day) {
        String dayname = "";
        if (day == 1) {
            dayname = "星期一";
        } else if (day == 2) {
            dayname = "星期二";
        } else if (day == 3) {
            dayname = "星期三";
        } else if (day == 4) {
            dayname = "星期四";
        } else if (day == 5) {
            dayname = "星期五";
        } else if (day == 6) {
            dayname = "星期六";
        } else if (day == 7) {
            dayname = "星期日";
        }
        return dayname;
    }

    /**
     * 从开始日期之后N个月的结束时间
     * 
     * @param startDate
     *            起始日期
     * @param months
     *            月数
     * @return
     * @throws ParseException
     */
    public static String getEndDate(Date startDate, int months) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat(DateUtil.FMT_sdf14);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, months);
        cal.add(Calendar.DATE, 0);
        return f.format(cal.getTime());
    }

    /**
     * 获取当前系统时间的14位记过 yyyyMMddHHmmss
     * 
     * @return
     */
    public static String getNow14() {
        return DateFormatUtils.format(new Date(), DateUtil.FMT_sdf14);
    }
    
    /**
     * 获取当前系统时间的8位记过 yyyyMMdd
     * 
     * @return
     */
    public static String getNow8() {
        return DateFormatUtils.format(new Date(), DateUtil.FMT_sdf8);
    }

    /**
     * yyyyMMddHHmmssSSS到 yyyy/MM/dd/ HH:mm:ss转换
     */
    public static String getDateShort(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = null;
        
        if(StringUtils.isBlank(date)){
            return "";
        }
        
        try {
            d = sdf17.parse(date);
        } catch (ParseException e) {
            logger.error(ClassUtil.getExceptionInfo(e));
            return null;
        }

        return sdf.format(d);
    }

    /**
     * 获取当前系统时间的17位记过 yyyymmddhhMMssSSS
     * 
     * @return
     */
    public static String getNow17() {
        return DateFormatUtils.format(new Date(), DateUtil.FMT_sdf17);
    }
    
    public static String getNowLast9() {
        return DateFormatUtils.format(new Date(), DateUtil.FMT_sdf9);
    }

}
