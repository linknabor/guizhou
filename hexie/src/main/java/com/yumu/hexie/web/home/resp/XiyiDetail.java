/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: XiyiDetail.java, v 0.1 2016年4月14日 上午9:04:57  Exp $
 */
public class XiyiDetail implements Serializable {
    public XiyiDetail(){}
    public XiyiDetail(YunXiyiBill bill) {
        try {
            BeanUtils.copyProperties(bill,this);
            Date sd = bill.getRequireDate();
            if(bill.getServiceDate()!= null) {
                sd = bill.getServiceDate();
            }
            setServiceDateStr(DateUtil.dtFormat(sd,"yyyy.MM.dd HH:mm"));
            setCreateDateStr(DateUtil.dtFormat(new Date(bill.getCreateDate()),"yyyy.MM.dd HH:mm"));
        } catch (Exception e) {
        }
    }
    private static final long serialVersionUID = -2853311912311861239L;
    private long id;
    private String billLogo;
    private String orderNo;

    private String serviceDateStr;//商户确认日期
    private String createDateStr;//预约日期
    
    private int status; //各业务自己处理

    private String address;
    private String tel;
    private String receiverName;
    
    private List<HomeBillItem> items;
    
    private String memo;
    
    //支付信息
    private BigDecimal amount;
    private BigDecimal realAmount;//实付金额
    
    public String getDiscountAmount() {
        if(amount.equals(realAmount)) {
            return "0";
        } else {
            return amount.subtract(realAmount).toString();
        }
    }

    private String receiveOperator;
    private String receiveOperatorTel;
    private String sendOperatorName;
    private String sendOperatorTel;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getBillLogo() {
        return billLogo;
    }
    public void setBillLogo(String billLogo) {
        this.billLogo = billLogo;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getServiceDateStr() {
        return serviceDateStr;
    }
    public void setServiceDateStr(String serviceDateStr) {
        this.serviceDateStr = serviceDateStr;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
    public List<HomeBillItem> getItems() {
        return items;
    }
    public void setItems(List<HomeBillItem> items) {
        this.items = items;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getRealAmount() {
        return realAmount;
    }
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
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
    public String getCreateDateStr() {
        return createDateStr;
    }
    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
}
