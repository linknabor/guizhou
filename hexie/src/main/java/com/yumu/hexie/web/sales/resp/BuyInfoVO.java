package com.yumu.hexie.web.sales.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.user.Address;

public class BuyInfoVO implements Serializable{

	private static final long serialVersionUID = -2676616753491950466L;
	//商品
	private Product product;
	//地址
	private Address address;
	//规则
	private SalePlan rule;
	public BuyInfoVO(){}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public SalePlan getRule() {
		return rule;
	}
	public void setRule(SalePlan rule) {
		this.rule = rule;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
}
