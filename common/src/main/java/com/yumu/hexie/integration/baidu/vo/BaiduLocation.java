package com.yumu.hexie.integration.baidu.vo;

import java.io.Serializable;

public class BaiduLocation implements Serializable{

	private static final long serialVersionUID = -5286457582105912932L;
	private int status;
	private BaiduDetail result;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BaiduDetail getResult() {
		return result;
	}
	public void setResult(BaiduDetail result) {
		this.result = result;
	}
	
	
}
