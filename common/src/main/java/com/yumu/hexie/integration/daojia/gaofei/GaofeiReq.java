package com.yumu.hexie.integration.daojia.gaofei;

import java.io.Serializable;

//高飞体育请求
public class GaofeiReq implements Serializable{
	private static final long serialVersionUID = 4808669460780339640L;
	private long orderId;//订单id
	private int paymentType;//0线下支付、1微信支付
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String expectedTime;//期望服务时间
	private String strMobile; //用户手机号
	private String strName; //用户姓名
	private String strWorkAddr; //用户地址
	private String strGaofeiAddr;//高飞地址
	private String StrGaofeiTel;//高飞电话
	private int serviceStatus;//服务状态 
	private String memo;//备注
	

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
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
	public String getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
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
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStrGaofeiAddr() {
		return strGaofeiAddr;
	}
	public void setStrGaofeiAddr(String strGaofeiAddr) {
		this.strGaofeiAddr = strGaofeiAddr;
	}
	public String getStrGaofeiTel() {
		return StrGaofeiTel;
	}
	public void setStrGaofeiTel(String strGaofeiTel) {
		StrGaofeiTel = strGaofeiTel;
	}
	public int getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
