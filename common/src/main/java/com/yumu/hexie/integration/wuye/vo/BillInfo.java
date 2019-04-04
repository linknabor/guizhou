package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

public class BillInfo implements Serializable {

	private static final long serialVersionUID = -5293315620093787139L;

	//{"bill_id":"BI150603800005948478","is_onlinepay":"true","service_fee_name":"物业管理费","pay_cell_addr":"普陀区123路123弄1号202室",
	//"service_fee_cycle":"2015年3月","pay_status":"02","fee_price":"0.10"}
	private String bill_id;
	private String is_onlinepay;
	private String service_fee_name;
	private String pay_cell_addr;
	private String service_fee_cycle;
	private String pay_status;
	private String fee_price;
	
	private boolean selected =false;//为了展示用
	
	public String getBill_id() {
		return bill_id;
	}
	public void setBill_id(String bill_id) {
		this.bill_id = bill_id;
	}
	public String getIs_onlinepay() {
		return is_onlinepay;
	}
	public void setIs_onlinepay(String is_onlinepay) {
		this.is_onlinepay = is_onlinepay;
	}
	public String getService_fee_name() {
		return service_fee_name;
	}
	public void setService_fee_name(String service_fee_name) {
		this.service_fee_name = service_fee_name;
	}
	public String getPay_cell_addr() {
		return pay_cell_addr;
	}
	public void setPay_cell_addr(String pay_cell_addr) {
		this.pay_cell_addr = pay_cell_addr;
	}
	public String getService_fee_cycle() {
		return service_fee_cycle;
	}
	public void setService_fee_cycle(String service_fee_cycle) {
		this.service_fee_cycle = service_fee_cycle;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getFee_price() {
		return fee_price;
	}
	public void setFee_price(String fee_price) {
		this.fee_price = fee_price;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
