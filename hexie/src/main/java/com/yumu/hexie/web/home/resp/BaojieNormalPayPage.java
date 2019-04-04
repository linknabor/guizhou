/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BaojieNormalPayPage.java, v 0.1 2016年5月24日 上午9:28:58  Exp $
 */
public class BaojieNormalPayPage implements Serializable {

    private static final long serialVersionUID = -7522498896467329710L;

    private ServiceItem item;
    private Address address;
    private List<Coupon> coupons;
    public ServiceItem getItem() {
        return item;
    }
    public void setItem(ServiceItem item) {
        this.item = item;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public List<Coupon> getCoupons() {
        return coupons;
    }
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
    
}
