package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

public class HexieUser implements Serializable {

	private static final long serialVersionUID = -8863855555160105591L;
	
	private String user_id;
	
	private String user_name;
	
	private String user_head;
	
	private String user_sex;
	
	private String user_email;
	
	private String email_activie;
	private String user_tel;
	private String office_tel;//小区电话
	
	public String getOffice_tel() {
		return office_tel;
	}
	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_head() {
		return user_head;
	}
	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getEmail_activie() {
		return email_activie;
	}
	public void setEmail_activie(String email_activie) {
		this.email_activie = email_activie;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
}