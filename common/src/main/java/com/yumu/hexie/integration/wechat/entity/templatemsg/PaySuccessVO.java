package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaySuccessVO  implements Serializable {
	private static final long serialVersionUID = -5051576905455252568L;

	private TemplateItem first;

	private TemplateItem orderMoneySum;

	private TemplateItem orderProductName;

	@JsonProperty("Remark")
	private TemplateItem remark;

	public TemplateItem getFirst() {
		return first;
	}

	public void setFirst(TemplateItem first) {
		this.first = first;
	}

	public TemplateItem getOrderMoneySum() {
		return orderMoneySum;
	}

	public void setOrderMoneySum(TemplateItem orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}

	public TemplateItem getOrderProductName() {
		return orderProductName;
	}

	public void setOrderProductName(TemplateItem orderProductName) {
		this.orderProductName = orderProductName;
	}

	public TemplateItem getRemark() {
		return remark;
	}

	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}
	
}
