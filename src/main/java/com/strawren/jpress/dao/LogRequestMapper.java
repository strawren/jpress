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
import java.util.Map;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.jpress.model.LogRequest;

/**
 * Mapper类，对应表LOG_REQUEST
 */
@BspMapper
public interface LogRequestMapper extends Dao<LogRequest, Long>{

    /**
     * 按分类统计
     * @param criterion
     * @return
     */
    List<Map<String, String>> countByTerm(BspCriterion criterion);

    /**
     * 按商品编号统计
     * @param criterion
     * @return
     */
    List<Map<String, String>> countByItem(BspCriterion criterion);

    /**
     * 按用户统计
     * @param criterion
     * @return
     */
    List<Map<String, String>> countByUser(BspCriterion criterion);
}