/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-3-29 上午8:40:12
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-3-29        Initailized
 */

package com.strawren.jpress.web.action.admin;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.model.CmsOption;
import com.strawren.jpress.service.CmsOptionService;
import com.strawren.jpress.util.ModelInfoUtils;


/**
 * 设置-常规
 *
 */
@Controller
@RequestMapping("${adminPath}/settings")
public class SettingsController extends BaseMultiActionController{
    @Autowired
    CmsOptionService cmsOptionService;

    /**
     * 常规设置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/regular.action")
    public ModelAndView regular(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsOption> optionsList = cmsOptionService.search(filters);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/settings/regular");
        mv.addObject("optionsList", optionsList);
        String message = request.getParameter("message");
        mv.addObject("message", message);
        
        log.debug("end!!!");
        return mv;
    }

    /**
     * 修改常规设置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateRegularConfig.action")
    public ModelAndView updateRegularConfig(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsOption> optionsList = cmsOptionService.search(filters);

        for(CmsOption option : optionsList){
            String code = option.getCode();
            String value = request.getParameter(code);
            option.setValue(value);
            ModelInfoUtils.updateModelInfoBySys(option);
            cmsOptionService.update(option);
        }

        ModelMap mmap = new ModelMap(); 
        mmap.addAttribute("message", "参数已更新。");
        mmap.addAttribute("menu", "menu_settings");
        ModelAndView mv = new ModelAndView("redirect:/cms/settings/regular.action", mmap);

        log.debug("end!!!");
        return mv;
    }

    @RequestMapping("/compose.action")
    public ModelAndView compose(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/settings/compose");

        log.debug("end!!!");
        return mv;
    }

}
