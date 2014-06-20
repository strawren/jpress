/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月2日 下午2:14:43
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月2日        Initailized
 */

package com.strawren.jpress.web.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.strawren.bsp.web.action.BaseMultiActionController;


/**
 * 评论相关功能
 *
 */
@Controller
@RequestMapping("${adminPath}/comment")
public class CommentController extends BaseMultiActionController {

    /**
     * 所有评论
     * @return
     */
    @RequestMapping("/all.action")
    public ModelAndView allComment() {
        log.debug("/cms/comment/allComment begin...");

        log.debug("/cms/comment/allComment end!!");
        return new ModelAndView("/cms/comment/all");
    }

}
