/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-26 下午02:33:47
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-26        Initailized
 */

package com.strawren.bsp.orm.query;

import java.io.Serializable;

/**
 * 分页行数设定对象
 * 
 */
public class RowBounds implements Serializable {
    
    private static final long serialVersionUID = 154393093419524432L;
    
    // 默认分页长度
    public final static int NO_ROW_OFFSET = 0;
    // 默认最到行数
    public final static int NO_ROW_LIMIT = Integer.MAX_VALUE;
    // 默认对象
    public final static RowBounds DEFAULT = new RowBounds();
    
    private int offset;
    private int limit;
    private String countSQL;
    // 是否统计总数
    private boolean isCountRowNum = false;
    
    public RowBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
        this.isCountRowNum = false;
    }
    
    public RowBounds(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
        this.isCountRowNum = true;
    }
    
    public RowBounds(int offset, int limit, boolean isCountRowNum) {
        this.offset = offset;
        this.limit = limit;
        this.isCountRowNum = isCountRowNum;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getLimit() {
        return limit;
    }
    
    public void setDefault() {
        this.limit = DEFAULT.limit;
        this.offset = DEFAULT.offset;
    }
    
    public boolean isCountRowNum() {
        return isCountRowNum;
    }
    
    public void setCountRowNum(boolean isCountRowNum) {
        this.isCountRowNum = isCountRowNum;
    }
    
    public String getCountSQL() {
        return countSQL;
    }
}
