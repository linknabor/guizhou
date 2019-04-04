/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceOperator.java, v 0.1 2016年1月1日 上午6:51:17  Exp $
 */
@Entity
public class ServiceOperator  extends BaseModel {

    private static final long serialVersionUID = 3121212638250089934L;
    
    private int type;
    
    private String companyName;//维修队名称
    private String name;//维修工名称
    private String tel;
    private String openId;//设置用户ID的时候将其匹配
    private long userId;
    

    private Long regionId;//东湖e家园区域ID
    private boolean fromWuye = false;
    private double longitude;
    private double latitude;
    
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Long getRegionId() {
        return regionId;
    }
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    public boolean isFromWuye() {
        return fromWuye;
    }
    public void setFromWuye(boolean fromWuye) {
        this.fromWuye = fromWuye;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    
}