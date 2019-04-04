package com.yumu.hexie.service.exception;

public class WechatException extends RuntimeException {

	private static final long serialVersionUID = 2340755527125970106L;

	private int errorCode;
	public WechatException(){}
	public WechatException(int errCode,String msg){
		this.message = msg;
		this.errorCode = errCode;
	}
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
