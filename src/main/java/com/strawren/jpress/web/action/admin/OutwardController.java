/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by gaowm at 2014-4-9 上午7:21:39
 * $URL$
 *
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * gaowm 2014-4-9 Initailized
 */

package com.strawren.jpress.web.action.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.strawren.bsp.util.BeanExtUtils;
import com.strawren.bsp.util.FileUtils;
import com.strawren.bsp.util.NumberUtils;
import com.strawren.bsp.util.SystemPropsUtils;
import com.strawren.bsp.web.action.BaseMultiActionController;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dto.MenuPostDTO;
import com.strawren.jpress.dto.TermOrTagDTO;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.model.CmsTermTaxonomy;
import com.strawren.jpress.service.CmsOptionService;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsTermRelationshipService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.service.CmsTermTaxonomyService;
import com.strawren.jpress.service.MenuProcessService;
import com.strawren.jpress.util.ModelInfoUtils;

/**
 * 菜单操作
 *
 */
@Controller
@RequestMapping("${adminPath}/outward")
public class OutwardController extends BaseMultiActionController {
    @Autowired
    CmsTermService cmsTermService;

    @Autowired
    CmsTermTaxonomyService cmsTermTaxonomyService;

    @Autowired
    CmsPostService cmsPostService;

    @Autowired
    CmsPostMetaService cmsPostMetaService;

    @Autowired
    CmsTermRelationshipService cmsTermRelationshipService;
    
    @Autowired
    CmsOptionService cmsOptionService;
    
    @Autowired
    MenuProcessService menuProcessService;
    
    /**主题文件临时id，用于文件树*/
    private static long fileTempId = 1;
    
