package com.yumu.hexie.integration.wuye.resp;

import java.util.List;

import com.yumu.hexie.integration.wuye.base.BaseQuery;

public class BaseResponseDTO<T> extends BaseQuery {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1637582137500635252L;
	
	private String requestId;	//请求ID
	private String returnCode;	//返回码	
	private String returnMessage;	//返回提示语
	private String sign;	//签名
	private T data;	//数据
	private List<String> sectList;	//小区id
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public List<String> getSectList() {
		return sectList;
	}
	public void setSectList(List<String> sectList) {
		this.sectList = sectList;
	}
	@Override
	public String toString() {
		return "BaseResponseDTO [requestId=" + requestId + ", returnCode=" + returnCode + ", returnMessage="
				+ returnMessage + ", sign=" + sign + ", data=" + data + ", sectList=" + sectList + "]";
	}
	
	
}
