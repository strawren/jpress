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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.dto.CmsLinkCategoryDTO;
import com.strawren.jpress.dto.CmsLinkDTO;
import com.strawren.jpress.model.CmsLink;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.service.CmsLinkService;
import com.strawren.jpress.service.CmsTermService;


/**
 * 链接相关功能
 *
 */
@Controller
@RequestMapping("${adminPath}/link")
public class LinkController extends BaseMultiActionController {

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsLinkService linkService;
    
    String adminPath = SystemPropsUtils.getString(Constants.JP_GLOBAL_SYS_INFO_ADMIN_PATH_KEY);
    
    /**
     * 所有链接
     * @return
     */
    @RequestMapping("/all.action")
    public ModelAndView allLink(String search,Page<CmsLinkDTO> page) {
        log.debug("/admin/link/allLink begin...");

        ModelAndView mv = new ModelAndView("/admin/link/all");

        if (search == null) {
            search="";
        }

        page.setNumPerPage(10);
        page.setOrderDirection("DESC");
        page.setOrderField("l.ID");

        page = linkService.searchLink(search,page);

        mv.addObject("page", page);
        mv.addObject("search", search);

        log.debug("/admin/link/allLink end!!");
        return mv;
    }

    /**
     * 新建链接
     * @return
     */
    @RequestMapping("/new.action")
    public ModelAndView newLink() {
        log.debug("/admin/link/newLink begin...");

        ModelAndView mv = new ModelAndView("/admin/link/new");
        //获取所有链接分类目录
        List<CmsLinkCategoryDTO> linkCategories = termService.findAllLinkCategory("");
        mv.addObject("linkCategories", linkCategories);

        log.debug("/admin/link/newLink end!!");
        return mv;
    }

    /**
     * 编辑链接
     * @return
     */
    @RequestMapping("/edit.action")
    public ModelAndView editLink(Long id) {
        log.debug("/admin/link/editLink begin...");

        ModelAndView mv = new ModelAndView("/admin/link/edit");

        CmsLinkDTO link = linkService.findLinkById(id);
        mv.addObject("link", link);

        //获取所有链接分类目录
        List<CmsLinkCategoryDTO> linkCategories = termService.findAllLinkCategory("");
        mv.addObject("linkCategories", linkCategories);

        log.debug("/admin/link/editLink end!!");
        return mv;
    }

    /**
     * 添加链接
     * @return
     */
    @RequestMapping("/add.action")
    public ModelAndView addLink(CmsLink link,Long termTaxonomyId) {
        log.debug("/admin/link/addCategory begin...");

        ModelAndView mv = new ModelAndView();
        //TODO
        try {
            linkService.saveLink(link,termTaxonomyId);
        }
        catch (Exception e) {
        	e.printStackTrace();
            log.error(e.getMessage());
            mv.addObject("link", link);
            mv.addObject("msg", "操作失败,请检查是否存在重复的字段");
            mv.addObject("menu", "menu_links");
            mv.setViewName("/admin/link/new");;
            return mv;
        }
        log.debug("/admin/link/addCategory end!!");

        mv.setViewName("redirect:" + adminPath + "/link/all.action?menu=menu_links");
        return mv;
    }

    /**
     * 更新链接
     * @return
     */
    @RequestMapping("/update.action")
    public ModelAndView updateLink(CmsLinkDTO link,Long termTaxonomyId) {
        log.debug("/admin/link/updateLink begin...");

        ModelAndView mv = new ModelAndView();

        //TODO
        try {
            linkService.updateLink(link,termTaxonomyId);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            mv.addObject("link", link);
            mv.addObject("msg", "操作失败,请检查是否存在重复的字段");

            //获取所有链接分类目录
            List<CmsLinkCategoryDTO> linkCategories = termService.findAllLinkCategory("");
            mv.addObject("linkCategories", linkCategories);

            mv.setViewName("/admin/link/edit");
            return mv;
        }
        log.debug("/admin/link/updateLink end!!");

        mv.setViewName("redirect:" + adminPath + "/link/all.action?menu=menu_links");
        return mv;
    }

    /**
     * 删除链接(支持批量)
     * @param ids 多个ID用,分割
     * @return
     */
    @RequestMapping("/remove.action")
    public ModelAndView removeLink(String ids){
        log.debug("/admin/link/removeLink begin...");

            String[] linkIds = ids.split(",");

            linkService.removeLinks(linkIds);

        log.debug("/admin/link/removeLink end!!");
        return new ModelAndView("redirect:" + adminPath + "/link/all.action?menu=menu_links");
    }

    /**
     * 链接分类
     * @return
     */
    @RequestMapping("/category.action")
    public ModelAndView linkCategory(String search,Page<CmsLinkCategoryDTO> page) {
        log.debug("/admin/link/linkCategory begin...");

        ModelAndView mv = new ModelAndView("/admin/link/category");

        if (search==null) {
           search = "";
        }

        page.setNumPerPage(10);
        page.setOrderDirection("DESC");
        page.setOrderField("t.ID");

        page = termService.findAllLinkCategory(search,page);
        mv.addObject("page",page);

        mv.addObject("search",search);

        log.debug("/admin/link/linkCategory end!!");
        return mv;
    }

    /**
     * 添加或编辑链接分类
     * @return
     */
    @RequestMapping("/addCategory.action")
    public ModelAndView addCategory(CmsTerm linkCategory) {
        log.debug("/admin/link/addCategory begin...");

        ModelAndView mv = new ModelAndView();

        //判断是否有ID
        boolean hasId = (linkCategory.getId()!=null);
        //TODO
        try {
            if (!hasId) {//如果没有ID,这新增
                termService.addLinkCategory(linkCategory);
            }else {
                //有ID,update
                termService.update(linkCategory);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            //如果是新增,将ID置空
            if (!hasId) {
                linkCategory.setId(null);
            }
            mv.addObject("linkCategory", linkCategory);
            mv.addObject("msg", "操作失败,请检查是否存在重复的字段");
            mv.setViewName("forward:" + adminPath + "/link/category.action?menu=menu_links");
            return mv;
        }
        log.debug("/admin/link/addCategory end!!");

        mv.setViewName("redirect:" + adminPath + "/link/category.action?menu=menu_links");
        return mv;
    }

    /**
     * 编辑链接分类
     * @return
     */
    @RequestMapping("/editCategory.action")
    public ModelAndView editCategory(Long id) {
        log.debug("/admin/link/editCategory begin...");

        ModelAndView mv = new ModelAndView("forward:" + adminPath + "/link/category.action?menu=menu_links");

        CmsTerm linkCategory = termService.get(id);
        mv.addObject("linkCategory", linkCategory);

        log.debug("/admin/link/editCategory end!!");
        return mv;
    }

    /**
     * 删除链接分类目录(支持批量)
     * @param ids
     * @return
     */
    @RequestMapping("/removeCategory.action")
    public ModelAndView removeCategory(String ids){
        log.debug("/admin/link/removeCategory begin...");

        ModelAndView mv = new ModelAndView("redirect:" + adminPath + "/link/category.action?menu=menu_links");

            String[] categoryIds = ids.split(",");

            termService.batchDeleteLinkCategory(categoryIds);

        log.debug("/admin/link/removeCategory end!!");
        return mv;
    }
}
