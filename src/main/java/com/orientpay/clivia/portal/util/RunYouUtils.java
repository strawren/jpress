/**
 * Copyright : http://www.orientpay.com , 2007-2012
 * Project : op-clivia-portal
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-5-21 上午1:54:28
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-5-21        Initailized
 */

package com.orientpay.clivia.portal.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.commons.collections.MapUtils;


/**
 * 润有工具类
 *
 */
public class RunYouUtils {
    /**
     * Used building output as Hex
     */
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 对字符串进行MD5加密
     * @param text明文
     * @return 密文
     */
    public static String md5(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }

        try {
            msgDigest.update(text.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {

            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");

        }

        byte[] bytes = msgDigest.digest();

        String md5Str = new String(encodeHex(bytes));

        return md5Str;
    }

    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }
    
    /**
     * 做MD5摘要
     * @param str
     * @return
     */
    public static String getString(String str) {
        return RunYouUtils.md5(str).substring(8, 24).trim();
    }
    
    /**
     * 加签意向订单.
     * @param map 待加密的参数
     * @return
     */
    public static String getMd5Str(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(MapUtils.getString(map, "appsecurity"));
        sb.append("|");
        sb.append(MapUtils.getString(map, "u_mobile"));
        sb.append("|");
        sb.append(MapUtils.getString(map, "u_name") == null ? "" : MapUtils.getString(map, "u_name"));
        sb.append("|");
        sb.append(MapUtils.getString(map, "u_address") == null ? "" : MapUtils.getShort(map, "u_address"));

        return RunYouUtils.getString(sb.toString());
    }
    
}
