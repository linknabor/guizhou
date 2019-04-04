package com.yumu.hexie.integration.daojia.huya;

import java.io.Serializable;
import java.util.Date;

//合协车辆信息表
public class HuyaOralReq implements Serializable{
	private static final long serialVersionUID = 4808669460780339640L;
	private long orderId;//订单id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private int paymentType;//0线下支付、1微信支付
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String clinicName;//诊所名称
	private String clinicAddr;//诊所地址
	private String clinicTel;//诊所电话
	private String customerName;//顾客姓名
	private String customerTel;//顾客电话
	private String memo;//备注
	private Date createDate;//创建时间

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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
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
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getClinicAddr() {
		return clinicAddr;
	}
	public void setClinicAddr(String clinicAddr) {
		this.clinicAddr = clinicAddr;
	}
	public String getClinicTel() {
		return clinicTel;
	}
	public void setClinicTel(String clinicTel) {
		this.clinicTel = clinicTel;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	
}
