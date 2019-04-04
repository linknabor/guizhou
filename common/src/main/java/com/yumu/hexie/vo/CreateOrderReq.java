package com.yumu.hexie.vo;

import java.io.Serializable;

public class CreateOrderReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private Long couponId;
	private long serviceAddressId;//FIXME 服务地址
	private int receiveTimeType;//周一至周五、周六周日、全周
	private String memo;
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public long getServiceAddressId() {
		return serviceAddressId;
	}
	public void setServiceAddressId(long serviceAddressId) {
		this.serviceAddressId = serviceAddressId;
	}
	public int getReceiveTimeType() {
		return receiveTimeType;
	}
	public void setReceiveTimeType(int receiveTimeType) {
		this.receiveTimeType = receiveTimeType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
