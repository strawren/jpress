package com.strawren.jpress.web.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dto.TermRelevanceTaxonomyDTO;
import com.strawren.jpress.dto.TermTaxonomyDTO;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsTermMetaService;
import com.strawren.jpress.service.CmsTermRelationshipService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.service.CmsTermTaxonomyService;
import com.strawren.jpress.service.TermRelevanceTaxonomyService;
import com.strawren.jpress.util.ModelInfoUtils;


@Controller
@RequestMapping("${adminPath}/taxonomy")
public class TaxonomyCatalogController extends BaseMultiActionController{

    @Autowired
    private TermRelevanceTaxonomyService termRelevanceTaxonomyService;

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


    /**
     * 添加分类目录首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/taxonomy_index.action")
    public ModelAndView taxonomyIndex(HttpServletRequest request, HttpServletResponse response){
        log.debug("taxonomyIndex begin...");

        ModelAndView mv = new ModelAndView("cms/content/taxonomy_catalog/show_index");

        //分类
        List<TermRelevanceTaxonomyDTO> parent = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        TermRelevanceTaxonomyDTO dto = new TermRelevanceTaxonomyDTO();
        dto.setName("无父级");
        dto.setId(-1L);
        dto.setParentId(0L);
        parent.add(0,dto);
        //显示
        List<TermRelevanceTaxonomyDTO> viewList = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);

        JSONArray parentPagesJson = JSONArray.fromObject(parent);
        mv.addObject("parentList", parentPagesJson);
        mv.addObject("viewList", viewList);

        log.debug("taxonomyIndex end!!");
        return mv;

    }


    @RequestMapping("/addTaxonomy.action")
    public ModelAndView addTaxonomy(HttpServletRequest request, HttpServletResponse response, CmsTerm term){
        log.debug("addTaxonomy bigin...");

        ModelAndView mv = new ModelAndView("forward:/cms/taxonomy/taxonomy_index.action?menu=menu_content");

        //分类
        term.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
//        terService.save(term);
//      //分类方法
//        CmsTermTaxonomy taxonomy = new CmsTermTaxonomy();
//        long termId = terService.getTermByslug(term.getSlug());
//        taxonomy.setTermId(termId);
//        taxonomy.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
//        //如果选择有父类Id
//        if(StringUtils.isBlank(request.getParameter("parentId"))){
//            taxonomy.setParentId(0L);
//        }else{
//            taxonomy.setParentId(Long.valueOf(request.getParameter("parentId")));
//        }
//        taxonomy.setPostCount(0);
//        taxonomy.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
//        termTaxonomyService.save(taxonomy);

        ///------------------------------------------------------------------
        CmsTermTaxonomy taxonomy = new CmsTermTaxonomy();
        taxonomy.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        if(StringUtils.isBlank(request.getParameter("parentId"))){
          taxonomy.setParentId(0L);
        }else{
            String parentId = request.getParameter("parentId");
            if(Integer.parseInt(parentId) < 0){
                taxonomy.setParentId(0L);
            }else{
                taxonomy.setParentId(Long.valueOf(request.getParameter("parentId")));
            }

        }
        taxonomy.setPostCount(0);
        taxonomy.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(term);
        ModelInfoUtils.createModelInfoBySys(taxonomy);
            try {
                termService.addTermTaxonomy(term, taxonomy, null);
            }
            catch (Exception e) {
               log.debug(e.getMessage(),e);
               mv.addObject("term", term);
               mv.addObject("save_status", "falil");
            }


        List<TermRelevanceTaxonomyDTO> parent = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        JSONArray parentPagesJson = JSONArray.fromObject(parent);
        log.debug("addTaxonomy end!!");
        mv.addObject("save_status", "succ");
        mv.addObject("parentList", parentPagesJson);

        return mv;

    }


    /**
     * 编辑分类目录页面展示
     * @param request
     * @param response
     * @param term
     * @return
     */
    @RequestMapping("/edit_term_show.action")
    public ModelAndView editTermShow(HttpServletRequest request, HttpServletResponse response){
        log.debug("edit_term_show bigin ...");

        ModelAndView mv = new ModelAndView("cms/content/taxonomy_catalog/edit_show");
        String termId = request.getParameter("termId");
        log.debug("termId is: " + termId);
        //分类目录（cat）
        List<TermRelevanceTaxonomyDTO> parent = gettaxonomyParent(JpressConstants.DICT_TERM_TAXONOMY_CATEGORY);
        List<TermRelevanceTaxonomyDTO> showParent = new ArrayList<TermRelevanceTaxonomyDTO>();
        for (TermRelevanceTaxonomyDTO termTaxonomyDTO : parent) {
            //做为父类时，在下拉框中将自己屏蔽掉
            if(!termId.equals(String.valueOf(termTaxonomyDTO.getId()))){
                showParent.add(termTaxonomyDTO);
            }else{
                CmsTerm term = termService.get(termTaxonomyDTO.getParentId());
                if(term != null){
                    log.debug("parent id is " + termTaxonomyDTO.getParentId());
                    mv.addObject("termName", term.getName());
                }else{
                    mv.addObject("termName", JpressConstants.NO_PARENT_TEXT);
                }

            }
        }
        TermRelevanceTaxonomyDTO dto = new TermRelevanceTaxonomyDTO();
        dto.setName("无父级");
        dto.setId(-1L);
        dto.setParentId(0L);
        showParent.add(0,dto);
        JSONArray parentPagesJson = JSONArray.fromObject(showParent);
        TermRelevanceTaxonomyDTO term = termRelevanceTaxonomyService.gettermTaxbyId(Long.valueOf(termId));
        mv.addObject("term", term);
        mv.addObject("parentList", parentPagesJson);

        log.debug("edit_term_show end");
        return mv;

    }


