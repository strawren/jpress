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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.bsp.util.BspCriterionUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.CmsLinkMapper;
import com.strawren.jpress.dto.CmsLinkDTO;
import com.strawren.jpress.model.CmsLink;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.util.ModelInfoUtils;

/**
 * 服务类，链接，对应表CMS_LINK
 */
@Service
@Transactional
public class CmsLinkService extends DefaultEntityService<CmsLink, Long>{

    /** Mapper类，链接， */
    @Autowired
    CmsLinkMapper cmsLinkMapper;

    @Autowired
    CmsTermRelationshipService termRelationshipService;

    @Autowired
    CmsTermTaxonomyService termTaxonomyService;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsLink, Long> getDao() {
        return cmsLinkMapper;
    }

    /**
     * 保存一个链接
     * @param link
     */
    public void saveLink(CmsLink link,Long termTaxonomyId) {
        //TODO 设置操作人ID,name,time
        Long operId = 1L;
        link.setOwnerId(operId);
        ModelInfoUtils.createModelInfoBySys(link);

        link.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);

        cmsLinkMapper.save(link);

        Long linkId = link.getId();

        CmsTermRelationship termRelationship = new CmsTermRelationship();

        ModelInfoUtils.createModelInfoBySys(termRelationship);

        termRelationship.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        termRelationship.setTermTaxonomyId(termTaxonomyId);

        termRelationship.setObjectId(linkId);

        termRelationshipService.save(termRelationship);

        //更新链接总数
        if(termTaxonomyId != null) {
        	incrementLinkCount(termTaxonomyId);
        }
    }

    /**
     * 查询所有链接
     * @param name
     * @return
     */
    public List<CmsLinkDTO> findAllLink(String name) {
        return cmsLinkMapper.findAllLink(name);
    }

    /**
     * 批量删除链接
     * @param linkIds
     */
    public void removeLinks(String[] linkIds) {
        //判空
        if (linkIds==null||linkIds.length==0) {
            return;
        }

        for (int i = 0; i < linkIds.length; i++) {
            Long linkId = Long.parseLong(linkIds[i]);

            CmsLink link = new CmsLink();
            link.setId(linkId);
            link.setStatus(JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE);

            //TODO
            ModelInfoUtils.updateModelInfoBySys(link);

            //避免脏数据检查
            CmsLink oldLink = cmsLinkMapper.get(linkId);
            link.setLastUpdTime(oldLink.getLastUpdTime());

            cmsLinkMapper.update(link);

            //将链接分类对应的链接数减1
            termTaxonomyService.decrementLinkCountByLinkId(linkId);
        }
    }

    /**
     * 根据ID查找链接
     * @param id
     * @return
     */
    public CmsLinkDTO findLinkById(Long id) {
        return cmsLinkMapper.findLinkById(id);
    }

    /**
     * 更新链接
     * @param link
     * @param termTaxonomyId
     */
    public void updateLink(CmsLinkDTO link, Long termTaxonomyId) {
        //将链接分类对应的链接数减1
        termTaxonomyService.decrementLinkCountByLinkId(link.getId());

        //TODO 设置操作人ID,name,time
        ModelInfoUtils.updateModelInfoBySys(link);

        cmsLinkMapper.update(link);

        //更新链接对应的链接分类目录
        Long linkId = link.getId();
        termRelationshipService.updateTermTaxonomyId(linkId,termTaxonomyId);

        //更新链接总数
        incrementLinkCount(termTaxonomyId);

    }

    /**
     * 将链接分类目录对应的链接总数加1
     * @param termTaxonomyId
     */
    private void incrementLinkCount(Long termTaxonomyId) {
    	if(termTaxonomyId < 1) {
    		return;
    	}
        CmsTermTaxonomy oldTermTaxonomy = termTaxonomyService.get(termTaxonomyId);

        CmsTermTaxonomy termTaxonomy = new CmsTermTaxonomy();
        termTaxonomy.setId(termTaxonomyId);
        termTaxonomy.setPostCount(oldTermTaxonomy.getPostCount()+1);
        termTaxonomy.setLastUpdTime(oldTermTaxonomy.getLastUpdTime());


        termTaxonomyService.update(termTaxonomy);
    }

    /**
     * 分页查询链接
     * @param search
     * @param page
     * @return
     */
    public Page<CmsLinkDTO> searchLink(String search, Page<CmsLinkDTO> page) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "l.name", search));

        BspCriterion criterion = BspCriterionUtils.createOecsCriterion(page, filters);
        org.apache.ibatis.session.RowBounds rowBounds = BspCriterionUtils.createRowBounds(page);

        long count = cmsLinkMapper.countLink(criterion);
        page.setTotalCount(count);

        if (count>0) {
            List<CmsLinkDTO> result = cmsLinkMapper.searchLink(criterion, rowBounds);
            page.setResult(result);
        }

        return page;
    }

    /**
     * 根据别名获取链接
     * @param alias
     * @return
     */
    public CmsLink getLinkUrlByAlias(String alias) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "ALT", alias));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsLink> links = search(filters);

        if (CollectionUtils.isNotEmpty(links)) {
            return links.get(0);
        }
        return null;
    }


}