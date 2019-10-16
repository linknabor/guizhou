package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class TempUser extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -132312953157751010L;
	
	private String name;
	private String tel;
	private String openid;
	private String celladdr;
	private String sectid;
	private String type;	//公众号：0合协 1大楼 2良友 3伟发 4友宜
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCelladdr() {
		return celladdr;
	}
	public void setCelladdr(String celladdr) {
		this.celladdr = celladdr;
	}
	public String getSectid() {
		return sectid;
	}
	public void setSectid(String sectid) {
		this.sectid = sectid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
