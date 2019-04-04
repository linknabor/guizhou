package com.yumu.hexie.model.community;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//区域
@Entity
public class RegionInfo extends BaseModel{

	private static final long serialVersionUID = 6011247541203926850L;
	
	private String sect_id;	 	//主键id
	private String name;	//区域名称
	private String db_code;	//
	private String regionType;	//区域类型
	private String super_regionId;	//上级ID 代表4级（小区、管理中心、物业公司、平台）
	private String super_regionId2;	//上级ID2
	private String super_regionId3;	//上级ID3
	
	public String getSect_id() {
		return sect_id;
	}
	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDb_code() {
		return db_code;
	}
	public void setDb_code(String db_code) {
		this.db_code = db_code;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getSuper_regionId() {
		return super_regionId;
	}
	public void setSuper_regionId(String super_regionId) {
		this.super_regionId = super_regionId;
	}
	public String getSuper_regionId2() {
		return super_regionId2;
	}
	public void setSuper_regionId2(String super_regionId2) {
		this.super_regionId2 = super_regionId2;
	}
	public String getSuper_regionId3() {
		return super_regionId3;
	}
	public void setSuper_regionId3(String super_regionId3) {
		this.super_regionId3 = super_regionId3;
	}
}
