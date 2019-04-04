/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.vo;

import java.math.BigDecimal;

import com.yumu.hexie.model.localservice.bill.BaojieBill;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BaojieListVO.java, v 0.1 2016年5月27日 下午3:53:50  Exp $
 */
public class BaojieDetailVO extends  BaojieListVO {

    private static final long serialVersionUID = -7522498896467329710L;
  //用户信息
    private long userId;
    //服务地址信息
    private long addressId;//有的预约不用上门所以没地址
    private String address;
    private String tel;
    private String receiverName;
    private long xiaoquId;
    private String xiaoquName;
    private String memo;

    private BigDecimal realAmount;//实付金额
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    
    public BaojieDetailVO(){}
    /**
 * 1. ORDER_STATUS_CREATE
 *    2. ORDER_STATUS_PAYED
 *    3. ORDER_STATUS_ACCEPT(后台设置)
 *    4. ORDER_STATUS_SIGNED
 *    5. ORDER_STATUS_CANCELED_TIMEOUT
 *    6. ORDER_STATUS_SERVICED
     * @param bill
     */
    public BaojieDetailVO(BaojieBill bill) {
        super(bill);
        if(bill == null) {
            return;
        }
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public long getXiaoquId() {
        return xiaoquId;
    }
    public void setXiaoquId(long xiaoquId) {
        this.xiaoquId = xiaoquId;
    }
    public String getXiaoquName() {
        return xiaoquName;
    }
    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public BigDecimal getRealAmount() {
        return realAmount;
    }
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    
}
