package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
@Entity
public class AyiServiceOrder extends BaseModel {
	private static final long serialVersionUID = -5243403183976637115L;
	
	private long orderId;
	
	
	private String guidServiceOrderID;
	private String strOrderCode;
	private String guidServiceTypeID;
	private String strServiceTypeName;

	private String guidSalaryRangeID;
	private String strSalaryRangeName;
	private String strSalaryRangeIntro;
	private String dtPlanStartDate;
	private String strServiceAddr;
	private String dtCreateTime;
	private String strOtherNeed;
	private String strOrderStatusID;
	private String strOrderStatusName;
	private String strPlanStartTimeHour;
	private String strPlanStartTimeMimute;
	private String strWorkDuration;
	private String strWorkFrequency;
	
	
	public String getGuidServiceOrderID() {
		return guidServiceOrderID;
	}
	public void setGuidServiceOrderID(String guidServiceOrderID) {
		this.guidServiceOrderID = guidServiceOrderID;
	}
	public String getStrOrderCode() {
		return strOrderCode;
	}
	public void setStrOrderCode(String strOrderCode) {
		this.strOrderCode = strOrderCode;
	}
	public String getGuidServiceTypeID() {
		return guidServiceTypeID;
	}
	public void setGuidServiceTypeID(String guidServiceTypeID) {
		this.guidServiceTypeID = guidServiceTypeID;
	}
	public String getStrServiceTypeName() {
		return strServiceTypeName;
	}
	public void setStrServiceTypeName(String strServiceTypeName) {
		this.strServiceTypeName = strServiceTypeName;
	}
	public String getGuidSalaryRangeID() {
		return guidSalaryRangeID;
	}
	public void setGuidSalaryRangeID(String guidSalaryRangeID) {
		this.guidSalaryRangeID = guidSalaryRangeID;
	}
	public String getStrSalaryRangeName() {
		return strSalaryRangeName;
	}
	public void setStrSalaryRangeName(String strSalaryRangeName) {
		this.strSalaryRangeName = strSalaryRangeName;
	}
	public String getStrSalaryRangeIntro() {
		return strSalaryRangeIntro;
	}
	public void setStrSalaryRangeIntro(String strSalaryRangeIntro) {
		this.strSalaryRangeIntro = strSalaryRangeIntro;
	}
	public String getDtPlanStartDate() {
		return dtPlanStartDate;
	}
	public void setDtPlanStartDate(String dtPlanStartDate) {
		this.dtPlanStartDate = dtPlanStartDate;
	}
	public String getStrServiceAddr() {
		return strServiceAddr;
	}
	public void setStrServiceAddr(String strServiceAddr) {
		this.strServiceAddr = strServiceAddr;
	}
	public String getDtCreateTime() {
		return dtCreateTime;
	}
	public void setDtCreateTime(String dtCreateTime) {
		this.dtCreateTime = dtCreateTime;
	}
	public String getStrOtherNeed() {
		return strOtherNeed;
	}
	public void setStrOtherNeed(String strOtherNeed) {
		this.strOtherNeed = strOtherNeed;
	}
	public String getStrOrderStatusID() {
		return strOrderStatusID;
	}
	public void setStrOrderStatusID(String strOrderStatusID) {
		this.strOrderStatusID = strOrderStatusID;
	}
	public String getStrOrderStatusName() {
		return strOrderStatusName;
	}
	public void setStrOrderStatusName(String strOrderStatusName) {
		this.strOrderStatusName = strOrderStatusName;
	}
	public String getStrPlanStartTimeHour() {
		return strPlanStartTimeHour;
	}
	public void setStrPlanStartTimeHour(String strPlanStartTimeHour) {
		this.strPlanStartTimeHour = strPlanStartTimeHour;
	}
	public String getStrPlanStartTimeMimute() {
		return strPlanStartTimeMimute;
	}
	public void setStrPlanStartTimeMimute(String strPlanStartTimeMimute) {
		this.strPlanStartTimeMimute = strPlanStartTimeMimute;
	}
	public String getStrWorkDuration() {
		return strWorkDuration;
	}
	public void setStrWorkDuration(String strWorkDuration) {
		this.strWorkDuration = strWorkDuration;
	}
	public String getStrWorkFrequency() {
		return strWorkFrequency;
	}
	public void setStrWorkFrequency(String strWorkFrequency) {
		this.strWorkFrequency = strWorkFrequency;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
