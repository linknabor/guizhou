package com.yumu.hexie.model.commonsupport.comment;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class Comment extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;

    private int orderType;//商品订单，云洗衣单，汽车单 等等
	private long orderId;
	private long productId;
	private String comments;
    @Column(length=1023)
    private String commentImgUrls;
	//线上商品评分
	private int serviceNo;//服务评分
	private int productNo;//商品评分
	private int logisticNo;//物流评分
    //线上商品评分

    //到家服务评分
    private int commentQuality;//质量
    private int commentAttitude;//态度
    private int commentService;//服务
    //到家服务评分
    
	private long userId;
	private String userName;
	private String userHeadImg;
	private String productName;
	private boolean anonymous =false;
	
    
	
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isAnonymous() {
		return anonymous;
	}
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserHeadImg() {
		return userHeadImg;
	}
	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}
	public long getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public int getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(int logisticNo) {
		this.logisticNo = logisticNo;
	}
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
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
}
