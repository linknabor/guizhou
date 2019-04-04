/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 服务项标签属性（颜色，尺寸，材质等）
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceItemTag.java, v 0.1 2016年3月24日 下午6:34:10  Exp $
 */
@Entity
public class ServiceItemTag extends BaseModel {

    private static final long serialVersionUID = 5300971067806468443L;
    private long itemId;
    private String tagName;
    private BigDecimal price;
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
