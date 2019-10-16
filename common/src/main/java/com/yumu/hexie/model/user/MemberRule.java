package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
@Entity
public class MemberRule extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -250900798949913113L;
	
	private String rulename;//规则名称
	private String rulevalue;//规则内容
	private String status;//规则状态
	
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getRulevalue() {
		return rulevalue;
	}
	public void setRulevalue(String rulevalue) {
		this.rulevalue = rulevalue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
