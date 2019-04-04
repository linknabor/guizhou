package com.yumu.hexie.model.market;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.market.saleplan.SalePlan;

/**
 * 搭配元素
 * @author Administrator
 *
 */
@Entity
public class CollocationItem extends BaseModel {

	private static final long serialVersionUID = 1470693413046624600L;

	private int salePlanType;//orderType
	private long salePlanId;
	private int status = ModelConstant.COLLOCATION_STATUS_AVAILABLE;//跟Colloctation一致，只是为了查询方便
	
	/** 冗余信息 **/
	private String productPic;
	private String productName;
	private Float oriPrice;
	private Float price;
	private Integer limitNumOnce;
	private String ruleName;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH }, optional = true)
    @JoinColumn(name = "collocationId")
	private Collocation collocation;
	
	@Transient
	private int count;
	@Transient
	private boolean selected = false;
	
	public CollocationItem(){}
	public CollocationItem(Product product, SalePlan salePlan){
		this.salePlanType = salePlan.getSalePlanType();
		this.salePlanId = salePlan.getId();
		this.price = salePlan.getPrice();
		this.ruleName = salePlan.getName();
		this.limitNumOnce = salePlan.getLimitNumOnce();
		
		this.productPic = product.getSmallPicture();
		this.productName = product.getName();
		this.oriPrice = product.getOriPrice();
	}
	public int getSalePlanType() {
		return salePlanType;
	}
	public void setSalePlanType(int salePlanType) {
		this.salePlanType = salePlanType;
	}
	public long getSalePlanId() {
		return salePlanId;
	}
	public void setSalePlanId(long salePlanId) {
		this.salePlanId = salePlanId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Collocation getCollocation() {
		return collocation;
	}
	public void setCollocation(Collocation collocation) {
		this.collocation = collocation;
	}
	
	@Transient
	public void setCollocationId(long collocationId) {
		this.collocation = new Collocation();
		this.collocation.setId(collocationId);
	}
	public Float getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(Float oriPrice) {
		this.oriPrice = oriPrice;
	}
	public Integer getLimitNumOnce() {
		return limitNumOnce;
	}
	public void setLimitNumOnce(Integer limitNumOnce) {
		this.limitNumOnce = limitNumOnce;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
