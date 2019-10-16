package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * 会员表
 * @author 内脏坏了
 *
 */
@Entity
public class Member extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3269528927531315992L;
	private long userid;//用户id
	private String startdate;//开始日期
	private String enddate;//结束日期
	private String status;//是否是会员
	
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
