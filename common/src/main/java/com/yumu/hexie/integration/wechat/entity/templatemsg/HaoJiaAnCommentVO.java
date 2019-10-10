package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HaoJiaAnCommentVO implements Serializable{
	@JsonProperty("first")
	private TemplateItem title;//标题
	
	@JsonProperty("keyword1")
	private TemplateItem userName;//投诉人
	
	@JsonProperty("keyword2")
	private TemplateItem userTel;//投诉电话
	
	@JsonProperty("keyword3")
	private TemplateItem reason;//投诉事由
	
	@JsonProperty("keyword4")
	private TemplateItem orderNo;//订单编号
	
	@JsonProperty("remark")
	private TemplateItem memo;//备注

	public TemplateItem getTitle() {
		return title;
	}

	public void setTitle(TemplateItem title) {
		this.title = title;
	}

	public TemplateItem getUserName() {
		return userName;
	}

	public void setUserName(TemplateItem userName) {
		this.userName = userName;
	}

	public TemplateItem getUserTel() {
		return userTel;
	}

	public void setUserTel(TemplateItem userTel) {
		this.userTel = userTel;
	}

	public TemplateItem getReason() {
		return reason;
	}

	public void setReason(TemplateItem reason) {
		this.reason = reason;
	}

	public TemplateItem getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(TemplateItem orderNo) {
		this.orderNo = orderNo;
	}

	public TemplateItem getMemo() {
		return memo;
	}

	public void setMemo(TemplateItem memo) {
		this.memo = memo;
	}
	

	
	
}
