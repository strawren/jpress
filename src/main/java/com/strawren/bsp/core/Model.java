/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-4 下午03:54:13
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-4        Initailized
 */

package com.strawren.bsp.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有Model的基类
 *
 */
public abstract class Model implements Serializable{
	private static final long serialVersionUID = -7346901881796052757L;

	//用来保存修改的字段
	protected Map<String, Object> entityMap = new HashMap<String, Object>();

	public Map<String, Object> getEntityMap() {
		return entityMap;
	}
	
	public abstract Serializable getPK();
}
