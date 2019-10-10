package com.yumu.hexie.integration.wuye.vo;

public class OtherBillInfo {
	private String start_date;
	private String end_date;
	private String bill_fee_type;
	private String fee_price;
	private String paid_bills;
	private String unpaid_bills;
	private String fee_type_show_name;
	private String ver_no;
	private String show_invoice_flag;//是否开通电子发票功能
	private String show_com_flag;//是否允许公司开票功能
	private String show_invoice;//是否开票
	private String invoice_title;//发票抬头（回显使用）
	private String cell_addr;//房屋地址
	private String cnst_area;//房屋平米
	private boolean selected =false;//为了展示用
	
	public String getCnst_area() {
		return cnst_area;
	}
	public void setCnst_area(String cnst_area) {
		this.cnst_area = cnst_area;
	}
	public String getCell_addr() {
		return cell_addr;
	}
	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}
	public String getVer_no() {
		return ver_no;
	}
	public void setVer_no(String ver_no) {
		this.ver_no = ver_no;
	}
	public String getShow_invoice_flag() {
		return show_invoice_flag;
	}
	public void setShow_invoice_flag(String show_invoice_flag) {
		this.show_invoice_flag = show_invoice_flag;
	}
	public String getShow_com_flag() {
		return show_com_flag;
	}
	public void setShow_com_flag(String show_com_flag) {
		this.show_com_flag = show_com_flag;
	}
	public String getShow_invoice() {
		return show_invoice;
	}
	public void setShow_invoice(String show_invoice) {
		this.show_invoice = show_invoice;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
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
	public String getBill_fee_type() {
		return bill_fee_type;
	}
	public void setBill_fee_type(String bill_fee_type) {
		this.bill_fee_type = bill_fee_type;
	}
	public String getFee_price() {
		return fee_price;
	}
	public void setFee_price(String fee_price) {
		this.fee_price = fee_price;
	}
	public String getPaid_bills() {
		return paid_bills;
	}
	public void setPaid_bills(String paid_bills) {
		this.paid_bills = paid_bills;
	}
	public String getUnpaid_bills() {
		return unpaid_bills;
	}
	public void setUnpaid_bills(String unpaid_bills) {
		this.unpaid_bills = unpaid_bills;
	}
	public String getFee_type_show_name() {
		return fee_type_show_name;
	}
	public void setFee_type_show_name(String fee_type_show_name) {
		this.fee_type_show_name = fee_type_show_name;
	}
	
	
}
