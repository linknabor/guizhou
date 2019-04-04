package com.yumu.hexie.model.tohome;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/** 
 * <p>项目：东湖e家园</p>
 * <p>模块：爱相伴服务</p>
 * <p>描述：</p>
 * <p>版    权: Copyright (c) 2016</p>
 * <p>公    司: 上海奈博信息科技有限公司</p>
 * @author hwb_work 
 * @version 1.0 
 * 创建时间：2016年4月15日 下午2:10:03
 */
@Entity
public class AixiangbanOrder extends BaseModel{
	private static final long serialVersionUID = -5166080760940133982L;
	private long sOrderId;//订单id
	private long yOrderId;//预约单Id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String endDate;//服务到期日期
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private String logisticName; //物流公司
	private String logisticNo; //物流单号
	private String CustomerMemo;//客户备注
	private String BackendMemo;//商户备注，用于客户修改时间等
	private int serviceStatus;//服务状态 
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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
	public String getLogisticName() {
		return logisticName;
	}
	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}
	public String getLogisticNo() {
		return logisticNo;
	}
	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
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
