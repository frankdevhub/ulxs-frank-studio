package com.ulxsfrank.business.data.rest.result;

import com.github.pagehelper.PageInfo;

public class PageResult<T> extends Result {

	private PageInfo<T> data;

	public PageInfo<T> getData() {
		return data;
	}

	public void setData(PageInfo<T> data) {
		this.data = data;
	}

}
