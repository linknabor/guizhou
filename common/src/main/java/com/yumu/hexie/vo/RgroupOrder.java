package com.yumu.hexie.vo;

import java.io.Serializable;

import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.RgroupRule;

public class RgroupOrder implements Serializable {

	private static final long serialVersionUID = -6692119516210622336L;
	private RgroupRule rule;
	private ServiceOrder order;
	public RgroupOrder(){}
	public RgroupOrder(RgroupRule rule,ServiceOrder order){
		this.rule = rule;
		this.order = order;
	}
	public String getProductThumbPic() {
		return order==null ? "" : order.getProductThumbPic();
	}
	public String getProductName() {
		return order==null ? "" : order.getProductName();
	}
	public int getCount() {
		return order==null ? 0 : order.getCount();
	}
	public int getGroupStatus() {
		return rule==null ? 0 : rule.getGroupStatus();
	}
	public long getLeftTime() {
		return rule==null ? 0 : rule.getLeftSeconds();
	}
	public int getProcess(){
		return rule==null ? 0 :rule.getProcess();
	}
	public long getId() {
		return order==null ? 0 : order.getId();
	}
	public int getOrderStatus() {
		return order==null ? 0 : order.getStatus();
	}
	public String getOrderStatusStr() {
		return order==null ? "" : order.getStatusStr();
	}
	public String createDateStr(){
		return order==null ? "" : order.getCreateDateStr();
	}
	public long getProductId() {
		return order==null ? 0 : order.getProductId();
	}
	public long getGroupRuleId() {
		return order==null ? 0 : order.getGroupRuleId();
	}
	public long getUserId() {
		return order==null ? 0 : order.getUserId();
	}
	public String getOrderNo() {
		return order==null ? "" : order.getOrderNo();
	}
	public float getPrice() {
		return order==null ? 0 : order.getPrice();
	}
	public int getStatus() {
		return order==null ? 0 : order.getStatus();
	}
	public long getCloseTime() {
		return order==null ? 0 : order.getCloseTime();
	}
	public int getPingjiaStatus() {
		return order==null ? 0 : order.getPingjiaStatus();
	}
	public float getShipFee() {
		return order==null ? 0 : order.getShipFee();
	}
	public int getCurrentNum() {
		return rule==null ? 0 :rule.getCurrentNum();
	}
	public int getGroupMinNum() {
		return rule==null ? 0 :rule.getGroupMinNum();
	}
	
	public String getCreateDateStr(){
		return order==null ? "" :order.getCreateDateStr();
	}
	public String getStatusStr(){
		return order==null ? "" :order.getStatusStr();
	}
}
