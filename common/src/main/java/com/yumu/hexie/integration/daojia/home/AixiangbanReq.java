package com.yumu.hexie.integration.daojia.home;

import java.io.Serializable;
import java.util.Date;

/** 
 * <p>项目：合协社区公众号</p>
 * <p>模块：爱相伴服务</p>
 * <p>描述：字段对应AixiangbanOrdre.java中的字段</p>
 * <p>版    权: Copyright (c) 2016</p>
 * <p>公    司: 上海奈博信息科技有限公司</p>
 * @author hwb_work 
 * @version 1.0 
 * 创建时间：2016年4月15日 下午1:54:26
 */
public class AixiangbanReq implements Serializable{
	private static final long serialVersionUID = 6898376376725362264L;
	private long orderId;//订单id
	private long ruleId;//规则id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private int paymentType;//0线下支付、1微信支付
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private String customerMemo;//备注
	private Date createDate;//创建时间
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public long getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(long payStatus) {
		this.payStatus = payStatus;
	}
	public String getPrices() {
		return prices;
	}
	public void setPrices(String prices) {
		this.prices = prices;
	}
	public String getStrMobile() {
		return strMobile;
	}
	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrWorkAddr() {
		return strWorkAddr;
	}
	public void setStrWorkAddr(String strWorkAddr) {
		this.strWorkAddr = strWorkAddr;
	}
	public String getCustomerMemo() {
		return customerMemo;
	}
	public void setCustomerMemo(String customerMemo) {
		this.customerMemo = customerMemo;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
