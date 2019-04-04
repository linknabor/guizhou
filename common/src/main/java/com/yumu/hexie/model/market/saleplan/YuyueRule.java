package com.yumu.hexie.model.market.saleplan;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.ModelConstant;

//到家服务规则
@Entity
public class YuyueRule extends SalePlan {

	private static final long serialVersionUID = 4808669460780339640L;
	private int productType;//到家服务类型
	private int serviceNo;//需要服务的次数

	@Transient
	@JsonIgnore
	public int getSalePlanType(){
		return ModelConstant.ORDER_TYPE_YUYUE;//默认特卖
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public int getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}
	
}
