package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

public class WechatPayInfo implements Serializable {

	private static final long serialVersionUID = 2567954737062142484L;
	private String appid;
	private String trade_water_id;
	private String timestamp;
	private String noncestr;
	private String packageValue;//在后台及js端为package 关键字。。
	private String signtype;
	private String paysign;
	private String user_pay_type;	//用户支付情况，0表示新用户首次支付，1表示老用户首次支付，2表示老用户非首次支付，返回值为空表示活动已结束
	private String packageId;
	private String token_id;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTrade_water_id() {
		return trade_water_id;
	}
	public void setTrade_water_id(String trade_water_id) {
		this.trade_water_id = trade_water_id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getSigntype() {
		return signtype;
	}
	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}
	public String getPaysign() {
		return paysign;
	}
	public void setPaysign(String paysign) {
		this.paysign = paysign;
	}
	public String getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
		String[]packageArr = packageValue.split("=");
		String retValue = "";
		if (packageArr.length>1) {
			retValue = packageArr[1];
		}
		this.packageId = retValue;
		
	}
	public String getUser_pay_type() {
		return user_pay_type;
	}
	public void setUser_pay_type(String user_pay_type) {
		this.user_pay_type = user_pay_type;
	}
	public String getPackageId() {
		return packageId;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	
	
}
