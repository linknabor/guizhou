package com.yumu.hexie.integration.wuye.resp;

import java.util.List;

public class CellListVO {

	private List<CellVO> sect_info;//小区集合
	private List<CellVO> build_info;//楼宇集合
	private List<CellVO> unit_info;//门牌集合
	private List<CellVO> house_info;//房屋集合
	
	public List<CellVO> getSect_info() {
		return sect_info;
	}
	public void setSect_info(List<CellVO> sect_info) {
		this.sect_info = sect_info;
	}
	public List<CellVO> getBuild_info() {
		return build_info;
	}
	public void setBuild_info(List<CellVO> build_info) {
		this.build_info = build_info;
	}
	public List<CellVO> getUnit_info() {
		return unit_info;
	}
	public void setUnit_info(List<CellVO> unit_info) {
		this.unit_info = unit_info;
	}
	public List<CellVO> getHouse_info() {
		return house_info;
	}
	public void setHouse_info(List<CellVO> house_info) {
		this.house_info = house_info;
	}
}
