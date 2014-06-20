/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpress
 * $Id$
 * $Revision$
 * Last Changed by admin at 2014年4月29日 上午10:32:43
 * $URL$
 *
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * xieming     2014年4月29日        Initailized
 */

package com.strawren.jpress.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strawren.jpress.dto.CmsPostDTO;
import com.strawren.jpress.model.CmsLink;
import com.strawren.jpress.model.CmsOption;
import com.strawren.jpress.model.CmsPostMeta;
import com.strawren.jpress.model.CmsTermMeta;


/**
 * 获取post元数据工具类.
 *
 */
@Service("postMetaTool")
public class PostMetaService {

    @Autowired
    CmsPostService postService;
    @Autowired
    CmsOptionService configService;
    @Autowired
    CmsLinkService linkService;

    /**
     * 根据key获取value
     * @param post
     * @param key
     * @return
     */
    public String getMetaValue(CmsPostDTO post,String key){
        Map<String, CmsPostMeta> metaMap = post.getMetaMap();

        CmsPostMeta meta = metaMap.get(key);
        if (meta == null) {
            return null;
        }
        return meta.getValue();
    }

    /**
     * 获取图片URL
     * @param post
     * @param key
     * @return
     */
    public String getMediaUrl(CmsPostDTO post,String key){
        Map<String, CmsPostMeta> metaMap = post.getMetaMap();

        CmsPostMeta meta = metaMap.get(key);
        if (meta == null) {
            return null;
        }

        Long medaId = Long.parseLong(meta.getValue());
        return postService.getMediaUrlById(medaId);
    }


    /**
     * 根据图片目录以及index获取图片路径
     * @param post
     * @param itemNoKey
     * @param pathKey
     * @param index
     * @return
     */
    public String getImageUrlByIndex(CmsPostDTO post,String itemNoKey,String pathKey,String index){
        Map<String, CmsPostMeta> metaMap = post.getMetaMap();

        String imageDirPath = "";
        CmsPostMeta imageDirPathMeta = metaMap.get(pathKey);
        if (imageDirPathMeta!=null) {
            imageDirPath = imageDirPathMeta.getValue();
        }

        String[] images = imageDirPath.split(",");
        for (int i = 0; i < images.length; i++) {
            String image = images[i];
            if (image.contains("_"+index+".")) {
                return image;
            }
        }

        return "";
    }

    /**
     * 将购物车所有key转化为string,以逗号分隔
     * @param items
     * @return
     */
    public String itemKeyToString(Map<Long, Object> items) {
        if (MapUtils.isEmpty(items)) {
            return "";
        }

        StringBuilder keyBuilder = new StringBuilder();
        for (Long itemId : items.keySet()) {
            keyBuilder.append(itemId);
            keyBuilder.append(",");
        }
        String keys = keyBuilder.toString();
        keys = keys.substring(0, keys.length()-1);
        return keys;
    }

    /**
     * 根据postId获取特色图片
     * @param medaId
     * @return
     */
    public String getImageUrl(CmsTermMeta meta){
        String value = meta.getValue();
        Long id = Long.valueOf(value);
        return postService.getMediaUrlById(id);
    }


    /**
     * 获取系统配置参数
     * @param key
     * @return
     */
    public String getSysConfig(String key){
        CmsOption option = configService.getConfigByKey(key);
        if (option!=null) {
            return option.getValue();
        }
        return null;
    }

    /**
     * 根据别名获取链接地址
     * @param key
     * @return
     */
    public String getLinkUrlByAlias(String alias){
         CmsLink link = linkService.getLinkUrlByAlias(alias);

         if (link!=null) {
            return link.getUrl();
         }

         return null;
    }
}
