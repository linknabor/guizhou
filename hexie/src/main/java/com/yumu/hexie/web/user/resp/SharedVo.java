package com.yumu.hexie.web.user.resp;

import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;

public class SharedVo {

	private Address address;
	private User buyer;
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	
}
