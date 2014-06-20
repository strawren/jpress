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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.dao.CmsPostMetaMapper;
import com.strawren.jpress.model.CmsPostMeta;

/**
 * 服务类，内容的属性，对应表CMS_POST_META
 */
@Service
@Transactional
public class CmsPostMetaService extends DefaultEntityService<CmsPostMeta, Long>{

    /** Mapper类，内容的属性， */
    @Autowired
    CmsPostMetaMapper cmsPostMetaMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsPostMeta, Long> getDao() {
        return cmsPostMetaMapper;
    }

    /**
     * 删除postMeta
     * @param param
     */
    public void delByTermMetaIdAndKey(Long postId){
        cmsPostMetaMapper.delByTermMetaIdAndKey(postId);
    }

    /**
     * 根据postId ,key删除内容特色图像关联
     * @param PostId
     */
    public void delPostImgMeta(Long postId){
        cmsPostMetaMapper.delPostImgMeta(postId);
    }

    /**
     * 根据postId获取meta
     * @param id
     * @return
     */
    public Map<String, CmsPostMeta> getMetasByPostId(Long id) {
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.EQ, PropertyType.L, "POST_ID", id+""));
        List<CmsPostMeta> metas = search(filters);

        Map<String, CmsPostMeta>  metaMap = new HashMap<String, CmsPostMeta>();

        for (CmsPostMeta cmsPostMeta : metas) {
            metaMap.put(cmsPostMeta.getKey(), cmsPostMeta);
        }

        return metaMap;
    }

    /**
     * 根据postId 获取属性最大排序号码
     * @param termId
     * @return
     */
    public long getMaxOrderNo(Long postId){
        return cmsPostMetaMapper.getMaxOerderNo(postId);
    }

}