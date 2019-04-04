/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.assign;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.repair.RepairOrder;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AssignRecord.java, v 0.1 2016年3月30日 下午2:50:14  Exp $
 */
@Entity
public class AssignRecord extends BaseModel {
    private static final long serialVersionUID = 5102329386889429025L;
    private int orderType;
    private Long orderId;
    
    private long operatorId;
    private Long operatorUserId;
    
    private long serviceType;
    private long itemId;//默认为一个商品    产品内容 
    private String projectName;//项目标题
    
    private String time;//预约时间
    private String orderTime;//订单时间
    private long orderDate;
    
    public AssignRecord(ServiceOperator ro,RepairOrder order) {
        this.operatorId = ro.getId();
        this.operatorUserId = ro.getUserId();
        
        this.orderId = order.getId();
        this.serviceType = order.getRepairType();
        this.itemId = order.getProjectId();
        this.projectName = order.getProjectName();
        this.orderTime = DateUtil.dtFormat(order.getRequireDate(),"MM.dd HH:mm");
        this.time = DateUtil.dtFormat(new Date(order.getCreateDate()),"MM.dd HH:mm");
        this.orderDate = order.getCreateDate();
    }

    public AssignRecord(ServiceOperator ro,YunXiyiBill order) {
        this.operatorId = ro.getId();
        this.operatorUserId = ro.getUserId();
        
        this.orderId = order.getId();
        this.projectName = order.getProjectName();
        this.orderTime = DateUtil.dtFormat(order.getRequireDate(),"MM.dd HH:mm");
        this.time = DateUtil.dtFormat(new Date(order.getCreateDate()),"MM.dd HH:mm");
        this.orderDate = order.getCreateDate();
    }
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
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
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public long getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public long getServiceType() {
        return serviceType;
    }

    public void setServiceType(long serviceType) {
        this.serviceType = serviceType;
    }
    
    
}
