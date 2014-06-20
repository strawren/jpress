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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.dao.CmsTermRelationshipMapper;
import com.strawren.jpress.model.CmsTermRelationship;

/**
 * 服务类，存储每个文章、链接和对应分类的关系

Posts are associated with categories and tags from the wp_terms table and this association is maintained in the wp_term_relationships table. The association of links to their respective categories are also kept in this table.，对应表CMS_TERM_RELATIONSHIP
 */
@Service
@Transactional
public class CmsTermRelationshipService extends DefaultEntityService<CmsTermRelationship, Long>{

    /** Mapper类，存储每个文章、链接和对应分类的关系

    Posts are associated with categories and tags from the wp_terms table and this association is maintained in the wp_term_relationships table. The association of links to their respective categories are also kept in this table.， */
    @Autowired
    CmsTermRelationshipMapper cmsTermRelationshipMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsTermRelationship, Long> getDao() {
        return cmsTermRelationshipMapper;
    }

    /**
     * 更新链接对应的链接分类目录
     * @param linkId
     * @param termTaxonomyId
     */
    public void updateTermTaxonomyId(Long linkId, Long termTaxonomyId) {
        Map<String, Long> params = new HashMap<String, Long>();
        params.put("linkId", linkId);
        params.put("termTaxonomyId", termTaxonomyId);

        cmsTermRelationshipMapper.updateTermTaxonomyId(params);
    }
}