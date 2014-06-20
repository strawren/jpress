/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-4-9 上午7:21:39
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * gaowm     2014-4-9        Initailized
 */

package com.strawren.jpress.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.core.Constants;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Page;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.util.Md5Utils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsOption;
import com.strawren.jpress.model.CmsUser;
import com.strawren.jpress.model.CmsUserMeta;
import com.strawren.jpress.service.CmsOptionService;
import com.strawren.jpress.service.CmsUserMetaService;
import com.strawren.jpress.service.CmsUserService;
import com.strawren.jpress.util.ModelInfoUtils;


/**
 * 操作用户
 *
 */
@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseMultiActionController{
    @Autowired
    CmsUserService cmsUserService;

    @Autowired
    CmsUserMetaService cmsUserMetaService;

    @Autowired
    CmsOptionService cmsOptionService;

    /**
     * 列出查询所有用户
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/allUser.action")
    public ModelAndView showAllUser(HttpServletRequest request, HttpServletResponse response, Page<CmsUser> page){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/all");
        String search = request.getParameter("search");  //搜索字段

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        if(StringUtils.isNotBlank(search)){
            filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "LOGIN_NAME", search));
        }
        page.setOrderField("LAST_UPD_TIME");
        page.setOrderDirection(Page.DESC);
        page.setNumPerPage(4);
        page = cmsUserService.search(page, filters);

        mv.addObject("page", page);
        mv.addObject("search", search);
        log.debug("end!!!");
        return mv;
    }

    /**
     * 跳转新增用户页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addUser.action")
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/new");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "CODE", JpressConstants.OPTIONS_KEY_SITE_USER_LEVEL));  //全局设置
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsOption> optionsList = cmsOptionService.search(filters);

        String userLevel = JpressConstants.USER_DEFAULT_LEVEL;
        if(optionsList != null && optionsList.size() > 0){
            userLevel = optionsList.get(0).getValue();
        }
        mv.addObject("userLevel", userLevel);

        log.debug("end!!!");
        return mv;
    }

    /**
     * 保存新增用户信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/saveUser.action")
    public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/new");

        //必填参数检查
        String loginName = request.getParameter("loginName");
        String userEmail = request.getParameter("userEmail");
        String loginPwd = request.getParameter("loginPwd");
        log.info("loginName:" + loginName + ",userEmail:" + userEmail + ",loginPwd:***");
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(userEmail) || StringUtils.isBlank(loginPwd)){
            log.warn("params error!!!");
            mv.addObject("msg", "参数错误");
            return mv;
        }

        CmsUser user = new CmsUser();
        user.setLoginName(loginName);
        user.setUserEmail(userEmail);
        user.setUserName(request.getParameter("userName"));
        user.setNickname(request.getParameter("nickName"));
        user.setLoginPwd(Md5Utils.getMd5Code(loginPwd, "UTF-8", true));  //16位MD5加密
        user.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(user);
        cmsUserService.save(user);

        CmsUserMeta userMeta = new CmsUserMeta();
        userMeta.setUserId(user.getId());
        userMeta.setKey("userLevel");
        userMeta.setValue(request.getParameter("userLevel"));
        userMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(userMeta);
        cmsUserMetaService.save(userMeta);

        mv.setViewName("redirect:/cms/user/allUser.action");
        log.debug("end!!!");
        return mv;
    }

    /**
     * 显示用户信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/showUserInfo.action")
    public ModelAndView showUserInfo(HttpServletRequest req, HttpServletResponse resp){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/show");

        CmsUser user = cmsUserService.get(ModelInfoUtils.getCurUserId());
        mv.addObject("cmsUser", user);

        log.debug("end!!!");
        return mv;
    }

    /**
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/modifyPwd.action")
    public ModelAndView modifyPwd(HttpServletRequest req, HttpServletResponse resp){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/pwdModify");

        log.debug("end!!!");
        return mv;
    }

    /**
     * 跳转到修改用户信息页面显示
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/updateShowUser.action")
    public ModelAndView updateShowUser(HttpServletRequest req, HttpServletResponse resp){
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/user/update");

        String uid = req.getParameter("uid");
        if (StringUtils.isNotBlank(uid)) {
            CmsUser user = cmsUserService.get(new Long(uid));
            mv.addObject("cmsUser",user);
        }


        log.debug("end!!!");
        return mv;
    }

    /**
     * 修改用户信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/updateUser.action")
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("begin...");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cms/user/allUser.action");

        String email = req.getParameter("userEmail");
        String name = req.getParameter("userName");
        String nickName = req.getParameter("nickName");
        String pwd = req.getParameter("loginPwd");
        String uid = req.getParameter("uid");

        if (StringUtils.isNotBlank(uid)) {
            CmsUser user = cmsUserService.get(new Long(uid));
            user.setNickname(nickName);
            user.setUserEmail(email);

            if (StringUtils.isNotBlank(pwd)) {
                user.setLoginPwd(Md5Utils.getMd5Code(pwd, "UTF-8", true));
            }
            user.setUserName(name);
            // 修改
            ModelInfoUtils.updateModelInfoBySys(user);
            cmsUserService.update(user);
        }

        log.debug("end!!!");
        return mv;
    }

    /**
     * 刪除用戶信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/deleteUser.action")
    public ModelAndView deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cms/user/allUser.action");

        String[] ids = req.getParameterValues("checkItem");
        String userId = req.getParameter("uid");

        if (StringUtils.isNotBlank(userId)) {
            CmsUser cmsUser = cmsUserService.get(new Long(userId));
            // 逻辑删除
            cmsUser.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
            cmsUserService.update(cmsUser);
        }

        // 批量删除
        if(ids != null && ids.length > 0) {
            for (String uid : ids) {
                CmsUser cmsUser = cmsUserService.get(new Long(uid));
                cmsUser.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
                cmsUserService.update(cmsUser);
            }
        }
        log.debug("end!!!");
        return mv;
    }





}
