package com.yumu.hexie.model.promotion.share;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * 分享产生的订单记录
 * @author Administrator
 */
@Entity
public class OrderShareRecord extends BaseModel {

	private static final long serialVersionUID = -5355824072138641636L;
	private String shareCode;
	private Long orderId;
	private Integer salePlanType;
	private Long salePlanId;//匹配到的物品，否则为空

	private Long toUserId;
	private Long fromUserId;
	public String getShareCode() {
		return shareCode;
	}
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getSalePlanType() {
		return salePlanType;
	}
	public void setSalePlanType(Integer salePlanType) {
		this.salePlanType = salePlanType;
	}
	public Long getSalePlanId() {
		return salePlanId;
	}
	public void setSalePlanId(Long salePlanId) {
		this.salePlanId = salePlanId;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
}
