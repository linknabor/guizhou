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
 * @version $Id: HasMerchant.java, v 0.1 2016年5月18日 上午11:30:59  Exp $
 */
public interface HasMerchant {

    public Long getMerchantId();
    public void setMerchantId(Long merchantId);

    public String getMerchantName();
    public void setMerchantName(String merchantName);

    public String getMerchantTel();
    public void setMerchantTel(String merchantTel);

    public Date getMerchantConfirmTime() ;
    public void setMerchantConfirmTime(Date merchantConfirmTime);
    
}
