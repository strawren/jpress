/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月23日 下午4:57:06
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月23日        Initailized
 */

package com.strawren.jpress.directive;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.util.FreemarkerParamUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 商品详情指令
 *
 * 入参:
 * postId 不能为空
 */
@Service("postDetailDirective")
public class PostDetail implements TemplateDirectiveModel {

    private static final Log log = LogFactory.getLog(PostDetail.class);

    @Autowired
    CmsPostService postService;

    /* (non-Javadoc)
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
     */
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("PostDetail Directive execute begin ...");

        Long postId = FreemarkerParamUtils.getLongParam(params,"postId");
        log.debug("get param postId:"+postId);

        CmsPostDTO postDetail = new CmsPostDTO();
        if (postId != null && postId > 0) {
            postDetail = postService.getDetailById(postId);
        }

        env.setVariable("post",DEFAULT_WRAPPER.wrap(postDetail));
        body.render(env.getOut());

        log.debug("PostDetail Directive execute end!");
    }

}
