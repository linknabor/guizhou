/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: XiyiListItem.java, v 0.1 2016年4月15日 下午7:35:40  Exp $
 */
public class XiyiListItem implements Serializable {
    private static final long serialVersionUID = 342299540914632784L;
    public XiyiListItem(){}
    public XiyiListItem(YunXiyiBill bill) {
        try {
            BeanUtils.copyProperties(bill, this);
            Date sd = bill.getRequireDate();
            if(bill.getServiceDate()!= null) {
                sd = bill.getServiceDate();
            }
            setServiceDateStr(DateUtil.dtFormat(sd,"yyyy.MM.dd HH:mm"));
            setCreateDateStr(DateUtil.dtFormat(new Date(bill.getCreateDate()),"yyyy.MM.dd HH:mm"));
            switch(status){
                case HomeServiceConstant.ORDER_STATUS_CREATE:
                    statusStr = "未支付";
                    break;
                case HomeServiceConstant.ORDER_STATUS_CANCELED_USER:
                case HomeServiceConstant.ORDER_STATUS_CANCELED_TIMEOUT:
                    statusStr = "已取消";
                    break;
                case HomeServiceConstant.ORDER_STATUS_REJECTED_MERCHANT:
                    statusStr = "该区域不支持";
                    break;
                case HomeServiceConstant.ORDER_STATUS_PAYED:
                    statusStr = "已支付";
                    break;
                case HomeServiceConstant.ORDER_STATUS_ACCEPT:
                    statusStr = "等待上门取件";
                    break;
                case HomeServiceConstant.ORDER_STATUS_FETCHED:
                    statusStr = "洗衣中";
                    break;
                case HomeServiceConstant.ORDER_STATUS_SERVICED:
                    statusStr = "送回中";
                    break;
                case HomeServiceConstant.ORDER_STATUS_BACKED:
                    statusStr = "已送回";
                    break;
                case HomeServiceConstant.ORDER_STATUS_SIGNED:
                    statusStr = "已签收";
                    break;
                default:
                    statusStr = "状态异常";
                    break;
            }
        } catch (Exception e) {
        }
    }

    private long id;
    private String billLogo;
    private String orderNo;

    private String serviceDateStr;//商户确认日期
    private String createDateStr;//预约日期

    private int status; //各业务自己处理
    private String statusStr; //各业务自己处理
    private BigDecimal realAmount;
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
    public String getCreateDateStr() {
        return createDateStr;
    }
    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public BigDecimal getRealAmount() {
        return realAmount;
    }
    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }
    public String getStatusStr() {
        return statusStr;
    }
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

}
