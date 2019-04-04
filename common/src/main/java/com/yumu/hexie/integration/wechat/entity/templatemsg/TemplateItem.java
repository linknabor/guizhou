package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

public class TemplateItem implements Serializable {

	private static final long serialVersionUID = -9114391852282171670L;
	private String  value;//":"巧克力",
	private String color = "#000000";//":"#173177"
	public TemplateItem(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
