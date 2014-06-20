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
import com.strawren.jpress.model.LogException;

/**
 * Mapper类，异常日志，异常信息，时间，关联的组件等：是否需要人工来处理，处理状态等，对应表LOG_EXCEPTION
 */
@BspMapper
public interface LogExceptionMapper extends Dao<LogException, Long>{
}