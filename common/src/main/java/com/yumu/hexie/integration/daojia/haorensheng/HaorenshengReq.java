package com.yumu.hexie.integration.daojia.haorensheng;

import java.io.Serializable;
import java.util.Date;

public class HaorenshengReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808669460780339640L;
	private long sOrderId;//订单id
	private long ruleId;//预约单Id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private int paymentType;//0线下支付、1微信支付
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private String memo;//备注
	private int serviceStatus;//服务状态 
	private Date createDate;//创建时间
	
	public long getsOrderId() {
		return sOrderId;
	}
	public void setsOrderId(long sOrderId) {
		this.sOrderId = sOrderId;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(long payStatus) {
		this.payStatus = payStatus;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
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
	public String getStrWorkAddr() {
		return strWorkAddr;
	}
	public void setStrWorkAddr(String strWorkAddr) {
		this.strWorkAddr = strWorkAddr;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

}
