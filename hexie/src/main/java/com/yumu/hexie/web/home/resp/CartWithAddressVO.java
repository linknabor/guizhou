/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;

import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.user.Address;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CartWithAddressVO.java, v 0.1 2016年3月29日 下午3:48:40  Exp $
 */
public class CartWithAddressVO implements Serializable {

    private static final long serialVersionUID = -7812593077115232849L;
    private Address address;
    private HomeCart cart;
    private ShipFeeTplVO shipFee;
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public HomeCart getCart() {
        return cart;
    }
    public void setCart(HomeCart cart) {
        this.cart = cart;
    }
    public ShipFeeTplVO getShipFee() {
        return shipFee;
    }
    public void setShipFee(ShipFeeTplVO shipFee) {
        this.shipFee = shipFee;
    }
    
}
