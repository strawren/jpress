/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : bsp
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-1 下午06:56:10
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-1        Initailized
 */

package com.strawren.bsp.util;

/**
 * Hex 处理工具类
 * 
 */
public class HexUtils {
    
    // 16进制
    public static final char[] hex = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F' };
    
    private HexUtils(){
        
    }
    
    /**
     * 把用十六进制字符串描述的数据转成byte类型
     * @param b
     * @return
     */
    public static final byte[] fromHex(String b) {
        char[] data = b.toCharArray();
        int l = data.length;
        
        byte[] out = new byte[l >> 1];
        int i = 0;
        for (int j = 0; j < l;) {
            int f = Character.digit(data[(j++)], 16) << 4;
            f |= Character.digit(data[(j++)], 16);
            out[i] = (byte) (f & 0xFF);
            ++i;
        }
        
        return out;
    }
    
    /**
     * 把byte类型转成用十六进制字符串描述的字符串
     * @param b
     * @return
     */
    public static final String toHex(byte[] b) {
        char[] buf = new char[b.length * 2];
        int j;
        int i = j = 0;
        for (; i < b.length; ++i) {
            int k = b[i];
            buf[(j++)] = hex[(k >>> 4 & 0xF)];
            buf[(j++)] = hex[(k & 0xF)];
        }
        return new String(buf);
    }
}