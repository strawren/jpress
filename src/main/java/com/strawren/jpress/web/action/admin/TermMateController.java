package com.strawren.jpress.web.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTermMeta;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsTermMetaService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.util.ModelInfoUtils;



/**
 * 分类目录属性Controller
 * @author liukh
 *
 */

@Controller
@RequestMapping("${adminPath}/term_meta")
public class TermMateController extends BaseMultiActionController{


    @Autowired
    private CmsTermMetaService termMetaService;

    @Autowired
    private CmsPostMetaService postMetaService;

    @Autowired
    private CmsTermService termService;

    /**
     * 目录属性列表展示
     * @return
     */
    @RequestMapping("/list_meta.action")
    public ModelAndView listMeta(HttpServletRequest request, HttpServletResponse response){
        log.debug("/cms/term_meta list_Meta.action begin...");

        ModelAndView mv = new ModelAndView("cms/content/term_meta/list");

        String termId = request.getParameter("termId");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("SHOW_ORDER", Page.ASC));
        String termName = termService.get(Long.valueOf(termId)).getName();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_ID", termId));
        List<CmsTermMeta> termMetaList = termMetaService.search(filters, orders);
        mv.addObject("metaType", request.getParameter("metaType"));
        mv.addObject("termId", termId);
        mv.addObject("termName", termName);
        mv.addObject("termMetaList", termMetaList);

        log.debug("/cms/term_meta list_Meta.action begin...");
        return mv;

    }





    /**
     * 目录属性或post属性添加
     * @return
     */
    @RequestMapping("/meta_add.action")
    public ModelAndView metaAdd(HttpServletRequest request, HttpServletResponse response, CmsTermMeta termMeta){
        log.debug("termMeta >> meta_add.action begin...");

        ModelAndView mv = new ModelAndView("forward:/cms/term_meta/list_meta.action?menu=menu_content");
        //排序序号查询
        Long maxOrderNo = termMetaService.getMaxOrderNo(termMeta.getTermId());
        termMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        termMeta.setShowOrder(maxOrderNo + 1);
        ModelInfoUtils.createModelInfoBySys(termMeta);
        termMetaService.save(termMeta);
        log.debug("termMeta >> meta_add.action end!!");

        return mv;
    }



    /**
     * 目录属性编辑展示
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/edit_show.action")
    public ModelAndView editShow(HttpServletRequest request, HttpServletResponse response){
        log.debug("termMeta >> edit_show.action begin");

        ModelAndView mv = new ModelAndView("cms/content/term_meta/edit");
        String metaId = request.getParameter("metaId");
        CmsTermMeta termMeta = termMetaService.get(Long.valueOf(metaId));
        //查找对应的post值
//        if(termMeta != null && JpressConstants.METE_TYPE_POST.equals(termMeta.getMetaType())){
//           CmsPostMeta postMeta = getPostMateByTermMeta(termMeta);
//           if(postMeta != null){
//               termMeta.setValue(postMeta.getValue());
//           }
//           mv.addObject("postMetaLstUpdTime", postMeta.getLastUpdTime());
//        }
        String termName = termService.get(termMeta.getTermId()).getName();
        mv.addObject("metaType", request.getParameter("metaType"));
        mv.addObject("termMeta", termMeta);
        mv.addObject("termName", termName);


        log.debug("termMeta >> edit_show.action end!!");
        return mv;
    }

    /**
     * 目录属性编辑
     * @param request
     * @param response
     * @param termMeta
     * @return
     */
    @RequestMapping("/edit.action")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, CmsTermMeta termMeta){
        log.debug("termMeta >> edit.action begin");

        ModelAndView mv = new ModelAndView("forward:/cms/term_meta/list_meta.action?termId=" + request.getParameter("termId"));
        ModelInfoUtils.updateModelInfoBySys(termMeta);
        termMetaService.update(termMeta);


        log.debug("termMeta >> edithtml end!!");
        return mv;

    }


    /**
     * 删除目录属性或post属性
     * @return
     */
    @RequestMapping("/delete.action")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response){
        log.debug("termMeta >> delete.action begin");

        ModelAndView mv = new ModelAndView("forward:/cms/term_meta/list_meta.action?menu=menu_content&termId=" + request.getParameter("termid"));
        String termMetaId = request.getParameter("metaId");
        String[] ids = {termMetaId};
        termMetaService.delTermMeta(ids);

        log.debug("termMeta >> delete.action end!!");
        return mv ;
    }


    @RequestMapping("/get_post_meta.action")
    @ResponseBody
    public List<CmsTermMeta> getPostMeta(HttpServletRequest request, HttpServletResponse response){
        log.debug("termMeta >> get_post_meta.action begin");

        String termId = request.getParameter("termId");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_ID", termId));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "META_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        List<CmsTermMeta> termMetaList = termMetaService.search(filters);
        log.debug("termMeta >> get_post_meta.action begin");
        return termMetaList;
    }



    public void sendJson(String json, HttpServletResponse response) {
        log.debug("json -> " + json);
        response.setContentType("text/Xml;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(json);
        }
        catch (IOException e) {
            log.debug("sendJson error:" + e.getMessage(), e);
        }
    }

    public JSONObject listToJson(List<?> list) {
        JSONObject resultJSON = new JSONObject();
        if (list.size() > 0) {
            resultJSON.accumulate("root", list);
        }
        log.debug("resultJSON ->" + resultJSON);
        return resultJSON;
    }




    /**
     * 根据termMeta获取postMeta
     * @param termMeta
     * @return
     */
    public CmsPostMeta getPostMateByTermMeta(CmsTermMeta termMeta){

        List<CmsPostMeta> postMatList = null;
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_META_ID", termMeta.getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", termMeta.getKey()));
        postMatList = postMetaService.search(filters);
        return postMatList.size() == 0 || postMatList == null ? null : postMatList.get(0) ;

    }






}
