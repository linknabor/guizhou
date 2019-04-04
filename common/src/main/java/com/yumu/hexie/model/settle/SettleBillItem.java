/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.settle;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SettleBillItem.java, v 0.1 2016年4月25日 下午5:13:17  Exp $
 */
@Entity
public class SettleBillItem extends BaseModel {

    private static final long serialVersionUID = 3295419267557628431L;

    private long settleBillId;
    private long billItemId;
    public long getSettleBillId() {
        return settleBillId;
    }
    public void setSettleBillId(long settleBillId) {
        this.settleBillId = settleBillId;
    }
    public long getBillItemId() {
        return billItemId;
    }
    public void setBillItemId(long billItemId) {
        this.billItemId = billItemId;
    }
    
    
}
