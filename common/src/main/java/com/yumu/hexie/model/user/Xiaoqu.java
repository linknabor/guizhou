/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: Xiaoqu.java, v 0.1 2016年4月28日 下午3:13:24  Exp $
 */
@Entity
public class Xiaoqu extends BaseModel{

    private static final long serialVersionUID = 4808669460780339640L;
    private long provinceId;
    private String province;
    private long cityId;
    private String city;
    private long countyId;
    private String county;
    
    private long xiaoquId;
    private String xiaoquName;
    
    private String xiaoquAddr;

    private double longitude;
    private double latitude;
    
    private int sort;
    public long getProvinceId() {
        return provinceId;
    }
    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public long getCityId() {
        return cityId;
    }
    public void setCityId(long cityId) {
        this.cityId = cityId;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public long getCountyId() {
        return countyId;
    }
    public void setCountyId(long countyId) {
        this.countyId = countyId;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public long getXiaoquId() {
        return xiaoquId;
    }
    public void setXiaoquId(long xiaoquId) {
        this.xiaoquId = xiaoquId;
    }
    public String getXiaoquName() {
        return xiaoquName;
    }
    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
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
    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
    public String getXiaoquAddr() {
        return xiaoquAddr;
    }
    public void setXiaoquAddr(String xiaoquAddr) {
        this.xiaoquAddr = xiaoquAddr;
    }
}
