package com.strawren.jpress.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.BspCriterionUtils;
import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.TermRelevanceTaxonomyMapper;
import com.strawren.jpress.dto.TermRelevanceTaxonomyDTO;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.util.ModelInfoUtils;

@Service
@Transactional
public class TermRelevanceTaxonomyService {

    /** Mapper类， */
    @Autowired
    TermRelevanceTaxonomyMapper termRelevanceTaxonomyMapper;

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsTermTaxonomyService termTaxonomyService;

    @Autowired
    CmsTermMetaService termMetaService;

    @Autowired
    CmsTermRelationshipService termRelationshipService;

    @Autowired
    CmsPostMetaService postMetaService;


    /**
     * 根据termId获取term、termTaxonomy
     * @param termId
     * @return
     */
    public TermRelevanceTaxonomyDTO gettermTaxbyId(long termId){

        return termRelevanceTaxonomyMapper.gettermTaxbyId(termId);

    }


    @SuppressWarnings("unchecked")
    public List<CmsTerm> getAllRelevance()
    {
        BspCriterion criterion = new BspCriterion();
        return termRelevanceTaxonomyMapper.searchTremRelevanceTaxonomy(criterion, null);
    }



    public Page<?> getAllRelevance(Page<?> page)
    {
        return searchRelevance(page, null);
    }

    @SuppressWarnings({ "rawtypes",
            "unchecked" })
    public Page searchRelevance(Page page, List filters)
    {
    	BspCriterion criterion = BspCriterionUtils.createOecsCriterion(page, filters);
        org.apache.ibatis.session.RowBounds rowBounds = BspCriterionUtils.createRowBounds(page);
        List result = termRelevanceTaxonomyMapper.searchTremRelevanceTaxonomy(criterion, rowBounds);
        page.setResult(result);
        if(page.isAutoCount()){
            page.setTotalCount(termRelevanceTaxonomyMapper.count(criterion));
        }
        return page;
    }

    @SuppressWarnings("rawtypes")
    public List searchRelevance(List filters)
    {
        return searchRelevance(filters, null);
    }

    @SuppressWarnings({ "rawtypes",
            "unchecked" })
    public List searchRelevance(List filters, List orders)
    {
    	BspCriterion criterion = new BspCriterion(filters);
        if(orders != null) {
            StringBuilder orderBySb = new StringBuilder();
            StringBuilder orderSb = new StringBuilder();
            int i = 0;
            Order order;
            for(Iterator<?> i$ = orders.iterator(); i$.hasNext(); orderSb.append(order.getOrder())) {
                order = (Order)i$.next();
                if(i != 0) {
                    orderBySb.append(",");
                    orderSb.append(",");
                }
                i++;
                orderBySb.append(order.getOrderBy());
            }

            criterion.setOrderBy(orderBySb.toString());
            criterion.setOrder(orderSb.toString());
        }
        return termRelevanceTaxonomyMapper.searchTremRelevanceTaxonomy(criterion, null);
    }


    /**
     * 分类目录编辑
     * @param TermTaxonomyDTO
     * @param parentFlag
     */
    public void termUpdate(CmsTerm term,String taxLastUpdTime,String parentId){
        ModelInfoUtils.updateModelInfoBySys(term);
        termService.update(term);
        CmsTermTaxonomy termTaxonomy = termTaxonomyService.getBydTermId(term.getId());
        termTaxonomy.setParentId(Long.valueOf(parentId));
        termTaxonomy.setLastUpdTime(DateTimeUtils.stringFormatToDate(taxLastUpdTime, "yyyy-MM-dd HH:mm:ss"));
        ModelInfoUtils.updateModelInfoBySys(termTaxonomy);
        termTaxonomyService.update(termTaxonomy);
    }


    /**
     * 分类目录删除
     * @param termId
     */
    public void termTaxDelete(String termId){

//        CmsTerm term = termService.get(Long.valueOf(termId));
//        term.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
//        ModelInfoUtils.updateModelInfoBySys(term);
//        termService.update(term);

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_ID", termId));
        List<CmsTermMeta> termMetaList = termMetaService.search(filters);
//        taxonomy.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
//        ModelInfoUtils.updateModelInfoBySys(taxonomy);
//        termTaxonomyService.update(taxonomy);
        //删除分类属性
        if(termMetaList != null && termMetaList.size() > 0){
            for (CmsTermMeta cmsTermMeta : termMetaList) {
              //termMeta关联的postMeta
                List<PropertyFilter> filterPostMeta = new ArrayList<PropertyFilter>();
                filterPostMeta.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filterPostMeta.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_META_ID", cmsTermMeta.getId().toString()));
                List<CmsPostMeta> postMetaList = postMetaService.search(filterPostMeta);
                if(postMetaList != null && postMetaList.size() > 0){
                    //删除postMeta
                    for (CmsPostMeta cmsPostMeta : postMetaList) {
                        postMetaService.delete(cmsPostMeta.getId());
                    }
                }
                termMetaService.delete(cmsTermMeta.getId());
            }
        }
        // 查找分类方法
        CmsTermTaxonomy taxonomy = termTaxonomyService.getBydTermId(Long.valueOf(termId));
        if(taxonomy != null){
          //查找term管理的Relationship
            List<PropertyFilter> relationshipFilters = new ArrayList<PropertyFilter>();
            relationshipFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            relationshipFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_TAXONOMY_ID", taxonomy.getId().toString()));
            List<CmsTermRelationship> relationshipList = termRelationshipService.search(relationshipFilters);
            if(relationshipList != null && relationshipList.size() > 0){
                for (CmsTermRelationship cmsTermRelationship : relationshipList) {
                    termRelationshipService.delete(cmsTermRelationship.getId());
                }
            }
        }
        //分类方法删除
        termTaxonomyService.delete(taxonomy.getId());
        termService.delete(Long.valueOf(termId));

    }

    /**
     * 获取当前编辑内容的分类
     * @param postId
     * @return
     */
    public List<Long> getTermIdByPostId(Long postId){
        return termRelevanceTaxonomyMapper.getTermIdByPostId(postId);
    }

}
