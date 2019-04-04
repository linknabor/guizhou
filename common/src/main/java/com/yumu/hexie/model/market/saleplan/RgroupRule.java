package com.yumu.hexie.model.market.saleplan;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.ModelConstant;

//真正的团购
@Entity
public class RgroupRule extends SalePlan {

	private static final long serialVersionUID = 4808669460780339640L;
	
	private int groupMinNum;
	private int groupMaxNum;//暂时没用
	private int groupStatus = ModelConstant.RGROUP_STAUS_GROUPING;//团购状态1 开始 2成功 3失败

	/** 售卖情况 */
	private long ownerId;//团长ID
	private int currentNum;//售卖份数

	private int ruleLimitUserCount = 0;//每个用户根据规则限制购买的数量，当值为0时不做限制
	
	private String ownerName;
	private String ownerAddr;
	private String ownerImg;
	/** 售卖情况 */
	

	@Transient
	@JsonIgnore
	public int getSalePlanType(){
		return ModelConstant.ORDER_TYPE_RGROUP;//默认特卖
	}
	private int productType;//团购商品类型

	public void updateFromBackend(RgroupRule p) {
		setLimitNumOnce(p.getLimitNumOnce());
		setRuleNo(p.getRuleNo());
		setName(p.getName());
		setStartDate(p.getStartDate());
		setEndDate(p.getEndDate());
		setPrice(p.getPrice());
		setPostageFee(p.getPostageFee());
		setFreeShippingNum(p.getFreeShippingNum());
		setSupportRegionType(p.getSupportRegionType());
		setStatus(p.getStatus());
		setGroupMinNum(p.getGroupMinNum());
		setProductType(p.getProductType());
	}
	
	@Transient
	public int getProcess() {
    	if(groupMinNum<=0) {
    		return 0;
    	}
    	return (100*currentNum/groupMinNum);
	}

	public int getGroupMinNum() {
		return groupMinNum;
	}
	public void setGroupMinNum(int groupMinNum) {
		this.groupMinNum = groupMinNum;
	}
	public int getGroupMaxNum() {
		return groupMaxNum;
	}
	public void setGroupMaxNum(int groupMaxNum) {
		this.groupMaxNum = groupMaxNum;
	}
	public int getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerAddr() {
		return ownerAddr;
	}
	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}
	public String getOwnerImg() {
		return ownerImg;
	}
	public void setOwnerImg(String ownerImg) {
		this.ownerImg = ownerImg;
	}
	public int getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(int groupStatus) {
		this.groupStatus = groupStatus;
	}

	public int getRuleLimitUserCount() {
		return ruleLimitUserCount;
	}

	public void setRuleLimitUserCount(int ruleLimitUserCount) {
		this.ruleLimitUserCount = ruleLimitUserCount;
	}
	
	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
}
	

}
