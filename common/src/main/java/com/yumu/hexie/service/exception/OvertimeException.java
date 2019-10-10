package com.yumu.hexie.service.exception;

public class OvertimeException extends RuntimeException{


	private static final long serialVersionUID = -8526031171563214034L;
	
	
	public OvertimeException(){}
	
	public OvertimeException(String msg){
		this.message = msg;
	}
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
