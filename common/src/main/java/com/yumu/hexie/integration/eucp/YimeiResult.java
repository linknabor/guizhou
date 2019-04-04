package com.yumu.hexie.integration.eucp;

import java.io.Serializable;

public class YimeiResult implements Serializable {
	private static final long serialVersionUID = -1047149803895243103L;
	public YimeiResult(){}
	private int error;
	private String message;
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess(){
		return error == 0;
	}
	@Override
	public String toString() {
		
		return "error: " + error + ", message: " + message;
	}
	
	
}
