package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * 九曳服务单
 * @author hwb_work
 *
 */
@Entity
public class JiuyeOrder extends BaseModel{
	private static final long serialVersionUID = 4808669460780339640L;
	private long sOrderId;//订单id
	private long yOrderId;//预约单Id
	private String serviceTypeName; //服务类型
	private long userId; //用户Id
	private long payStatus;//支付状态；0未支付，1已支付
	private String prices;//支付金额 
	private String expectedTime;//期望服务时间
	private String strMobile;
	private String strName;
	private String strWorkAddr;
	private int serviceIsSingle;//0为单次 1为周期
	private String cycleTime;//配送周期时间;单次配送为空
	private int ServiceNo;//服务总次数
	private int serviceCount;//第几次服务
	private String logisticName; //物流公司
	private String logisticNo; //物流单号
	private String logisticCode; //用于查找物流
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
	public int getServiceCount() {
		return serviceCount;
	}
	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
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
	public int getServiceIsSingle() {
		return serviceIsSingle;
	}
	public void setServiceIsSingle(int serviceIsSingle) {
		this.serviceIsSingle = serviceIsSingle;
	}
	public String getCycleTime() {
		return cycleTime;
	}
	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}
	public int getServiceNo() {
		return ServiceNo;
	}
	public void setServiceNo(int serviceNo) {
		ServiceNo = serviceNo;
	}
    public String getLogisticCode() {
        return logisticCode;
    }
    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }
}
