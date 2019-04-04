package com.yumu.hexie.integration.amap.resp;

import java.io.Serializable;

public class BaseResp  implements Serializable {

	private static final long serialVersionUID = 2243368998351186024L;
	private int status; //返回状态 1：成功；0：失败
	private String info; //返回信息，

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess(){
		return status == 1;
	}
}
