/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeServiceConstant.java, v 0.1 2016年4月8日 上午10:53:22  Exp $
 */
public class HomeServiceConstant {

    public static final int SERVICE_TYPE_REPAIR = 1;//FIXME 必须与数据库值一致
    public static final int SERVICE_TYPE_XIYI = 2;
    public static final int SERVICE_TYPE_BAOJIE = 3;

    public static final int SERVICE_ITEM_STATUS_VALID = 1;

    public static final int ORDER_STATUS_CREATE = 1;
    public static final int ORDER_STATUS_PAYED = 2;
    public static final int ORDER_STATUS_ACCEPT = 3;//已接受，取件中
    public static final int ORDER_STATUS_FETCHED = 4;//已上门取件
    public static final int ORDER_STATUS_SERVICED = 5;
    public static final int ORDER_STATUS_BACKED = 6;
    public static final int ORDER_STATUS_SIGNED = 7;
    public static final int ORDER_STATUS_CANCELED_USER = 8;
    public static final int ORDER_STATUS_REJECTED_MERCHANT = 9;
    public static final int ORDER_STATUS_CANCELED_TIMEOUT = 10;
    
}
