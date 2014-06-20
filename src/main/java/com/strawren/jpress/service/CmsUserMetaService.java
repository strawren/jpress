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
import com.strawren.jpress.dao.CmsUserMetaMapper;
import com.strawren.jpress.model.CmsUserMeta;

/**
 * 服务类，用户的属性，对应表CMS_USER_META
 */
@Service
@Transactional
public class CmsUserMetaService extends DefaultEntityService<CmsUserMeta, Long>{

    /** Mapper类，用户的属性， */
    @Autowired
    CmsUserMetaMapper cmsUserMetaMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsUserMeta, Long> getDao() {
        return cmsUserMetaMapper;
    }
}