    /**
     * 展示菜单首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/menu.action")
    public ModelAndView showMenu(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/outward/menu");
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_NAV_MENU));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<Order> orderFilters = new ArrayList<Order>();
        orderFilters.add(new Order("ID", Page.DESC));
        List<CmsTermTaxonomy> termTaxonomysList = cmsTermTaxonomyService.search(filters, orderFilters);
        
        List<CmsTerm> termsList = new ArrayList<CmsTerm>();
        for(CmsTermTaxonomy taxonomy : termTaxonomysList){
            termsList.add(cmsTermService.get(taxonomy.getTermId()));
        }
        mv.addObject("menuList", termsList);

        //左边页面树
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_PAGE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> postsList = cmsPostService.search(filters);
        JSONArray postsArrList = JSONArray.fromObject(postsList);
        mv.addObject("postsArrList", postsArrList);

        //左边的分类目录或标签
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_CATEGORY + "' or TAXONOMY='" + JpressConstants.DICT_TERM_TAXONOMY_TAG));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTermTaxonomy> termTaxonomiesList = cmsTermTaxonomyService.search(filters);
        
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        termsList = cmsTermService.search(filters);
        
        List<TermOrTagDTO> termOrTagsList = new ArrayList<TermOrTagDTO>(10);
        int termsCount = 0;
        TermOrTagDTO termOrTagDTO = null;
        outer:
        for(int i=0; i<termTaxonomiesList.size(); i++){
            for(int j=0; j<termsList.size(); j++){
                if(termTaxonomiesList.get(i).getTermId().equals(termsList.get(j).getId())){
                    termOrTagDTO = new TermOrTagDTO();
                    try {
                        BeanExtUtils.copyProperties(termOrTagDTO, termsList.get(j));
                    }
                    catch (Exception e) {
                        log.warn("bean properties copy error!!!", e);
                    }
                    termOrTagDTO.setDataType(termTaxonomiesList.get(i).getTaxonomy());
                    termOrTagsList.add(termOrTagDTO);
                    ++termsCount;
                    if(termsCount >= 10){
                        break outer;
                    }
                }
            }
        }
        log.debug("termOrTagsList: " + termOrTagsList);
        
        mv.addObject("termOrTagsList", termOrTagsList);
        
        //删除菜单成功后刷新页面的提示
        String succMsg = request.getParameter("alertSuccMsg");
        log.debug("succMsg:" + succMsg);
        if(StringUtils.isNotBlank(succMsg)){
            mv.addObject("succMsg", succMsg);
        }
        
        //创建的新菜单的名称
        String newMenuName = request.getParameter("newMenuName");
        if(StringUtils.isNotBlank(newMenuName)){
            mv.addObject("newMenuName", newMenuName);
        }
        
        log.debug("end!!!");
        return mv;
    }

    /**
     * 展示编辑主题页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/theme.action")
    public ModelAndView showTheme(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/outward/theme");
        
        String basePath = SystemPropsUtils.getString(JpressConstants.OPTIONS_KEY_SITE_TEMPLATE_URL);
        File file = new File(basePath);
        if(!file.isDirectory()){
            log.warn(JpressConstants.OPTIONS_KEY_SITE_TEMPLATE_URL + ": config template path not a directory!!!");
        }
        
        String currTemplateName = cmsOptionService.getTemplateName();
        mv.addObject("currTemplateName", currTemplateName);
        
        File[] filesList = file.listFiles();
        List<String> fileNameList = new ArrayList<String>();
        String filePath = "";
        for(File tempFile : filesList){
            if(tempFile.isDirectory() && !".svn".equals(tempFile.getName())){
                fileNameList.add(tempFile.getName());
                if(StringUtils.isNotBlank(currTemplateName) && currTemplateName.equals(tempFile.getName())){
                    filePath = SystemPropsUtils.getString(JpressConstants.OPTIONS_KEY_SITE_TEMPLATE_URL) + System.getProperty("file.separator") + currTemplateName;
                }
            }
        }
        
        if(fileNameList.size() > 0){
            mv.addObject("themeList", fileNameList);
        }
        
        if(StringUtils.isNotBlank(filePath)){
            File templateFile = new File(filePath);
            if(!file.isDirectory()){
                log.warn("config template path not a directory!!!");
            }
            
            List<Map<String, String>> themeFileList = new ArrayList<Map<String, String>>();
            getFile(templateFile, filePath, "0", themeFileList);
            log.debug("themeFileList: " + themeFileList.size());

            JSONArray themeFileArray = JSONArray.fromObject(themeFileList);
            mv.addObject("themeFileArray", themeFileArray);
        }
        
        log.debug("end!!!");
        return mv;
    }
    
    private void getFile(File file, String filePath, String parentId, List<Map<String, String>> themeFileList){
        Map<String, String> fileParamMap = null;
        String fileId = String.valueOf(fileTempId);
        ++fileTempId;
        
        if(file.isFile() && (file.getName().endsWith(".ftl") || file.getName().endsWith(".css"))){
            fileParamMap = new HashMap<String, String>();
            fileParamMap.put("id", fileId);
            fileParamMap.put("parentId", parentId);
            fileParamMap.put("fileName", file.getName());
            fileParamMap.put("fileUrl", filePath);
            fileParamMap.put("fileType", "file");
            themeFileList.add(fileParamMap);
        }
        else if(file.isDirectory() && !file.getName().equals(".svn")){
            fileParamMap = new HashMap<String, String>();
            fileParamMap.put("id", fileId);
            fileParamMap.put("parentId", parentId);
            fileParamMap.put("fileName", file.getName());
            fileParamMap.put("fileUrl", filePath);
            fileParamMap.put("fileType", "directory");
            themeFileList.add(fileParamMap);
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                String childFilePath = filePath + System.getProperty("file.separator") + files[i].getName();
                getFile(files[i], childFilePath, fileId, themeFileList);
            }
        }
        else{
            //log.warn(file.getName() + " is what???");
        }
    }
    
    /**
     * 编辑模板文件
     * @param request
     * @param response
     */
    @RequestMapping("/editTemplateFile.action")
    @ResponseBody
    public Map<String, String> editTemplateFile(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("result", "fail");
        
        String fileUrl = request.getParameter("fileUrl");
        String fileName = request.getParameter("fileName");
        String content = request.getParameter("fileContent");
        log.debug("fileUrl:" + fileUrl + ", fileName:" + fileName + ", content: [" + content + "]");
        
        List<String> fileStrList = null;
        StringBuilder fileStringBuilder = new StringBuilder();
        try {
            fileStrList = FileUtils.readLines(new File(fileUrl), "UTF-8");
        }
        catch (IOException e1) {
            log.debug(fileUrl + ", IOException!!!");
            resMap.put("descMsg", "文件读取异常");
            return resMap;
        }
        
        for(String str : fileStrList){
            fileStringBuilder.append(str).append("\r\n");
        }
        
        resMap.put("result", fileStringBuilder.toString());
        
        log.debug("end!!!");
        return resMap;
    }
    
