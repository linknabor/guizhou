package com.yumu.hexie.integration.wuye.vo;

import java.util.List;

import com.yumu.hexie.integration.wuye.base.BaseQuery;

public class BaseRequestDTO<T> extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6339566615468136284L;
	
	private String requestId;	//请求ID
	private String oatype;	//公众号类型
	private List<String> sectList;	//小区id
	private String beginDate;	//查询开始日期
	private String endDate;		//查询结束日期
	private String sign;	//签名
	
	private T data;	//数据
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getOatype() {
		return oatype;
	}
	public void setOatype(String oatype) {
		this.oatype = oatype;
	}
	public List<String> getSectList() {
		return sectList;
	}
	public void setSectList(List<String> sectList) {
		this.sectList = sectList;
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
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "BaseRequestDTO [requestId=" + requestId + ", oatype=" + oatype + ", sectList=" + sectList
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", sign=" + sign + ", data=" + data + "]";
	}
	
	
	
}
