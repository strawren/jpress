/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-11-29 下午12:51:26
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-11-29        Initailized
 */

package com.strawren.bsp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数字处理帮助类
 *
 */
public class NumberUtils {
    public static void main(String[] args) {
        float a = 1.1f;
        float b = 2.2f;
        System.out.println(NumberUtils.add(a, b));
        System.out.println(NumberUtils.sub(a, b));
        System.out.println(NumberUtils.mul(a, b));
        System.out.println(NumberUtils.div(a, b));
        System.out.println(NumberUtils.round(a));
        System.out.println(NumberUtils.numberNotPointFormat(b * 10));
        
        System.out.println(NumberUtils.div(100L, 100) + ">");
        double d = 88.88;
        long l = Math.round(d);
        System.out.println(l);
        
    }
    
    // 加法
    public static float add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }
    
    // 减法
    public static float sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    // 乘法
    public static float mul(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).floatValue();
    }

    // 除法
    public static float div(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    // 截取3位
    public static float round(float v) {
        BigDecimal b = new BigDecimal(Float.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 3, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static String numberFormat(String pattern, double value) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String numberPointFormat(double value) {
        return new DecimalFormat("0.00").format(value);
    }

    public static String numberNotPointFormat(double value) {
        return new DecimalFormat("0").format(value);
    }
    
    public static String numberNotPointFormat(BigDecimal bd){
        return numberNotPointFormat(bd, 100);
    }
    
    public static String numberNotPointFormat(BigDecimal bd, int scale){
        double d = bd.doubleValue() * scale;
        return numberNotPointFormat(d);
    }
    
    public static BigDecimal StringNotPointFormat(String bd, float scale){
        return BigDecimal.valueOf(mul(Long.parseLong(bd), scale));
    }
    
    public static long StringToLong(String str){
        if(isNumber(str)){
            if(str.length() > 18){
                return -1;
            }
            return Long.parseLong(str);
        }
        return -1;
    }
    
    public static int StringToInt(String str){
        if(isNumber(str)){
            if(str.length() > 9){
                return 0;
            }
            return Integer.parseInt(str);
        }
        return 0;
    }
    
    // 检查是否是数字
    public static boolean isNumber(String value) { 
        String patternStr = "^\\d+$";
        Pattern p = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE); // 忽略大小写;
        Matcher m = p.matcher(value);
        return m.find();
    }
    
}