    /***
     * 保存模板文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/updateTemplateFile.action")
    @ResponseBody
    public Map<String, String> updateTemplateFile(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");
        
        String fileUrl = request.getParameter("fileUrl");
        String fileContent = request.getParameter("fileContent");
        log.debug("fileUrl: " + fileUrl + ", fileContent length: " + fileContent.length());
        
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("result", "success");
        
        File file = new File(fileUrl);
        if(file.isDirectory()){
            resMap.put("result", "fail");
        }
        
        try {
            FileUtils.writeFileByEncoding(file, fileContent, "UTF-8");
        }
        catch (IOException e1) {
            log.warn("file: " + fileUrl + "write exception!!!", e1);
            resMap.put("result", "fail");
            return resMap;
        }
        
        log.debug("end!!!");
        return resMap;
    }

    /**
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/static.action")
    public ModelAndView showStatic(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("begin...");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/cms/outward/static");

        log.debug("end!!!");
        return mv;
    }

    /**
     * 创建新菜单
     * @param request
     * @param response
     */
    @RequestMapping("/createNewMenu.action")
    @ResponseBody
    public Map<String, String> createNewMenu(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        Map<String, String> resultMap = new HashMap<String, String>();

        String newMenuName = request.getParameter("newMenuName");  //新菜单名
        String newMenuSlugName = request.getParameter("newMenuSlugName"); //新菜单别名
        String newMenuDesc = request.getParameter("newMenuDesc");
        log.debug("newMenuName: " + newMenuName + ",newMenuSlugName: " + newMenuSlugName + ",newMenuDesc: " + newMenuDesc);

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "NAME", newMenuName));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTerm> termsList = cmsTermService.search(filters);
        if(termsList != null && termsList.size() > 0){
            resultMap.put("tipMsg", "菜单名已存在");
            log.debug("menu name already exist!!!");
            return resultMap;
        }

        CmsTerm term = new CmsTerm();
        term.setName(newMenuName);
        term.setSlug(newMenuSlugName);
        term.setMiscDesc(newMenuDesc);
        term.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(term);
        cmsTermService.save(term);

        CmsTermTaxonomy termTaxonomy = new CmsTermTaxonomy();
        termTaxonomy.setTermId(term.getId());
        termTaxonomy.setTaxonomy(JpressConstants.DICT_TERM_TAXONOMY_NAV_MENU);
        termTaxonomy.setParentId(0l);
        termTaxonomy.setPostCount(0);
        termTaxonomy.setStatus(Constants.DICT_GLOBAL_STATUS_VALIDATE);
        ModelInfoUtils.createModelInfoBySys(termTaxonomy);
        cmsTermTaxonomyService.save(termTaxonomy);

        log.debug("end!!!");
        return resultMap;
    }

    /**
     * 菜单保存
     * @param request
     * @param response
     */
    @RequestMapping("/saveMenu.action")
    @ResponseBody
    public Map<String, String> saveMenu(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        Map<String, String> returnMap = new HashMap<String, String>();

        //编辑的菜单id
        String editMenuId = request.getParameter("editMenuId");

        //获取菜单树json数组参数
        String nodesArr = request.getParameter("nodesArr");
        JSONArray menuDataJsonArray = JSONArray.fromObject(nodesArr);
        log.debug("nodesArr: " + menuDataJsonArray.toString());

        try {
            menuProcessService.saveMenu(editMenuId, menuDataJsonArray);
        }
        catch (Exception e) {
            log.warn(e);
            returnMap.put("failMsg", "菜单查询失败");
        }

        log.debug("end!!!");
        return returnMap;
    }

    /**
     * 展示选择编辑的菜单树
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/showEditMenuTree.action")
    @ResponseBody
    public List<MenuPostDTO> showEditMenuTree(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        List<MenuPostDTO> postsList = new ArrayList<MenuPostDTO>();

        String editMenuId = request.getParameter("editMenuId");
        log.debug("editMenuId: " + editMenuId);
        //根据id查询菜单分类
        CmsTerm cmsTerm = cmsTermService.get(NumberUtils.StringToLong(editMenuId));
        if(cmsTerm == null){
            log.warn("menu id is not exist!!!");
            return postsList;
        }
        
        //根据菜单分类id查询分类方法
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_NAV_MENU));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_ID", cmsTerm.getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTermTaxonomy> cmsTermTaxonomysList = cmsTermTaxonomyService.search(filters);
        if(cmsTermTaxonomysList == null || cmsTermTaxonomysList.size() == 0){
            log.warn("menu taxonomy is not exist!!!");
            return postsList;
        }
        
        //根据分类方法id查询分类关联的菜单post列表
        filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "TERM_TAXONOMY_ID", cmsTermTaxonomysList.get(0).getId().toString()));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<Order> orderFilters =  new ArrayList<Order>();
        orderFilters.add(new Order("TERM_ORDER", Page.ASC));
        List<CmsTermRelationship> cmsTermRelationshipsList = cmsTermRelationshipService.search(filters, orderFilters);
        if(cmsTermRelationshipsList == null || cmsTermRelationshipsList.size() == 0){
            log.warn("menu taxonomyRelationship is not exist!!!");
            return postsList;
        }

        //处理每个菜单post
        for(int i=0; i<cmsTermRelationshipsList.size(); i++){
            CmsPost cmsPost = cmsPostService.get(cmsTermRelationshipsList.get(i).getObjectId());
            MenuPostDTO menuPostDTO = new MenuPostDTO();
            try {
                BeanUtils.copyProperties(menuPostDTO, cmsPost);
            }
            catch (Exception e) {
                log.warn("object properties copy error!!!", e);
            }
            
            //菜单父id
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", cmsPost.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.POST_META_TAR_MENU_PARENT_ID));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
            List<CmsPostMeta> cmsPostMetasList = cmsPostMetaService.search(filters);
            menuPostDTO.setMenuParentId(NumberUtils.StringToLong(cmsPostMetasList.get(0).getValue()));
            
            //菜单目标对象数据类型
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", cmsPost.getId().toString()));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.MENU_POINT_TYPE_KEY));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
            cmsPostMetasList = cmsPostMetaService.search(filters);
            menuPostDTO.setTarObjType(cmsPostMetasList.get(0).getValue());
            
            postsList.add(menuPostDTO);
        }

        log.debug("end!!!");
        return postsList;
    }
    
    /**
     * 增加链接
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addLinkToMenu.action")
    @ResponseBody
    public Map<String, String> addLinkToMenu(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");
        
        String linkName = request.getParameter("linkName");
        String linkUrl = request.getParameter("linkUrl");
        String editMenuId = request.getParameter("editMenuId");
        log.debug("linkName:" + linkName + ", linkUrl:" + linkUrl + ", editMenuId:" + editMenuId);
        
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("result", "success");
        
        try {
            menuProcessService.saveLinkToMenu(linkName, linkUrl, editMenuId);
        }
        catch (Exception e) {
            log.warn(e);
            resMap.put("result", "fail");
        }
        
        log.debug("end!!!");
        return resMap;
    }
    
    /**
     * 增加分类或标签到菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addTermOrTagToMenu.action")
    @ResponseBody
    public Map<String, String> addTermOrTagToMenu(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");
        
        String termOrTagId = request.getParameter("termOrTagId");
        String editMenuId = request.getParameter("editMenuId");
        String termOrTagName = request.getParameter("termOrTagName");
        String dataType = request.getParameter("dataType");
        String slug = request.getParameter("slug");
        log.debug("termOrTagId:" + termOrTagId + ", editMenuId:" + editMenuId + ", termOrTagName:" + termOrTagName + ", dataType:" + dataType + ", slug:" + slug);
        
        Map<String, String> resMap = new HashMap<String, String>();
        resMap.put("result", "success");
        
        try {
            menuProcessService.saveTermOrTagToMenu(termOrTagId, termOrTagName, editMenuId, slug, dataType);
        }
        catch (Exception e) {
            log.warn(e);
            resMap.put("result", "fail");
        }
        
        log.debug("end!!!");
        return resMap;
    }
    
    /**
     * 查询分类或标签（根据输入的名称）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getTermOrTagByName.action")
    @ResponseBody
    public List<TermOrTagDTO> getTermOrTagByName(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");
        
        String inputTermOrTagName = request.getParameter("inputTermOrTagName");
        log.debug("inputTermOrTagName:" + inputTermOrTagName);
        
        //根据名称查询term或tag
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.LIKE, PropertyType.S, "NAME", inputTermOrTagName));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsTerm> termsList = cmsTermService.search(filters);
        
        if(termsList == null || termsList.size() == 0){
            log.debug("no term like " + inputTermOrTagName + " found!!!");
            return null;
        }
        
        List<TermOrTagDTO> cmsTermOrTagsList = new ArrayList<TermOrTagDTO>(10);
        int termsCount = 0;
        TermOrTagDTO termOrTagDTO = null;
        for(int i=0; i<termsList.size(); i++){
            CmsTermTaxonomy cmsTermTaxonomy = cmsTermTaxonomyService.getBydTermId(termsList.get(i).getId());
            if(cmsTermTaxonomy != null && Constants.DICT_GLOBAL_STATUS_VALIDATE.equals(cmsTermTaxonomy.getStatus()) 
                    && (JpressConstants.DICT_TERM_TAXONOMY_CATEGORY.equals(cmsTermTaxonomy.getTaxonomy()) 
                        || JpressConstants.DICT_TERM_TAXONOMY_TAG.equals(cmsTermTaxonomy.getTaxonomy()))){
                termOrTagDTO = new TermOrTagDTO();
                try {
                    BeanUtils.copyProperties(termOrTagDTO, termsList.get(i));
                }
                catch (Exception e) {
                    log.warn("bean copy properties error!!!", e);
                    e.printStackTrace();
                }
                termOrTagDTO.setDataType(cmsTermTaxonomy.getTaxonomy());
                cmsTermOrTagsList.add(termOrTagDTO); 
               ++termsCount;
               if(termsCount >= 10){
                   break;
               }
            }
        }
        
        log.debug("cmsTermOrTagsList: " + cmsTermOrTagsList);
        log.debug("end!!!");
        return cmsTermOrTagsList;
    }

    /**
     * 删除菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/delMenu.action")
    @ResponseBody
    public Map<String, String> delMenu(HttpServletRequest request, HttpServletResponse response){
        log.debug("begin...");
        
        Map<String, String> resmMap = new HashMap<String, String>();
        resmMap.put("result", "success");
        
        String menuId = request.getParameter("menuId");
        String menuName = request.getParameter("menuName");
        log.debug("menuId:" + menuId + ", menuName:" + menuName);
        
        try {
            menuProcessService.delMenuByIdAndName(menuId, menuName);
        }
        catch (Exception e) {
            log.warn(e);
            resmMap.put("result", "未找到菜单" + menuName + ", id为:" + menuId);
        }
        
        log.debug("end!!!");
        return resmMap;
    }
    
    @RequestMapping("/getAllPagePost.action")
    @ResponseBody
    public List<CmsPost> getAllPagePost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_PAGE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> postsList = cmsPostService.search(filters);
        JSONArray.fromObject(postsList);

        log.debug("end!!!");
        return postsList;
    }


    @RequestMapping("/getMenuTreeById.action")
    @ResponseBody
    public List<CmsPost> getMenuTreeById(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin...");

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_TYPE", JpressConstants.DICT_POST_TYPE_PAGE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", Constants.DICT_GLOBAL_STATUS_VALIDATE));
        List<CmsPost> postsList = cmsPostService.search(filters);
        JSONArray.fromObject(postsList);

        log.debug("end!!!");
        return postsList;
    }
    
    public static void main(String[] args) {
        PropertyFilter filter = new PropertyFilter(MatchType.EQ, PropertyType.S, "TAXONOMY", JpressConstants.DICT_TERM_TAXONOMY_CATEGORY + "' or TAXONOMY='" + JpressConstants.DICT_TERM_TAXONOMY_TAG);
        System.out.println(filter.getSqlString());
    }
}
