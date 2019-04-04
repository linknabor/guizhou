/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeBillItem.java, v 0.1 2016年3月29日 下午3:23:18  Exp $
 */
@Entity
public class HomeBillItem extends BaseModel {

    private static final long serialVersionUID = 1517529651954781883L;
    private long billId;//订单编号
    private long billType;//大类型
    
    //服务项ID
    private long parentType;//小类型
    private long serviceId;
    private float count;
    private BigDecimal price;
    private String title;
    private String logo;
    @JsonIgnore
    public BigDecimal getAmount(){
        if(price == null || count == 0) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(count));
    }
    public float getCount() {
        return count;
    }
    public void setCount(float count) {
        this.count = count;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public long getBillId() {
        return billId;
    }
    public void setBillId(long billId) {
        this.billId = billId;
    }
    public long getBillType() {
        return billType;
    }
    public void setBillType(long billType) {
        this.billType = billType;
    }
    public long getServiceId() {
        return serviceId;
    }
    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }
    public long getParentType() {
        return parentType;
    }
    public void setParentType(long parentType) {
        this.parentType = parentType;
    }
}
