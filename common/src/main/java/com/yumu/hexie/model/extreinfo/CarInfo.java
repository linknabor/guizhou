package com.yumu.hexie.model.extreinfo;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//第三方车数据库
@Entity
public class CarInfo extends BaseModel{
	private static final long serialVersionUID = 4808669460780339640L;
	private String firstLetter; //首写字母
	private String makeName;//奥迪
	private String modelSeries;//奥迪
	private String modelName;//奥迪A1（进口）
	private String typeSeries;//2011款
	private String typeName;//1.4 TFSI7档双离合  Urban款
	private String country;//德
	private String technology; //进口
	private String vehicleClass; //小型车
	private String engineCapacity;//1400
	private String transmission;//双离合

	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	public String getMakeName() {
		return makeName;
	}
	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}
	public String getModelSeries() {
		return modelSeries;
	}
	public void setModelSeries(String modelSeries) {
		this.modelSeries = modelSeries;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getTypeSeries() {
		return typeSeries;
	}
	public void setTypeSeries(String typeSeries) {
		this.typeSeries = typeSeries;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getEngineCapacity() {
		return engineCapacity;
	}
	public void setEngineCapacity(String engineCapacity) {
		this.engineCapacity = engineCapacity;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

}
