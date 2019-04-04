package com.yumu.hexie.model.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class Operation extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	
	private int bizType;//1 serviceOrder
	private int operate;
	private long bizId;
	private Date opTime;
	private int fromStatus;
	private int endStatus;
	private String fromStatusStr;
	private String endStatusStr;
	private int opUserType;//0 普通用户 1.系统用户
	@Column(nullable=true)
	private long opUserId;
	private String memo;


	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public long getBizId() {
		return bizId;
	}

	public void setBizId(long bizId) {
		this.bizId = bizId;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	

	public int getOpUserType() {
		return opUserType;
	}

	public void setOpUserType(int opUserType) {
		this.opUserType = opUserType;
	}

	public long getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(long opUserId) {
		this.opUserId = opUserId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(int fromStatus) {
		this.fromStatus = fromStatus;
	}

	public int getEndStatus() {
		return endStatus;
	}

	public void setEndStatus(int endStatus) {
		this.endStatus = endStatus;
	}

	public String getFromStatusStr() {
		return fromStatusStr;
	}

	public void setFromStatusStr(String fromStatusStr) {
		this.fromStatusStr = fromStatusStr;
	}

	public String getEndStatusStr() {
		return endStatusStr;
	}

	public void setEndStatusStr(String endStatusStr) {
		this.endStatusStr = endStatusStr;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}
}
