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
import com.strawren.jpress.dao.LogExceptionMapper;
import com.strawren.jpress.model.LogException;

/**
 * 服务类，异常日志，异常信息，时间，关联的组件等：是否需要人工来处理，处理状态等，对应表LOG_EXCEPTION
 */
@Service
@Transactional
public class LogExceptionService extends DefaultEntityService<LogException, Long>{

    /** Mapper类，异常日志，异常信息，时间，关联的组件等：是否需要人工来处理，处理状态等， */
    @Autowired
    LogExceptionMapper logExceptionMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<LogException, Long> getDao() {
        return logExceptionMapper;
    }
}