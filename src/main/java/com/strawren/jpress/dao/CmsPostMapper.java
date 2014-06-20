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

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.model.CmsPost;

/**
 * Mapper类，内容：包括page、revision、post、attachment

The core of the WordPress data is the posts. It is stored in the wp_posts table. Also Pages and navigation menu items are stored in this table.，对应表CMS_POST
 */
@BspMapper
public interface CmsPostMapper extends Dao<CmsPost, Long>{

    /**
     * 获取全部页面总数
     * @return
     */
    long countAllPage();

    /**
     * 获取已发布页面总数
     * @return
     */
    long countAllPublishPage();

    /**
     * 获取草稿页面总数
     * @return
     */
    long countAllDraftPage();

    /**
     * 获取回收站页面总数
     * @return
     */
    long countAllDeletePage();

    /**
     * 根据termId分页查询对应的post总数
     * @param criterion
     * @return
     */
    long countByTermId(BspCriterion criterion);

    /**
     * 根据termId分页查询所有post
     * @param criterion
     * @param rowBounds
     * @return
     */
    List<CmsPostDTO> searchByTermId(BspCriterion criterion, RowBounds rowBounds);
}