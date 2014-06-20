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
import com.strawren.jpress.model.CmsTermTaxonomy;

/**
 * Mapper类，分类方法：存储每个目录、标签所对应的分类
taxonomy：分类方法(category/post_tag/nav_menu/link_category)

This table describes the taxonomy (category, link, or tag) for the entries in the wp_terms table.，对应表CMS_TERM_TAXONOMY
 */
@BspMapper
public interface CmsTermTaxonomyMapper extends Dao<CmsTermTaxonomy, Long>{

	public CmsTermTaxonomy getBydTermId(long termId);


    /**
     * 将链接分类对应的链接数减1
     * @param linkId
     */
    void decrementLinkCountByLinkId(Long linkId);
}