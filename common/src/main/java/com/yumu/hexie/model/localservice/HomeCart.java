/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 到家服务购物车
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeCart.java, v 0.1 2016年3月29日 下午3:32:00  Exp $
 */
public class HomeCart extends BaseModel {

    private static final long serialVersionUID = 234022228327280674L;

    private long baseType;
    private long itemType;

    private List<HomeBillItem> items;
    
    @JsonIgnore
    public BigDecimal getAmount(){
        BigDecimal amount = BigDecimal.ZERO;
        if(items != null) {
            for(HomeBillItem i : items) {
                amount = amount.add(i.getAmount());
            } 
        }
        return amount;
    }

    public List<HomeBillItem> getItems() {
        return items;
    }

    public void setItems(List<HomeBillItem> items) {
        this.items = items;
    }

    public long getBaseType() {
        return baseType;
    }

    public void setBaseType(long baseType) {
        this.baseType = baseType;
    }

    public long getItemType() {
        return itemType;
    }

    public void setItemType(long itemType) {
        this.itemType = itemType;
    }
    
}
