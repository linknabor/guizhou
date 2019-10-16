package com.yumu.hexie.service.exception;

import com.yumu.hexie.model.ModelConstant;

public class BizValidateException extends RuntimeException {

	private static final long serialVersionUID = 2340755527125970106L;
	
	private int bizType;//内部订单，文章，预约单,现金券
	private long bizId;//业务ID
	private int level = ModelConstant.EXCEPTION_LEVEL_INFO;

	public BizValidateException(int bizType,long bizId,String msg){
		this.message = msg;
		this.bizId = bizId;
		this.bizType = bizType;
	}
	
	public BizValidateException(long bizId,String msg){
		this.message = msg;
		this.bizId = bizId;
		this.bizType = ModelConstant.EXCEPTION_BIZ_TYPE_ORDER;
	}
	
	public BizValidateException(){}
	public BizValidateException(String msg){
		this.message = msg;
	}
	public BizValidateException(String msg, Throwable cause){
		super(msg, cause);
		this.message = msg;
	}
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
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

	public int getLevel() {
		return level;
	}

	public BizValidateException setError() {
		this.level = ModelConstant.EXCEPTION_LEVEL_ERROR;
		return this;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
