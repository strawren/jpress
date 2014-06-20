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
import com.strawren.jpress.dao.LogDbOpMapper;
import com.strawren.jpress.model.LogDbOp;

/**
 * 服务类，数据库操作日志，通过AOP来实现的，对应表LOG_DB_OP
 */
@Service
@Transactional
public class LogDbOpService extends DefaultEntityService<LogDbOp, Long>{

    /** Mapper类，数据库操作日志，通过AOP来实现的， */
    @Autowired
    LogDbOpMapper logDbOpMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<LogDbOp, Long> getDao() {
        return logDbOpMapper;
    }
}