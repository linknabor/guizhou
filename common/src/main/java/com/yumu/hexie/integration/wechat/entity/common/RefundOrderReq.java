package com.yumu.hexie.integration.wechat.entity.common;

import java.io.Serializable;

import com.yumu.hexie.model.payment.RefundOrder;

public class RefundOrderReq implements Serializable{

	private static final long serialVersionUID = -2421647370495536244L;
	//	<xml>
//	   <appid>wx2421b1c4370ec43b</appid>
//	   <mch_id>10000100</mch_id>
//	   <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
//	   <op_user_id>10000100</op_user_id>
//	   <out_refund_no>1415701182</out_refund_no>
//	   <out_trade_no>1415757673</out_trade_no>
//	   <refund_fee>1</refund_fee>
//	   <total_fee>1</total_fee>
//	   <transaction_id></transaction_id>
//	   <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
//	</xml>
	public RefundOrderReq(){
		
	}
	public RefundOrderReq(RefundOrder rOrder) {
		this.out_refund_no = rOrder.getRefundNo();
		this.refund_fee = (int)(rOrder.getRefundFee()*100)+"";
		this.total_fee = (int)(rOrder.getTotalFee()*100)+"";
		this.transaction_id = rOrder.getChannelPaymentNo();
	}
	private String out_refund_no;
	private String refund_fee;
	private String total_fee;
	private String transaction_id;
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
}
