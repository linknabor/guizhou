package com.yumu.hexie.web.common.req;

import java.io.Serializable;

public class ShareInfoReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private Integer salePlanType;
	private Long salePlanId;
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
	
}
