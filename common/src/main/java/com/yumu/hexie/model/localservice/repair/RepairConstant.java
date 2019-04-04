/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairConstant.java, v 0.1 2016年1月1日 上午6:48:39  Exp $
 */
public class RepairConstant {

    public static final int STATUS_CREATE = 1;
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_ACCEPT = 3;
    public static final int STATUS_FININSH = 4;
    public static final int STATUS_PAYED = 5;
    
    public static final int SHOW_STATUS_INIT = 1;
    public static final int SHOW_STATUS_CAN_FINISH = 2;
    public static final int SHOW_STATUS_OP_FININSH = 3;
    public static final int SHOW_STATUS_DELETABLE = 4;
    public static final int SHOW_STATUS_WAIT_COMMENT = 5;
    

    public static final int OP_STATUS_CANRAB = 1;
    public static final int OP_STATUS_CANFINISH = 2;
    public static final int OP_STATUS_DELETABLE = 3;
    
    public static final int PAY_TYPE_ONLINE = 1;
    public static final int PAY_TYPE_OFFLINE = 2;
    
    public static final int COMMENT_TRUE = 1;
    public static final int COMMENT_FALSE = 2;
    
    public static final int ORDER_OP_STATUS_UNACCEPT = 1;
    public static final int ORDER_OP_STATUS_UNFINISH = 2;
    public static final int ORDER_OP_STATUS_FINISHED = 3;
    
    public static final int PROJECT_STATUS_AVALIBLE = 1;
}
