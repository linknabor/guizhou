/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.vo.req;

import java.io.Serializable;
import java.util.Date;

/**
 * 维修单
 * @author tongqian.ni
 * @version $Id: RepairOrder.java, v 0.1 2016年1月1日 下午4:50:11  Exp $
 */
public class RepairOrderReq implements Serializable {

    private static final long serialVersionUID = 2557963293779975186L;

    private long projectId;
    private long addressId;
    private int assignType;//订单分配方式
    private String memo;
    private String imgUrls;
    private String requireDateStr;
    private Date requireDate;
    public long getProjectId() {
        return projectId;
    }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
    public int getAssignType() {
        return assignType;
    }
    public void setAssignType(int assignType) {
        this.assignType = assignType;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getImgUrls() {
        return imgUrls;
    }
    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }
    public Date getRequireDate() {
        return requireDate;
    }
    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }
    public String getRequireDateStr() {
        return requireDateStr;
    }
    public void setRequireDateStr(String requireDateStr) {
        this.requireDateStr = requireDateStr;
    }
    
}
