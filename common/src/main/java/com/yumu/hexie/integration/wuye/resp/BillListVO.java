package com.yumu.hexie.integration.wuye.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.integration.wuye.vo.BillInfo;

public class BillListVO implements Serializable {

	private static final long serialVersionUID = 1592900991660863594L;

	private int total_count;
	private int total_not_pay;	//未支付的账单数量
	private List<BillInfo> bill_info;
	private List<BillInfo> car_bill_info;
	private int permit_skip_pay;//0?
	private int permit_skip_car_pay;
	private int park_discount_rule_conf;
	private String park_discount_rule;
	private String pay_least_month;
	private String reduce_mode;	//减免模式，记账时总金额四舍五入，0表示没有此功能，1表示四舍五入至元，2表示四舍五入至角，3表示自由调价
	private int bills_size;

	public int getPark_discount_rule_conf() {
		return park_discount_rule_conf;
	}
	public void setPark_discount_rule_conf(int park_discount_rule_conf) {
		this.park_discount_rule_conf = park_discount_rule_conf;
	}
	public String getPark_discount_rule() {
		return park_discount_rule;
	}
	public void setPark_discount_rule(String park_discount_rule) {
		this.park_discount_rule = park_discount_rule;
	}
	public int getPermit_skip_car_pay() {
		return permit_skip_car_pay;
	}
	public void setPermit_skip_car_pay(int permit_skip_car_pay) {
		this.permit_skip_car_pay = permit_skip_car_pay;
	}
	public int getBills_size() {
		return bills_size;
	}
	public void setBills_size(int bills_size) {
		this.bills_size = bills_size;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public List<BillInfo> getBill_info() {
		return bill_info;
	}
	public void setBill_info(List<BillInfo> bill_info) {
		this.bill_info = bill_info;
		this.bills_size = bill_info==null?0:bill_info.size();
	}
	public int getPermit_skip_pay() {
		return permit_skip_pay;
	}
	public void setPermit_skip_pay(int permit_skip_pay) {
		this.permit_skip_pay = permit_skip_pay;
	}
	public List<BillInfo> getCar_bill_info() {
		return car_bill_info;
	}
	public void setCar_bill_info(List<BillInfo> car_bill_info) {
		this.car_bill_info = car_bill_info;
	}
	public int getTotal_not_pay() {
		return total_not_pay;
	}
	public void setTotal_not_pay(int total_not_pay) {
		this.total_not_pay = total_not_pay;
	}
	public String getPay_least_month() {
		return pay_least_month;
	}
	public void setPay_least_month(String pay_least_month) {
		this.pay_least_month = pay_least_month;
	}
	public String getReduce_mode() {
		return reduce_mode;
	}
	public void setReduce_mode(String reduce_mode) {
		this.reduce_mode = reduce_mode;
	}
	
	
}
