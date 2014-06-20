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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.CmsOptionMapper;
import com.strawren.jpress.model.CmsOption;

/**
 * 服务类，内容管理平台的属性配置，对应表CMS_OPTION
 */
@Service
@Transactional
public class CmsOptionService extends DefaultEntityService<CmsOption, Long>{

    /** Mapper类，内容管理平台的属性配置， */
    @Autowired
    CmsOptionMapper cmsOptionMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsOption, Long> getDao() {
        return cmsOptionMapper;
    }

    /**
     * 获取当前使用的模板
     * @return
     */
    public String getTemplateName() {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "CODE", JpressConstants.OPTIONS_KEY_SITE_THEME_CODE));

        List<CmsOption> search = search(filters);
        if (CollectionUtils.isNotEmpty(search)) {
            return search.get(0).getValue();
        }
        else {
            return "default";
        }
    }

    /**
     * 获取所有有效的配置项
     * @return
     */
    public List<CmsOption> searchAll() {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));

        return search(filters);
    }

    /**
     * 根据key获取系统配置项
     * @param key
     * @return
     */
    public CmsOption getConfigByKey(String key) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "CODE", key));

        List<CmsOption> options = search(filters);
        if (CollectionUtils.isNotEmpty(options)) {
            return options.get(0);
        }
        return null;
    }
}