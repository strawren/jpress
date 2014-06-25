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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.bsp.util.BspCriterionUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.CmsTermMapper;
import com.strawren.jpress.dao.CmsTermTaxonomyMapper;
import com.strawren.jpress.dto.CmsLinkCategoryDTO;
import com.strawren.jpress.dto.TermTaxonomyDTO;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.util.ModelInfoUtils;

/**
 * 服务类，目录、标签
The categories for both posts and links and the tags for posts are found within the wp_terms table.，对应表CMS_TERM
 */
@Service
@Transactional
public class CmsTermService extends DefaultEntityService<CmsTerm, Long>{

    /** Mapper类，目录、标签
    The categories for both posts and links and the tags for posts are found within the wp_terms table.， */
    @Autowired
    CmsTermMapper cmsTermMapper;

    @Autowired
    CmsTermTaxonomyMapper termTaxonomyServiceMapper;

    @Autowired
    CmsTermTaxonomyMapper cmsTermTaxonomyMapper;

    @Autowired
    CmsTermMetaService termMetaService;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsTerm, Long> getDao() {
        return cmsTermMapper;
    }

    /**
     * 添加分类
     * @param term
     * @param taxonomy
     * @param post
     */
    public void addTermTaxonomy(CmsTerm term, CmsTermTaxonomy taxonomy, String postId)throws Exception{
        Long maxOrderNo = getMaxOerderNo(JpressConstants.MENU_PONIT_TYPE_TAG);
        term.setJgroup("" + (maxOrderNo + 1));
        cmsTermMapper.save(term);
        long termId = term.getId();
        if(termId > 0){
            if(StringUtils.isNotBlank(postId)){
                CmsTermMeta termMeta = new CmsTermMeta();
                termMeta.setTermId(termId);
                termMeta.setMetaType(JpressConstants.DICT_POST_TYPE_POST);
//                termMeta.setKey(CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX + termId + "_" + postId);
                termMeta.setJkey(JpressConstants.KEY_PRCTURE_FEATURE_PERFIX);
                termMeta.setName(JpressConstants.PRCTURE_FEATURE_TEXT);
                termMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
                termMeta.setValue(String.valueOf(postId));
                ModelInfoUtils.createModelInfoBySys(termMeta);
                termMetaService.save(termMeta);
            }
            taxonomy.setTermId(termId);
            termTaxonomyServiceMapper.save(taxonomy);
        }else{
            log.debug("term not fund,add termTaxonomy fail!!");
        }

    }


    /**
     * 添加链接分类目录
     * @param linkCategory
     */
    public void addLinkCategory(CmsTerm linkCategory) {
      //TODO 设置操作人ID,name,time
        Long operId = 1L;
        String operName = "管理员";
        Date curTime = new Date();

        linkCategory.setCreateOperId(operId);
        linkCategory.setCreateOperName(operName);
        linkCategory.setCreateTime(curTime);
        linkCategory.setLastUpdOperId(operId);
        linkCategory.setLastUpdOperName(operName);
        linkCategory.setLastUpdTime(curTime);

        linkCategory.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        cmsTermMapper.save(linkCategory);

        Long termId = linkCategory.getId();

        CmsTermTaxonomy termTaxonomy = new CmsTermTaxonomy();

        termTaxonomy.setTermId(termId);
        termTaxonomy.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_LINK);
        termTaxonomy.setParentId(0L);
        termTaxonomy.setPostCount(0);

        termTaxonomy.setCreateOperId(operId);
        termTaxonomy.setCreateOperName(operName);
        termTaxonomy.setCreateTime(curTime);
        termTaxonomy.setLastUpdOperId(operId);
        termTaxonomy.setLastUpdOperName(operName);
        termTaxonomy.setLastUpdTime(curTime);
        termTaxonomy.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);

        cmsTermTaxonomyMapper.save(termTaxonomy);
    }

    /**
     * 获取所有的链接分类目录
     * @param name
     * @return
     */
    public List<CmsLinkCategoryDTO> findAllLinkCategory(String name) {
        return cmsTermMapper.findAllLinkCategory(name);
    }

    /**
     * 删除链接分类目录(支持批量)
     * @param categoryIds
     */
    public void batchDeleteLinkCategory(String[] categoryIds) {
        if (categoryIds!=null&&categoryIds.length>0) {
            for (int i = 0; i < categoryIds.length; i++) {
                Long categoryId = Long.parseLong(categoryIds[i]);

                CmsTerm linkCategory = new CmsTerm();
                linkCategory.setId(categoryId);
                linkCategory.setStatus(JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE);

                //TODO 设置操作人ID,name,time
                ModelInfoUtils.updateModelInfoBySys(linkCategory);

                //避免脏数据检查
                CmsTerm oldLinkCategory = cmsTermMapper.get(categoryId);
                linkCategory.setLastUpdTime(oldLinkCategory.getLastUpdTime());

                cmsTermMapper.update(linkCategory);
            }
        }
    }

    /**
     * 分页获取所有的链接分类目录
     * @param search
     * @param page
     * @return
     */
    public Page<CmsLinkCategoryDTO> findAllLinkCategory(String search, Page<CmsLinkCategoryDTO> page) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "t.name", search));

        BspCriterion criterion = BspCriterionUtils.createOecsCriterion(page, filters);
        RowBounds rowBounds = BspCriterionUtils.createRowBounds(page);

        long count = cmsTermMapper.countLinkCategory(criterion);
        page.setTotalCount(count);

        if (count>0) {
            List<CmsLinkCategoryDTO> result = cmsTermMapper.searchLinkCategory(criterion, rowBounds);
            page.setResult(result);
        }

        return page;
    }


    /**
     * 根据别名和分类方法类型查找term
     * @param slug
     * @return
     */
    public Long getMenuBarBySlug(Map<String,Object> param){
        return cmsTermMapper.getMenuBarBySlug(param);
    }


    /**
     * 查询分类分类方法
     * @param filters
     * @return
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<TermTaxonomyDTO> searchTermTaxonomy(List filters, List<Order> orders){
        BspCriterion criterion = new BspCriterion(filters);
        if(orders != null && orders.size() > 0){
            StringBuilder orderBySb = new StringBuilder();
            StringBuilder orderSb = new StringBuilder();
            int i = 0;
            Order order;
            for(Iterator iter = orders.iterator(); iter.hasNext(); orderSb.append(order.getOrder()))
            {
                order = (Order)iter.next();
                if(i != 0)
                {
                    orderBySb.append(",");
                    orderSb.append(",");
                }
                i++;
                orderBySb.append(order.getOrderBy());
            }

            criterion.setOrderBy(orderBySb.toString());
            criterion.setOrder(orderSb.toString());
        }
        return cmsTermMapper.searchTermTaxonomy(criterion);
    }

    /**
     * 添加系统分类，主要用来添加商品公用属性
     */
    public Long addSysTerm(){
        CmsTerm term = new CmsTerm();
        term.setSlug(JpressConstants.SYS_COMMON_META_TERM_SLUG);
        term.setName("商品公共属性");
        term.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(term);
        save(term);
        CmsTermTaxonomy termTax = new CmsTermTaxonomy();
        termTax.setTermId(term.getId());
        termTax.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_COMMONMETA);
        termTax.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(termTax);
        termTaxonomyServiceMapper.save(termTax);
        Long termId = term.getId();
        return termId;
    }

    /**
     * 获取排序最大序号
     * @param taxonomy
     * @return
     */
    public Long getMaxOerderNo(String taxonomy){
        return cmsTermMapper.getMaxOerderNo(taxonomy);
    }


}