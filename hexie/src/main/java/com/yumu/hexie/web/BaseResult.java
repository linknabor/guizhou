package com.yumu.hexie.web;



/**
 * Created by Administrator on 2015/4/6.
 */
public class BaseResult<T> {
    private boolean success = true;
    private String message;
    private T result;
    private int errorCode = 0;
    public static final int NEED_LOGIN = 40001;
    public static final int NEED_MAIN_LOGIN = 40002; //需要主公众号授权
    public static final int NEED_AUTH = 42032; //需要微信登录授权

    public static BaseResult fail(int code){
        BaseResult r = new BaseResult();
        r.setSuccess(false);
        r.setErrorCode(code);
        return r;
    }

    public static BaseResult fail(int code,String message){
        BaseResult r = new BaseResult();
        r.setSuccess(false);
        r.setErrorCode(code);
        r.setMessage(message);
        return r;
    }
    
    public static BaseResult fail(String errorMsg){
        BaseResult r = new BaseResult();
        r.setSuccess(false);
        r.setMessage(errorMsg);
        return r;
    }

    public static  BaseResult successResult(Object obj){
        BaseResult r = new BaseResult();
        r.setSuccess(true);
        r.setResult(obj);
        return r;
    }

    public BaseResult<T> failMsg(String errorMsg){
        setSuccess(false);
        setMessage(errorMsg);
        return this;
    }
    public BaseResult<T> failCode(int code){
        setSuccess(false);
        setErrorCode(code);
        return this;
    }

    public  BaseResult<T> success(T obj){
        setSuccess(true);
        setResult(obj);
        return this;
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
