/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.req;

import java.io.Serializable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairCancelReq.java, v 0.1 2016年1月1日 上午7:25:20  Exp $
 */
public class RepairCancelReq implements Serializable {

    private static final long serialVersionUID = -2374472725016301740L;
    private long orderId;
    private int cancelReasonType;
    private String cancelReason;
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
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    
}
