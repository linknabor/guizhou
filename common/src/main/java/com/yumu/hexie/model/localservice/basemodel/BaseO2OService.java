/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.promotion.coupon.Coupon;

/**
 * <pre>
 * 到家服务单
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeService.java, v 0.1 2016年3月24日 下午3:32:49  Exp $
 */
@MappedSuperclass
public abstract class BaseO2OService  extends BaseModel {

    private static final long serialVersionUID = 1077724205190579700L;

    @Column(length=60)
    private String orderNo;
    
    private int status; //各业务自己处理
    //默认的服务类型及服务项，用于显示
    @Column(nullable=true)
    private long itemType;
    @Column(nullable=true)
    private long itemId;//服务ID，冗余，一对多的时候取其中一个
    private float itemCount;//服务项个数，冗余，一对多的时候取其中一个
    
    private String billLogo;
    private String projectName;//项目名称，用于展示
    
    @Transient
    private List<HomeBillItem> items;
    
    @Column(length=255)
    private String memo;
    
    private Long couponId;
    @Transient
    private Coupon coupon;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    //预约基础配置
    private Date requireDate;//预约日期
    //服务时间（前端展示用）
    private Date serviceDate;
    private Date userConfirmTime;
    
    //用户信息
    private long userId;
    //服务地址信息
    @Column(nullable=true)
    private long addressId;//有的预约不用上门所以没地址
    @Column(length=200)
    private String address;
    @Column(length=20)
    private String tel;
    @Column(length=100)
    private String receiverName;
    @Column(nullable=true)
    private long xiaoquId;
    @Column(length=100)
    private String xiaoquName;
    
    //支付信息
    private BigDecimal amount;
    private BigDecimal realAmount;//实付金额
    private Long paymentId;
    
    public abstract int getOrderType();

    public abstract int getPaymentOrderType();
    public abstract int getSettleType();
    public boolean payable(){
        return getStatus() == HomeServiceConstant.ORDER_STATUS_CREATE;
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
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

    public String getXiaoquName() {
        return xiaoquName;
    }

    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Date getUserConfirmTime() {
        return userConfirmTime;
    }

    public void setUserConfirmTime(Date userConfirmTime) {
        this.userConfirmTime = userConfirmTime;
    }



    public long getXiaoquId() {
        return xiaoquId;
    }

    public void setXiaoquId(long xiaoquId) {
        this.xiaoquId = xiaoquId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public List<HomeBillItem> getItems() {
        return items;
    }

    public void setItems(List<HomeBillItem> items) {
        this.items = items;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }
    public String getBillLogo() {
        return billLogo;
    }
    public void setBillLogo(String billLogo) {
        this.billLogo = billLogo;
    }
    public long getItemType() {
        return itemType;
    }
    public void setItemType(long itemType) {
        this.itemType = itemType;
    }
    public float getItemCount() {
        return itemCount;
    }
    public void setItemCount(float itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
