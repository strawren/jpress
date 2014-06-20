package com.strawren.bsp.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端UI菜单
 * @author Administrator
 *
 */
public class UiMenu {

	private String id;
	//上级ID
	private String pid;
	private String name;
	private String iconCls;
	private String url;
	private List<UiMenu> child = new ArrayList<UiMenu>(5);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<UiMenu> getChild() {
		return child;
	}

	public void setChild(List<UiMenu> child) {
		this.child = child;
	}
}
