package com.yumu.hexie.integration.baidu.vo;

import java.io.Serializable;

public class Location implements Serializable{

	private static final long serialVersionUID = -672026802827213409L;
	private double lat;
	private double lng;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
}
