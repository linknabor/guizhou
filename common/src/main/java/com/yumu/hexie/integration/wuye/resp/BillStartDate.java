package com.yumu.hexie.integration.wuye.resp;

public class BillStartDate {
	private String start_date;//开始时间
	private String end_date;//结束时间
	private String is_null="0";// 0：前端页面可以选择结束日期  1：前端页面可以选择开始日期
	
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}
	
	
}
