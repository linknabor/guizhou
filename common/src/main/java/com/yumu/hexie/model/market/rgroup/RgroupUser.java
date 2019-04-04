package com.yumu.hexie.model.market.rgroup;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.user.User;

@Entity
public class RgroupUser  extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long userId;
	private long ruleId;
	private long orderId;
	private boolean first;
	private String headUrl;
	private String tel;
	private String userName;
	private int count = 1;//份数
	
	public RgroupUser(ServiceOrder order, User user, boolean isFirst){
		userId = user.getId();
		orderId = order.getId();
		ruleId = order.getGroupRuleId();
		first = isFirst;
		headUrl = user.getHeadimgurl();
		userName = user.getName();
		tel = user.getTel();
	}
	
	public RgroupUser(){
		
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
