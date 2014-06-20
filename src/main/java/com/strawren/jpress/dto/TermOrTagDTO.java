/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-5-8 上午3:07:11
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-5-8        Initailized
 */

package com.strawren.jpress.dto;

import com.strawren.jpress.model.CmsTerm;


/**
 * 分类或标签dto
 *
 */
public class TermOrTagDTO extends CmsTerm {
    private static final long serialVersionUID = 1L;
    
    //分类或标签2种类型
    private String dataType;

    
    public String getDataType() {
        return dataType;
    }

    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
}
