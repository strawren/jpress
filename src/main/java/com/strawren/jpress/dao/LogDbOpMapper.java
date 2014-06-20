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
import com.strawren.jpress.model.LogDbOp;

/**
 * Mapper类，数据库操作日志，通过AOP来实现的，对应表LOG_DB_OP
 */
@BspMapper
public interface LogDbOpMapper extends Dao<LogDbOp, Long>{
}