    /**
     * 分类栏目修改
     * @param request
     * @param response
     * @param tertax
     * @return
     */
    @RequestMapping("/term_tax_update.action")
    public ModelAndView termTaxUpadate(HttpServletRequest request, HttpServletResponse response,  CmsTerm term){
        log.debug("term_tax_update.action bigin...");
        ModelAndView mv = new ModelAndView("forward:/cms/taxonomy/taxonomy_index.action?menu=menu_content");
        String termId = request.getParameter("termId");
        String parentId = request.getParameter("parentId");
        String taxLastUpdTime = request.getParameter("taxLastUpdTime");
        term.setId(Long.valueOf(termId));
        termRelevanceTaxonomyService.termUpdate(term, taxLastUpdTime, parentId);

        log.debug("term_tzx_update.action end!!");
        return mv;

    }


    /**
     * 分类目录删除
     * @param request
     * @param response
     * @param tertax
     * @return
     */
    @RequestMapping("/term_tax_delete.action")
    public ModelAndView termTaxDelete(HttpServletRequest request, HttpServletResponse response, TermRelevanceTaxonomyDTO tertax){
        log.debug("term_tax_delete.action bigin...");

        ModelAndView mv = new ModelAndView("forward:/cms/taxonomy/taxonomy_index.action?menu=menu_content");

        String termId = request.getParameter("termId");
        log.debug("termId is: " + termId);
        termRelevanceTaxonomyService.termTaxDelete(termId);

        log.debug("term_tax_delete.action end!!");
        return mv;

    }


    /**
     * 根据taxonomy获取分类目录父分类
     * @param taxonomy
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TermRelevanceTaxonomyDTO> gettaxonomyParent(String taxonomy){

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        List<TermRelevanceTaxonomyDTO> termParent = null;
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "t1.TAXONOMY", taxonomy));
        termParent = termRelevanceTaxonomyService.searchRelevance(filters);

        return termParent;
    }


    /**
     * 分类目录查看
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/term_view.action")
    public ModelAndView termView(HttpServletRequest request, HttpServletResponse response){
        log.debug("term_view.action begin...");

        ModelAndView mv = new ModelAndView("cms/content/taxonomy_catalog/view");
        String termId = request.getParameter("termId");
        log.debug("termId is:" + termId);
        TermRelevanceTaxonomyDTO termDto = termRelevanceTaxonomyService.gettermTaxbyId(Long.valueOf(termId));
        if(termDto != null){
            log.debug("parent id is " + termDto.getParentId());
            CmsTerm term = termService.get(termDto.getParentId());
            if(term != null){
                mv.addObject("termName", term.getName());
            }else{
                mv.addObject("termName", JpressConstants.NO_PARENT_TEXT);
            }

        }else{
            mv.addObject("termName", JpressConstants.NO_PARENT_TEXT);
        }

        mv.addObject("term", termDto);

        log.debug("term_view.action end!!");
        return mv;

    }


    /**
     * 检查分类目录别名是否存在
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
        param.put("taxonomy", "cat");
        Long menuBarId = termService.getMenuBarBySlug(param);
        if(menuBarId != null){
            existFlag = false;
        }
        log.debug("existFlag > > " + existFlag);

        log.debug("check_slug.action end!!");
        return existFlag;
    }

    /**
     * 添加系统term
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/sys_term_add.action")
    public ModelAndView sysTermAdd(HttpServletRequest request, HttpServletResponse response){
        Long termId = null;
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "SLUG", JpressConstants.SYS_COMMON_META_TERM_SLUG));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_COMMONMETA));
        List<TermTaxonomyDTO> termTaxList = termService.searchTermTaxonomy(filters, null);
        //如果系统term不存在，则去添加
        if(termTaxList == null || termTaxList.size() <=0){
            termId = termService.addSysTerm();
        }
        else{
            TermTaxonomyDTO termTaxDto = termTaxList.get(0);
            termId = termTaxDto.getTermId();
        }
        return new ModelAndView("forward:/cms/term_meta/list_meta.action?termId=" + termId + "&metaType=post");
    }


}


