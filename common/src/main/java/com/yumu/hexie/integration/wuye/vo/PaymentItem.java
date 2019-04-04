package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;
import java.util.List;

//支付项（同一类型支付并入一起）
public class PaymentItem implements Serializable {

	private static final long serialVersionUID = 9017390740835998429L;
	private String service_fee_name;
	private List<PaymentDetail> fee_detail;
	public String getService_fee_name() {
		return service_fee_name;
	}
	public void setService_fee_name(String service_fee_name) {
		this.service_fee_name = service_fee_name;
	}
	public List<PaymentDetail> getFee_detail() {
		return fee_detail;
	}
	public void setFee_detail(List<PaymentDetail> fee_detail) {
		this.fee_detail = fee_detail;
	}

	private float total = 0f;
	public float getTotalFee() {
		try{
			if(total == 0) {
				for(PaymentDetail d : fee_detail){
					total += Float.parseFloat(d.getFee_price());
				}
			}
			return total;
		}catch(Exception e) {
			return 0.00f;
		}
	}
}
