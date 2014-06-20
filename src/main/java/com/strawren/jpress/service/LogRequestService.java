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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.orm.query.BspCriterion;
import com.strawren.bsp.orm.query.MatchType;
import com.strawren.bsp.orm.query.PropertyFilter;
import com.strawren.bsp.orm.query.PropertyType;
import com.strawren.bsp.service.DefaultEntityService;
import com.strawren.bsp.util.DateTimeUtils;
import com.strawren.jpress.dao.LogRequestMapper;
import com.strawren.jpress.model.LogRequest;

/**
 * 服务类，对应表LOG_REQUEST
 */
@Service
@Transactional
public class LogRequestService extends DefaultEntityService<LogRequest, Long>{

    /** Mapper类， */
    @Autowired
    LogRequestMapper logRequestMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<LogRequest, Long> getDao() {
        return logRequestMapper;
    }

    /**
     * 按分类统计
     * @param preDate
     * @param curDate
     * @return
     */
    public List<Map<String, String>> countByTerm(String preDate, String curDate) {
        BspCriterion criterion = buildCriterion(preDate,curDate);

        return logRequestMapper.countByTerm(criterion);
    }

    /**
     * 按商品编号统计
     * @param preDate
     * @param curDate
     * @return
     */
    public List<Map<String, String>> countByItem(String preDate, String curDate) {
    	BspCriterion criterion = buildCriterion(preDate,curDate);

        return logRequestMapper.countByItem(criterion);
    }

    /**
     * 构建criterion
     * @param preDate
     * @param curDate
     * @return
     */
    private BspCriterion buildCriterion(String preDate, String curDate) {
        //截止时间加1天
        Date curTime = DateTimeUtils.stringFormatToDate(curDate, DateTimeUtils.DEFUALT_SHOT_TIME_FORMAT);
        curTime = DateUtils.addDays(curTime, 1);
        curDate = DateTimeUtils.dateToStringFormat(curTime, DateTimeUtils.DEFUALT_SHOT_TIME_FORMAT);

        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter(MatchType.GT, PropertyType.D, "REQ_DATE", preDate));
        filters.add(new PropertyFilter(MatchType.LT, PropertyType.D, "REQ_DATE", curDate));

        BspCriterion criterion = new BspCriterion();
        criterion.setCriteria(filters );
        return criterion;
    }

    /**
     * 按用户统计
     * @param preDate
     * @param curDate
     * @return
     */
    public List<Map<String, String>> countByUser(String preDate, String curDate) {
    	BspCriterion criterion = buildCriterion(preDate,curDate);

        return logRequestMapper.countByUser(criterion);
    }
}