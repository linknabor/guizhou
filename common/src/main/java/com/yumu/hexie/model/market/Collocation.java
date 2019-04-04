package com.yumu.hexie.model.market;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;

/**
 * 搭配
 */
@Entity
public class Collocation extends BaseModel {

	private static final long serialVersionUID = -7286282717925230136L;

	private String title;
	private String description;
	private int salePlanType;//一种组合如果有多种销售方案，将不可处理
	
	private Float satisfyAmount;//满减底价
	private Float discountAmount;//满减金额
	
	private Float freeShipAmount;//免邮费价
	private Float shipAmount;//搭配邮费
	@JsonIgnore
	private Float postageFee;


	@Transient
	private List<CollocationItem> products;
	
	@JsonIgnore
    @OneToMany(targetEntity = CollocationItem.class, fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH}, mappedBy = "collocation")
    @Fetch(FetchMode.SUBSELECT)
	private List<CollocationItem> items;
	@Transient
	private String salePlanIds;//ruleId的组合，逗号分隔

	@Column(nullable=true)
	private long timeoutForPay;
	
	private int status = ModelConstant.COLLOCATION_STATUS_AVAILABLE;//
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Float getSatisfyAmount() {
		return satisfyAmount;
	}
	public void setSatisfyAmount(Float satisfyAmount) {
		this.satisfyAmount = satisfyAmount;
	}
	public Float getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Float discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Float getFreeShipAmount() {
		return freeShipAmount;
	}
	public void setFreeShipAmount(Float freeShipAmount) {
		this.freeShipAmount = freeShipAmount;
	}
	public Float getShipAmount() {
		return shipAmount;
	}
	public void setShipAmount(Float shipAmount) {
		this.shipAmount = shipAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSalePlanType() {
		return salePlanType;
	}
	public void setSalePlanType(int salePlanType) {
		this.salePlanType = salePlanType;
	}
	public List<CollocationItem> getItems() {
		return items;
	}
	public void setItems(List<CollocationItem> items) {
		this.items = items;
	}
	public long getTimeoutForPay() {
		return timeoutForPay;
	}
	public void setTimeoutForPay(long timeoutForPay) {
		this.timeoutForPay = timeoutForPay;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSalePlanIds() {
		return salePlanIds;
	}
	public void setSalePlanIds(String salePlanIds) {
		this.salePlanIds = salePlanIds;
	}
	public List<CollocationItem> getProducts() {
		return products;
	}
	public void setProducts(List<CollocationItem> products) {
		this.products = products;
	}
	public Float getPostageFee() {
		return postageFee;
	}
	public void setPostageFee(Float postageFee) {
		this.postageFee = postageFee;
	}
	
}
