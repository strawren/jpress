/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月9日 上午9:36:32
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月9日        Initailized
 */

package com.strawren.jpress.dto;

import com.strawren.jpress.model.CmsTerm;


/**
 * 链接分类目录DTO
 *
 */
public class CmsLinkCategoryDTO extends CmsTerm {

    private static final long serialVersionUID = -171526011307908342L;

    /**
     * 链接分类目录下的页面总数
     *
     */
    private long linkCount;

    /**
     * 链接分类方法ID
     */
    private Long termTaxonomyId;


    /**
     * Property accessor of linkCount
     * @return the linkCount
     */
    public long getLinkCount() {
        return linkCount;
    }

    /**
     * @param linkCount the linkCount to set
     */
    public void setLinkCount(long linkCount) {
        this.linkCount = linkCount;
    }

    /**
     * Property accessor of termTaxonomyId
     * @return the termTaxonomyId
     */
    public Long getTermTaxonomyId() {
        return termTaxonomyId;
    }

    /**
     * @param termTaxonomyId the termTaxonomyId to set
     */
    public void setTermTaxonomyId(Long termTaxonomyId) {
        this.termTaxonomyId = termTaxonomyId;
    }

}
