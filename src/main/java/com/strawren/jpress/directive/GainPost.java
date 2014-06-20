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
import com.strawren.jpress.model.CmsPost;
import com.strawren.jpress.service.CmsPostService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 根据postId获取具体的Post
 * @author liukh
 *
 */
@Service("gainPostDirective")
public class GainPost implements TemplateDirectiveModel{
    private static final Log log = LogFactory.getLog(GainPost.class);

    @Autowired
    CmsPostService postService;

    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("gainPostDirective is begin ...");

        //postId
        TemplateModel postId = (TemplateModel) params.get("postId");
        if(postId == null){
            log.debug("postId is null ");
            return;
        }
        log.debug("postId >>" + String.valueOf(postId));
        CmsPost post = null;
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "ID", String.valueOf(postId)));
        List<CmsPost> postList = postService.search(filters);
        if(postList != null && postList.size() > 0){
            log.debug("postList size is > > " + postList.size());
            post = postList.get(0);
        }else{
            log.debug("postList is null...");
        }

        env.setVariable("post",DEFAULT_WRAPPER.wrap(post));
        body.render(env.getOut());

        log.debug("GainPost is begin ...");
    }

}
