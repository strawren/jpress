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
import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.jpress.model.CmsPostMeta;

/**
 * Mapper类，内容的属性，对应表CMS_POST_META
 */
@BspMapper
public interface CmsPostMetaMapper extends Dao<CmsPostMeta, Long>{

    public void delByTermMetaIdAndKey(Long postId);


    /**
     * 删除内容特色图片关联
     * @param postId
     */
    public void delPostImgMeta(Long postId);

    /**
     * 根据termId获取最大的排序序号
     * @param termId
     * @return
     */
    public long getMaxOerderNo(Long postId);

}