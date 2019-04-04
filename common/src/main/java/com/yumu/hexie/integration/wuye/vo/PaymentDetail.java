package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

//支付细节（月份金额）
public class PaymentDetail implements Serializable{
	private static final long serialVersionUID = 9017390740835998429L;

	private String service_fee_cycle;
	private String fee_price;
	public String getService_fee_cycle() {
		return service_fee_cycle;
	}
	public void setService_fee_cycle(String service_fee_cycle) {
		this.service_fee_cycle = service_fee_cycle;
	}
	public String getFee_price() {
		return fee_price;
	}
	public void setFee_price(String fee_price) {
		this.fee_price = fee_price;
	}
}
