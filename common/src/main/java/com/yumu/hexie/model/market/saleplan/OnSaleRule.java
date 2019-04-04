package com.yumu.hexie.model.market.saleplan;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.ModelConstant;

//特卖规则
@Entity
public class OnSaleRule extends SalePlan  {

	private static final long serialVersionUID = 4808669460780339640L;
	private int productType;//特卖商品类型
	private String ruleDesDescribe;//热卖商品或新品上市
	
	@Transient
	@JsonIgnore
	public int getSalePlanType(){
		return ModelConstant.ORDER_TYPE_ONSALE;//默认特卖
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public String getRuleDesDescribe() {
		return ruleDesDescribe;
	}
	public void setRuleDesDescribe(String ruleDesDescribe) {
		this.ruleDesDescribe = ruleDesDescribe;
	}
	
}
