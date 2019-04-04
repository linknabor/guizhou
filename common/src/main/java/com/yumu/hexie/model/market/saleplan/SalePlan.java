package com.yumu.hexie.model.market.saleplan;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;

@MappedSuperclass
public class SalePlan extends BaseModel {
	private static final long serialVersionUID = 3468345175276564755L;
	private long productId;
	
	private String ruleNo;//规则编号
	private String name;//规则名称

	private int limitNumOnce;//单次购买份数
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")  
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")  
	private Date endDate;
	
    private float price;//团购价格
    private float oriPrice;//市场价

	private float postageFee;//快递费
	private int freeShippingNum;//包邮件数
	
	private String description;
	private String descriptionMore;

	private String productName;
	
	@Column(nullable=true)
	private long timeoutForPay;
	
	private int supportRegionType;//0 全城购，1邻里购

	private String tagUrl;//左上角标签URL
	private int status = ModelConstant.RULE_STATUS_ON;//1 有效 0无效
	
	@Transient
	public boolean valid(int count) {
	    if(getStatus() == ModelConstant.RULE_STATUS_OFF) {
	        return false;
        } else if(getLimitNumOnce() < count){
            return false;
        } else if(getEndDate() != null && getEndDate().getTime() < System.currentTimeMillis()){
            return false;
        }
        return true;
	}
	@Transient
	@JsonIgnore
	public int getSalePlanType(){
		return ModelConstant.ORDER_TYPE_ONSALE;//默认特卖
	}
	
	public void fillProductInfo(Product p) {
		setProductName(p.getName());
		setOriPrice(p.getOriPrice());
	}
	
	@Transient
	public String getDiscount(){
    	if(oriPrice<=0) {
    		return "";
    	}
    	DecimalFormat decimalFormat=new DecimalFormat("0.0");
    	return decimalFormat.format(price*10/oriPrice)+"折";
    }
	@Transient
	public long getLeftSeconds(){
		if(endDate == null){
			return 3600*24*7;
		}
		return (endDate.getTime()- System.currentTimeMillis())/1000;
	}
	@Transient
	public String getTypeName() {
		if(supportRegionType == ModelConstant.RULE_TYPE_CITY){
			return "全城购";
		}
		else{// if(groupType == ModelConstant.RULE_TYPE_NEARBY){
			return "邻里购";
		}
	}
	
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLimitNumOnce() {
		return limitNumOnce;
	}

	public void setLimitNumOnce(int limitNumOnce) {
		this.limitNumOnce = limitNumOnce;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

	public String getDescription() {
		if(StringUtil.isNotEmpty(description)){
			return description.replaceAll("\n", "<br/>");
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescriptionMore() {
		if(StringUtil.isNotEmpty(descriptionMore)){
			return descriptionMore.replaceAll("\n", "<br/>");
		}
		return descriptionMore;
	}

	public void setDescriptionMore(String descriptionMore) {
		this.descriptionMore = descriptionMore;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getTimeoutForPay() {
		return timeoutForPay;
	}

	public void setTimeoutForPay(long timeoutForPay) {
		this.timeoutForPay = timeoutForPay;
	}

	public int getSupportRegionType() {
		return supportRegionType;
	}

	public void setSupportRegionType(int supportRegionType) {
		this.supportRegionType = supportRegionType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public float getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(float oriPrice) {
		this.oriPrice = oriPrice;
	}

	public String getTagUrl() {
		return tagUrl;
	}

	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}

}
