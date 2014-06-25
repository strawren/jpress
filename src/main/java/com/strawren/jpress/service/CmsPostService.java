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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
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
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.CmsPostMapper;
import com.strawren.jpress.dao.CmsTermRelationshipMapper;
import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.util.ModelInfoUtils;

/**
 * 服务类，内容：包括page、revision、post、attachment

The core of the WordPress data is the posts. It is stored in the wp_posts table. Also Pages and navigation menu items are stored in this table.，对应表CMS_POST
 */
@Service
@Transactional
public class CmsPostService extends DefaultEntityService<CmsPost, Long>{

    /** Mapper类，内容：包括page、revision、post、attachment
    The core of the WordPress data is the posts. It is stored in the wp_posts table. Also Pages and navigation menu items are stored in this table.， */
    @Autowired
    CmsPostMapper cmsPostMapper;

    @Autowired
    CmsTermRelationshipService termRelationshipService;

    @Autowired
    CmsTermRelationshipMapper termRelationshipMapper;

    @Autowired
    CmsPostMetaService postMetaService;

    @Autowired
    CmsTermTaxonomyService termTaxonomyService;

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsTermMetaService termMetaService;


    /**
     * admin 项目对应的web访问路径
     */
    protected final String CMS_ADMIN_SITE_URL = SystemPropsUtils.getString("clivia.admin.site.url");

    /**
     * 媒体文件访问路径
     *
     */
    protected final String ADMIN_MEDIA_DIR = SystemPropsUtils.getString("sys.media.url");

    /** 数据存取方法 */
    @Override
    protected Dao<CmsPost, Long> getDao() {
        return cmsPostMapper;
    }

    /**
     * 获取全部页面总数
     */
    public long countAllPage() {
        return cmsPostMapper.countAllPage();
    }

    /**
     * 获取已发布页面总数
     * @return
     */
    public long countAllPublishPage() {
        return cmsPostMapper.countAllPublishPage();
    }

    /**
     * 获取草稿页面总数
     * @return
     */
    public long countAllDraftPage() {
        return cmsPostMapper.countAllDraftPage();
    }

    /**
     * 获取回收站页面总数
     * @return
     */
    public long countAllDeletePage() {
        return cmsPostMapper.countAllDeletePage();
    }

    /**
     * 批量删除页面
     * @param pageIds
     */
    public void batchRemovePage(String[] pageIds) {
        if (pageIds!=null&&pageIds.length>0) {
            for (int i = 0; i < pageIds.length; i++) {
                Long pageId = Long.parseLong(pageIds[i]);

                CmsPost page = new CmsPost();
                page.setId(pageId);
                page.setStatus(JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE);

                //TODO 设置操作人ID,name,time
                ModelInfoUtils.updateModelInfoBySys(page);

                //避免脏数据检查
                CmsPost oldPage = cmsPostMapper.get(pageId);
                page.setLastUpdTime(oldPage.getLastUpdTime());

                cmsPostMapper.update(page);
            }
        }
    }

