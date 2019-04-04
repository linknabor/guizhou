package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

public class TemplateMsg<T> implements Serializable {

	private static final long serialVersionUID = -3389303374046044405L;
	private String touser;//":"OPENID",
	private String template_id;//":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
	private String url;//":"http://weixin.qq.com/download",  
	private T data;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
