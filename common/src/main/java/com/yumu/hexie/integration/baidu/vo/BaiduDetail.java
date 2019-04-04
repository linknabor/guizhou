package com.yumu.hexie.integration.baidu.vo;

import java.io.Serializable;

public class BaiduDetail implements Serializable {
	private static final long serialVersionUID = 7411088452604351038L;
	private Location location;
	private int precise;
	private int confidence;
	private String level;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getPrecise() {
		return precise;
	}

	public void setPrecise(int precise) {
		this.precise = precise;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
}
