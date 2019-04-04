package com.yumu.hexie.web.user.req;

import java.io.Serializable;

public class UserEdit implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private String mobile;
	private String yzm;
	private int sex;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getYzm() {
		return yzm;
	}
	public void setYzm(String yzm) {
		this.yzm = yzm;
	}
	
}
