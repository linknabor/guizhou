package com.yumu.hexie.integration.wechat.entity.common;

import java.io.Serializable;
import java.util.List;

public class Openids  implements Serializable{

	private static final long serialVersionUID = -3583724198544408405L;

	private List<String> openid;

	public List<String> getOpenid() {
		return openid;
	}

	public void setOpenid(List<String> openid) {
		this.openid = openid;
	}
}
