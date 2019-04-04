package com.yumu.hexie.model.promotion.share;

import com.yumu.hexie.model.BaseModel;

public class ShareAccessRecord extends BaseModel {

	private static final long serialVersionUID = -5770517178784153824L;
	public ShareAccessRecord(){}
	public ShareAccessRecord(String shareCode,Long salePlanId,Integer salePlanType){
		this.shareCode = shareCode;
		this.salePlanId = salePlanId;
		this.salePlanType = salePlanType;
	}
	private String shareCode;
	private Long salePlanId;
	private Integer salePlanType;
	public String getShareCode() {
		return shareCode;
	}
	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
	public Long getSalePlanId() {
		return salePlanId;
	}
	public void setSalePlanId(Long salePlanId) {
		this.salePlanId = salePlanId;
	}
	public Integer getSalePlanType() {
		return salePlanType;
	}
	public void setSalePlanType(Integer salePlanType) {
		this.salePlanType = salePlanType;
	}
}
