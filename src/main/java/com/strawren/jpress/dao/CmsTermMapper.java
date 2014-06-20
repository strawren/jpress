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
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.jpress.dto.CmsLinkCategoryDTO;
import com.strawren.jpress.dto.TermTaxonomyDTO;
import com.strawren.jpress.model.CmsTerm;

/**
 * Mapper类，目录、标签
The categories for both posts and links and the tags for posts are found within the wp_terms table.，对应表CMS_TERM
 */
@BspMapper
public interface CmsTermMapper extends Dao<CmsTerm, Long>{

    /**
     * 获取所有的链接分类目录
     * @param name
     * @return
     */
    List<CmsLinkCategoryDTO> findAllLinkCategory(String name);

    /**
     * 获取所有的链接分类目录条数
     * @param criterion
     * @return
     */
    long countLinkCategory(BspCriterion criterion);

    /**
     * 分页获取所有的链接分类目录
     * @param criterion
     * @param rowBounds
     * @return
     */
    List<CmsLinkCategoryDTO> searchLinkCategory(BspCriterion criterion, RowBounds rowBounds);


    /**
     * 查询菜单条
     * @param slug
     * @return
     */
    public Long getMenuBarBySlug(Map<String, Object> map);

    public List<TermTaxonomyDTO> searchTermTaxonomy(BspCriterion criterion);

    /**
     * 获取排序做大序号
     * @param taxonomy
     * @return
     */
    public Long getMaxOerderNo(String taxonomy);


}