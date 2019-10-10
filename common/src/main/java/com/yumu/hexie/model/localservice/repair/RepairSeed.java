/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.localservice.ServiceOperator;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairSeed.java, v 0.1 2016年1月1日 下午12:34:53  Exp $
 */
@Entity
public class RepairSeed  extends BaseModel {

    private static final long serialVersionUID = 6008636437689344133L;
    
    private long operatorId;
    private Long operatorUserId;
    private long repairOrderId;
    
    private int repairType;
    private long projectId;
    private String projectName;
    private String time;
    private String orderTime;
    private long orderDate;
    
    public RepairSeed(){}
    public RepairSeed(ServiceOperator ro,RepairOrder order) {
        this.operatorId = ro.getId();
        this.operatorUserId = ro.getUserId();
        this.repairOrderId = order.getId();
        this.repairType = order.getRepairType();
        this.projectId = order.getProjectId();
     //   this.projectName = order.getProjectName();
        this.projectName = order.getMemo();//把项目内容替换为维修内容
        this.orderTime = DateUtil.dtFormat(order.getRequireDate(),"MM.dd HH:mm");
        this.time = DateUtil.dtFormat(new Date(order.getCreateDate()),"MM.dd HH:mm");
        this.orderDate = order.getCreateDate();
    }
    public long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }
    public Long getOperatorUserId() {
        return operatorUserId;
    }
    public void setOperatorUserId(Long operatorUserId) {
        this.operatorUserId = operatorUserId;
    }
    public long getRepairOrderId() {
        return repairOrderId;
    }
    public void setRepairOrderId(long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }
    public int getRepairType() {
        return repairType;
    }
    public void setRepairType(int repairType) {
        this.repairType = repairType;
    }
    public long getProjectId() {
        return projectId;
    }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public long getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    
}
