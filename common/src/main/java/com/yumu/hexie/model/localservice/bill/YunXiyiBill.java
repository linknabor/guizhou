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
import com.yumu.hexie.model.localservice.basemodel.NeedShipFee;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.settle.SettleConstant;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: YunXiyiBill.java, v 0.1 2016年3月29日 下午4:03:41  Exp $
 */
@Entity
public class YunXiyiBill extends BaseO2OService 
    implements HasMerchant,CancelAble,NeedShipFee {

    private static final long serialVersionUID = -5251807583039360700L;
    
    private String receiveOperator;
    private String receiveOperatorTel;
    private String sendOperatorName;
    private String sendOperatorTel;
    
    private Long merchantId;
    private String merchantName;
    private String merchantTel;
    private Date merchantConfirmTime;
    
    private int cancelReasonType;
    private String cancelReason ;
    private Date cancelTime;
    
    private BigDecimal shipFee;
    private long shipFeeTplId;

    private BigDecimal shipSettleFee;
    
    public void pay(long paymentId){
        setPaymentId(paymentId);
    }

    
    public void signed(){
        setStatus(HomeServiceConstant.ORDER_STATUS_SIGNED);
        setUserConfirmTime(new Date());
    }

    @Transient
    @Override
    public int getPaymentOrderType() {
        return PaymentConstant.TYPE_XIYI_ORDER;
    }
    @Transient
    @Override
    public int getOrderType() {
        return HomeServiceConstant.SERVICE_TYPE_XIYI;
    }
    public String getReceiveOperator() {
        return receiveOperator;
    }

    public void setReceiveOperator(String receiveOperator) {
        this.receiveOperator = receiveOperator;
    }

    public String getReceiveOperatorTel() {
        return receiveOperatorTel;
    }

    public void setReceiveOperatorTel(String receiveOperatorTel) {
        this.receiveOperatorTel = receiveOperatorTel;
    }

    public String getSendOperatorName() {
        return sendOperatorName;
    }

    public void setSendOperatorName(String sendOperatorName) {
        this.sendOperatorName = sendOperatorName;
    }

    public String getSendOperatorTel() {
        return sendOperatorTel;
    }

    public void setSendOperatorTel(String sendOperatorTel) {
        this.sendOperatorTel = sendOperatorTel;
    }

    @Override
    public Long getMerchantId() {
        return merchantId;
    }
    @Override
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    @Override
    public String getMerchantName() {
        return merchantName;
    }
    @Override
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public Date getMerchantConfirmTime() {
        return merchantConfirmTime;
    }
    public void setMerchantConfirmTime(Date merchantConfirmTime) {
        this.merchantConfirmTime = merchantConfirmTime;
    }
    public String getMerchantTel() {
        return merchantTel;
    }
    public void setMerchantTel(String merchantTel) {
        this.merchantTel = merchantTel;
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


    /** 
     * @return
     * @see com.yumu.hexie.model.localservice.basemodel.BaseO2OService#getSettleType()
     */
    @Override
    public int getSettleType() {
        return SettleConstant.ORDER_TYPE_XIYI;
    }


    public BigDecimal getShipFee() {
        return shipFee;
    }


    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }


    public long getShipFeeTplId() {
        return shipFeeTplId;
    }


    public void setShipFeeTplId(long shipFeeTplId) {
        this.shipFeeTplId = shipFeeTplId;
    }


    public BigDecimal getShipSettleFee() {
        return shipSettleFee;
    }


    public void setShipSettleFee(BigDecimal shipSettleFee) {
        this.shipSettleFee = shipSettleFee;
    }
}
