/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月9日 下午4:03:32
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月9日        Initailized
 */

package com.strawren.jpress.dto;

import com.strawren.jpress.model.CmsLink;


/**
 * 链接分类目录DTO
 *
 */
public class CmsLinkDTO extends CmsLink {

    private static final long serialVersionUID = 7764912839848761874L;

    /**
     * 链接分类方法ID
     */
    private Long termTaxonomyId;

    /**
     * 链接分类ID
     *
     */
    private Long linkCategoryId;

    /**
     * 链接分类名称
     *
     */
    private String linkCategoryName;


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


    /**
     * Property accessor of linkCategoryId
     * @return the linkCategoryId
     */
    public Long getLinkCategoryId() {
        return linkCategoryId;
    }


    /**
     * @param linkCategoryId the linkCategoryId to set
     */
    public void setLinkCategoryId(Long linkCategoryId) {
        this.linkCategoryId = linkCategoryId;
    }


    /**
     * Property accessor of linkCategoryName
     * @return the linkCategoryName
     */
    public String getLinkCategoryName() {
        return linkCategoryName;
    }


    /**
     * @param linkCategoryName the linkCategoryName to set
     */
    public void setLinkCategoryName(String linkCategoryName) {
        this.linkCategoryName = linkCategoryName;
    }

}
