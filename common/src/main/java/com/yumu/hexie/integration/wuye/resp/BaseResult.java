package com.yumu.hexie.integration.wuye.resp;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -1148781270530596482L;

	private String result;//00 成功 99 异常 01 账号不存在
	
	private T data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public boolean isSuccess() {
		return "00".equals(result);
	}
	
}
