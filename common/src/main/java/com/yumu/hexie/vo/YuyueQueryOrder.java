package com.yumu.hexie.vo;

import java.io.Serializable;

public class YuyueQueryOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5941043351676553826L;
	
	private long orderId;
	private String workTime;		//预约时间
	private String memo;		//备注
	private String tel;					//手机号
	private String receiverName;		//联系人
	private String address;		//详细地址
	private String serviceTypeName;		//服务项目s
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	

}
