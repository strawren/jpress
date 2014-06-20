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
import com.strawren.jpress.dao.LogLoginMapper;
import com.strawren.jpress.model.LogLogin;

/**
 * 服务类，登陆日志，通过标志位来区分系统后台还是商户前台登陆，对应表LOG_LOGIN
 */
@Service
@Transactional
public class LogLoginService extends DefaultEntityService<LogLogin, Long>{

    /** Mapper类，登陆日志，通过标志位来区分系统后台还是商户前台登陆， */
    @Autowired
    LogLoginMapper logLoginMapper;

    /** 数据存取方法 */
    @Override
    protected Dao<LogLogin, Long> getDao() {
        return logLoginMapper;
    }
}