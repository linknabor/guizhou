package com.yumu.hexie.model.car;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**车辆年款（来自车大大）
 * 
 * @author hwb_work
 *
 */
@Entity
public class CarYear extends BaseModel{
	private static final long serialVersionUID = -42424248221241394L;
	
	private String styleId; //车型编号
	private String yearName; //年款名称
	private String brandId; //品牌编号
	private String yearId; //年款编号
	private String carIdentifier; //车辆唯一标示码
	private String fId; //厂家id
	private String year; //年款
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
	public String getCarIndentifier() {
		return carIdentifier;
	}
	public void setCarIndentifier(String carIdentifier) {
		this.carIdentifier = carIdentifier;
	}
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	
	
}

