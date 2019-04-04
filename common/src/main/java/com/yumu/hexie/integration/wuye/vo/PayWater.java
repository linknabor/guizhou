package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

import javax.persistence.Transient;

import com.yumu.hexie.integration.wuye.util.KeyToNameUtil;

public class PayWater implements Serializable {
	private static final long serialVersionUID = -6900519479030680681L;

	private String trade_water_id;
	private String tran_time;
	private String bill_tran_amt;
	private String paymethod;
	
	@Transient
	public String getPaymethodStr(){
		return KeyToNameUtil.keyToName(KeyToNameUtil.PAYMETHOD_TYPE, paymethod);
	}
	public String getTrade_water_id() {
		return trade_water_id;
	}
	public void setTrade_water_id(String trade_water_id) {
		this.trade_water_id = trade_water_id;
	}
	public String getTran_time() {
		return tran_time;
	}
	public void setTran_time(String tran_time) {
		this.tran_time = tran_time;
	}
	public String getBill_tran_amt() {
		return bill_tran_amt;
	}
	public void setBill_tran_amt(String bill_tran_amt) {
		this.bill_tran_amt = bill_tran_amt;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	
}
