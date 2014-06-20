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
package com.strawren.jpress.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.jpress.dto.CmsLinkDTO;
import com.strawren.jpress.model.CmsLink;

/**
 * Mapper类，链接，对应表CMS_LINK
 */
@BspMapper
public interface CmsLinkMapper extends Dao<CmsLink, Long>{

    /**
     * 查询所有链接
     * @param name
     * @return
     */
    List<CmsLinkDTO> findAllLink(String name);

    /**
     * 根据ID查找链接
     * @param id
     * @return
     */
    CmsLinkDTO findLinkById(Long id);

    /**
     * 获取链接总数
     * @param criterion
     * @return
     */
    long countLink(BspCriterion criterion);

    /**
     * 分页查询链接
     * @param criterion
     * @param rowBounds
     * @return
     */
    List<CmsLinkDTO> searchLink(BspCriterion criterion, RowBounds rowBounds);
}