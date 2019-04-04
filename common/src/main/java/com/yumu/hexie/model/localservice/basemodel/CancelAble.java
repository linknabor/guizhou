/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;

import java.util.Date;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CancelAble.java, v 0.1 2016年5月18日 上午11:21:32  Exp $
 */
public interface CancelAble {
    public int getCancelReasonType() ;
    public void setCancelReasonType(int cancelReasonType);
    public String getCancelReason() ;
    public void setCancelReason(String cancelReason) ;
    public Date getCancelTime();
    public void setCancelTime(Date cancelTime) ;
}
