package com.yumu.hexie.model.commonsupport.gotong;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class SmsHis extends BaseModel {
	private static final long serialVersionUID = 4808669460780339640L;
	
	private String phone;
	private int messageType = 0;//消息类型  0 验证码(还包括了团购信息、用户注册成功消息，是否考虑分成不同的短信类别),  1订单状态通知	2.注册成功送优惠券	3.优惠券到期提醒
	
	private Date sendDate;
	
	private String code;
	private String msg;
	
	private long userId;
	private String userName;
	

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
