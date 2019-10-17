package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class TempHouse extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6233101637204414735L;
	
//	web_user_id','toalHouse','province_id','province_name','city_id','city_name','region_id','region_name','sect_name','sect_addr','cell_addr'
	
	private String wuyeId;
	private String province;
	private String provinceName;
	private String city;
	private String cityName;
	private String region;
	private String regionName;
	private String sectName;
	private String sectAddr;
	private String cellAddr;
	
	public String getWuyeId() {
		return wuyeId;
	}
	public void setWuyeId(String wuyeId) {
		this.wuyeId = wuyeId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSectName() {
		return sectName;
	}
	public void setSectName(String sectName) {
		this.sectName = sectName;
	}
	public String getSectAddr() {
		return sectAddr;
	}
	public void setSectAddr(String sectAddr) {
		this.sectAddr = sectAddr;
	}
	public String getCellAddr() {
		return cellAddr;
	}
	public void setCellAddr(String cellAddr) {
		this.cellAddr = cellAddr;
	}
	
	

}
