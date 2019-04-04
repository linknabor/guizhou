/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.resp;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.localservice.repair.RepairConstant;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.localservice.repair.RepairSeed;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairListItem.java, v 0.1 2016年1月1日 上午8:02:33  Exp $
 */
public class RepairListItem implements Serializable {

    private static final long serialVersionUID = -4406482359216279855L;

    private long id;
    private int repairType;
    private long projectId;
    private String typeName;
    private String typeImg;
    private String projectName;
    private String time;
    private String statusStr;
    private int status = RepairConstant.STATUS_CREATE;
    
    public RepairListItem() {
        
    }
    
    public RepairListItem(RepairSeed seed) {
        id = seed.getRepairOrderId();
        repairType = seed.getRepairType();
        projectId = seed.getProjectId();
        projectName = seed.getProjectName();
        time = seed.getTime();
        long duration = System.currentTimeMillis() - seed.getCreateDate();
        if(duration>3600*1000*24) {
            statusStr = "已等" + duration/(3600*1000*24) +"天";
        } else if(duration>3600*1000) {
            statusStr = "已等" + duration/(3600*1000) +"小时";
        } else if(duration>60*1000) {
            statusStr = "已等" + duration/(60*1000) +"分钟";
        } else if(duration>0) {
            statusStr = "刚刚";
        } else {
            statusStr = "";
        }
    }
    
    public RepairListItem(RepairOrder order) {
        id = order.getId();
        repairType = order.getRepairType();
        projectId = order.getProjectId();
        projectName = order.getProjectName();
        time = DateUtil.dtFormat(order.getRequireDate(),"MM.dd HH:mm");
        switch (order.getStatus()){
            case RepairConstant.STATUS_CREATE:
                statusStr = "未接单";
                break;
            case RepairConstant.STATUS_CANCEL:
                statusStr = "已取消";
                break;
            case RepairConstant.STATUS_ACCEPT:
                statusStr = "已接单";
                break;
            case RepairConstant.STATUS_FININSH:
                statusStr = "已完成";
                break;
            case RepairConstant.STATUS_PAYED:
                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                statusStr = "维修费￥"+ decimalFormat.format(order.getAmount());
                break;
            default:
                statusStr="";
                break;
        }
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public String getStatusStr() {
        return statusStr;
    }
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(String typeImg) {
        this.typeImg = typeImg;
    }
}
