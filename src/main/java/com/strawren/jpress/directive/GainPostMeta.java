package com.strawren.jpress.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.service.CmsPostMetaService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 获取postMeta
 * @author liukh
 *
 */
@Service("gainPostMetaDirective")
public class GainPostMeta implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainPostMeta.class);

    @Autowired
    CmsPostMetaService postMetaService;


    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("gainPostMetaDirective is begin ...");

        //postId
        TemplateModel postId = (TemplateModel) params.get("postId");
        //key
        TemplateModel postMetakey = (TemplateModel) params.get("key");
        if(postId == null || postMetakey == null){
            log.debug("postId or postMetakey is null ");
            return;
        }
        log.debug("postId >>" + String.valueOf(postId));
        log.debug("postMetakey >>" + String.valueOf(postMetakey));

        CmsPostMeta postMeta = null;
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", String.valueOf(postId)));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", String.valueOf(postMetakey)));

        List<CmsPostMeta> postMetaList = postMetaService.search(filters);
        if(postMetaList != null && postMetaList.size() > 0){
            log.debug("postMetaList size is > > " + postMetaList.size());
            postMeta = postMetaList.get(0);
            //查询指向的数据的类型
            filters = new ArrayList<PropertyFilter>();
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", String.valueOf(postId)));
            filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", JpressConstants.MENU_POINT_TYPE_KEY));
            List<CmsPostMeta> dataTypeList = postMetaService.search(filters);
            if(dataTypeList != null && dataTypeList.size() > 0){
                log.debug("dataTypeList size is > > " + dataTypeList.size());
                CmsPostMeta type = dataTypeList.get(0);
                log.debug("object type is > > " + type.getValue());
                env.setVariable("type",DEFAULT_WRAPPER.wrap(type.getValue()));
            }
        }else{
            log.debug("postMetaList is null...");
        }

        env.setVariable("postMeta",DEFAULT_WRAPPER.wrap(postMeta));
        body.render(env.getOut());

        log.debug("GainPost is begin ...");


    }

}
