/**
 * 
 */
package com.yumu.hexie.model.community;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 百事通模块
 * @author HuYM
 *
 */
@Entity
public class CommunityInfo implements Serializable{

	private static final long serialVersionUID = 5022993868010054679L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long infoId;
	
	private String infoType;		//信息类型
	private String infoName;	//信息中文名称
	private String infoTypeName;	//信息类型名称
	private String infoTypeImg;		//信息类型图片
	private String infoAddress;		//信息地址
	private String infoTel;			//信息电话
	private long sectId;			//所关联小区
	private long regionId;			//所属区县
	private long cityId;			//所属城市
	private long provinceId;		//所属省份
	
	public long getInfoId() {
		return infoId;
	}
	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	
	public String getInfoTypeImg() {
		return infoTypeImg;
	}
	public void setInfoTypeImg(String infoTypeImg) {
		this.infoTypeImg = infoTypeImg;
	}
	public String getInfoAddress() {
		return infoAddress;
	}
	public void setInfoAddress(String infoAddress) {
		this.infoAddress = infoAddress;
	}
	public String getInfoTel() {
		return infoTel;
	}
	public void setInfoTel(String infoTel) {
		this.infoTel = infoTel;
	}
	public long getSectId() {
		return sectId;
	}
	public void setSectId(long sectId) {
		this.sectId = sectId;
	}
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public String getInfoTypeName() {
		return infoTypeName;
	}
	public void setInfoTypeName(String infoTypeName) {
		this.infoTypeName = infoTypeName;
	}
	
}
