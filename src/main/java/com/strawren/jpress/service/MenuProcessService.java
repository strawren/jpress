/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-5-30 上午1:40:14
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-5-30        Initailized
 */

package com.strawren.jpress.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.NumberUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.util.ModelInfoUtils;


/**
 * 菜单处理服务类
 *
 */
@Service
@Transactional
public class MenuProcessService {
    @Autowired
    private CmsTermService cmsTermService;
    
    @Autowired
    private CmsTermTaxonomyService cmsTermTaxonomyService;
    
    @Autowired
    private CmsTermRelationshipService cmsTermRelationshipService;
    
    @Autowired
    private CmsPostService cmsPostService;
    
    @Autowired
    private CmsPostMetaService cmsPostMetaService;
    
    private final Log log = LogFactory.getLog(getClass());
    
    /**
     * 保存菜单数据
     * @param editMenuId 编辑的菜单id
     * @param menuDataJsonArray 菜单树的json数组数据
     * @throws Exception
     */
    public void saveMenu(String editMenuId, JSONArray menuDataJsonArray) throws Exception{
        //查询出原菜单
        CmsTerm term = cmsTermService.get(NumberUtils.StringToLong(editMenuId));

        //原菜单所关联的分类
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_NAV_MENU));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_ID", term.getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTermTaxonomy> cmsTermTaxonomyList = cmsTermTaxonomyService.search(filters);
        if(cmsTermTaxonomyList == null || cmsTermTaxonomyList.size() == 0){
            log.debug("edit menu not exist!!!");
            throw new Exception("edit menu not exist!!!");
        }

        //修改原菜单关联的post为I状态
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_TAXONOMY_ID", cmsTermTaxonomyList.get(0).getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTermRelationship> cmsTermRelationshipList = cmsTermRelationshipService.search(filters);
        CmsTermRelationship srcCmsTermRelationship = null;
        CmsPost srcCmsPost = null;
        for(int i=0; i<cmsTermRelationshipList.size(); i++){
            srcCmsTermRelationship = cmsTermRelationshipList.get(i);
            srcCmsTermRelationship.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
            ModelInfoUtils.updateModelInfoBySys(srcCmsTermRelationship);
            cmsTermRelationshipService.update(srcCmsTermRelationship);

            srcCmsPost = cmsPostService.get(srcCmsTermRelationship.getObjectId());
            srcCmsPost.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
            ModelInfoUtils.updateModelInfoBySys(srcCmsPost);
            cmsPostService.update(srcCmsPost);
        }

        //保存菜单
        int menuOrder = 1; //菜单顺序
        Map<Long, Long> relationMap = new HashMap<Long, Long>();
        for(int i=0; i<menuDataJsonArray.size(); i++){
            JSONObject jsonObject = menuDataJsonArray.getJSONObject(i);
            long id = jsonObject.getLong("id");
            long menuParentId = jsonObject.getLong("menuParentId");
            String title = jsonObject.getString("title");
            String slug = jsonObject.getString("slug");
            String tarObjType = jsonObject.getString("tarObjType");
            log.debug("id:" + id + ",menuParentId:" + menuParentId + ",title:" + title + ",slug:" + slug + ",tarObjType:" + tarObjType);

            CmsPost cmsPost = new CmsPost();
            cmsPost.setPostType(JpressConstants.DICT_POST_TYPE_MENU_NAV);  //导航菜单对象post类型
            cmsPost.setTitle(title);
            cmsPost.setSlug(slug);
            cmsPost.setMenuOrder(menuOrder);
            cmsPost.setParentId(0l); //暂时放菜单的父id
            cmsPost.setGuid("0");   
            if(menuParentId != 0){
                cmsPost.setParentId(menuParentId);
            }
            cmsPost.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(cmsPost);
            cmsPostService.save(cmsPost);

            //post第1属性，菜单目标对象id
            CmsPostMeta cmsPostMeta = new CmsPostMeta();
            cmsPostMeta.setPostId(cmsPost.getId());
            cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_OBJ_ID);   //post id

            CmsPost targetPost = cmsPostService.get(id);
            if(targetPost != null && !JpressConstants.DICT_POST_TYPE_MENU_NAV.equals(targetPost.getPostType())){
                cmsPostMeta.setValue(String.valueOf(id));
            }
            else{
                filters = new ArrayList<PropertyFilter>();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", String.valueOf(id)));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.POST_META_TAR_MENU_OBJ_ID));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
                List<CmsPostMeta> postMetasList = cmsPostMetaService.search(filters);
                if(postMetasList != null && postMetasList.size() > 0){
                    cmsPostMeta.setValue(postMetasList.get(0).getValue());  //指向真实的对象
                }
            }

            cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
            cmsPostMetaService.save(cmsPostMeta);

            //post第2属性，菜单的父菜单
            cmsPostMeta = new CmsPostMeta();
            cmsPostMeta.setPostId(cmsPost.getId());
            cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_PARENT_ID); 
            if(menuParentId == 0){
                cmsPostMeta.setValue("0");
            }
            else{
                cmsPostMeta.setValue(relationMap.get(menuParentId).toString());
            }
            cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
            cmsPostMetaService.save(cmsPostMeta);
            
            //post第3属性，菜单的目标对象类型
            cmsPostMeta = new CmsPostMeta();
            cmsPostMeta.setPostId(cmsPost.getId());
            cmsPostMeta.setKey(JpressConstants.MENU_POINT_TYPE_KEY); 
            if(targetPost != null && !JpressConstants.DICT_POST_TYPE_MENU_NAV.equals(targetPost.getPostType())){
                cmsPostMeta.setValue(JpressConstants.MENU_PONIT_TYPE_PAGE);
            }
            else{
                cmsPostMeta.setValue(tarObjType);
            }
            cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
            cmsPostMetaService.save(cmsPostMeta);

            //post与菜单关联
            CmsTermRelationship cmsTermRelationship = new CmsTermRelationship();
            cmsTermRelationship.setTermTaxonomyId(cmsTermTaxonomyList.get(0).getId());
            cmsTermRelationship.setObjectId(cmsPost.getId());
            cmsTermRelationship.setTermOrder(menuOrder);
            cmsTermRelationship.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(cmsTermRelationship);
            cmsTermRelationshipService.save(cmsTermRelationship);

            relationMap.put(id, cmsPost.getId());
            ++menuOrder;
        }
        
        //删除菜单原关联关系
        for(int i=0; i<cmsTermRelationshipList.size(); i++){
            srcCmsTermRelationship = cmsTermRelationshipList.get(i);
            srcCmsPost = cmsPostService.get(srcCmsTermRelationship.getObjectId());
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", srcCmsPost.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
            List<CmsPostMeta> cmsPostMetasList = cmsPostMetaService.search(filters);
            for(CmsPostMeta meta : cmsPostMetasList){
                cmsPostMetaService.delete(meta.getId());
            }
            cmsPostService.delete(srcCmsPost.getId());
            cmsTermRelationshipService.delete(srcCmsTermRelationship.getId());
        }
    }
    
    /**
     * 保存链接到菜单
     * @param linkName 链接名
     * @param linkUrl 链接url
     * @param editMenuId 编辑的菜单id
     * @throws Exception
     */
    public void saveLinkToMenu(String linkName, String linkUrl, String editMenuId) throws Exception{
        //保存link到post
        CmsPost cmsPost = new CmsPost();
        cmsPost.setContent(linkName);
        cmsPost.setPostType(JpressConstants.DICT_POST_TYPE_LINK);
        cmsPost.setParentId(0l);
        cmsPost.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPost);
        cmsPostService.save(cmsPost);
        
        //url属性放在meta中
        CmsPostMeta postMeta = new CmsPostMeta();
        postMeta.setPostId(cmsPost.getId());
        postMeta.setKey(JpressConstants.POST_META_POST_VISIT_URL);
        postMeta.setValue(linkUrl);
        postMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(postMeta);
        cmsPostMetaService.save(postMeta);
        
        //将该link添加到菜单
        CmsPost menuPost = new CmsPost();
        menuPost.setPostType(JpressConstants.DICT_POST_TYPE_MENU_NAV);  //导航菜单对象post类型
        menuPost.setTitle(linkName);
        menuPost.setMenuOrder(0);
        menuPost.setParentId(0l);
        cmsPost.setGuid("0");  //暂时放菜单的父id
        menuPost.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(menuPost);
        cmsPostService.save(menuPost);
        
        //保存该菜单的属性，目标link的post
        CmsPostMeta cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_OBJ_ID);   //post id
        cmsPostMeta.setValue(String.valueOf(cmsPost.getId()));
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        //第2属性，menu的父子关系
        cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_PARENT_ID);
        cmsPostMeta.setValue("0");
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        //第3属性，menu目标对象的类型
        cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.MENU_POINT_TYPE_KEY);
        cmsPostMeta.setValue(JpressConstants.MENU_PONIT_TYPE_LINK);
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        CmsTermTaxonomy cmsTermTaxonomy = cmsTermTaxonomyService.getBydTermId(Long.valueOf(editMenuId));
        if(cmsTermTaxonomy == null){
            log.warn("edit menu taxonomy not found!!!");
            throw new Exception("edit menu taxonomy not found!!!");
        }
        
        CmsTermRelationship cmsTermRelationship = new CmsTermRelationship();
        cmsTermRelationship.setTermTaxonomyId(cmsTermTaxonomy.getId());
        cmsTermRelationship.setObjectId(menuPost.getId());
        cmsTermRelationship.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsTermRelationship);
        cmsTermRelationshipService.save(cmsTermRelationship);
    }
    
    /**
     * 保存分类或标签到菜单
     * @param termOrTagId  分类或标签id
     * @param termOrTagName 分类或标签名
     * @param editMenuId  编辑的菜单id
     * @param slug  节点别名
     * @param dataType  节点数据类型
     * @throws Exception
     */
    public void saveTermOrTagToMenu(String termOrTagId, String termOrTagName, String editMenuId, String slug, String dataType) throws Exception{
        //将该term添加到菜单
        CmsPost menuPost = new CmsPost();
        menuPost.setPostType(JpressConstants.DICT_POST_TYPE_MENU_NAV);  //导航菜单对象post类型
        menuPost.setTitle(termOrTagName);
        menuPost.setSlug(slug);
        menuPost.setMenuOrder(0);
        menuPost.setParentId(0l);
        menuPost.setGuid("0");  //暂时放菜单的父id
        menuPost.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(menuPost);
        cmsPostService.save(menuPost);
        
        //保存该菜单的属性，目标link的post
        CmsPostMeta cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_OBJ_ID);   //post id
        cmsPostMeta.setValue(String.valueOf(termOrTagId));
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        //第2属性，menu的父子关系
        cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.POST_META_TAR_MENU_PARENT_ID);
        cmsPostMeta.setValue("0");
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        //第3属性，menu目标对象的类型
        cmsPostMeta = new CmsPostMeta();
        cmsPostMeta.setPostId(menuPost.getId());
        cmsPostMeta.setKey(JpressConstants.MENU_POINT_TYPE_KEY);
        if(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY.equals(dataType)){
            cmsPostMeta.setValue(JpressConstants.MENU_PONIT_TYPE_TERM);
        }
        else if(JpressConstants.DICT_TERM_TAXONOMY_TAG.equals(dataType)){
            cmsPostMeta.setValue(JpressConstants.MENU_PONIT_TYPE_TAG);
        }
        else{
            log.warn("edit menu tarObj type exception!!!");
            throw new Exception("edit menu tarObj type exception!!!");
        }
        
        cmsPostMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsPostMeta);
        cmsPostMetaService.save(cmsPostMeta);
        
        CmsTermTaxonomy cmsTermTaxonomy = cmsTermTaxonomyService.getBydTermId(Long.valueOf(editMenuId));
        if(cmsTermTaxonomy == null){
            log.warn("edit menu taxonomy not found!!!");
            throw new Exception("edit menu taxonomy not found!!!");
        }
        
        CmsTermRelationship cmsTermRelationship = new CmsTermRelationship();
        cmsTermRelationship.setTermTaxonomyId(cmsTermTaxonomy.getId());
        cmsTermRelationship.setObjectId(menuPost.getId());
        cmsTermRelationship.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(cmsTermRelationship);
        cmsTermRelationshipService.save(cmsTermRelationship);
    }
    
    /**
     * 根据id删除菜单
     * @param menuId  菜单id
     * @param menuName 菜单名
     */
    public void delMenuByIdAndName(String menuId, String menuName) throws Exception{
        //一个分类只能对应一个分类方法???
        CmsTermTaxonomy cmsTermTaxonomy = cmsTermTaxonomyService.getBydTermId(NumberUtils.StringToLong(menuId));
        if(cmsTermTaxonomy == null){
            log.debug("未找到菜单" + menuName + ", id为:" + menuId);
            throw new Exception("未找到菜单" + menuName + ", id为:" + menuId);
        }
        
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_TAXONOMY_ID", cmsTermTaxonomy.getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTermRelationship> termRelationshipsList = cmsTermRelationshipService.search(filters);
        
        for(CmsTermRelationship ship: termRelationshipsList){
            CmsPost cmsPost = cmsPostService.get(ship.getObjectId());
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", cmsPost.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
            List<CmsPostMeta> cmsPostMetasList = cmsPostMetaService.search(filters);
            for(CmsPostMeta meta: cmsPostMetasList){
                cmsPostMetaService.delete(meta.getId());
            }
            cmsPostService.delete(cmsPost.getId());
            cmsTermRelationshipService.delete(ship.getId());
        }
        
        cmsTermTaxonomyService.delete(cmsTermTaxonomy.getId());
        cmsTermService.delete(NumberUtils.StringToLong(menuId));
    }
}
