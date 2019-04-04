/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.commonsupport.comment;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 售后反馈
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AfterSale.java, v 0.1 2016年4月1日 下午7:52:28  Exp $
 */
@Entity
public class AfterSaleBill extends BaseModel {

    private static final long serialVersionUID = -5724364450361039103L;

    private int orderType;//商品订单，云洗衣单，汽车单 等等
    private long orderId;
    private long productId;
    private String comments;
    @Column(length=1023)
    private String commentImgUrls;
    private int status;
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getCommentImgUrls() {
        return commentImgUrls;
    }
    public void setCommentImgUrls(String commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    
}
