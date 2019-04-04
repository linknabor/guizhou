/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 到家服务产品配置
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceItem.java, v 0.1 2016年3月24日 下午1:47:07  Exp $
 */
@Entity
public class ServiceItem extends BaseModel {
    private static final long serialVersionUID = -2312170260993749078L;
    private String imageUrl;
    private String title;
    private BigDecimal price;
    @Column(length=1023)
    private String description;
    @Column(length=1023)
    private String extraDesc1;
    @Column(length=2047)
    private String extraDesc2;
    private long type;
    @Transient//用户页面绑定
    private int count = 0;
    @JsonIgnore
    private BigDecimal settleRate;
    @JsonIgnore
    private BigDecimal settleAmount;
    //运费模板
    private Long shipTemplate;
    
    private int status = HomeServiceConstant.SERVICE_ITEM_STATUS_VALID;
    public ServiceItem(){}
    public ServiceItem(long id, String title,BigDecimal price, String imageUrl){
        setId(id);
        setTitle(title);
        setPrice(price);
        setImageUrl(imageUrl);
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExtraDesc1() {
        return extraDesc1;
    }
    public void setExtraDesc1(String extraDesc1) {
        this.extraDesc1 = extraDesc1;
    }
    public String getExtraDesc2() {
        return extraDesc2;
    }
    public void setExtraDesc2(String extraDesc2) {
        this.extraDesc2 = extraDesc2;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public BigDecimal getSettleRate() {
        return settleRate;
    }
    public void setSettleRate(BigDecimal settleRate) {
        this.settleRate = settleRate;
    }
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }
    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }
    public Long getShipTemplate() {
        return shipTemplate;
    }
    public void setShipTemplate(Long shipTemplate) {
        this.shipTemplate = shipTemplate;
    }
}
