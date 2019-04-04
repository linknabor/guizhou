package com.yumu.hexie.service.o2o.req;

import java.io.Serializable;

public class CommonBillReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private Long couponId;
	private long addressId;//FIXME 服务地址
	private String reqTime;//预期时间
	private String memo;
    public Long getCouponId() {
        return couponId;
    }
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
    public String getReqTime() {
        return reqTime;
    }
    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
