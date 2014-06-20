package com.strawren.jpress.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dto.TermRelevanceTaxonomyDTO;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;
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
@RequestMapping("${adminPath}/tag")
public class TagController extends BaseMultiActionController{


    @Autowired
    private CmsTermService terService;

    @Autowired
    private TermRelevanceTaxonomyService termRelevanceTaxonomyService;

    @Autowired
    CmsPostService cmsPostService;

    @Autowired
    CmsTermMetaService termMetaService;

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsTermTaxonomyService termTaxonomyService;

    @Autowired
    CmsTermRelationshipService termRelationshipService;

    @Autowired
    CmsPostMetaService postMetaService;


    //媒体文件访问路径
    public static final String ADMIN_MEDIA_DIR = SystemPropsUtils.getString("sys.media.url");


    /**
     * tag添加展示
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("tag_index.action")
    public ModelAndView tagIndex(HttpServletRequest request, HttpServletResponse response){
        log.debug("tag_index.action begin...");

        ModelAndView mv = new ModelAndView("cms/content/tag/show_index");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "t1.TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_TAG));
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("\"GROUP\"", Page.ASC));
        List tagParent = termRelevanceTaxonomyService.searchRelevance(filters, orders);
        mv.addObject("tagList", tagParent);
        mv.addObject("mediaUrl", ADMIN_MEDIA_DIR);
        log.debug("sys media url is > >" + ADMIN_MEDIA_DIR);
        log.debug("tag_index.action end!!");
        return mv;

    }

    /**
     * 新增标签
     * @param request
     * @param response
     * @param term
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/tag_add.action")
    public ModelAndView tagAdd(HttpServletRequest request, HttpServletResponse response, CmsTerm term){
        log.debug("tag_add.action begin ...");

        String postId = request.getParameter("postId");
        ModelAndView mv = new ModelAndView("forward:/cms/tag/tag_index.action?menu=menu_content");
        //分类
        term.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);

        //创建erm taxonomy dto
        CmsTermTaxonomy taxonomy = new CmsTermTaxonomy();
        taxonomy.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_TAG);
        taxonomy.setPostCount(0);
        taxonomy.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(term);
        ModelInfoUtils.createModelInfoBySys(taxonomy);
            try {
                terService.addTermTaxonomy(term, taxonomy, postId);
            }
            catch (Exception e) {
               log.debug(e.getMessage(),e);
               mv.addObject("term", term);
               mv.addObject("save_status", "falil");
            }
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "t1.TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_TAG));
        List tagParent = termRelevanceTaxonomyService.searchRelevance(filters);
        mv.addObject("tagList", tagParent);
        mv.addObject("save_status", "succ");

        log.debug("tag_add.action end!!");
        return mv;

    }


    /**
     * 编辑标签展示
     * @param request
     * @param response
     * @param term
     * @return
     */
    @RequestMapping("/edit_tag_show.action")
    public ModelAndView editTermShow(HttpServletRequest request, HttpServletResponse response){
        log.debug("edit_tag_show.action bigin ...");

        ModelAndView mv = new ModelAndView("cms/content/tag/edit_show");
        String tagId = request.getParameter("tagId");
        log.debug("tagId is: " + tagId);
        TermRelevanceTaxonomyDTO term = termRelevanceTaxonomyService.gettermTaxbyId(Long.valueOf(tagId));

        //该标签关联的特色图片
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", tagId));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.KEY_PRCTURE_FEATURE_PERFIX));
        List<CmsTermMeta> termetaList = termMetaService.search(filters);
        CmsTermMeta termMeta = null;
        CmsPost imgPost =  null;
        if(termetaList != null && termetaList.size() > 0){
            termMeta = termetaList.get(0);
        }
        if(termMeta != null){
            imgPost = cmsPostService.get(Long.valueOf(termMeta.getValue()));
        }
        mv.addObject("imgPost", imgPost);
        mv.addObject("term", term);

        log.debug("edit_tag_show.action end");
        return mv;

    }

    /**
     * 标签编辑
     * @param request
     * @param response
     * @param tertax
     * @return
     */
    @RequestMapping("/tag_eidt.action")
    public ModelAndView tagEidt(HttpServletRequest request, HttpServletResponse response, CmsTerm tag){
        log.debug("tag_Eidt.action begin...");

        ModelAndView mv= new ModelAndView("forward:/cms/tag/tag_index.action?menu=menu_content");
        String ImgPostId = request.getParameter("postId");
        Long maxOrderNo = termService.getMaxOerderNo(JpressConstants.MENU_PONIT_TYPE_TAG);
        tag.setGroup(maxOrderNo + 1);
        ModelInfoUtils.updateModelInfoBySys(tag);
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", String.valueOf(tag.getId())));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.KEY_PRCTURE_FEATURE_PERFIX));
        List<CmsTermMeta> termMetaList = termMetaService.search(filters);
        CmsTermMeta imgMeta = null;
        if(termMetaList != null && termMetaList.size() > 0){
            imgMeta = termMetaList.get(0);
            imgMeta.setValue(ImgPostId);
            ModelInfoUtils.updateModelInfoBySys(imgMeta);
        }
        termMetaService.editTag(tag, imgMeta, ImgPostId);
        log.debug("tag_Eidt.action end");

        return mv;
    }

    /**
     *  标签删除
     * @param request
     * @param response
     * @param tertax
     * @return
     */
    @RequestMapping("/tag_delete.action")
    public ModelAndView tagDelete(HttpServletRequest request, HttpServletResponse response, TermRelevanceTaxonomyDTO tertax){
        log.debug("tag_delete.action bigin...");

        ModelAndView mv= new ModelAndView("forward:/cms/tag/tag_index.action?menu=menu_content");
        String tagId = request.getParameter("tagId");
        log.debug("tagId is: " + tagId);
        termRelevanceTaxonomyService.termTaxDelete(tagId);

        log.debug("tag_delete.action end!!");
        return mv;

    }

    /**
     * 检查标签别名是否存在
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/check_slug.action")
    @ResponseBody
    public boolean checkSlug(HttpServletRequest request, HttpServletResponse response){
        log.debug("check_slug.action begin ...");

        boolean existFlag = true;
        String slug = request.getParameter("slug");
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("slug", slug);
        param.put("taxonomy", "tag");
        Long menuBarId = terService.getMenuBarBySlug(param);
        if(menuBarId != null){
            existFlag = false;
        }
        log.debug("existFlag > > " + existFlag);

        log.debug("check_slug.action end!!");
        return existFlag;
    }

}
