package com.yumu.hexie.integration.wechat.entity.customer;

public class DataJsonVo {
	private String value;
	private String color = "#173177";
	
	public DataJsonVo() {
		
	}
	public DataJsonVo(String value) {
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
