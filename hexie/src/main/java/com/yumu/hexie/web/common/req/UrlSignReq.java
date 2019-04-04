package com.yumu.hexie.web.common.req;

import java.io.Serializable;

public class UrlSignReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
