package com.yumu.hexie.model.distribution;

import java.text.DecimalFormat;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.yumu.hexie.model.market.saleplan.SalePlan;
@MappedSuperclass
public class RuleDistribution extends Distribution{
	private static final long serialVersionUID = 4808669460780339640L;

	private long ruleId;
    private long ruleCloseTime;
    

	private int sortNo = 10;
    /*** 没用上 ***/
    private String ruleNo;//规则编号
    private String ruleName;//规则名称

    /** 冗余 用于展示 */
	private float postageFee;
	private int freeShippingNum;
	private float oriPrice;//市场价
    private float price;//团购价
    
    public void updateForRuleChange(SalePlan sp ) {
    	postageFee = sp.getPostageFee();
    	freeShippingNum = sp.getFreeShippingNum();
    	oriPrice = sp.getOriPrice();
    	price = sp.getPrice();
    }
    
    @Transient
    public long getLeftTime(){
    	return (ruleCloseTime - System.currentTimeMillis())/1000;
    }

    @Transient
    public String getDiscount(){
    	if(getOriPrice()<=0) {
    		return "";
    	}
    	DecimalFormat decimalFormat=new DecimalFormat("0.0");
    	return decimalFormat.format(getPrice()*10/getOriPrice())+"折";
    }
    
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public long getRuleCloseTime() {
		return ruleCloseTime;
	}
	public void setRuleCloseTime(long ruleCloseTime) {
		this.ruleCloseTime = ruleCloseTime;
	}
	public float getPostageFee() {
		return postageFee;
	}
	public void setPostageFee(float postageFee) {
		this.postageFee = postageFee;
	}
	public int getFreeShippingNum() {
		return freeShippingNum;
	}
	public void setFreeShippingNum(int freeShippingNum) {
		this.freeShippingNum = freeShippingNum;
	}
	public float getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(float oriPrice) {
		this.oriPrice = oriPrice;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
    
}
