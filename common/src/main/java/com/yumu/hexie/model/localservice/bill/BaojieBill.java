/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.bill;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.localservice.basemodel.CancelAble;
import com.yumu.hexie.model.localservice.basemodel.HasMerchant;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.settle.SettleConstant;

/**
 * <pre>
 * 步骤：
 *    1. ORDER_STATUS_CREATE
 *    2. ORDER_STATUS_PAYED
 *    3. ORDER_STATUS_ACCEPT(后台设置)
 *    4. ORDER_STATUS_SIGNED
 *    5. ORDER_STATUS_CANCELED_TIMEOUT
 *    7. ORDER_STATUS_CANCELED_TIMEOUT
 *    6. ORDER_STATUS_SERVICED
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BaojieBill.java, v 0.1 2016年5月18日 上午10:57:23  Exp $
 */
@Entity
public class BaojieBill extends BaseO2OService implements HasMerchant,CancelAble {

    private static final long serialVersionUID = 7642902547929637598L;

    //public static final int NEED_PREPAY = 1;
    //public static final int NO_PREPAY = 2;
    private Long merchantId;
    private String merchantName;
    private String merchantTel;
    private Date merchantConfirmTime;
    
    private int cancelReasonType;
    private String cancelReason ;
    private Date cancelTime;
    
    private String typeName;
    
    private BigDecimal totalAmount;//实付金额

    public void signed(){
        setStatus(HomeServiceConstant.ORDER_STATUS_SIGNED);
        setUserConfirmTime(new Date());
    }
    /** 
     * @return
     * @see com.yumu.hexie.model.localservice.basemodel.BaseO2OService#getOrderType()
     */
    @Transient
    @Override
    public int getOrderType() {
        return HomeServiceConstant.SERVICE_TYPE_BAOJIE;
    }

    /** 
     * @return
     * @see com.yumu.hexie.model.localservice.basemodel.BaseO2OService#getPaymentOrderType()
     */
    @Transient
    @Override
    public int getPaymentOrderType() {
        return PaymentConstant.TYPE_BAOJIE_ORDER;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantTel() {
        return merchantTel;
    }

    public void setMerchantTel(String merchantTel) {
        this.merchantTel = merchantTel;
    }

    public Date getMerchantConfirmTime() {
        return merchantConfirmTime;
    }

    public void setMerchantConfirmTime(Date merchantConfirmTime) {
        this.merchantConfirmTime = merchantConfirmTime;
    }

    public int getCancelReasonType() {
        return cancelReasonType;
    }

    public void setCancelReasonType(int cancelReasonType) {
        this.cancelReasonType = cancelReasonType;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /** 
     * @return
     * @see com.yumu.hexie.model.localservice.basemodel.BaseO2OService#getSettleType()
     */
    @Override
    public int getSettleType() {
        return SettleConstant.ORDER_TYPE_BAOJIE;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
