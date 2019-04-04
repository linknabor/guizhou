package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//用户车辆信息
@Entity
public class UserCarInfo  extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private String brandName;//品牌
	private String modelName;//型号
	private String colour;//颜色
	private String year; //年份
	private String plateProvince;//车牌省份	
	private String plateNumber;//车牌号
    
	private long userId;
	private String userName;
	private boolean main;//是否是默认地址
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPlateProvince() {
		return plateProvince;
	}
	public void setPlateProvince(String plateProvince) {
		this.plateProvince = plateProvince;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isMain() {
		return main;
	}
	public void setMain(boolean main) {
		this.main = main;
	}	
}
