package com.yumu.hexie.integration.wechat.vo;

public class UnionPayVO {
	public String bankType;
	public String merNo;
	public String orderDate;
	public String orderNo;
	public String productId;
	public String respCode;
	public String respDesc;
	public String signature;
	public String transAmt;
	public String transId;
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	public String getUnionPayStr() {
		return "bankType="+ this.bankType +"&merNo="+ this.merNo +"&orderDate="+ this.orderDate +"&orderNo="+ this.orderNo +"&productId="+ this.productId +"&respCode="+ this.respCode +"&respDesc="+ this.respDesc +"&signature="+ this.signature +"&transAmt="+ this.transAmt +"&transId="+this.transId;
	};
}
