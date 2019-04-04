/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.distribution;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 操作员服务区域设置
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceRegion.java, v 0.1 2016年2月2日 下午12:38:03  Exp $
 */
@Entity
public class ServiceRegion extends BaseModel {

    private static final long serialVersionUID = 695839881040321117L;
    //对应操作员
    private String operatorName;
    private long operatorId;
    
    //对应区域
    private int regionType;
    private long regionId;
    private String regionName;
    
    //对应的服务
    private long orderType;
    
    //服务优先级
    private int priority = 1;
    public long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
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
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public long getOrderType() {
        return orderType;
    }
    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }
}
