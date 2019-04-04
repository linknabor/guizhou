package com.yumu.hexie.integration.daojia.ayilaile.req;

import java.io.Serializable;

public class ServiceOrderReq implements Serializable {

	private static final long serialVersionUID = -5968127142709415032L;

	private String strMobile;
	private String strName;
	private String strServiceAddr;
	
	private String guidServiceTypeID;
	private String guidSalaryRangeID;
	
	private String dtPlanStartDate;
	
	private String strOtherNeed;
	private String strPlanStartTimeHour;
	private String strPlanStartTimeMimute;
	
	private String strWorkDuration;
	private String strWorkFrequency;

	public ServiceOrderReq() {

	}

	public ServiceOrderReq(String strMobile, String strName,
			String guidServiceTypeID, String guidSalaryRangeID,
			String dtPlanStartDate, String strServiceAddr,
			String strOtherNeed, String strPlanStartTimeHour,
			String strPlanStartTimeMimute, String strWorkDuration,
			String strWorkFrequency) {
		this.strMobile = strMobile;
		this.strName = strName;
		this.guidServiceTypeID = guidServiceTypeID;
		this.guidSalaryRangeID = guidSalaryRangeID;
		this.dtPlanStartDate = dtPlanStartDate;
		this.strServiceAddr = strServiceAddr;
		this.strOtherNeed = strOtherNeed;
		this.strPlanStartTimeHour = strPlanStartTimeHour;
		this.strPlanStartTimeMimute = strPlanStartTimeMimute;
		this.strWorkDuration = strWorkDuration;
		this.strWorkFrequency = strWorkFrequency;
	}

	public String getStrMobile() {
		return strMobile;
	}

	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}

	public String getGuidServiceTypeID() {
		return guidServiceTypeID;
	}

	public void setGuidServiceTypeID(String guidServiceTypeID) {
		this.guidServiceTypeID = guidServiceTypeID;
	}

	public String getGuidSalaryRangeID() {
		return guidSalaryRangeID;
	}

	public void setGuidSalaryRangeID(String guidSalaryRangeID) {
		this.guidSalaryRangeID = guidSalaryRangeID;
	}


	public String getStrServiceAddr() {
		return strServiceAddr;
	}

	public void setStrServiceAddr(String strServiceAddr) {
		this.strServiceAddr = strServiceAddr;
	}

	public String getStrOtherNeed() {
		return strOtherNeed;
	}

	public void setStrOtherNeed(String strOtherNeed) {
		this.strOtherNeed = strOtherNeed;
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

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getDtPlanStartDate() {
		return dtPlanStartDate;
	}

	public void setDtPlanStartDate(String dtPlanStartDate) {
		this.dtPlanStartDate = dtPlanStartDate;
	}

}
