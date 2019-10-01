package com.ulxsfrank.business.data.rest.result;

public class SingleResult<T> extends Result {
	/**
	 * @author fangchensheng
	 * @date 2018年3月12日
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
