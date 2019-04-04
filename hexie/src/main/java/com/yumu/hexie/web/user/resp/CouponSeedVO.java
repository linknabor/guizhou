package com.yumu.hexie.web.user.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.promotion.coupon.CouponSeed;

public class CouponSeedVO implements Serializable{

	private static final long serialVersionUID = -2676616753491950466L;
	private CouponSeed seed;
	private boolean fetched = false;
	private Coupon coupon;
	private List<Coupon> coupons;
	public CouponSeed getSeed() {
		return seed;
	}
	public void setSeed(CouponSeed seed) {
		this.seed = seed;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	public List<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	public boolean isFetched() {
		return fetched;
	}
	public void setFetched(boolean fetched) {
		this.fetched = fetched;
	}
}
