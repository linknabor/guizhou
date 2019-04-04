/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.settle;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 结算单
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SettleBill.java, v 0.1 2016年4月25日 下午3:33:01  Exp $
 */
@Entity
public class SettleBill extends BaseModel {

    private static final long serialVersionUID = 6439099576495193440L;

    private long orderId;
    private int orderType;
    
    private String orderNo;
    
    private int status = SettleConstant.STATUS_PAYED;
    
    private long paymentId;
    private Long couponId;
    private Long merchantId;
    private BigDecimal amount;
    
    private Date confirmDate;
    private Date serviceFinishDate;

    private BigDecimal shipFee;
    private BigDecimal shipSettleFee;
    
    public long getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Date getServiceFinishDate() {
        return serviceFinishDate;
    }
    public void setServiceFinishDate(Date serviceFinishDate) {
        this.serviceFinishDate = serviceFinishDate;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
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
    public Date getConfirmDate() {
        return confirmDate;
    }
    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }
    public Long getCouponId() {
        return couponId;
    }
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public BigDecimal getShipFee() {
        return shipFee;
    }
    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }
    public BigDecimal getShipSettleFee() {
        return shipSettleFee;
    }
    public void setShipSettleFee(BigDecimal shipSettleFee) {
        this.shipSettleFee = shipSettleFee;
    }
    
}
