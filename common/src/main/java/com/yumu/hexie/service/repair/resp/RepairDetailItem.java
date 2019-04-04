/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.localservice.repair.RepairConstant;
import com.yumu.hexie.model.localservice.repair.RepairOrder;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairDetailItem.java, v 0.1 2016年1月1日 上午8:05:13  Exp $
 */
public class RepairDetailItem implements Serializable {
    private static final long serialVersionUID = 6555357353040717365L;
    private long id;
    private int repairType;
    private long projectId;
    private String projectName;
    private String time;
    private String statusStr;
    private int showStatus;
    private int opStatus;
    private int status;
    private String requireDateStr;
    private int assignType;//订单分配方式

    private String memo;
    private String imgUrls;
    
    private Long orderId;
    private Float amount;
    private int payType = 1;
    
    private String address;
    private String tel;
    private String receiverName;
    private String xiaoquName;
    
    private Long operatorId;
    private String operatorCompanyName;//维修队名称
    private String operatorName;//维修工名称
    private String operatorTel; 
    
    private int commentStatus;
    private int commentQuality;
    private int commentAttitude;
    private int commentService;
    private String comment;
    private String commentImgUrls;
    
    private int cancelReasonType;
    private String cancelReason;

    private boolean finishByUser = false;
    private boolean finishByOperator = false;
    
    private Date cancelTime;
    private Date finishTime;
    private Date payReqTime;
    private Date paySuccessTime;
    //冗余字段
    private boolean publicProject = false;
    
    public static RepairDetailItem fromOrder(RepairOrder order) {
        RepairDetailItem i = new RepairDetailItem();
        BeanUtils.copyProperties(order, i);
        i.setTime(DateUtil.dtFormat(order.getCreateDate(), "yyyy-MM-dd HH:mm"));
        i.setRequireDateStr(DateUtil.dtFormat(order.getRequireDate(), "yyyy-MM-dd HH:mm"));
        String statusStr;
        
        switch (order.getStatus()){
            case RepairConstant.STATUS_CREATE:
                statusStr = "未接单";
                i.setShowStatus(RepairConstant.SHOW_STATUS_INIT);
                i.setOpStatus(RepairConstant.OP_STATUS_CANRAB);
                break;
            case RepairConstant.STATUS_CANCEL:
                statusStr = "已取消";
                i.setShowStatus(RepairConstant.SHOW_STATUS_DELETABLE);
                i.setOpStatus(RepairConstant.OP_STATUS_DELETABLE);
                break;
            case RepairConstant.STATUS_ACCEPT:
                statusStr = "已接单";
                i.setShowStatus(RepairConstant.SHOW_STATUS_CAN_FINISH);
                i.setOpStatus(RepairConstant.OP_STATUS_CANFINISH);
                break;
            case RepairConstant.STATUS_FININSH:
                statusStr = "已完成";
                i.setShowStatus(RepairConstant.SHOW_STATUS_CAN_FINISH);
                if(!order.isFinishByOperator()) {
                    i.setOpStatus(RepairConstant.OP_STATUS_CANFINISH);
                } else {
                    i.setOpStatus(RepairConstant.OP_STATUS_DELETABLE);
                }
                break;
            case RepairConstant.STATUS_PAYED:
                statusStr = "已支付";
                if(order.getCommentStatus() == RepairConstant.COMMENT_FALSE){
                    i.setShowStatus(RepairConstant.SHOW_STATUS_WAIT_COMMENT);
                    statusStr = "未评价";
                } else {
                    i.setShowStatus(RepairConstant.SHOW_STATUS_DELETABLE);
                }
                if(!order.isFinishByOperator()) {
                    i.setOpStatus(RepairConstant.OP_STATUS_CANFINISH);
                } else {
                    i.setOpStatus(RepairConstant.OP_STATUS_DELETABLE);
                }
                break;
            default:
                statusStr="";
                i.setShowStatus(0);
                i.setOpStatus(0);
                break;
        }
        i.setStatusStr(statusStr);
        return i;
    }
    
    public List<String> getImgUrlList(){
        List<String> r = new ArrayList<String>();
        if(StringUtil.isNotEmpty(imgUrls)){
            String[] urls = imgUrls.split(RepairOrder.IMG_SPLIT);
            for(String url : urls) {
                if(url.indexOf("http") >=0) {
                    r.add(url);
                }
            }
        }
        return r;
    }
    
    public List<String> getCommentImgUrlList(){
        List<String> r = new ArrayList<String>();
        if(StringUtil.isNotEmpty(commentImgUrls)){
            String[] urls = commentImgUrls.split(RepairOrder.IMG_SPLIT);
            for(String url : urls) {
                if(url.indexOf("http") >=0) {
                    r.add(url);
                }
            }
        }
        return r;
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
    public int getShowStatus() {
        return showStatus;
    }
    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }
    public String getRequireDateStr() {
        return requireDateStr;
    }
    public void setRequireDateStr(String requireDateStr) {
        this.requireDateStr = requireDateStr;
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
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Float getAmount() {
        return amount;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    public int getPayType() {
        return payType;
    }
    public void setPayType(int payType) {
        this.payType = payType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getXiaoquName() {
        return xiaoquName;
    }
    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }
    public int getCommentQuality() {
        return commentQuality;
    }
    public void setCommentQuality(int commentQuality) {
        this.commentQuality = commentQuality;
    }
    public int getCommentAttitude() {
        return commentAttitude;
    }
    public void setCommentAttitude(int commentAttitude) {
        this.commentAttitude = commentAttitude;
    }
    public int getCommentService() {
        return commentService;
    }
    public void setCommentService(int commentService) {
        this.commentService = commentService;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCommentImgUrls() {
        return commentImgUrls;
    }
    public void setCommentImgUrls(String commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }
    public int getCancelReasonType() {
        return cancelReasonType;
    }
    public void setCancelReasonType(int cancelReasonType) {
        this.cancelReasonType = cancelReasonType;
    }
    public String getCancelReason() {
        return cancelReason;
    }
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
    public Date getCancelTime() {
        return cancelTime;
    }
    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    public Date getPayReqTime() {
        return payReqTime;
    }
    public void setPayReqTime(Date payReqTime) {
        this.payReqTime = payReqTime;
    }
    public Date getPaySuccessTime() {
        return paySuccessTime;
    }
    public void setPaySuccessTime(Date paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }
    public boolean isPublicProject() {
        return publicProject;
    }
    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }
    public Long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public String getOperatorCompanyName() {
        return operatorCompanyName;
    }
    public void setOperatorCompanyName(String operatorCompanyName) {
        this.operatorCompanyName = operatorCompanyName;
    }
    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorTel() {
        return operatorTel;
    }
    public void setOperatorTel(String operatorTel) {
        this.operatorTel = operatorTel;
    }

    public boolean isFinishByUser() {
        return finishByUser;
    }

    public void setFinishByUser(boolean finishByUser) {
        this.finishByUser = finishByUser;
    }

    public boolean isFinishByOperator() {
        return finishByOperator;
    }

    public void setFinishByOperator(boolean finishByOperator) {
        this.finishByOperator = finishByOperator;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
