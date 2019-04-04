package com.yumu.hexie.backend.web.dto;



/**
 * Created by Administrator on 2015/4/6.
 */
public class BaseResult<T> {
    private boolean success = true;
    private String message;
    private T result;
    private int errorCode = 0;
    public static final int NEED_LOGIN = 40001;
    public static final int NEED_AUTH = 42032; //需要微信登录授权

    public static <T> BaseResult<T> fail(int code){
        BaseResult<T> r = new BaseResult<T>();
        r.setSuccess(false);
        r.setErrorCode(code);
        return r;
    }

    public static <T> BaseResult<T> fail(int code,String message){
        BaseResult<T> r = new BaseResult<T>();
        r.setSuccess(false);
        r.setErrorCode(code);
        r.setMessage(message);
        return r;
    }
    
    public static <T> BaseResult<T> fail(String errorMsg){
        BaseResult<T> r = new BaseResult<T>();
        r.setSuccess(false);
        r.setMessage(errorMsg);
        return r;
    }

    public static <T> BaseResult<T> successResult(T obj){
        BaseResult<T> r = new BaseResult<T>();
        r.setSuccess(true);
        r.setResult(obj);
        return r;
    }
    

    public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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
