/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-15 上午11:39:51
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2011-8-15        Initailized
 */

package com.strawren.bsp.core;

import java.io.Serializable;

/**
 * SpringSecurity UserDetails impl
 * 
 */
public class BspUserDetails implements Serializable {

	private static final long serialVersionUID = 4779719257045848137L;

	// 用户唯一的ID
	private String userId;
	// 用户名
	private String username;
	// 用户真实姓名
	private String realname;
	// 登录方式，目前支持口令和动态密码
	private String loginType;
	// 密码
	private String password;
	// 默认的系统appCode
	private String defautAppCode;
	// 扩展信息
	private Object extraInfo;
	// 是否已经初始化权限信息
	private boolean initAuthInfoFlag;
	// 用户部门信息
	private String userOrgName;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {

		return password;
	}

	public String getUsername() {

		return username;
	}

	public boolean isAccountNonExpired() {

		return true;
	}

	public boolean isAccountNonLocked() {

		return true;
	}

	public boolean isCredentialsNonExpired() {

		return true;
	}

	public boolean isEnabled() {

		return true;
	}

	public Object getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Object extraInfo) {
		this.extraInfo = extraInfo;
	}

	public boolean isInitAuthInfoFlag() {
		return initAuthInfoFlag;
	}

	public void setInitAuthInfoFlag(boolean initAuthInfoFlag) {
		this.initAuthInfoFlag = initAuthInfoFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * Property accessor of realname
	 * 
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname
	 *            the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * Property accessor of loginType
	 * 
	 * @return the loginType
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType
	 *            the loginType to set
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/**
	 * Property accessor of defautAppCode
	 * 
	 * @return the defautAppCode
	 */
	public String getDefautAppCode() {
		return defautAppCode;
	}

	/**
	 * @param defautAppCode
	 *            the defautAppCode to set
	 */
	public void setDefautAppCode(String defautAppCode) {
		this.defautAppCode = defautAppCode;
	}

	public String getUserOrgName() {
		return userOrgName;
	}

	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}
}
