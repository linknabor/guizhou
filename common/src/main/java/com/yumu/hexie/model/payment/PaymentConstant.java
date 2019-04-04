/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.payment;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: PaymentConstant.java, v 0.1 2016年4月5日 下午3:12:23  Exp $
 */
public class PaymentConstant {

    public static final int PAY_TYPE_WECHAT = 1;
    public static final int PAY_TYPE_OFFLINE = 2;

    public static final int TYPE_MARKET_ORDER = 1;
    public static final int TYPE_XIYI_ORDER = 2;
    public static final int TYPE_BAOJIE_ORDER = 3;
    
    //1. 已提交 2. 支付成功 3. 支付失败 4.支付取消
    public static final int PAYMENT_STATUS_INIT = 1;
    public static final int PAYMENT_STATUS_SUCCESS = 2;
    public static final int PAYMENT_STATUS_FAIL = 3;
    public static final int PAYMENT_STATUS_CANCEL = 4;
    public static final int PAYMENT_STATUS_REFUNDED = 5;
    public static final int PAYMENT_STATUS_REFUNDING = 6;
    
    

    //退款发起方
    public static final int REFUND_INITIATOR_USER = 0;
    public static final int REFUND_INITIATOR_SYSTEM = 1;
    public static final int REFUND_INITIATOR_BACKEND = 2;
    public static final int REFUND_INITIATOR_MERCHANT = 3;
    public static final int REFUND_INITIATOR_ERROR = 99;
    //退款状态
    public static final int REFUND_STATUS_INIT = 0;
    public static final int REFUND_STATUS_APPLYED = 1;
    public static final int REFUND_STATUS_SUCCESS = 2;
    public static final int REFUND_STATUS_FAIL = 3;
    //退款类型
    public static final int REFUND_TYPE_PINDAN_FAIL = 0;
    public static final int REFUND_TYPE_USERAPPLY = 1;
    public static final int REFUND_TYPE_RETURN_GOODS = 2;
}
