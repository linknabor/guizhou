package com.yumu.hexie.model.view;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//banner表
@Entity
public class Banner extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private int bannerType; //0服务；1特卖；2团购；3物业；4特卖活动页；5特卖的品牌
	private int sortNo = 10;//排序
	private String picture;//banner图片或者小图标
	private String bannerUrl;//目的跳转地址
	private int status;//0无效；1有效
	private long regionId;//区域
	private int regionType;//与上架规则区域一致
	//冗余字段
	private int onSaleType=0;//与OnsaleRule中的productType对应
	private String brandName;//品牌名称

	public int getBannerType() {
		return bannerType;
	}
	public void setBannerType(int bannerType) {
		this.bannerType = bannerType;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public int getRegionType() {
		return regionType;
	}
	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}
	public int getOnSaleType() {
		return onSaleType;
	}
	public void setOnSaleType(int onSaleType) {
		this.onSaleType = onSaleType;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