    /**
     * 内容发布
     * @param post
     * @param relationship
     */
    public void publishArticle(CmsPost post, List<CmsTermRelationship> relationship, List<CmsTermMeta> termMetaList,HttpServletRequest request, String tagIds){

        //发布版本保存
        cmsPostMapper.save(post);
        //所属分类保存（包括内容关联的分类和标签）
        for (CmsTermRelationship cmsTermRelationship : relationship) {
            cmsTermRelationship.setObjectId(post.getId());
            termRelationshipService.save(cmsTermRelationship);
        }
      //保存postmeta

        //公共属性保存
        List<CmsTermMeta> commTermMeta = searchCommMeta();
        if(commTermMeta != null && commTermMeta.size() > 0){
            savePostMeta(post.getId(), commTermMeta, request);
        }
        //分类属性保存
        if(termMetaList != null && termMetaList.size() > 0){
            savePostMeta(post.getId(), termMetaList, request);
        }

        //保存图片路径
        String imgPath = request.getParameter("imgPath");
        log.debug("content img id is > >" + imgPath);
        if(StringUtils.isNotBlank(imgPath)){
            CmsPostMeta imgMeta = new CmsPostMeta();
            imgMeta.setPostId(post.getId());
            imgMeta.setJkey(JpressConstants.SYS_POST_IMAGE_PATH_KEY);
            imgMeta.setName(JpressConstants.PRCTURE_FEATURE_TEXT);
            imgMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
            imgMeta.setValue(imgPath);
            ModelInfoUtils.createModelInfoBySys(imgMeta);
            postMetaService.save(imgMeta);
        }

        //备份版本保存
        post.setPostStatus(JpressConstants.DICT_POST_STATUS_INHERIT);
        post.setTitle(post.getId() + "-revision-v1");
        post.setPostStatus(JpressConstants.DICT_POST_STATUS_INHERIT);
        post.setParentId(post.getId());
        post.setPostType(JpressConstants.DICT_POST_TYPE_REVISION);
        post.setId(null);
        save(post);

        //修改标签关联内容
        if(StringUtils.isNotBlank(tagIds)){
            String[] tags = tagIds.split(",");
            for(int i=0; i<tags.length;i++){
                CmsTermTaxonomy termTaxonomy = termTaxonomyService.get(Long.valueOf(tags[i]));
                termTaxonomy.setPostCount(termTaxonomy.getPostCount() + 1);
                termTaxonomyService.update(termTaxonomy);
            }
        }

    }

