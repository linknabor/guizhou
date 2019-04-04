package com.yumu.hexie.integration.baidu.vo;

import java.io.Serializable;

public class BaiduAddress implements Serializable {
	private static final long serialVersionUID = -1617622962510566359L;

	private String name;
	private Location location;
	private String address;
	private int detail;
	private String uid;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDetail() {
		return detail;
	}
	public void setDetail(int detail) {
		this.detail = detail;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
}
