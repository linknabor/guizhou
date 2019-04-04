package com.yumu.hexie.integration.baidu.vo;

import java.io.Serializable;
import java.util.List;

public class BaiduQueryResult implements Serializable {
	private static final long serialVersionUID = -301401143450240118L;
	private int status;
	private String message;
	private int total;
	private List<BaiduAddress> results;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<BaiduAddress> getResults() {
		return results;
	}
	public void setResults(List<BaiduAddress> results) {
		this.results = results;
	}
	
}
