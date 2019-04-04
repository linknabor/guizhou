package com.yumu.hexie.integration.daojia.daojiamei;

import java.io.Serializable;
import java.util.Date;

//合协车辆信息表
public class DaoJiaMeiReq implements Serializable{
	private static final long serialVersionUID = 4808669460780339640L;
	private long orderId;//订单id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private int paymentType;//0线下支付、1微信支付
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String expectedTime;//期望服务时间
	private int timeFrame;//0;上午，1下午，2晚上
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private String memo;//备注
	private Date createDate;//创建时间

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getTimeFrame() {
		return timeFrame;
	}
	public void setTimeFrame(int timeFrame) {
		this.timeFrame = timeFrame;
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
	
}