    /**
     * 内容编辑service
     * @param post
     */
    public void editArticle(CmsPost post, List<CmsTermRelationship> relationship, List<CmsTermMeta> termMetaList, HttpServletRequest request){

        Long postId = post.getId();
        //修改编辑后的内容
        post.setPostType(JpressConstants.DICT_POST_TYPE_POST);
        ModelInfoUtils.updateModelInfoBySys(post);
        update(post);

        //删除当前postId关联的所有postMeta数据
        postMetaService.delByTermMetaIdAndKey(postId);
        //公共属性保存
        List<CmsTermMeta> commTermMeta = searchCommMeta();
        if(commTermMeta != null && commTermMeta.size() > 0){
            savePostMeta(post.getId(), commTermMeta, request);
        }
        //分类属性保存
        if(termMetaList != null && termMetaList.size() > 0){
            savePostMeta(post.getId(), termMetaList, request);
        }

      //保存特色图像关联
//        String imgPostId = request.getParameter("imgPostId");
//        log.debug("content img id is > >" + imgPostId);
//        if(StringUtils.isNotBlank(imgPostId)){
//            //查找当前内容关联的特色图像数据
//            List<PropertyFilter> imgPostfilters = new ArrayList<PropertyFilter>();
//            imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", String.valueOf(postId)));
//            imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", CliviaConstants.DICT_GLOBAL_STATUS_VALIDATE));
//            imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX));
//            List<CmsPostMeta> imgMetaList = postMetaService.search(imgPostfilters);
//            if(imgMetaList != null && imgMetaList.size() > 0){
//                CmsPostMeta imgPost = imgMetaList.get(0);
//                imgPost.setValue(imgPostId);
//                ModelInfoUtils.updateModelInfoBySys(imgPost);
//                postMetaService.update(imgPost);
//            }else{
//                CmsPostMeta imgMeta = new CmsPostMeta();
//                imgMeta.setPostId(post.getId());
////                imgMeta.setKey(CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX + post.getId() + "_" + imgPostId);
//                imgMeta.setKey(CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX);
//                imgMeta.setName(CliviaConstants.PRCTURE_FEATURE_TEXT);
//                imgMeta.setValue(imgPostId);
//                imgMeta.setStatus(CliviaConstants.DICT_GLOBAL_STATUS_VALIDATE);
//                ModelInfoUtils.createModelInfoBySys(imgMeta);
//                postMetaService.save(imgMeta);
//            }
//
//        }else{
//            postMetaService.delPostImgMeta(postId);
//            log.debug("del post img meta id >> " + postId);
//        }

        //保存图片路径
        String imgPath = request.getParameter("imgPath");
        if(StringUtils.isNotBlank(imgPath)){
          //查找当前内容关联的特色图像数据
          List<PropertyFilter> imgPostfilters = new ArrayList<PropertyFilter>();
          imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", String.valueOf(postId)));
          imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
          imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.SYS_POST_IMAGE_PATH_KEY));
          List<CmsPostMeta> imgMetaList = postMetaService.search(imgPostfilters);
          if(imgMetaList != null && imgMetaList.size() > 0){
              CmsPostMeta imgPost = imgMetaList.get(0);
              imgPost.setValue(imgPath);
              ModelInfoUtils.updateModelInfoBySys(imgPost);
              postMetaService.update(imgPost);
          }else{
              CmsPostMeta imgMeta = new CmsPostMeta();
              imgMeta.setPostId(post.getId());
//              imgMeta.setKey(CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX + post.getId() + "_" + imgPostId);
              imgMeta.setJkey(JpressConstants.SYS_POST_IMAGE_PATH_KEY);
              imgMeta.setName(JpressConstants.PRCTURE_FEATURE_TEXT);
              imgMeta.setValue(imgPath);
              imgMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
              ModelInfoUtils.createModelInfoBySys(imgMeta);
              postMetaService.save(imgMeta);
          }
        }
        //备份新修改的内容
        post.setId(null);
        post.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        post.setPostStatus(JpressConstants.DICT_POST_STATUS_INHERIT);
        post.setTitle(postId + "-revision-v1");
        post.setParentId(postId);
        post.setPostType(JpressConstants.DICT_POST_TYPE_REVISION);
        ModelInfoUtils.createModelInfoBySys(post);
        cmsPostMapper.save(post);

      //删除内容分类目录关联关系
        termRelationshipMapper.delByObjId(postId);
        //所属分类保存
        for (CmsTermRelationship cmsTermRelationship : relationship) {
            cmsTermRelationship.setObjectId(postId);
            termRelationshipService.save(cmsTermRelationship);
        }
    }



    /**
     * 批量删除媒体文件
     * @param mediaIds
     */
    public void batchRemoveMedia(String[] mediaIds) {
        if (mediaIds!=null&&mediaIds.length>0) {
            for (int i = 0; i < mediaIds.length; i++) {
                Long mediaId = Long.parseLong(mediaIds[i]);

                CmsPost media = new CmsPost();
                media.setId(mediaId);
                media.setStatus(JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE);

                //TODO 设置操作人ID,name,time
                ModelInfoUtils.updateModelInfoBySys(media);

                //避免脏数据检查
                CmsPost oldPage = cmsPostMapper.get(mediaId);
                media.setLastUpdTime(oldPage.getLastUpdTime());

                cmsPostMapper.update(media);
            }
        }
    }

    /**
     * 根据termId分页查询所有post
     * @param termId
     * @param page
     */
    public void searchByTermId(Long termId, Page<CmsPostDTO> page) {

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "tt.TERM_ID", termId+""));

        BspCriterion criterion = BspCriterionUtils.createOecsCriterion(page, filters);
        criterion.setOrderBy("p.LAST_UPD_TIME");

        RowBounds rowBounds = BspCriterionUtils.createRowBounds(page);

        long count = cmsPostMapper.countByTermId(criterion);
        page.setTotalCount(count);

        if (count>0) {
            List<CmsPostDTO> result = cmsPostMapper.searchByTermId(criterion, rowBounds);

            for (CmsPostDTO cmsPostDTO : result) {
                Map<String, CmsPostMeta> metaMap = postMetaService.getMetasByPostId(cmsPostDTO.getId());
                cmsPostDTO.setMetaMap(metaMap);
            }

            page.setResult(result);
        }

    }

    /**
     * 获取post及meta信息
     * @param postId
     * @return
     */
    public CmsPostDTO getDetailById(Long postId) {
        CmsPost cmsPost = cmsPostMapper.get(postId);

        CmsPostDTO cmsPostDTO = new CmsPostDTO();
        if (cmsPost==null) {
            return cmsPostDTO;
        }

        BeanUtils.copyProperties(cmsPost, cmsPostDTO);

        Map<String, CmsPostMeta> metaMap = postMetaService.getMetasByPostId(postId);
        cmsPostDTO.setMetaMap(metaMap);

        return cmsPostDTO;
    }

    public String getMediaUrlById(Long postId){
        CmsPost picture = cmsPostMapper.get(postId);
        String fileName = picture.getGuid();

        return ADMIN_MEDIA_DIR + fileName;
    }

    /**
     * 根据条件获取总条数
     * @param filters
     * @return
     */
    public long count(List<PropertyFilter> filters){

        BspCriterion criterion = new BspCriterion();
        if(filters != null && filters.size() > 0)
        criterion.setCriteria(filters);

        return cmsPostMapper.count(criterion);
    }

    public void delPost(String postId){

        //查询该post关联的Relationship,删除
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "OBJECT_ID", postId));
        List<CmsTermRelationship> relationshipList =  termRelationshipService.search(filters);
        if(relationshipList != null && relationshipList.size() > 0){
            for (CmsTermRelationship cmsTermRelationship : relationshipList) {
                termRelationshipService.delete(cmsTermRelationship.getId());
            }
        }
      //查询该post关联的postMeta,删除
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", postId));
        List<CmsPostMeta> postMetaList =  postMetaService.search(filters);
        if(postMetaList != null && postMetaList.size() > 0){
            for (CmsPostMeta cmsPostMeta : postMetaList) {
                postMetaService.delete(cmsPostMeta.getId());
            }
        }

        //删除该post备份数据
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "PARENT_ID", postId));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_INHERIT));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> inhPostList = search(filters);
        if(inhPostList != null && inhPostList.size() > 0){
            for (CmsPost inhPost : inhPostList) {
                delete(inhPost.getId());
            }
        }
        delete(Long.valueOf(postId));

    }


    /**
     * 获取商品公共属性
     * @return
     */
    public List<CmsTermMeta> searchCommMeta(){

        //公共term查询
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "SLUG", JpressConstants.SYS_COMMON_META_TERM_SLUG));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTerm> commTermList = termService.search(filters);
        List<CmsTermMeta> commMetaList = null;
        if(commTermList != null && commTermList.size() > 0){
            //公共termMeta查询
            CmsTerm cmsTerm = commTermList.get(0);
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", cmsTerm.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            List<Order> orders = new ArrayList<Order>();
            orders.add(new Order("SHOW_ORDER", Page.ASC));
            commMetaList = termMetaService.search(filters, orders);
        }

        return commMetaList;

    }


    /**
     * post属性保存
     * @param termMetaList
     * @param request
     */
    public void savePostMeta(Long postId, List<CmsTermMeta> termMetaList, HttpServletRequest request){

        Long maxOrderNo = postMetaService.getMaxOrderNo(postId);
        //分类属性保存
        for (CmsTermMeta cmsTermMeta : termMetaList) {
            maxOrderNo += 1;
            String postValue = request.getParameter(cmsTermMeta.getJkey() + "_" + cmsTermMeta.getId());
            log.debug("postValue is > > " + postValue);
            CmsPostMeta postMeta = new CmsPostMeta();
            postMeta.setPostId(postId);
            postMeta.setTermMetaId(cmsTermMeta.getId());
            postMeta.setName(cmsTermMeta.getName());
            postMeta.setJkey(cmsTermMeta.getJkey());
            postMeta.setValue(postValue);
            postMeta.setShowOrder(Integer.valueOf(maxOrderNo.toString()));
            postMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(postMeta);
            postMetaService.save(postMeta);
        }
    }

}