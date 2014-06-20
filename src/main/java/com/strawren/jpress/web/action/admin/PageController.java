/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月2日 下午2:14:43
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月2日        Initailized
 */

package com.strawren.jpress.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.util.ModelInfoUtils;


/**
 * 所有页面,新增页面功能
 *
 */
@Controller
@RequestMapping("${adminPath}/page")
public class PageController extends BaseMultiActionController {

    @Autowired
    CmsPostService postService;
    /**
     * 所有页面
     * @return
     */
    @RequestMapping("/all.action")
    public ModelAndView allPage(String search,Integer type,Page<CmsPost> page) {
        log.debug("/cms/page/allPage begin...");

        ModelAndView modelAndView = new ModelAndView();

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_PAGE));
        if (null==type) {
            type = 0;
        }
        switch (type) {
            case 0://全部
                //filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                break;
            case 1://已发布
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_PUBLISH));
                break;
            case 2://已发布
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_DRAFT));
                break;
            case 3://回收站
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE));
                break;

            default:
                break;
        }

        if (!StringUtils.isBlank(search)) {
            filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "title", search));
        }

//        List<CmsPost> pages = postService.search(filters);
//        model.addAttribute("pages", pages);

        page.setNumPerPage(10);
        page.setOrderDirection("DESC");
        page.setOrderField("ID");
        //TODO
        page = postService.search(page, filters);
        modelAndView.addObject("page", page);

        modelAndView.addObject("search", search);
        modelAndView.addObject("type", type);

        //获取全部页面总数
        long allPageCount = postService.countAllPage();
        modelAndView.addObject("allPageCount", allPageCount);
        //获取已发布页面总数
        long allPublishPageCount = postService.countAllPublishPage();
        modelAndView.addObject("allPublishPageCount", allPublishPageCount);
        //获取草稿页面总数
        long allDraftPageCount = postService.countAllDraftPage();
        modelAndView.addObject("allDraftPageCount", allDraftPageCount);
        //获取回收站页面总数
        long allDeletePageCount = postService.countAllDeletePage();
        modelAndView.addObject("allDeletePageCount", allDeletePageCount);

        log.debug("/cms/page/allPage end!!");

        modelAndView.setViewName("/cms/page/all");
        return modelAndView;
    }

    /**
     * 新建页面
     * @return
     */
    @RequestMapping("/new.action")
    public ModelAndView newPage() {
        log.debug("/cms/page/newPage begin...");

        ModelAndView mv = new ModelAndView("/cms/page/new");
        //获取所有已发布的页面列表,并设置到model中

        setAllPublishPagesToModel(mv);

        log.debug("/cms/page/newPage end!!");
        return mv;
    }

    /**
     * 编辑页面
     * @return
     */
    @RequestMapping("/edit.action")
    public ModelAndView editPage(Long id) {
        log.debug("/cms/page/editPage begin...");

        ModelAndView mv = new ModelAndView("/cms/page/edit");
        //获取页面信息
        CmsPost page = postService.get(id);
        mv.addObject("page", page);

        //获取所有已发布的页面列表,并设置到model中
        setAllPublishPagesToModel(mv);


        log.debug("/cms/page/editPage end!!");
        return mv;
    }


    /**
     * 获取所有已发布的页面列表,并设置到model中
     * @param model
     */
    private void setAllPublishPagesToModel(ModelAndView mv) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_PAGE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_PUBLISH));

        List<CmsPost> parentPages = postService.search(filters);

        //添加无父级节点
        CmsPost rootPage = new CmsPost();
        rootPage.setId(0L);
        rootPage.setTitle("(无父级)");
        parentPages.add(rootPage);

        JSONArray parentPagesJson = JSONArray.fromObject(parentPages);
        mv.addObject("parentPages", parentPagesJson);
    }

    /**
     * 添加一个页面
     * @return
     */
    @RequestMapping(value="/add.action",method=RequestMethod.POST)
    public ModelAndView addPage(CmsPost page) {
        log.debug("/cms/page/addPage begin...");

        //TODO 设置操作人ID,name,time
        Long operId = 1L;
        String operName = "管理员";
        page.setOwnerId(operId);
        page.setShowOwner(operName);
        ModelInfoUtils.createModelInfoBySys(page);

        page.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);
        page.setPostType(JpressConstants.DICT_POST_TYPE_PAGE);
        postService.save(page);

        log.debug("/cms/page/addPage end!!");

        return new ModelAndView("redirect:/cms/page/all.action?menu=menu_page");
    }

    /**
     * 更新一个页面
     * @return
     */
    @RequestMapping(value="/update.action",method=RequestMethod.POST)
    public ModelAndView updatePage(CmsPost page) {
        log.debug("/cms/page/updatePage begin...");

        //TODO 设置操作人ID,name,time
        ModelInfoUtils.updateModelInfoBySys(page);
        //已移入回收站的页面,更新时更新状态
        page.setStatus(JpressConstants.DICT_GLOBAL_STATUS_VALIDATE);

        postService.update(page);

        log.debug("/cms/page/updatePage end!!");

        return new ModelAndView("redirect:/cms/page/all.action?menu=menu_page");
    }

    /**
     * 将页面移至回收站(支持批量)
     * @param ids 多个ID用,分割
     * @return
     */
    @RequestMapping("/remove.action")
    public ModelAndView removePage(String ids){
        log.debug("/cms/page/removePage begin...");

            String[] pageIds = ids.split(",");
            postService.batchRemovePage(pageIds);

        log.debug("/cms/page/removePage end!!");
        return new ModelAndView("redirect:/cms/page/all.action?menu=menu_page");
    }
}
