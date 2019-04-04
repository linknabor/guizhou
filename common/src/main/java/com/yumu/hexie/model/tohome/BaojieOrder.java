package com.yumu.hexie.model.tohome;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * 保洁服务单
 * @author hwb_work
 *
 */
@Entity
public class BaojieOrder extends BaseModel{
	private static final long serialVersionUID = 4808669460780339640L;
	private long sOrderId;//订单id
	private long yOrderId;//预约单Id
	private String serviceTypeName; //服务名称（=预约规则名称）
	private long userId; //用户Id
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String expectedTime;//期望服务时间
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private String CustomerMemo;//客户备注
	private String BackendMemo;//商户备注，用于客户修改时间等
	private int serviceStatus;//服务状态 

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public String getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
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
	public long getsOrderId() {
		return sOrderId;
	}
	public void setsOrderId(long sOrderId) {
		this.sOrderId = sOrderId;
	}
	public long getyOrderId() {
		return yOrderId;
	}
	public void setyOrderId(long yOrderId) {
		this.yOrderId = yOrderId;
	}
	public String getCustomerMemo() {
		return CustomerMemo;
	}
	public void setCustomerMemo(String customerMemo) {
		CustomerMemo = customerMemo;
	}
	public String getBackendMemo() {
		return BackendMemo;
	}
	public void setBackendMemo(String backendMemo) {
		BackendMemo = backendMemo;
	}
	public int getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}
