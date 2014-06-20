/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月24日 上午10:09:40
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月24日        Initailized
 */

package com.strawren.jpress.dto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsPostMeta;


/**
 * PostDTO
 *
 */
public class CmsPostDTO extends CmsPost {

    private static final long serialVersionUID = -7090896298146865596L;

    /**
     * 用户订购的商品数量
     */
    private int count = 1;

    /**
     * post对应的meta数据,key对应key字段,value对应CmsPostMeta
     *
     */
    private Map<String, CmsPostMeta> metaMap = new HashMap<String, CmsPostMeta>();


    /**
     * Property accessor of metaMap
     * @return the metaMap
     */
    public Map<String, CmsPostMeta> getMetaMap() {
        return metaMap;
    }

    /**
     * @param metaMap the metaMap to set
     */
    public void setMetaMap(Map<String, CmsPostMeta> metaMap) {
        this.metaMap = metaMap;
    }

    /**
     * Property accessor of count
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
