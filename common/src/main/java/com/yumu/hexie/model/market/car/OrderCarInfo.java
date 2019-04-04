package com.yumu.hexie.model.market.car;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.annotation.Transient;

import com.yumu.hexie.model.BaseModel;

/**
 * 用户商品订单车辆信息(用户车辆信息来自CarYear及其关联的表)
 * @author hwb_work
 *
 */
@Entity
public class OrderCarInfo  extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	
	private String styleId; //车型编号
	private String yearName; //年款名称
	private String brandId; //品牌编号
	private String yearId; //年款编号
	private String year; //年款
	private String licensePlate; //汽车牌照编号
	private String licensePlateProvince; //汽车牌照省份
	private long userId; //用户ID
	private long serviceOrderId; //商品订单ID
	private Date requireDate; //预约服务时间
	
	private String inputCarStyle;//手动输入的其他车型
	
	public String getInputCarStyle() {
		return inputCarStyle;
	}
	public void setInputCarStyle(String inputCarStyle) {
		this.inputCarStyle = inputCarStyle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public Date getRequireDate() {
		return requireDate;
	}
	public void setRequireDate(Date requireDate) {
		this.requireDate = requireDate;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public String getLicensePlateProvince() {
		return licensePlateProvince;
	}
	public void setLicensePlateProvince(String licensePlateProvince) {
		this.licensePlateProvince = licensePlateProvince;
	}
	
	
}
