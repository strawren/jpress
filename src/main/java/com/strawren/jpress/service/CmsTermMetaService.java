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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.jpress.core.JpressConstants;
import com.strawren.jpress.dao.CmsTermMetaMapper;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTerm;
import com.strawren.jpress.model.CmsTermMeta;

/**
 * 服务类，目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。
该表不为wordpress固有的，而是新增的。，对应表CMS_TERM_META
 */
@Service
@Transactional
public class CmsTermMetaService extends DefaultEntityService<CmsTermMeta, Long>{

    /** Mapper类，目录所拥有的属性，可以用来标志是分类本身还是分类的内容的属性。如果是内容，则对应于CMS_POSTMETA的配置项。
该表不为wordpress固有的，而是新增的。， */
    @Autowired
    CmsTermMetaMapper cmsTermMetaMapper;

    @Autowired
    CmsPostMetaService postMetaService;

    @Autowired
    CmsTermService termService;

    /** 数据存取方法 */
    @Override
    protected Dao<CmsTermMeta, Long> getDao() {
        return cmsTermMetaMapper;
    }

    /**
     * 修改目录属性或post属性
     * @param termMeta
     * @param postMeta
     */
    public void editTermMeta(CmsTermMeta termMeta, CmsPostMeta postMeta){
        update(termMeta);
        if(postMeta != null){
            postMetaService.update(postMeta);
        }
    }

    /**
     * 删除目录属性或post属性
     * @param termMetaIds
     */
    public void delTermMeta(String[] termMetaIds){
        for(int i=0;i<termMetaIds.length;i++){
            CmsTermMeta termMeta = get(Long.valueOf(termMetaIds[i]));
            if(JpressConstants.METE_TYPE_POST.equals(termMeta.getMetaType())){
                List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "STATUS", JpressConstants.DICT_GLOBAL_STATUS_VALIDATE));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "TERM_META_ID", termMeta.getId().toString()));
                filters.add(new PropertyFilter(MatchType.EQ, PropertyType.S, "KEY", termMeta.getKey()));
                List<CmsPostMeta> postMetaList = postMetaService.search(filters);
                if(postMetaList != null && postMetaList.size() > 0){
                    CmsPostMeta postMeta = postMetaService.search(filters).get(0);
                    postMetaService.delete(postMeta.getId());
                    postMeta.setStatus(JpressConstants.DICT_GLOBAL_STATUS_INVLIDATE);
//                  ModelInfoUtils.updateModelInfoBySys(postMeta);
//                  postMetaService.update(postMeta);
            }
        }
            delete(Long.valueOf(termMetaIds[i]));
    }
    }

    /**
     * 标签编辑
     * @param tag
     * @param termMeta
     * @param ImgPostId
     */
    public void editTag(CmsTerm tag, CmsTermMeta termMeta, String ImgPostId){
        //修改标签
        termService.update(tag);
        if(StringUtils.isNotBlank(ImgPostId)){
            getDao().update(termMeta);
        }else{
            //删除原来的
            cmsTermMetaMapper.delImgPost(tag.getId());
        }
    }


    /**
     * 根据termId 获取属性最大排序号码
     * @param termId
     * @return
     */
    public long getMaxOrderNo(Long termId){
        return cmsTermMetaMapper.getMaxOerderNo(termId);
    }





}