package com.yumu.hexie.model.system;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class SystemConfig  extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	
	private String sysKey;
	private String sysValue;
	private String remark;
	
	public SystemConfig(){}
	public SystemConfig(String sysKey,String sysValue){
		this.sysKey = sysKey;
		this.sysValue = sysValue;
	}
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysValue() {
		return sysValue;
	}
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
