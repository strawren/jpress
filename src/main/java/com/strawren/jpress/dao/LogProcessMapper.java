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

import com.strawren.bsp.dao.Dao;
import com.strawren.bsp.dao.BspMapper;
import com.strawren.jpress.model.LogProcess;

/**
 * Mapper类，处理日志，通过日志类型+动作+是否显示给用户
日志类型包括订单、商户，对应表LOG_PROCESS
 */
@BspMapper
public interface LogProcessMapper extends Dao<LogProcess, Long>{
}