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
import com.strawren.jpress.dao.CmsCommentMetaMapper;
import com.strawren.jpress.model.CmsCommentMeta;

/**
 * 服务类，评论的元数据或者属性，对应表CMS_COMMENT_META
 */
@Service
@Transactional
public class CmsCommentMetaService extends DefaultEntityService<CmsCommentMeta, Long>{

    /** Mapper类，评论的元数据或者属性， */
    @Autowired
    CmsCommentMetaMapper cmsCommentMetaMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsCommentMeta, Long> getDao() {
        return cmsCommentMetaMapper;
    }
}