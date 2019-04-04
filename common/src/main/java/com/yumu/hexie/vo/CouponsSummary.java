package com.yumu.hexie.vo;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.promotion.coupon.Coupon;

public class CouponsSummary implements Serializable {

	private static final long serialVersionUID = -9001869501906817043L;

	private int validCount;
	private int invalidCount;
	private List<Coupon> validCoupons;
	private List<Coupon> invalidCoupons;
	public int getValidCount() {
		return validCount;
	}
	public void setValidCount(int validCount) {
		this.validCount = validCount;
	}
	public int getInvalidCount() {
		return invalidCount;
	}
	public void setInvalidCount(int invalidCount) {
		this.invalidCount = invalidCount;
	}
	public List<Coupon> getValidCoupons() {
		return validCoupons;
	}
	public void setValidCoupons(List<Coupon> validCoupons) {
		this.validCoupons = validCoupons;
	}
	public List<Coupon> getInvalidCoupons() {
		return invalidCoupons;
	}
	public void setInvalidCoupons(List<Coupon> invalidCoupons) {
		this.invalidCoupons = invalidCoupons;
	}
	
}
