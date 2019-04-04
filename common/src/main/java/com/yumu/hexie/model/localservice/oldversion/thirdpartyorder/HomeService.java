package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
@Entity
public class HomeService extends BaseModel {
	private static final long serialVersionUID = -5243403183976637115L;

	private int isHandpick;	//是否精选，1为精选，0为非精选
	private int serviceType;//0洗车; 1鲜花; 2家政；3洗衣；4装修维修；5家电；6宠物；7厨师；8美容；
	private float price;//服务价格
	private String picture;//服务封面图片
	private String serviceName;//服务名称
	private String serviceUrl;//服务连接
	private int status;//是否有效,1为有效；0为无效
	private int sortNo = 10;//增加排序

	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsHandpick() {
		return isHandpick;
	}
	public void setIsHandpick(int isHandpick) {
		this.isHandpick = isHandpick;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
}
