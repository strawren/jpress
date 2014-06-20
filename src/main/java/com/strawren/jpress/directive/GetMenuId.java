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
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTermRelationship;
import com.strawren.jpress.service.CmsPostMetaService;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.service.CmsTermRelationshipService;
import com.strawren.jpress.service.CmsTermService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 根据别名获取菜单指向的具体Id
 * @author liukh
 *
 */
@Service("getMenuIdDirective")
public class GetMenuId implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainTerm.class);


    @Autowired
    CmsPostService postService;

    @Autowired
    CmsPostMetaService postMetaService;

    @Autowired
    CmsTermService termService;

    @Autowired
    CmsTermRelationshipService termRelationshipService;


    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("getMenuIdDirective is begin...");


        //菜单条别名
        TemplateModel menuBarSlug = (TemplateModel) params.get("menuBarSlug");
        //菜单别名
        TemplateModel menuslug = (TemplateModel) params.get("menuSlug");

        if(menuBarSlug == null || menuslug == null){
            log.debug("menuBarSlug or menuBarSlug is null...");
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
        //查询当前菜单条关联的菜单
        String tagetId = searchMenu(menuBarId,menuslug.toString());

        env.setVariable("tagetId",DEFAULT_WRAPPER.wrap(tagetId));
        body.render(env.getOut());

        log.debug("getMenuIdDirective is end!!");

    }




    /**
     * 获取指向的Id
     * @param menuBarId
     * @param menuslug
     * @return
     */
    public String searchMenu(Long menuBarId, String menuslug){

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
            menuList= postService.search(filters, orderList);
            if(menuList != null && menuList.size() > 0){
                for (CmsPost post : menuList) {
                    Long menuId = null;
                    if(menuslug.equals(post.getSlug())){
                        menuId = post.getId();
                        filters = new ArrayList<PropertyFilter>();
                        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", "target_menu_object_id"));
                        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "POST_ID", menuId.toString()));
                        List<CmsPostMeta> postMetaList =  postMetaService.search(filters);
                        if(postMetaList != null && postMetaList.size() > 0){
                            log.debug("postMetaList size is >> " + postMetaList.size());
                            log.debug("target id is >> " + postMetaList.get(0).getValue());
                            return postMetaList.get(0).getValue();
                        }
                    }
                }

            }
        }
        return null;

    }

}
