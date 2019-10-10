package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;
import java.util.List;

public class HexieHouse implements Serializable {

	private static final long serialVersionUID = -699024784725033137L;

	private String mng_cell_id;
	private String sect_name;
	private String city_name;
	private String cell_addr;
	private String cnst_area;
	private String ver_no;
	private List<ParkInfo> park_inf;
	
	private long province_id;//省id
	private String province_name;//省名
	private long city_id;//城市ID
	private long region_id;//区域ID
	private String region_name;//区域名
	private String sect_addr;
	
	private String sect_id;//小区id
	private String csp_id;//公司
	private String center_id;//中心管理id
	
	public String getSect_id() {
		return sect_id;
	}
	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}
	public String getCsp_id() {
		return csp_id;
	}
	public void setCsp_id(String csp_id) {
		this.csp_id = csp_id;
	}
	public String getCenter_id() {
		return center_id;
	}
	public void setCenter_id(String center_id) {
		this.center_id = center_id;
	}
	
	public String getSect_addr() {
		return sect_addr;
	}
	public void setSect_addr(String sect_addr) {
		this.sect_addr = sect_addr;
	}
	public long getProvince_id() {
		return province_id;
	}
	public void setProvince_id(long province_id) {
		this.province_id = province_id;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public long getCity_id() {
		return city_id;
	}
	public void setCity_id(long city_id) {
		this.city_id = city_id;
	}
	public long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getMng_cell_id() {
		return mng_cell_id;
	}
	public void setMng_cell_id(String mng_cell_id) {
		this.mng_cell_id = mng_cell_id;
	}
	public String getSect_name() {
		return sect_name;
	}
	public void setSect_name(String sect_name) {
		this.sect_name = sect_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getCell_addr() {
		return cell_addr;
	}
	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}
	public String getCnst_area() {
		return cnst_area;
	}
	public void setCnst_area(String cnst_area) {
		this.cnst_area = cnst_area;
	}
	public String getVer_no() {
		return ver_no;
	}
	public void setVer_no(String ver_no) {
		this.ver_no = ver_no;
	}
	public List<ParkInfo> getPark_inf() {
		return park_inf;
	}
	public void setPark_inf(List<ParkInfo> park_inf) {
		this.park_inf = park_inf;
	}
	
}
