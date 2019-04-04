package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WuyePaySuccessVO implements Serializable{

	private static final long serialVersionUID = -7146874427275734542L;
	
	private TemplateItem first;
	
	@JsonProperty("keyword1")
	private TemplateItem trade_water_id;
	
	@JsonProperty("keyword2")
	private TemplateItem real_name;
	
	@JsonProperty("keyword3")
	private TemplateItem fee_price;
	
	@JsonProperty("keyword4")
	private TemplateItem fee_type;
	
	@JsonProperty("keyword5")
	private TemplateItem pay_time;
	
	private TemplateItem remark;

	public TemplateItem getFirst() {
		return first;
	}

	public void setFirst(TemplateItem first) {
		this.first = first;
	}

	public TemplateItem getTrade_water_id() {
		return trade_water_id;
	}

	public void setTrade_water_id(TemplateItem trade_water_id) {
		this.trade_water_id = trade_water_id;
	}

	public TemplateItem getReal_name() {
		return real_name;
	}

	public void setReal_name(TemplateItem real_name) {
		this.real_name = real_name;
	}

	public TemplateItem getFee_price() {
		return fee_price;
	}

	public void setFee_price(TemplateItem fee_price) {
		this.fee_price = fee_price;
	}

	public TemplateItem getFee_type() {
		return fee_type;
	}

	public void setFee_type(TemplateItem fee_type) {
		this.fee_type = fee_type;
	}

	public TemplateItem getPay_time() {
		return pay_time;
	}

	public void setPay_time(TemplateItem pay_time) {
		this.pay_time = pay_time;
	}

	public TemplateItem getRemark() {
		return remark;
	}

	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}
	
	
}
