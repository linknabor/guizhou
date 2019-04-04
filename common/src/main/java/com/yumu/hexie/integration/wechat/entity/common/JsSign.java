package com.yumu.hexie.integration.wechat.entity.common;

import java.io.Serializable;

public class JsSign implements Serializable{

	private static final long serialVersionUID = 2290838252340193419L;
	private String appId;//: '', // 必填，公众号的唯一标识
	private String timestamp;//: , // 必填，生成签名的时间戳
	private String nonceStr;//: '', // 必填，生成签名的随机串
	private String signature;//: '',// 必填，签名，见附录1
	private String pkgStr;
	private String signType = "MD5";
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getPkgStr() {
		return pkgStr;
	}
	public void setPkgStr(String pkgStr) {
		this.pkgStr = pkgStr;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	
}
