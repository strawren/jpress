/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by fuhy at 2014-4-2 上午10:02:22
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * fuhy     2014-4-2        Initailized
 */
package com.strawren.jpress.dao;

import java.util.Map;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.jpress.model.CmsTermMeta;

/**
 * Mapper类，目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。
该表不为wordpress固有的，而是新增的。，对应表CMS_TERM_META
 */
@BspMapper
public interface CmsTermMetaMapper extends Dao<CmsTermMeta, Long>{

    /**
     * 修改数据状态
     */
    public void updateStatus(Map<String,Object> param);


    public void delImgPost(Long termId);

    /**
     * 根据termId获取最大的排序序号
     * @param termId
     * @return
     */
    public long getMaxOerderNo(Long termId);


}