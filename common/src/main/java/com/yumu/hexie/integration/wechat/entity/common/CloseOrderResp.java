package com.yumu.hexie.integration.wechat.entity.common;

import java.io.Serializable;

public class CloseOrderResp implements Serializable {

	private static final long serialVersionUID = 3921473771130515126L;

/*	<xml>
	   <return_code><![CDATA[SUCCESS]]></return_code>
	   <return_msg><![CDATA[OK]]></return_msg>
	   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
	   <mch_id><![CDATA[10000100]]></mch_id>
	   <nonce_str><![CDATA[BFK89FC6rxKCOjLX]]></nonce_str>
	   <sign><![CDATA[72B321D92A7BFA0B2509F3D13C7B1631]]></sign>
	   <result_code><![CDATA[SUCCESS]]></result_code>
	</xml>*/
//	ORDERPAID	           订单已支付	订单已支付，不能发起关单	订单已支付，不能发起关单，请当作已支付的正常交易
//	SYSTEMERROR	           系统错误	系统异常，请重新调用该API
//	ORDERNOTEXIST	订单不存在	不需要关单，当作未提交的支付的订单
//	ORDERCLOSED		订单已关闭	订单已关闭，无需继续调用
//	SIGNERROR		签名错误	请检查签名参数和方法是否都符合签名算法要求
//	REQUIRE_POST_METHOD	请使用post方法 	请检查请求参数是否通过post方法提交
//	XML_FORMAT_ERROR	XML格式错误	请检查XML参数格式是否正确
	private String return_code;
	private String return_msg;
	private String appid;
	private String mch_id;
	private String nonce_str;
	private String result_code;
	
	private String err_code;
	private String err_code_des;
	
	public boolean isSuccess(){
		return "SUCCESS".equalsIgnoreCase(return_code)
				&& !"SYSTEMERROR".equalsIgnoreCase(result_code)
				&& !"SIGNERROR".equalsIgnoreCase(result_code)
				&& !"REQUIRE_POST_METHOD".equalsIgnoreCase(result_code)
				&& !"XML_FORMAT_ERROR".equalsIgnoreCase(result_code);
	}
	
	public boolean isCloseSuccess(){
		return "SUCCESS".equalsIgnoreCase(result_code)
				||"ORDERCLOSED".equalsIgnoreCase(err_code)
				||"ORDERNOTEXIST".equalsIgnoreCase(err_code);
	}
	public boolean isOrderPayed(){
		return "FAIL".equalsIgnoreCase(result_code)
				&& "ORDERPAID".equalsIgnoreCase(err_code);
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	
}
