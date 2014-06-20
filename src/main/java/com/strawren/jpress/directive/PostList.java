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

import com.strawren.bsp.orm.query.Page;
import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.service.CmsPostService;
import com.strawren.jpress.util.FreemarkerParamUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


/**
 * 商品列表指令
 *
 * 入参:
 * termId 不能为空
 * pageNum 当前第几页,默认为1
 * numPerPage 每页条数,默认为8
 */
@Service("postListDirective")
public class PostList implements TemplateDirectiveModel {

    private static final Log log = LogFactory.getLog(PostList.class);

    @Autowired
    CmsPostService postService;

    /* (non-Javadoc)
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
     */
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopV, TemplateDirectiveBody body) throws TemplateException, IOException {
        log.debug("PostList Directive execute begin ...");

        Long termId = FreemarkerParamUtils.getLongParam(params,"termId");
        log.debug("get param termId:"+termId);

        Integer pageNum = FreemarkerParamUtils.getIntParam(params,"pageNum");
        log.debug("get param pageNum:"+pageNum);
        if (null==pageNum || pageNum<1) {
            pageNum = 1;
        }

        Integer numPerPage = FreemarkerParamUtils.getIntParam(params,"numPerPage");
        log.debug("get param numPerPage:"+numPerPage);
        if (null==numPerPage || numPerPage<1) {
            numPerPage = 8;
        }

        Page<CmsPostDTO> page = new Page<CmsPostDTO>(numPerPage);
        page.setPageNum(pageNum);

        if (termId != null && termId > 0) {
            postService.searchByTermId(termId,page);
            log.debug("searchByTermId total:"+page.getTotalCount());
        }else {
            page.setTotalCount(0);
        }

        env.setVariable("postPage",DEFAULT_WRAPPER.wrap(page));
        body.render(env.getOut());

        log.debug("PostList Directive execute end!");
    }

}
