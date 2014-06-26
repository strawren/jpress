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
import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.bsp.util.Md5Utils;
import com.strawren.bsp.util.PropertyFilterUtils;
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
public class UserController extends BaseMultiActionController {
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
    @RequestMapping("/list.action")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response, Page<CmsUser> page) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView("/admin/user/list", "page", page);
        
        List<PropertyFilter> filters = PropertyFilterUtils.buildPropertyFilters(request);
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        //默认每行四个
        page.setNumPerPage(4);
        page = cmsUserService.search(page, filters);

        log.debug("end!!!");
        return mv;
    }

    /**
     * 跳转新增用户页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/edit.action")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, CmsUser model) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView("/admin/user/edit", "model", model);
        
        if(model == null || model.getId() < 1) {
        	model = new CmsUser();
        }
        else {
			model = cmsUserService.get(model.getId());
		}

        putUserLevel2Mv(mv);
        log.debug("end!!!");
		return mv;
    }

    /**
     * 保存新增用户信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/save.action")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, CmsUser model) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView("/admin/user/edit", "model", model);
        
        //必填参数检查
        String loginName = model.getLoginName();
        String userEmail = model.getUserEmail();
        String loginPwd = model.getLoginPwd();
        log.info("loginName:" + loginName + ", userEmail : " + userEmail + ", loginPwd : ***");
        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(userEmail) || StringUtils.isBlank(loginPwd)){
            log.warn("params error!!!");
            mv.addObject("message", "参数错误");
            putUserLevel2Mv(mv);
            return mv;
        }
        
        try {
        	boolean createFlag = model.getId() > 0 ? false : true;
        	//新建
        	if (createFlag) {
        		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
                //登录名重复
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "LOGIN_NAME", loginName)); 
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
                List<CmsUser> cmsUserList = cmsUserService.search(filters);
                if(cmsUserList != null && cmsUserList.size() > 0){
                    log.warn("reduplicated login name :" + loginName);
                    mv.addObject("message", "登录名已经存在");
                    putUserLevel2Mv(mv);
                    return mv;
                }
                //邮箱重复
                filters.clear();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "USER_EMAIL", userEmail)); 
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
                cmsUserList = cmsUserService.search(filters);
                if(cmsUserList != null && cmsUserList.size() > 0){
                    log.warn("reduplicated user email :" + userEmail);
                    mv.addObject("message", "邮箱已经存在");
                    putUserLevel2Mv(mv);
                    return mv;
                }
                
                //处理密码
                model.setLoginPwd(Md5Utils.getMd5Code(loginPwd, "UTF-8", true));  //16位MD5加密
                model.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
                model.setUserStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
                model.setRegisterDate(DateTimeUtils.getCurrentDate());
                model.setSerialKey("NA");
                
                ModelInfoUtils.createModelInfoBySys(model);
                cmsUserService.save(model);
                
                CmsUserMeta userMeta = new CmsUserMeta();
                userMeta.setUserId(model.getId());
                userMeta.setJkey("userLevel");
                userMeta.setValue(request.getParameter("userLevel"));
                userMeta.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
                ModelInfoUtils.createModelInfoBySys(userMeta);
                cmsUserMetaService.save(userMeta);
        	}
        	//更新
        	else {
        		if (StringUtils.isNotBlank(loginPwd)) {
                    model.setLoginPwd(Md5Utils.getMd5Code(loginPwd, "UTF-8", true));
                }
        		//登录名不能
        		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "LOGIN_NAME", loginName)); 
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
                List<CmsUser> cmsUserList = cmsUserService.search(filters);
                if(cmsUserList != null && cmsUserList.size() == 0){
                    log.warn("login name cannot modify :" + loginName);
                    mv.addObject("message", "登录名不能修改");
                    putUserLevel2Mv(mv);
                    return mv;
                }
                //邮箱重复
                filters.clear();
                filters.add(new PropertyFilter(MatchType.NEQ, PropertyType.L, "ID", String.valueOf(model.getId()))); 
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "USER_EMAIL", userEmail)); 
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
                cmsUserList = cmsUserService.search(filters);
                if(cmsUserList != null && cmsUserList.size() > 0){
                    log.warn("reduplicated user email :" + userEmail);
                    mv.addObject("message", "邮箱已经存在");
                    putUserLevel2Mv(mv);
                    return mv;
                }
                
                // 修改
                ModelInfoUtils.updateModelInfoBySys(model);
                cmsUserService.update(model);
        	}
        }
        catch(Exception e) {
        	log.info(e.getMessage(), e);
        	wrapModelAndView(mv, e);
        }
        
        log.debug("end!!!");
        return list(request, response, new Page<CmsUser>());
    }

    /**
     * 显示用户信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/view.action")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response, CmsUser model) {
        log.debug("begin...");

        model = cmsUserService.get(model.getId());
        log.debug("end!!!");
        return new ModelAndView("/admin/user/view", "model", model);
    }

    /**
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/pwd.action")
    public ModelAndView pwd(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        ModelAndView mv = new ModelAndView("/admin/user/pwd");

        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        
        if(StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd)) {
        	log.warn("old pwd or new pwd is null");
            mv.addObject("message", "新密码或者旧密码为空!");
            return mv;
        }
        
        CmsUser model = (CmsUser)request.getSession().getAttribute("user");
        if(model == null) {
        	log.info("can not change pwd, for session user is null");
        	 mv.addObject("message", "当前登录用户已经失效，请重新登录后再试！");
             return mv;
        }
        
        model = cmsUserService.get(model.getId());
        if(model == null) {
        	log.warn("user don't exist");
            mv.addObject("message", "用户不存在!");
            return mv;
        }
        if(!Md5Utils.getMd5Code(oldPwd, "UTF-8", true).equals(model.getLoginPwd())) {
        	log.warn("user's old pwd is incorrect for [" + model.getId() + "]");
            mv.addObject("message", "原密码不正确 !");
            return mv;
        }
        
        model.setLoginPwd(Md5Utils.getMd5Code(newPwd, "UTF-8", true)); 
        ModelInfoUtils.updateModelInfoBySys(model);
        cmsUserService.update(model);
        mv.addObject("message", "修改密码成功，请牢记新密码哦!");
        
        log.debug("end!!!");
        return mv;
    }

    /**
     * 刪除用戶信息
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/del.action")
    public ModelAndView del(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        String id = getModelId(request);
		String [] ids = request.getParameterValues("ids");
		log.debug("id->" + id + ", and ids ->" + ids);
		
        if (StringUtils.isNotBlank(id)) {
            CmsUser model = cmsUserService.get(new Long(id));
            model.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
            ModelInfoUtils.updateModelInfoBySys(model);
            cmsUserService.update(model);
        }
        if(ids != null && ids.length > 0) {
            for (String uid : ids) {
            	CmsUser model = cmsUserService.get(new Long(uid));
                model.setStatus(Constants.DICT_GLOBAL_STATUS_INVALIDATE);
                ModelInfoUtils.updateModelInfoBySys(model);
                cmsUserService.update(model);
            }
        }
        
        log.debug("end!!!");
        return list(request, response, new Page<CmsUser>());
    }

    protected void putUserLevel2Mv(ModelAndView mv) {
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        //全局设置
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "CODE", JpressConstants.OPTIONS_KEY_SITE_USER_LEVEL)); 
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsOption> optionsList = cmsOptionService.search(filters);
        String userLevel = JpressConstants.USER_DEFAULT_LEVEL;
        if(optionsList != null && optionsList.size() > 0){
            userLevel = optionsList.get(0).getValue();
        }
        mv.addObject("userLevel", userLevel);
    }
}
