/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.repair.req.RepairComment;
import com.yumu.hexie.vo.req.RepairOrderReq;

/**
 * 维修单
 * @author tongqian.ni
 * @version $Id: RepairOrder.java, v 0.1 2016年1月1日 下午4:50:11  Exp $
 */
@Entity
public class RepairOrder extends BaseModel {
    public static final String IMG_SPLIT = ",";

    private static final long serialVersionUID = 2557963293779975186L;

    private int repairType;
    private long projectId;
    private long addressId;
    private long userId;
    private Date requireDate;
    private int assignType;//订单分配方式
    @Column(length=255)
    private String memo;
    @Column(length=1023)
    private String imgUrls;
    
    private String orderNo;
    
    private boolean imageUploaded = false;
    
    private int status = RepairConstant.STATUS_CREATE; //1.创建  2.已取消 3.已接单 4.已完工 5.已支付 
    
    private Long orderId;
    private Float amount;
    private int payType;//1. 线下支付 2.微信支付
    
    private String address;
    private String tel;
    private String receiverName;
    private String xiaoquName;
    
    private Long operatorId;
    private Long operatorUserId;
    private String operatorCompanyName;//维修队名称
    private String operatorName;//维修工名称
    private String operatorTel; 
    
    private int commentStatus = RepairConstant.COMMENT_FALSE;//1.已评价 2.待评价
    private int commentQuality;
    private int commentAttitude;
    private int commentService;
    private String comment;
    @Column(length=1023)
    private String commentImgUrls;
    
    private boolean userDeleted = false;
    private boolean operatorDeleted = false;
    
    private int cancelReasonType;
    private String cancelReason;

    private boolean finishByUser = false;
    private boolean finishByOperator = false;
    private Date cancelTime;
    private Date finishTime;
    private Date operatorFinishTime;
    private Date payReqTime;
    private Date paySuccessTime;
    //冗余字段
    private boolean publicProject = false;
    private String projectName;
    private String openId;
    
    public RepairOrder(){ setOrderNo(OrderNoUtil.generateRepairOrderNo());}
    public RepairOrder(RepairOrderReq req, User user, RepairProject project, Address address){
        setRepairType(project.getRepairType());
        setProjectId(project.getId());
        setAddressId(address.getId());
        setAddress(address.getCity() + address.getCounty() +address.getXiaoquName()+ address.getDetailAddress());
        setUserId(user.getId());
        
        setRequireDate(req.getRequireDate());
        setMemo(req.getMemo());
        setImgUrls(req.getImgUrls());
        setAssignType(req.getAssignType());
        
        setTel(address.getTel());
        setReceiverName(address.getReceiveName());
        setXiaoquName(address.getXiaoquName());
        
        setProjectName(project.getName());
        setPublicProject(project.isPublicProject());
        setOpenId(user.getOpenid());
        
        setOrderNo(OrderNoUtil.generateRepairOrderNo());
    }

    public void accept(ServiceOperator ro){
        operatorId = ro.getId();
        operatorUserId = ro.getUserId();
        operatorCompanyName = ro.getCompanyName();//维修队名称
        operatorName = ro.getName();//维修工名称
        operatorTel = ro.getTel(); 
        status = RepairConstant.STATUS_ACCEPT;
    }
    public boolean canFinish(boolean byUser) {
        return (
                (byUser&&!this.finishByUser)&&
                
                (this.status == RepairConstant.STATUS_FININSH
                ||this.status == RepairConstant.STATUS_ACCEPT)
               )
                ||((!byUser&&!this.finishByOperator)&&
                (this.status == RepairConstant.STATUS_FININSH
                        ||this.status == RepairConstant.STATUS_PAYED
                        ||this.status == RepairConstant.STATUS_ACCEPT))
                        ;
    }
    public void finish(boolean byUser){
        if(byUser) {
            this.finishByUser = true;
            this.status = RepairConstant.STATUS_FININSH;
            this.finishTime = new Date();
        } else {
            this.finishByOperator = true;
            this.status = this.status == RepairConstant.STATUS_PAYED ?RepairConstant.STATUS_PAYED:RepairConstant.STATUS_FININSH ;
            this.operatorFinishTime = new Date();
        }
    }

    public void cancel(int reasonType,String reason){
        this.status = RepairConstant.STATUS_CANCEL;
        this.cancelReasonType = reasonType;
        this.cancelReason = reason;
    }

    public void deleteByUser(){
        this.userDeleted = true;
    }
    public void deleteByOperator(){
        this.operatorDeleted = true;
    }
    public void requestPay(long orderId,float amount){
        this.payType = RepairConstant.PAY_TYPE_ONLINE;
        this.amount = amount;
        this.orderId = orderId;
        this.payReqTime = new Date();
    }
    public void paySuccess(){
        this.payType = RepairConstant.PAY_TYPE_ONLINE;
        this.status = RepairConstant.STATUS_PAYED;
        this.paySuccessTime = new Date();
    }
    public void payOffline(float amount){
        this.amount = amount;
        this.payType = RepairConstant.PAY_TYPE_OFFLINE;
        this.status = RepairConstant.STATUS_PAYED;
        this.paySuccessTime = new Date();
        
    }
    public void comment(RepairComment rc) {
        this.comment = rc.getComment();
        this.commentAttitude = rc.getCommentAttitude();
        this.commentImgUrls = rc.getCommentImgUrls();
        this.commentQuality = rc.getCommentQuality();
        this.commentService = rc.getCommentService();
        this.commentStatus = RepairConstant.COMMENT_TRUE;
        this.imageUploaded = false;
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
    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
    public Date getRequireDate() {
        return requireDate;
    }
    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getCommentStatus() {
        return commentStatus;
    }
    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
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
    public String getCommentImgUrls() {
        return commentImgUrls;
    }
    public void setCommentImgUrls(String commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
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
    public boolean isUserDeleted() {
        return userDeleted;
    }
    public void setUserDeleted(boolean userDeleted) {
        this.userDeleted = userDeleted;
    }
    public boolean isOperatorDeleted() {
        return operatorDeleted;
    }
    public void setOperatorDeleted(boolean operatorDeleted) {
        this.operatorDeleted = operatorDeleted;
    }
    public boolean isPublicProject() {
        return publicProject;
    }
    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
    public Long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public Long getOperatorUserId() {
        return operatorUserId;
    }
    public void setOperatorUserId(Long operatorUserId) {
        this.operatorUserId = operatorUserId;
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
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public boolean isFinishByOperator() {
        return finishByOperator;
    }
    public void setFinishByOperator(boolean finishByOperator) {
        this.finishByOperator = finishByOperator;
    }
    public boolean isImageUploaded() {
        return imageUploaded;
    }
    public void setImageUploaded(boolean imageUploaded) {
        this.imageUploaded = imageUploaded;
    }
    public Date getOperatorFinishTime() {
        return operatorFinishTime;
    }
    public void setOperatorFinishTime(Date operatorFinishTime) {
        this.operatorFinishTime = operatorFinishTime;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
