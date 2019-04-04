package com.yumu.hexie.model.promotion.coupon;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;

/**
 * 
 * <pre>
 * 现金券分发规则
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CouponSeed.java, v 0.1 2016年4月11日 上午10:16:55  Exp $
 */
//现金券种子
@Entity
public class CouponSeed extends BaseModel  {
	private static final long serialVersionUID = -4076665176847269672L;

	private int seedType;//1订单分裂  2用户注册   3关注现金券   4活动发布   5订单分裂模板（2、3、5都只有最近一个是有效的）
	private Date startDate;
	private Date endDate;
	private int status;//0可用 1失效 2用完 3超时
	private String title;//用于页面展示
	private String description;
	private String ruleDescription;
	
	private String seedImg;

    private double rate = 1;//总概率
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable=false)
	private String seedStr;//现金券标识--如果来自订单则适用MD5生成标识

	private Long userId;//如果来自订单，则需要订单拥有者
	private String userImgUrl;//如果来自订单，则需要订单拥有者
	private Long bizId;//原始种子，如订单号 根据seedType而有所区别
	private Long merchantId;//只能用于特定商户订单的现金券
	
	/*****汇总信息******/
	private Integer totalCount = 0;
	private Float totalAmount = 0f;
	private Float receivedAmount = 0f;
	private Integer receivedCount = 0;
	private Float usedAmount = 0f;
	private Integer usedCount = 0;
	/*****汇总信息******/

	@Transient
	public String getState(){
		return "closed";
	}
	@Transient
	public boolean isCanUse(){
		return status == ModelConstant.COUPON_SEED_STATUS_AVAILABLE
				|| getTotalCount() - getReceivedCount() == 0;
	}
	@Transient
	public void update(CouponSeed seed) {
		setSeedType(seed.getSeedType());
		setStartDate(seed.getStartDate());
		setEndDate(seed.getEndDate());
		setStatus(seed.getStatus());
		setTitle(seed.getTitle());
		setDescription(seed.getDescription());
		setRuleDescription(seed.getRuleDescription());
		setRate(seed.getRate());
		if(seedType == ModelConstant.COUPON_SEED_ACTIVITY) {
			setSeedStr(seed.getSeedStr());//如果不为空数据库中将不会更新
		}
		//setRuleCount(seed.getRuleCount());
		//setTotalCouponAmount(seed.getTotalCouponAmount());
	}
	
	@Transient
	public void updateTotal(List<CouponRule> rules) {
		if(rules != null) {
			totalCount = receivedCount = usedCount =  0;
			totalAmount = receivedAmount = usedAmount = 0f;
			for(CouponRule rule : rules) {
//				if(rule.getStatus() == ModelConstant.COUPON_RULE_STATUS_INVALID)
				//无效的也会加入
				totalCount += rule.getTotalCount();
				receivedCount += rule.getReceivedCount();
				usedCount += rule.getUsedCount();
				
				totalAmount += rule.getTotalAmount();
				receivedAmount += rule.getReceivedAmount();
				usedAmount += rule.getUsedAmount();
			}
		}
		
	}
//	@Transient
//	public void removeRule(CouponRule rule) {
//		totalCount -= rule.getTotalCount();
//		totalAmount -= rule.getTotalAmount();
//	}
//
//	@Transient
//	public void addReceived(Coupon coupon){
//		receivedCount ++;
//		receivedAmount += coupon.getAmount();
//	}
//
//	@Transient
//	public void addUsed(Coupon coupon){
//		usedCount ++;
//		usedAmount += coupon.getAmount();
//	}
	
	public int getSeedType() {
		return seedType;
	}
	public void setSeedType(int seedType) {
		this.seedType = seedType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public String getSeedStr() {
		if(StringUtil.isEmpty(seedStr)) {
			seedStr = getGeneratedCouponSeedStr();
		}
		return seedStr;
	}
	public void setSeedStr(String seedStr) {
		this.seedStr = seedStr;
	}
	public Float getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(Float receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public Float getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(Float usedAmount) {
		this.usedAmount = usedAmount;
	}
	public Integer getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Integer getReceivedCount() {
		return receivedCount;
	}
	public void setReceivedCount(Integer receivedCount) {
		this.receivedCount = receivedCount;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBizId() {
		return bizId;
	}
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}
	@JsonIgnore
	@Transient
	public String getGeneratedCouponSeedStr() {
		if(ModelConstant.COUPON_SEED_ORDER_BUY == seedType){
			return DigestUtils.md5Hex(ModelConstant.COUPON_SEED_ORDER_BUY+"-"+userId+"-"+bizId);
		}
		else {
			return DigestUtils.md5Hex(seedType+"--"+getCreateDate());
		}
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getUserImgUrl() {
		return userImgUrl;
	}
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}
	public String getSeedImg() {
		return seedImg;
	}
	public void setSeedImg(String seedImg) {
		this.seedImg = seedImg;
	}
	
}
