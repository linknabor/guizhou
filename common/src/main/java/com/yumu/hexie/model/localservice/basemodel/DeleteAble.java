/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: Deleteable.java, v 0.1 2016年5月18日 上午11:19:49  Exp $
 */
public interface DeleteAble {
    public boolean isUserDeleted();
    public void setUserDeleted(boolean userDeleted);
    public boolean isOperatorDeleted();
    public void setOperatorDeleted(boolean operatorDeleted);
    public boolean isMerchantDeleted();
    public void setMerchantDeleted(boolean merchantDeleted);
}
