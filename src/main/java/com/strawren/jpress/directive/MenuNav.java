package com.strawren.jpress.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.Order;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsTermRelationshipService;
import com.strawren.jpress.service.CmsTermService;
import com.strawren.jpress.service.TermRelevanceTaxonomyService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 *  获取菜单导航
 * @author liukh
 *
 */
@Service("menuNavDirective")
public class MenuNav implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(MenuNav.class);


    @Autowired
    TermRelevanceTaxonomyService termRelevanceTaxonomyService;

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsPostService postService;

    @Autowired
    CmsPostMetaService postMetaService;

    @Autowired
    CmsTermRelationshipService termRelationshipService;

    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("MenuNav execute begin ...");

        //菜单条别名
        TemplateModel menuBarSlug = (TemplateModel) params.get("menuBarSlug");
        //菜单别名
        TemplateModel menuslug = (TemplateModel) params.get("menuSlug");
//        //查询菜单个数
//        TemplateModel rownNum = (TemplateModel) params.get("menuNum");

        if(menuBarSlug == null){
            log.debug("menuBarSlug is null...");
            return;
        }

        //根据别名获取当前菜单条id
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("slug", menuBarSlug.toString());
        param.put("taxonomy", "nav");
        Long menuBarId = termService.getMenuBarBySlug(param);
        log.debug("menuBarId is > > " + menuBarId);
        if(menuBarId == null){
            return;
        }
        Long parentId = 0L;
        //根据菜单别名获取菜单id
        if(menuslug != null){
            parentId = getMenuIdBySlug(menuslug.toString());
        }

        //查询当前菜单条关联的菜单
        List<CmsPost> menuList = searchMenu(menuBarId, parentId);

        env.setVariable("menuList",DEFAULT_WRAPPER.wrap(menuList));
        body.render(env.getOut());

    }


    /**
     * 根据Relationship表中的postId获取菜单
     * @param menuBarId
     * @return
     */
    public List<CmsPost> searchMenu(Long menuBarId, Long parentId){

        //菜单和菜单树关联查询
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_TAXONOMY_ID", menuBarId.toString()));
        List<CmsTermRelationship> relationshipList = termRelationshipService.search(filters);

        List<CmsPost> menuList = null;
        //获取菜单
        if(relationshipList != null && relationshipList.size() > 0){
            log.debug("relationshipList size is >> " + relationshipList.size());
            StringBuilder relationIds = new StringBuilder();
            for(CmsTermRelationship relation : relationshipList){
                relationIds.append(relation.getObjectId()).append(",");
            }
            log.debug("relationIds is >> " + relationIds);
            List<Order> orderList = new ArrayList<Order>();
            Order order = new Order();
            order.setOrderBy("MENU_ORDER");
            order.setOrder("asc");
            orderList.add(order);
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            filters.add(new PropertyFilter(MatchType.IN, PropertyType.A, "ID", relationIds.toString().substring(0, relationIds.toString().length() - 1)));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "PARENT_ID", parentId.toString()));
            menuList= postService.search(filters, orderList);
        }
        return menuList;
    }

    /**
     * 根据别名获取postId
     * @param slug
     * @return
     */
    public Long getMenuIdBySlug(String slug){
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "SLUG", slug));
        List<CmsPost> postList = postService.search(filters);
        if(postList != null && postList.size() > 0){
            return postList.get(0).getId();
        }
        return 0L;

    }


}
