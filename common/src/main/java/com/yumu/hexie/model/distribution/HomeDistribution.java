/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.distribution;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 到家服务发布模型
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeDistribution.java, v 0.1 2016年3月29日 下午2:17:58  Exp $
 */
@Entity
public class HomeDistribution extends BaseModel {
    private static final long serialVersionUID = 829031613128121548L;

    private int regionType;
    private long regionId;
    private int itemType;//指定的是服务类型或者服务项
    private long parentTypeId;
    private long itemId;
    private int sort=0;
    public int getRegionType() {
        return regionType;
    }
    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }
    public long getRegionId() {
        return regionId;
    }
    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }
    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
    public long getParentTypeId() {
        return parentTypeId;
    }
    public void setParentTypeId(long parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    public int getItemType() {
        return itemType;
    }
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
