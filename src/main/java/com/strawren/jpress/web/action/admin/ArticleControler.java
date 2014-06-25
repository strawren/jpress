package com.strawren.jpress.web.action.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dto.ShowPostDTO;
import com.strawren.jpress.dto.TermRelevanceTaxonomyDTO;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsTermMetaService;
import com.strawren.jpress.service.CmsTermRelationshipService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.service.CmsTermTaxonomyService;
import com.strawren.jpress.service.TermRelevanceTaxonomyService;
import com.strawren.jpress.util.ModelInfoUtils;

@Controller
@RequestMapping("${adminPath}/article")
public class ArticleControler extends BaseMultiActionController {

    // 媒体文件访问路径
    public static final String ADMIN_MEDIA_DIR = SystemPropsUtils.getString("sys.media.url");

    @Autowired
    private TermRelevanceTaxonomyService termRelevanceTaxonomyService;

    @Autowired
    private CmsPostService postService;

    @Autowired
    private CmsTermMetaService termMetaService;

    @Autowired
    private CmsPostMetaService postMetaService;

    @Autowired
    private CmsTermService termService;

    @Autowired
    private CmsTermRelationshipService termRelationshipService;

    @Autowired
    private CmsTermTaxonomyService taxonomyService;

    /**
     * 所有内容
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/list.action")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, Page<CmsPost> page, @RequestParam(value = "termSet", required = false) String termTaxonomy){
        log.debug("article list.action begin ...");

        ModelAndView mv = new ModelAndView("cms/content/article/list");
        String postStatus = request.getParameter("postStatus");
//        List<TermRelevanceTaxonomyDTO> termList = gettaxonomyParent(CliviaConstants.DICT_TERM_TAXONOMY_CATEGORY);
        log.debug("termTaxonomy is >> " + termTaxonomy);
        // postStatus默认为all
        if (StringUtils.isBlank(postStatus)) {
            postStatus = "all";
        }

        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);
        log.debug("sys media url is > >" + ADMIN_MEDIA_DIR);
        page.setOrderDirection("desc");
        page.setOrderField("CREATE_TIME");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        if ("all".equals(postStatus)) {
            filters.add(new PropertyFilter(MatchType.NEQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_INHERIT));
        }
        else {
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", postStatus));
        }
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        page = postService.search(page, filters);
        List<CmsPost> postList = page.getResult();

        // 查询分类目录名称
        ShowPostDTO showPost = null;
        List<ShowPostDTO> showPostList = new ArrayList<ShowPostDTO>();
        if (postList != null && postList.size() > 0) {
            log.debug("postList size is >> " + postList.size());
            for (CmsPost post : postList) {
                filters = new ArrayList<PropertyFilter>();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", post.getId().toString()));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", "sys_item_no"));
                List<CmsPostMeta> postMeteaList = postMetaService.search(filters);
                String articleNumber = null;
                if(postMeteaList != null && postMeteaList.size() > 0){
                    articleNumber = postMeteaList.get(0).getValue();
                }

                showPost = new ShowPostDTO();
                BeanUtils.copyProperties(post, showPost);
                filters = new ArrayList<PropertyFilter>();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "OBJECT_ID", post.getId().toString()));
                List<CmsTermRelationship> relationshipList = termRelationshipService.search(filters);
                if (relationshipList != null && relationshipList.size() > 0) {
                    log.debug("relationshipList size is >> " + relationshipList.size());
                    if(relationshipList.size() > 1){
                        String termNames = "";
                        for(CmsTermRelationship relationship : relationshipList){
                            CmsTermTaxonomy taxonomy = taxonomyService.get(relationship.getTermTaxonomyId());
                            CmsTerm term = termService.get(taxonomy.getTermId());
                            termNames = termNames + "/" + term.getName();
                        }
                        showPost.setTermName(termNames.substring(1, termNames.length()));
                        showPost.setArticleNumber(articleNumber);
                        showPostList.add(showPost);

                    }else{
                        CmsTermRelationship relationship = relationshipList.get(0);
                        CmsTermTaxonomy taxonomy = taxonomyService.get(relationship.getTermTaxonomyId());
                        CmsTerm term = termService.get(taxonomy.getTermId());
                        showPost.setTermName(term.getName());
                        showPost.setArticleNumber(articleNumber);
                        showPostList.add(showPost);

                    }

//                    }
                }
            }

        }

        log.debug("showPostList size >> " + showPostList.size());
        mv.addObject("postList", showPostList);
//        mv.addObject("termList", termList);
        mv.addObject("termTaxonomy", termTaxonomy);

        log.debug("article list.action begin ...");

        return mv;
    }

    /**
     * 内容添加展示
     *
     * @param request
     * @param response
     */

