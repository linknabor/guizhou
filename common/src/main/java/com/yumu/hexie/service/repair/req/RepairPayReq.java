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
 * @version $Id: RepairPayReq.java, v 0.1 2016年1月1日 上午7:11:43  Exp $
 */
public class RepairPayReq implements Serializable {

    public static final int PAY_ONLINE = 1;
    public static final int PAY_OFFLINE = 2;
    private static final long serialVersionUID = -201819096849218778L;

    private long orderId;
    private float amount;
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
    
}
