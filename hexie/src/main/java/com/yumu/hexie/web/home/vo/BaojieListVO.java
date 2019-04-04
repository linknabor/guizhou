/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.bill.BaojieBill;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BaojieListVO.java, v 0.1 2016年5月27日 下午3:53:50  Exp $
 */
public class BaojieListVO implements Serializable {

    private static final long serialVersionUID = -7522498896467329710L;
    
    private long id;
    private String orderNo;

    private String billLogo;
    private String typeName;//项目类型
    private String projectName;//项目名称，非日常保洁需要显示
    
    private int status;
    private String statusStr; 

    private String itemCount;//服务项个数，冗余，一对多的时候取其中一个

    private String requireDateStr;
    private String createDateStr;
    
    public BaojieListVO(){}
    /**
 * 1. ORDER_STATUS_CREATE
 *    2. ORDER_STATUS_PAYED
 *    3. ORDER_STATUS_ACCEPT(后台设置)
 *    4. ORDER_STATUS_SIGNED
 *    5. ORDER_STATUS_CANCELED_TIMEOUT
 *    6. ORDER_STATUS_SERVICED
     * @param bill
     */
    public BaojieListVO(BaojieBill bill) {
        if(bill == null) {
            return;
        }
        BeanUtils.copyProperties(bill, this, "itemCount");
        setItemCount(String.format("%.1f",bill.getItemCount()));
        setRequireDateStr(DateUtil.dtFormat(bill.getRequireDate(), "yyyy-MM-dd HH:mm"));
        setCreateDateStr(DateUtil.dtFormat(new Date(bill.getCreateDate()), "yyyy-MM-dd HH:mm"));
        switch(this.status){
            case HomeServiceConstant.ORDER_STATUS_CREATE:
                setStatusStr("待支付");
                break;
            case HomeServiceConstant.ORDER_STATUS_PAYED:
                setStatusStr("已支付");
                break;
            case HomeServiceConstant.ORDER_STATUS_ACCEPT:
                setStatusStr("预约成功");
                break;
            case HomeServiceConstant.ORDER_STATUS_SIGNED:
                setStatusStr("订单确认");
                break;
            case HomeServiceConstant.ORDER_STATUS_CANCELED_TIMEOUT:
            case HomeServiceConstant.ORDER_STATUS_CANCELED_USER:
                setStatusStr("已取消");
                break;
            case HomeServiceConstant.ORDER_STATUS_SERVICED:
                setStatusStr("完成");
                break;
            default:
                setStatusStr("已取消");
                break;
        }
        //FIXME
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getBillLogo() {
        return billLogo;
    }
    public void setBillLogo(String billLogo) {
        this.billLogo = billLogo;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getStatusStr() {
        return statusStr;
    }
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
    public String getItemCount() {
        return itemCount;
    }
    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }
    public String getRequireDateStr() {
        return requireDateStr;
    }
    public void setRequireDateStr(String requireDateStr) {
        this.requireDateStr = requireDateStr;
    }
    public String getCreateDateStr() {
        return createDateStr;
    }
    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
