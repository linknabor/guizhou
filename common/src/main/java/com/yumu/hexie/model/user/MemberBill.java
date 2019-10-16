package com.yumu.hexie.model.user;


import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
@Entity
public class MemberBill extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8890242631230858623L;
	
	private String memberbillid;//会员账单id
	
	private String trandate;//交易日期
	
	private String startdate;//交易开始日期
	
	private String enddate;//交易结束日期
	
	private float price;//交易金额
	
	private String status;//交易状态 01交易中   02交易成功   03交易失败（唤起没有支付的订单）
	
	private String paymethod;//支付渠道
	
	private long userid;
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getMemberbillid() {
		return memberbillid;
	}
	public void setMemberbillid(String memberbillid) {
		this.memberbillid = memberbillid;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	
	
}
