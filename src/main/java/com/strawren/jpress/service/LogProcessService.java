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
import com.strawren.jpress.dao.LogProcessMapper;
import com.strawren.jpress.model.LogProcess;

/**
 * 服务类，处理日志，通过日志类型+动作+是否显示给用户
日志类型包括订单、商户，对应表LOG_PROCESS
 */
@Service
@Transactional
public class LogProcessService extends DefaultEntityService<LogProcess, Long>{

    /** Mapper类，处理日志，通过日志类型+动作+是否显示给用户
日志类型包括订单、商户， */
    @Autowired
    LogProcessMapper logProcessMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<LogProcess, Long> getDao() {
        return logProcessMapper;
    }
}