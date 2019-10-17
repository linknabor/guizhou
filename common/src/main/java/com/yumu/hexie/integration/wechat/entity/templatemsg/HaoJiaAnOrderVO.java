package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HaoJiaAnOrderVO implements Serializable{

	private static final long serialVersionUID = 4594080226499648498L;
	@JsonProperty("first")
	private TemplateItem title;
	
	@JsonProperty("keyword1")
	private TemplateItem appointmentDate;	//预约时间
	
	@JsonProperty("keyword2")
	private TemplateItem appointmentContent;	//预约内容
	
	@JsonProperty("remark")
	private TemplateItem address;	//服务地址

	
	public TemplateItem getTitle() {
		return title;
	}

	public void setTitle(TemplateItem title) {
		this.title = title;
	}

	public TemplateItem getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(TemplateItem appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public TemplateItem getAppointmentContent() {
		return appointmentContent;
	}

	public void setAppointmentContent(TemplateItem appointmentContent) {
		this.appointmentContent = appointmentContent;
	}

	public TemplateItem getAddress() {
		return address;
	}

	public void setAddress(TemplateItem address) {
		this.address = address;
	}
	
}
