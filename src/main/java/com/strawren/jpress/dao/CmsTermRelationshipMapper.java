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
import com.strawren.jpress.model.CmsTermRelationship;

/**
 * Mapper类，存储每个文章、链接和对应分类的关系

Posts are associated with categories and tags from the wp_terms table and this association is maintained in the wp_term_relationships table. The association of links to their respective categories are also kept in this table.，对应表CMS_TERM_RELATIONSHIP
 */
@BspMapper
public interface CmsTermRelationshipMapper extends Dao<CmsTermRelationship, Long>{

    /**
     * 更新链接对应的链接分类目录
     */
    void updateTermTaxonomyId(Map<String, Long> params);

    public void delByObjId(Long objectId);

}