    @RequestMapping("/add_show.action")
    public ModelAndView addShow(HttpServletRequest request, HttpServletResponse response) {
        log.debug("add_show.action begin...");

        ModelAndView mv = new ModelAndView("cms/content/article/add");

        // 获取分类目录
        List<TermRelevanceTaxonomyDTO> parent = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        // 获取tag标签列表
        List<TermRelevanceTaxonomyDTO> tagsList = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_TAG);

        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("SHOW_ORDER", Page.ASC));
        // 商品公共属性
        List<CmsTermMeta> commMetaList = searchCommMeta(null, orders);
        JSONArray parentPagesJson = JSONArray.fromObject(parent);
        JSONArray tagsJsonList = JSONArray.fromObject(tagsList);
        JSONArray termTaxIdList = JSONArray.fromObject(null);
        JSONArray tagTermTaxIdList = JSONArray.fromObject(null);
        mv.addObject("commMetaList", commMetaList);
        mv.addObject("parentList", parentPagesJson);
        mv.addObject("tagTermTaxIdList", tagTermTaxIdList);
        mv.addObject("tagsJsonList", tagsJsonList);
        mv.addObject("flag", "add");
        mv.addObject("checkTermId", termTaxIdList);
        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);
        log.debug("sys media url is > >" + ADMIN_MEDIA_DIR);
        log.debug("add_show.action end!!");

        return mv;
    }

    /**
     * 内容编辑展示
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/edit_show.action")
    public ModelAndView editShow(HttpServletRequest request, HttpServletResponse response) {
        log.debug("edit_show.action begin...");

        ModelAndView mv = new ModelAndView("cms/content/article/add");
        String postId = request.getParameter("postId");
        // 商品公共属性
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order();
        order.setOrderBy("SHOW_ORDER");
        order.setOrder("ASC");
        orders.add(order);
        List<CmsTermMeta> commMetaList = searchCommMeta(null, orders);
        for (CmsTermMeta termMeta : commMetaList) {
            List<PropertyFilter> commFilters = new ArrayList<PropertyFilter>();
            commFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", postId));
            commFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", termMeta.getJkey()));
            commFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            List<CmsPostMeta> postMetaList = postMetaService.search(commFilters);
            if (postMetaList == null || postMetaList.size() <= 0) {
                CmsPostMeta postMeta = new CmsPostMeta();
                postMeta.setJkey(termMeta.getJkey());
                postMeta.setPostId(Long.valueOf(postId));
                postMeta.setName(termMeta.getName());
                postMeta.setTermMetaId(termMeta.getId());
                postMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
                ModelInfoUtils.createModelInfoBySys(postMeta);
                postMetaService.save(postMeta);
            }

        }
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", postId));
        filters.add(new PropertyFilter(MatchType.NOTNULL, PropertyType.S, "TERM_META_ID", null));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPostMeta> postMetaShowList = postMetaService.search(filters);
        CmsPost post = postService.get(Long.valueOf(postId));
        List<TermRelevanceTaxonomyDTO> parent = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        JSONArray parentPagesJson = JSONArray.fromObject(parent);
        // 获取tag标签列表
        List<TermRelevanceTaxonomyDTO> tagsList = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_TAG);

        // 获取内容特色图片
        // List<PropertyFilter> imgPostfilters = new ArrayList<PropertyFilter>();
        // imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", postId));
        // imgPostfilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS",
        // CliviaConstants.DICT_GLOBAL_STATUS_VALIDATE));
        // imgPostfilters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "KEY",
        // CliviaConstants.KEY_PRCTURE_FEATURE_PERFIX));
        // List<CmsPostMeta> imgMetaList = postMetaService.search(imgPostfilters);
        // if(imgMetaList != null && imgMetaList.size() > 0){
        // CmsPostMeta imgMeta = imgMetaList.get(0);
        // CmsPost imgPost = postService.get(Long.valueOf(imgMeta.getValue()));
        // mv.addObject("imgPost", imgPost);
        // }

        // 获取图片路径
        List<PropertyFilter> imgMetaFilters = new ArrayList<PropertyFilter>();
        imgMetaFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", postId));
        imgMetaFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        imgMetaFilters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.SYS_POST_IMAGE_PATH_KEY));
        List<CmsPostMeta> imgMetaList = postMetaService.search(imgMetaFilters);
        if (imgMetaList != null && imgMetaList.size() > 0) {
            CmsPostMeta imgMeta = imgMetaList.get(0);
            String[] imgPaths = imgMeta.getValue().split(",");
            String imgUrl = null;
            for (int i = 0; i < imgPaths.length; i++) {
                if (imgPaths[i].contains("_1")) {
                    imgUrl = imgPaths[i];
                }
            }
            mv.addObject("allImgUrl", imgMeta.getValue());
            mv.addObject("imgUrl", imgUrl);
        }

        JSONArray tagsJsonList = JSONArray.fromObject(tagsList);
        mv.addObject("tagsJsonList", tagsJsonList);
        mv.addObject("parentList", parentPagesJson);
        mv.addObject("post", post);
        mv.addObject("flag", "edit");

        // 获取当前post关联的分类目录
        List<Long> checkTermId = termRelevanceTaxonomyService.getTermIdByPostId(Long.valueOf(postId));
        JSONArray termTaxIdList = JSONArray.fromObject(checkTermId);
        // 获取当前post关联的标签
        List<Long> checkTagTermId = termRelevanceTaxonomyService.getTermIdByPostId(Long.valueOf(postId));
        JSONArray tagTermTaxIdList = JSONArray.fromObject(checkTagTermId);
        mv.addObject("viewList", postMetaShowList);
        mv.addObject("checkTermId", termTaxIdList);
        mv.addObject("tagTermTaxIdList", tagTermTaxIdList);

        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);
        log.debug("sys media url is > >" + ADMIN_MEDIA_DIR);
        log.debug("edit_show.action end!!");
        return mv;

    }

    /**
     * 内容编辑
     *
     * @param request
     * @param response
     * @param post
     * @return
     */
    @RequestMapping("/article_edit.action")
    public ModelAndView articleEidt(HttpServletRequest request, HttpServletResponse response, CmsPost post) {
        log.debug("article eidt begin ...");

        ModelAndView mv = new ModelAndView("forward:/cms/article/list.action?menu=menu_content");

        // 分类方法Ids
        String termTaxIdsStr = request.getParameter("termTaxIds");
        log.debug("termTaxIdsStr > > " + termTaxIdsStr);

        // 目录目录ids
        String termIdsStr = request.getParameter("termIds");
        String[] termIds = termIdsStr.split(",");
        // 标签ids
        String tagIdsStr = request.getParameter("tagIds");
        // 标签和目录的ids
        String tagIdsStrtagIdsStr = termTaxIdsStr + ","
                + tagIdsStr;
        log.debug("termTaxIdsStr @@ tagIdsStr ==== tagIdsStr > > " + tagIdsStrtagIdsStr);

        // 根据termId获取termMeta
        List<CmsTermMeta> termMetaList = getTermMetaByeTermId(termIds);
        log.debug("termMetaList size > >" + termMetaList.size());

        // 获取选中的分类目录(重新选择的分类目录保存)
        List<CmsTermRelationship> relationshipList = structureTermIdList(tagIdsStrtagIdsStr);
        log.debug("relationshipList size > >" + relationshipList.size());

        // 修改
        postService.editArticle(post, relationshipList, termMetaList, request);

        log.debug("article eidt end!!");
        return mv;

    }

    /**
     * 添加内容
     *
     * @return
     */
    @RequestMapping("/publish_article.action")
    public ModelAndView publishArticle(HttpServletRequest request, HttpServletResponse response, CmsPost post) {
        log.debug("add_article.action begin ...");

        post.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(post);
        post.setParentId(0L);
        post.setPostType(JpressConstants.DICT_POST_TYPE_POST);
        ModelAndView mv = new ModelAndView("forward:/cms/article/list.action?menu=menu_content");

        // 分类目录Ids(用来找到termMeta中类型为post的数据)
        String termIdStr = request.getParameter("termIds");
        String[] termIds = termIdStr.split(",");
        List<CmsTermMeta> termMetaList = getTermMetaByeTermId(termIds);

        // 目录(cat)分类方法id
        String termTaxIdsStr = request.getParameter("termTaxIds");
        // 标签(tag)分类方法id
        String tagIdsStr = request.getParameter("tagIds");
        // 获取选中的分类目录
        String termIdAll = termTaxIdsStr + ","
                + tagIdsStr;
        log.debug("termIdAll >> " + termIdAll);
        List<CmsTermRelationship> relationshipList = structureTermIdList(termIdAll);
        postService.publishArticle(post, relationshipList, termMetaList, request, tagIdsStr);

        log.debug("add_article.action end...");

        return mv;
    }

    /**
     * 内容删除
     *
     * @param request
     * @param response
     * @param post
     * @return
     */
    @RequestMapping("/del_article.action")
    public ModelAndView delArticle(HttpServletRequest request, HttpServletResponse response) {
        log.debug("del_article.action begin...");

        ModelAndView mv = new ModelAndView("forward:/cms/article/list.action?menu=menu_content");
        String articleId = request.getParameter("postId");
        log.debug("del psotId is:" + articleId);
        postService.delPost(articleId);

        log.debug("del_article.action begin...");
        return mv;

    }

    /**
     * 根据taxonomy获取分类目录父分类
     *
     * @param taxonomy
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TermRelevanceTaxonomyDTO> gettaxonomyParent(String taxonomy) {

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        List<TermRelevanceTaxonomyDTO> termParent = null;
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "t1.TAXONOMY", taxonomy));
        termParent = termRelevanceTaxonomyService.searchRelevance(filters);

        return termParent;
    }

    /**
     * 根据选中的分类目录Id构建选择分类目录集合
     *
     * @param termIdsStr
     * @return
     */
    public List<CmsTermRelationship> structureTermIdList(String termIdsStr) {

        List<CmsTermRelationship> relationshipList = new ArrayList<CmsTermRelationship>();
        String[] termIds = null;
        // 循环termid
        if (StringUtils.isNotBlank(termIdsStr)) {
            termIds = termIdsStr.split(",");
            // 根据termId创建CmsTermRelationship对象
            for (int i = 0; i < termIds.length; i++) {
                CmsTermRelationship termRelationship = new CmsTermRelationship();
                termRelationship.setTermTaxonomyId(Long.valueOf(termIds[i]));
                termRelationship.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
                ModelInfoUtils.createModelInfoBySys(termRelationship);
                relationshipList.add(termRelationship);
            }
        }
        else {
            // 为分类目录（目前不启用）
            CmsTermRelationship termRelationship = new CmsTermRelationship();
            termRelationship.setTermTaxonomyId(0L);
            termRelationship.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
            ModelInfoUtils.createModelInfoBySys(termRelationship);
            relationshipList.add(termRelationship);
        }

        return relationshipList;
    }

    /**
     * 根据termId获取termMeta
     *
     * @param termId
     * @return
     */
    public List<CmsTermMeta> getTermMetaByeTermId(String[] termIds) {

        List<CmsTermMeta> termMetaAll = new ArrayList<CmsTermMeta>();
        for (int i = 0; i < termIds.length; i++) {
            List<CmsTermMeta> termMeta = null;
            List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", termIds[i]));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "META_TYPE", JpressConstants.DICT_POST_TYPE_POST));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            List<Order> orders = new ArrayList<Order>();
            orders.add(new Order("SHOW_ORDER", Page.ASC));
            termMeta = termMetaService.search(filters, orders);
            termMetaAll.addAll(termMeta);
        }

        return termMetaAll;
    }

    /**
     * 检查分类目录别名是否存在
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/check_slug.action")
    @ResponseBody
    public boolean checkSlug(HttpServletRequest request, HttpServletResponse response) {
        log.debug("check_slug.action begin ...");

        String sulg = request.getParameter("slug");
        boolean existFlag = true;
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "SLUG", sulg));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> postList = postService.search(filters);
        if (postList != null && postList.size() > 0) {
            existFlag = false;
        }
        log.debug("existFlag > > " + existFlag);

        log.debug("check_slug.action end!!");
        return existFlag;
    }

    /**
     * 获取商品公共属性
     *
     * @return
     */
    public List<CmsTermMeta> searchCommMeta(List<PropertyFilter> filters, List<Order> orders) {

        // 公共term查询
        if (filters == null || filters.size() <= 0) {
            filters = new ArrayList<PropertyFilter>();
        }

        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "SLUG", JpressConstants.SYS_COMMON_META_TERM_SLUG));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTerm> commTermList = termService.search(filters);
        List<CmsTermMeta> commMetaList = null;
        if (commTermList != null && commTermList.size() > 0) {
            // 公告termMeta查询
            CmsTerm cmsTerm = commTermList.get(0);
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", cmsTerm.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            commMetaList = termMetaService.search(filters, orders);
        }

        return commMetaList;

    }
}
