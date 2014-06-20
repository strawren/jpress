/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-5-12 上午3:15:38
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-5-12        Initailized
 */

package com.strawren.jpress.dto;

import com.strawren.jpress.model.CmsPost;


/**
 * 菜单post的dto，增加菜单目标对象的数据类型，如：link、post、term、tag等；
 * 增加菜单的父id字段；
 *
 */
public class MenuPostDTO extends CmsPost {

    private static final long serialVersionUID = 1L;
    
    /**目标对象数据类型*/
    private String tarObjType;

    /**菜单的父id*/
    private long menuParentId;
    
    /**
     * Property accessor of tarObjType
     * @return the tarObjType
     */
    public String getTarObjType() {
        return tarObjType;
    }

    /**
     * @param tarObjType the tarObjType to set
     */
    public void setTarObjType(String tarObjType) {
        this.tarObjType = tarObjType;
    }
    
    /**
     * Property accessor of menuParentId
     * @return the menuParentId
     */
    public long getMenuParentId() {
        return menuParentId;
    }
    
    /**
     * @param menuParentId the menuParentId to set
     */
    public void setMenuParentId(long menuParentId) {
        this.menuParentId = menuParentId;
    }
}
