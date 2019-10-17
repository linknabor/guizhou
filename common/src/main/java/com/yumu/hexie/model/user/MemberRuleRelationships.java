package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
@Entity
public class MemberRuleRelationships extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -314314343420022958L;
	
	private long memberid;//会员id
	private long ruleid;//规则id
	
	
	public long getMemberid() {
		return memberid;
	}
	public void setMemberid(long memberid) {
		this.memberid = memberid;
	}
	public long getRuleid() {
		return ruleid;
	}
	public void setRuleid(long ruleid) {
		this.ruleid = ruleid;
	}
	
	
	
}
