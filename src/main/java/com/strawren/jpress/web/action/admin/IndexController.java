package com.strawren.jpress.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsUser;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsUserMetaService;
import com.strawren.jpress.service.CmsUserService;
import com.strawren.jpress.util.RandomValidateCode;

@Controller
@RequestMapping("${adminPath}")
public class IndexController extends BaseMultiActionController {
    @Autowired
    CmsUserService cmsUserService;

    @Autowired
    CmsUserMetaService cmsUserMetaService;

    @Autowired
    CmsPostService postService;

    /**
     * 跳转到仪表盘页面
     * @param request
     * @param response
     * @return
     */
	@RequestMapping("/index.action")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        log.debug("index begin...");

        ModelAndView mv = new ModelAndView("/admin/index");

        //获取内容草稿数
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_DRAFT));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        long dtrCount = postService.count(filters);

        //获取待审核总数
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_WAITING));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        long waiCount = postService.count(filters);

        //最近草稿
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_DRAFT));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        Page<CmsPost> postPage = new Page<CmsPost>(Page.MIN_PAGESIZE, false);
        postPage.setOrderField("CREATE_TIME");
        postPage.setOrderDirection(Page.DESC);
        List<CmsPost> latelyDrtList = postService.search(postPage, filters).getResult();

        //待审核内容
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_WAITING));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> waiList = postService.search(postPage, filters).getResult();

        //最近内容
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_STATUS", JpressConstants.DICT_POST_STATUS_PUBLISH));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_POST));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost>  latelyList = postService.search(postPage, filters).getResult();

        mv.addObject("dtrCount", dtrCount);
        mv.addObject("waiCount", waiCount);
        mv.addObject("latelyDrtList", latelyDrtList);
        mv.addObject("waiList", waiList);
        mv.addObject("latelyList", latelyList);

        log.debug("index end!!!");
        return mv;
    }

	/**
     * 登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login.action")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/login");

        if(StringUtils.isBlank(request.getParameter("loginFlag"))){
            return mv;
        }

        String loginName = request.getParameter("loginName");
        String ranValCode = request.getParameter("ranValCode");
        Object obj = request.getSession().getAttribute(JpressConstants.RANDOM_CODE_KEY);
        if(obj != null){
            log.debug("ranValCode in session: " + obj.toString() + ", user input ranValCode: " + ranValCode);
            if(!obj.toString().equalsIgnoreCase(ranValCode)){
                mv.addObject("loginName", loginName);
                mv.addObject("error", "验证码错误");
                return mv;
            }
        }
        else{
            mv.addObject("error", "验证码异常，请重试");
            return mv;
        }

        String loginPwd = Md5Utils.getMd5Code(request.getParameter("loginPwd"), "UTF-8", true);
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPwd);
        String rememberMe = request.getParameter("rememberMe");
        log.debug("rememberMe: " + rememberMe);
        if(StringUtils.isNotBlank(rememberMe)){
            token.setRememberMe(true);
        }
        Subject currentUser = SecurityUtils.getSubject();
        try{
            currentUser.login(token);
        }
        catch (UnknownAccountException e1) {
            mv.addObject("error", "用户名不存在");
            return mv;
        }
        catch (IncorrectCredentialsException e2) {
            mv.addObject("error", "用户名或密码不正确");
            return mv;
        }
        catch (LockedAccountException e3) {
            mv.addObject("error", "用户被锁定");
            return mv;
        }
        catch (Exception e) {
            mv.addObject("error", "用户认证异常");
            return mv;
        }

        log.debug("user: " + currentUser.getPrincipal() + " login success!!!");
        Session session = currentUser.getSession();

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "LOGIN_NAME", loginName));
        List<CmsUser> usersList = cmsUserService.search(filters);
        if(usersList != null && usersList.size() > 0){
            session.setAttribute("user", usersList.get(0));
        }

        log.debug("end!!!");
        return new ModelAndView("redirect:" + adminPath + "/index.action");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout.action")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();

        log.debug("user: " + currentUser.getPrincipal() + " logout success!!!");
        log.debug("end!!!");
        return new ModelAndView("redirect:" + adminPath + "/login.action");
    }

    /**
     * 生产随机验证码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/random.action")
    public ModelAndView random(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");

        response.setContentType("image/jpeg");				//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");			//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } 
        catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

}
