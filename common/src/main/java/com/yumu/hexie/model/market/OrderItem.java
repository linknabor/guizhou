package com.yumu.hexie.model.market;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.market.saleplan.SalePlan;

@Entity
public class OrderItem  extends BaseModel {

	private static final long serialVersionUID = -6159495377390849171L;

	private Long collocationId;
	private Long ruleId;
	private Long userId;
	private Integer count = 1;
	

	private Long productId;
	private int orderType;

	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH }, optional = true)
    @JoinColumn(name = "orderId")
	private ServiceOrder serviceOrder;

	private Float price;
	private Float oriPrice;
	private Float amount;
	
	//产品冗余信息
	private Long merchantId;
	private String productName;
	private String productPic;
	private String productThumbPic;
	private String ruleName;
	
	public OrderItem(){}
	@Transient
	public void fillDetail(SalePlan plan,Product product){
		//ruleId = plan.getId();
		orderType = plan.getSalePlanType();
		price = plan.getPrice();
		amount = plan.getPrice() * count;
		ruleName = plan.getName();
		
		productId = product.getId();
		oriPrice = product.getOriPrice();
		merchantId = product.getMerchantId();
		productName = product.getName();
		productPic = product.getMainPicture();
		productThumbPic = product.getSmallPicture();
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getProductThumbPic() {
		return productThumbPic;
	}
	public void setProductThumbPic(String productThumbPic) {
		this.productThumbPic = productThumbPic;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}
	public Long getCollocationId() {
		return collocationId;
	}
	public void setCollocationId(Long collocationId) {
		this.collocationId = collocationId;
	}
	public Float getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(Float oriPrice) {
		this.oriPrice = oriPrice;
	}
	
}
