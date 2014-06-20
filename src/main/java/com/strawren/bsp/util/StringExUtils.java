/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by jason at 2011-10-25 下午7:47:19
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * jason     2011-10-25        Initailized
 */

package com.strawren.bsp.util;

import com.strawren.bsp.core.Constants;


/**
 * StringUtils的扩展工具类
 *
 */
public class StringExUtils {

    /**
     * 字符串填充
     * @param src
     * @param fix
     * @param finalSize
     * @return
     */
    public static String postfixStrWith(String src, String fix, int finalSize) {
        src = (src == null) ? "" : src;
        StringBuffer result = produceFix(src, fix, finalSize);
        return src + result.toString();
    }

    /**
     * 空白填充
     * @param src
     * @param finalSize
     * @return
     */
    public static String postfixStrWithBlank(String src, int finalSize) {
        return postfixStrWith(src, " ", finalSize);
    }

    /**
     * 前缀填充
     * @param src 源字符串
     * @param fix 填充时加入的字符
     * @param finalSize 字符最终长度
     * @return String 填充后的字符
     */
    public static String prefixStrWith(String src, String fix, int finalSize) {
        src = (src == null) ? "" : src;
        StringBuffer result = produceFix(src, fix, finalSize);
        return result.toString() + src;
    }

    public static String varLenWithValue(String val, int len, String charset){
        StringBuilder sb = new StringBuilder();

        int valLen = 0;
        if(val == null){
            val = "";
        }
        try{
            valLen = val.getBytes(charset).length;
        }
        catch(Exception e){
            valLen = val.getBytes().length;
        }
        String valLenStr = String.valueOf(valLen);

        int valLenStrLen = valLenStr.getBytes().length;
        int diff = len - valLenStrLen;
        for(int i  = 0; i < diff; i++){
            sb.append("0");
        }

        sb.append(valLenStr);
        sb.append(val);

        return sb.toString();
    }

    public static String stringToCertCharset(String str, String charset){
        try{
            return new String(str.getBytes(), charset);
        }
        catch(Exception e){
            return str;
        }
    }

    public static String byteToCertCharset(byte [] bytes, String charset){
        try{
            return new String(bytes, charset);
        }
        catch(Exception e){
            return new String();
        }
    }

    /**
     * 前缀填充
     * @param src 源字符串
     * @param fix 填充时加入的字符
     * @param finalSize 字符最终长度
     * @return String 填充后的字符
     */
    private static StringBuffer produceFix(String src, String fix, int finalSize) {
        try{
            AssertUtils.isTrue(src.getBytes(Constants.DEFAULT_PROTOCOL_CHARSET_NAME).length <= finalSize,
                    "src.length() should <= finalSize, but not!");
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < finalSize - src.getBytes(Constants.DEFAULT_PROTOCOL_CHARSET_NAME).length; i++) {
                result.append(fix);
            }
            return result;
        }
        catch(Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String [] arg){
        System.out.println(varLenWithValue("你好",2,Constants.DEFAULT_PROTOCOL_CHARSET_NAME));
    }
}
