package com.yumu.hexie.integration.wuye.vo;

public class HexieAddress {
	
	private String cell_addr;
	private String sect_id;
	private String sect_name;
	private String sect_addr;
	private String region_name;
	private String city_name;
	private String province_name;
	public String getCell_addr() {
		return cell_addr;
	}
	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}
	public String getSect_id() {
		return sect_id;
	}
	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}
	public String getSect_name() {
		return sect_name;
	}
	public void setSect_name(String sect_name) {
		this.sect_name = sect_name;
	}
	public String getSect_addr() {
		return sect_addr;
	}
	public void setSect_addr(String sect_addr) {
		this.sect_addr = sect_addr;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	@Override
	public String toString() {
		return "HexieAddress [cell_addr=" + cell_addr + ", sect_id=" + sect_id + ", sect_name=" + sect_name
				+ ", sect_addr=" + sect_addr + ", region_name=" + region_name + ", city_name=" + city_name
				+ ", province_name=" + province_name + "]";
	}

	
	
}
