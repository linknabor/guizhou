package com.yumu.hexie.model.localservice.oldversion;

import javax.persistence.Entity;

import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.BaseModel;

//预约服务单
@Entity
public class YuyueOrder  extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long merchantId;//商户Id
	private int productType;//1尚匠,2flowerPlus 3、上海沪雅口腔，4、白富美、5阿姨来了
	private String productName;
	private String price;
	private long userId;
	
	//201501011221xxxx
	private String orderNo;
	private long serviceOrderId;//订单id	
	//地址信息
	private long addressId;
	private int status;//0预约成功、1预约失败、2预约超时
	private int paymentType;//0线下支付；1微信支付
	private int payStatus;//支付状态
	private long confirmDate;//第三方确认时间

	private int serviceNo;//服务总次数
	private int serviceUsedNo;//已用的服务次数
	/**************冗余信息********************/

	private String address;
	private String tel;
	private String receiverName;
	
	private String workTime;

	private String memo;
	
	private String duration;
	
	//private String nPayMoney;
	
	private String strPlanStartTimeHour;
	private String strPlanStartTimeMimute;
	
	private String strWorkFrequency;
	/**************冗余信息********************/
	
	public YuyueOrder(){
		orderNo = OrderNoUtil.generateYuyueOrderNo();
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(long confirmDate) {
		this.confirmDate = confirmDate;
	}

	public long getAddressId() {
		return addressId;
	}


	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}


	public String getWorkTime() {
		return workTime;
	}


	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}




	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getStrPlanStartTimeHour() {
		return strPlanStartTimeHour;
	}


	public void setStrPlanStartTimeHour(String strPlanStartTimeHour) {
		this.strPlanStartTimeHour = strPlanStartTimeHour;
	}


	public String getStrPlanStartTimeMimute() {
		return strPlanStartTimeMimute;
	}


	public void setStrPlanStartTimeMimute(String strPlanStartTimeMimute) {
		this.strPlanStartTimeMimute = strPlanStartTimeMimute;
	}


	public String getStrWorkFrequency() {
		return strWorkFrequency;
	}


	public void setStrWorkFrequency(String strWorkFrequency) {
		this.strWorkFrequency = strWorkFrequency;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}

	public int getPayStatus() {
		return payStatus;
	}


	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}


	public int getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}


	public long getServiceOrderId() {
		return serviceOrderId;
	}


	public void setServiceOrderId(long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}


	public int getProductType() {
		return productType;
	}


	public void setProductType(int productType) {
		this.productType = productType;
	}


	public int getServiceNo() {
		return serviceNo;
	}


	public void setServiceNo(int serviceNo) {
		this.serviceNo = serviceNo;
	}


	public int getServiceUsedNo() {
		return serviceUsedNo;
	}


	public void setServiceUsedNo(int serviceUsedNo) {
		this.serviceUsedNo = serviceUsedNo;
	}


	public long getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	

}
