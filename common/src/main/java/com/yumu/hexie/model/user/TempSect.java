package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class TempSect extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sectId;
	private String sectName;
	private String regionName;
	public String getSectId() {
		return sectId;
	}
	public void setSectId(String sectId) {
		this.sectId = sectId;
	}
	public String getSectName() {
		return sectName;
	}
	public void setSectName(String sectName) {
		this.sectName = sectName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	

}
