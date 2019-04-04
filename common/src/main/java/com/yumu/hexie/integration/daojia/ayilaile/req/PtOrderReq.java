package com.yumu.hexie.integration.daojia.ayilaile.req;

import java.io.Serializable;

public class PtOrderReq implements Serializable{

	private static final long serialVersionUID = -5968127142709415032L;
	
	private String strMobile;
	private String strName;
	private String dtWorkTime;
	private String strWorkAddr;
	private String nPayMoney;
	private String strWorkDetail;
	private String nWorkHourLength;
	public PtOrderReq(){
		
	}
	public PtOrderReq(String strMobile,String strName,String dtWorkTime,
			String strWorkAddr,String nPayMoney,String strWorkDetail,String nWorkHourLength){
		this.strMobile = strMobile;
		this.strName = strName;
		this.dtWorkTime = dtWorkTime;
		this.strWorkAddr = strWorkAddr;
		this.nPayMoney = nPayMoney;
		this.strWorkDetail = strWorkDetail;
		this.nWorkHourLength = nWorkHourLength;
	}
	
	public String getStrMobile() {
		return strMobile;
	}
	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getDtWorkTime() {
		return dtWorkTime;
	}
	public void setDtWorkTime(String dtWorkTime) {
		this.dtWorkTime = dtWorkTime;
	}
	public String getStrWorkAddr() {
		return strWorkAddr;
	}
	public void setStrWorkAddr(String strWorkAddr) {
		this.strWorkAddr = strWorkAddr;
	}
	public String getnPayMoney() {
		return nPayMoney;
	}
	public void setnPayMoney(String nPayMoney) {
		this.nPayMoney = nPayMoney;
	}
	public String getnWorkHourLength() {
		return nWorkHourLength;
	}
	public void setnWorkHourLength(String nWorkHourLength) {
		this.nWorkHourLength = nWorkHourLength;
	}
	public String getStrWorkDetail() {
		return strWorkDetail;
	}
	public void setStrWorkDetail(String strWorkDetail) {
		this.strWorkDetail = strWorkDetail;
	}
	
}
