/**
 * Copyright : www.orientpay.com , 2007-2011
 * Project : tvauths_core
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2013-8-23 下午12:15:58
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * ZhouXushun     2013-8-23        Initailized
 */

package com.orientpay.clivia.portal.web.auths;

/**
 * TODO Add class descriptions
 * 
 */
public class CommonMessageFooter {

	private String mac;

	public String getFooter() {
		return mac;
	}

	public void setFooter(String mac) {
		this.mac = mac;
	}

	@Override
    public String toString() {
		return "CommonMessageFooter [mac=" + mac + "]";
	}
}
