/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2013-11-4 下午5:27:08
 * $URL$
 * 
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * gaowm 2013-11-4 Initailized
 */

package com.strawren.bsp.util;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

/**
 * md5加密工具类
 * 
 */
public class Md5Utils {
    
    private Md5Utils() {
        
    }
    
    /**
     * 32位MD5签名
     * 
     * @param strObj
     *            明文串
     * @param charsetName
     *            字符编码，可为空
     * @return
     */
    public static String getMd5Code(String strObj, String charsetName) {
        if(strObj == null){
            return "";
        }
        return getMd5Code(strObj, charsetName, false);
    }
    
    /**
     * 16位或者32位MD5签名
     * 
     * @param plainText
     *            明文串
     * @param charsetName
     *            字符编码，可为空
     * @param lenFlag
     *            返回的签名长度 默认(false)false:32位;true：16位
     * @return
     */
    public static String getMd5Code(String plainText, String charsetName, boolean shortFlag) {
        if(plainText == null){
            return "";
        }
        
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] byteArr;
            if (StringUtils.isNotBlank(charsetName)) {
                byteArr = plainText.getBytes(charsetName);
            }
            else {
                byteArr = plainText.getBytes();
            }
            md.update(byteArr);
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            if (shortFlag) {
                result = buf.toString().substring(8, 24).toUpperCase();
            }
            else {
                result = buf.toString().toUpperCase();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(Md5Utils.getMd5Code("", "UTF-8"));
        System.out.println(Md5Utils.getMd5Code("0000", "UTF-8"));
        System.out.println(Md5Utils.getMd5Code("000000", "UTF-8", true));
        System.out.println(Md5Utils.getMd5Code("CHNIA201307290141033321", "", true));
    }
    
}
