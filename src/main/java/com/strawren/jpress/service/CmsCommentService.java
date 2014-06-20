/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by fuhy at 2014-4-2 上午10:02:22
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * fuhy     2014-4-2        Initailized
 */
package com.strawren.jpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.dao.CmsCommentMapper;
import com.strawren.jpress.model.CmsComment;

/**
 * 服务类，评论，对应表CMS_COMMENT
 */
@Service
@Transactional
public class CmsCommentService extends DefaultEntityService<CmsComment, Long>{

    /** Mapper类，评论， */
    @Autowired
    CmsCommentMapper cmsCommentMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsComment, Long> getDao() {
        return cmsCommentMapper;
    }
}