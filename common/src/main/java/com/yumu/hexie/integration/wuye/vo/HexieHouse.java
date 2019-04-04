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
	private String sect_id;//小区ID plat
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
	public String getSect_id() {
		return sect_id;
	}
	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}
	@Override
	public String toString() {
		return "HexieHouse [mng_cell_id=" + mng_cell_id + ", sect_name="
				+ sect_name + ", city_name=" + city_name + ", cell_addr="
				+ cell_addr + ", cnst_area=" + cnst_area + ", ver_no=" + ver_no
				+ ", park_inf=" + park_inf + ", sect_id=" + sect_id + "]";
	}
	
	
}
