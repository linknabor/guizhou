package com.yumu.hexie.model.region;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class RegionUrl extends BaseModel{

	private static final long serialVersionUID = 4276437344314180001L;
	
	private String regionname;
	private String regionUrl;
	
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public String getRegionUrl() {
		return regionUrl;
	}
	public void setRegionUrl(String regionUrl) {
		this.regionUrl = regionUrl;
	}
	
	
}
