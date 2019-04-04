package com.yumu.hexie.web.sales.resp;

import java.io.Serializable;

import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.Collocation;
import com.yumu.hexie.model.user.Address;

public class MultiBuyInfo implements Serializable{

	private static final long serialVersionUID = -2676616753491950466L;
	//地址
	private Address address;
	private Collocation collocation;
	private Cart cart;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Collocation getCollocation() {
		return collocation;
	}
	public void setCollocation(Collocation collocation) {
		this.collocation = collocation;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
