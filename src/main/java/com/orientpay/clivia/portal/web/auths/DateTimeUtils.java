/**
 * Copyright : http://www.orientpay.com , 2007-2012
 * Project : oecs-g2-common-utility-trunk
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-4 下午02:56:15
 * $URL$
 * 
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * ZhouXushun 2011-8-4 Initailized
 */

package com.orientpay.clivia.portal.web.auths;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期时间帮助类
 * 
 */
public class DateTimeUtils {
    
    private static final Log log = LogFactory.getLog(DateTimeUtils.class);
    
    // 默认的时间格式
    public static final String DEFUALT_SHOT_TIME_FORMAT = "yyyy-MM-dd";
    public static final String SHOT_DATE_FORMAT = "yyyyMMdd";
    public static final String DEFUALT_LONG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    public static final String SHOT_TIME_FORMAT = "HHmmss";
    private DateTimeUtils() {
        
    }
    
    /**
     * 清除小时分钟和秒
     */
    public static Date clearHourMiniteSecond(Date date) {
        if (date == null) {
            return null;
        }
        
        Calendar gc = GregorianCalendar.getInstance();
        gc.clear();
        gc.setTime(date);
        
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        
        return gc.getTime();
    }
    
    /**
     * 按照指定格式返回当前时间，如果格式为空，则默认为yyyy-MM-dd
     * 
     * @param dateFormat
     * @return
     */
    public static String getCurrentDateToString(String dateFormat) {
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DEFUALT_SHOT_TIME_FORMAT;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(getCurrentDate());
    }
    
    /**
     * 按照指定格式返回当前时间，如果格式为空，则默认为yyyy-MM-dd
     * 
     * @param dateFormat
     * @return
     */
    public static Date stringFormatToDate(String date, String dateFormat) {
        if (date == null) {
            return null;
        }
        
        if (StringUtils.isBlank(dateFormat)) {
            if (date.length() > 10) {
                dateFormat = DEFUALT_LONG_TIME_FORMAT;
            }
            else {
                dateFormat = DEFUALT_SHOT_TIME_FORMAT;
            }
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(date);
        }
        catch (Exception e) {
            log.info("INFO", e);
            return null;
        }
    }
    
    /**
     * 格式化日期输出，如果格式为空，则默认为yyyy-MM-dd
     * 
     * @param date
     * @param dateFormat
     * @return
     */
    public static String dateToStringFormat(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DEFUALT_SHOT_TIME_FORMAT;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.format(date);
        }
        catch (Exception e) {
            log.info("INFO", e);
            return null;
        }
    }
    
    /**
     * 得到当前时间
     * 
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    
    /**
     * 比较两个时间的间隔，单位为毫秒
     * 
     * @param endDate
     * @param startDate
     * @return
     */
    public static int diffTwoDateWithTime(Date endDate, Date startDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }
        int diff = (int) (endDate.getTime() - startDate.getTime());
        
        return diff;
    }
    
    /**
     * 比较两个时间的间隔，单位为小时
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffTwoDateWithHour(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000) + 1);
    }
    
    /**
     * 时间增加一天Calendar的使用
     * @param s
     * @param n
     * @return
     */
    public static String addDate(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(SHOT_DATE_FORMAT);
            
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);// 增加一天
            // cd.add(Calendar.MONTH, n);//增加一个月
            
            return sdf.format(cd.getTime());
            
        }
        catch (Exception e) {
            log.info("INFO", e);
            
            return null;
        }
        
    }
    
   
}
