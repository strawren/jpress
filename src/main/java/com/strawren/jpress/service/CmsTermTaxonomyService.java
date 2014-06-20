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
package com.strawren.jpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.dao.CmsTermTaxonomyMapper;
import com.strawren.jpress.model.CmsTermTaxonomy;

/**
 * 服务类，分类方法：存储每个目录、标签所对应的分类
taxonomy：分类方法(category/post_tag/nav_menu/link_category)

This table describes the taxonomy (category, link, or tag) for the entries in the wp_terms table.，对应表CMS_TERM_TAXONOMY
 */
@Service
@Transactional
public class CmsTermTaxonomyService extends DefaultEntityService<CmsTermTaxonomy, Long>{

    /** Mapper类，分类方法：存储每个目录、标签所对应的分类
    taxonomy：分类方法(category/post_tag/nav_menu/link_category)
    This table describes the taxonomy (category, link, or tag) for the entries in the wp_terms table.， */
    @Autowired
    CmsTermTaxonomyMapper cmsTermTaxonomyMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsTermTaxonomy, Long> getDao() {
        return cmsTermTaxonomyMapper;
    }

	/**
     * 根据termid 获取CmsTermTaxonomy对象
     * @param termId
     * @return
     */
    public CmsTermTaxonomy  getBydTermId(long termId){
        return cmsTermTaxonomyMapper.getBydTermId(termId);
    }


    /**
     * 将链接分类对应的链接数减1
     * @param linkId
     */
    public void decrementLinkCountByLinkId(Long linkId) {
        cmsTermTaxonomyMapper.decrementLinkCountByLinkId(linkId);
    }
}