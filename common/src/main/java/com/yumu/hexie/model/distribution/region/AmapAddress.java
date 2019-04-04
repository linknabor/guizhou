/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.distribution.region;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yumu.hexie.common.util.StringUtil;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AmapAddress.java, v 0.1 2016年2月19日 下午5:25:25  Exp $
 */
@Entity
public class AmapAddress implements Serializable  {

    private static final long serialVersionUID = -8917882434435903334L;
    private Double lon;
    private Double lat;
    @Column(length=100)
    @JsonProperty("_name")
    private String name;
    @Column(length=511)
    @JsonProperty("_address")
    private String address;//全称
    @Column(length=255)
    private String detailaddress;
    @Column(length=30)
    private String region;
    @Column(length=30)
    private String city;
    @JsonProperty("_createtime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @JsonProperty("_updatetime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    @JsonProperty("_location")
    @Column(length=50)
    private String location; //坐标
    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("_id")
    private long id;

    @Column(updatable=false)
    @JsonIgnore
    private Long createDate = System.currentTimeMillis();
    
    public Double getLon() {
        if(lon == null || lon == 0d) {
            initLocation();
        }
        return lon;
    }
    private void initLocation() {
        if(!StringUtil.isEmpty(location)) {
            String str[] = location.split(",");
            try{
                lon = Double.parseDouble(str[0]); 
                lat = Double.parseDouble(str[1]); 
            } catch(Exception e){}
        }
    }
    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getLat() {
        if(lon == null || lon == 0d) {
            initLocation();
        }
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDetailaddress() {
        return detailaddress;
    }
    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Date getCreatetime() {
        return createtime;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    public Date getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
        if(lon == null || lon == 0d) {
            initLocation();
        }
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Long getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}
