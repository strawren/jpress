package com.strawren.bsp.core;

import java.util.ArrayList;
import java.util.List;

public class GridViewDto<T> {

	private List<T> rows = new ArrayList<T>(20);
	private Long total = 0L;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
