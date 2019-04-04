package com.yumu.hexie.model.payment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.localservice.basemodel.HasMerchant;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.settle.SettleConstant;

//支付单
@Entity
public class PaymentOrder  extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private int payType = PaymentConstant.PAY_TYPE_WECHAT;
	private int orderType;
	
	private long orderId;
	
	private Integer settleType;
	private Long settleId;
	private long userId;
	private String openId;
	private long merchantId;
	private float price;//需要支付的金额

	private String productName;
	private String paymentNo;
	private String payAccount;
	private String channelPaymentId;
	private String prepayId;
	
	private int status;//1.提交成功  2. 支付成功 3. 支付失败 4.取消支付 5.已退款

	private long updateDate;
	

    private Date submitDate;
    private Date succussDate;
    private Date failDate;
    private Date cancelDate;
    private Date refundApplyDate;
    private Date refundDate;

	public void refreshOrder() {
		paymentNo = OrderNoUtil.generatePaymentOrderNo();
	}
	public PaymentOrder(){
		paymentNo = OrderNoUtil.generatePaymentOrderNo();
	}
    public PaymentOrder(ServiceOrder order) {
        paymentNo = OrderNoUtil.generatePaymentOrderNo();
        setProductName(order.getProductName());
        setOrderId(order.getId());
        setUserId(order.getUserId());
        setOpenId(order.getOpenId());
        setMerchantId(order.getMerchantId());
        setPrice(order.getPrice());
        setStatus(PaymentConstant.PAYMENT_STATUS_INIT);
        setOrderType(PaymentConstant.TYPE_MARKET_ORDER);
        setSettleId(order.getId());
        setSettleType(SettleConstant.TYPE_MARKET);
    }
    public PaymentOrder(BaseO2OService order,String openId) {
        paymentNo = OrderNoUtil.generatePaymentOrderNo();
        setProductName(order.getProjectName());
        setOrderId(order.getId());
        setUserId(order.getUserId());
        setOpenId(openId);
        if(order instanceof HasMerchant) {
            setMerchantId(((HasMerchant)order).getMerchantId() == null ? 0 : ((HasMerchant)order).getMerchantId());    
        }
        
        setPrice(order.getRealAmount().floatValue());
        setStatus(PaymentConstant.PAYMENT_STATUS_INIT);
        setOrderType(order.getPaymentOrderType());
        setSettleType(SettleConstant.TYPE_HOME);
    }
	

	@Transient
	public void close() {
		setStatus(PaymentConstant.PAYMENT_STATUS_CANCEL);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void paySuccess(String transaction_id){
		setStatus(PaymentConstant.PAYMENT_STATUS_SUCCESS);
		setChannelPaymentId(transaction_id);
		setUpdateDate(System.currentTimeMillis());
        setSuccussDate(new Date());
	}
	
	@Transient
    public void requestPay(String prepayId){
        setPrepayId(prepayId);
        setStatus(PaymentConstant.PAYMENT_STATUS_INIT);
        setSubmitDate(new Date());
    }


    @Transient
    public void refunded(){
        setStatus(PaymentConstant.PAYMENT_STATUS_REFUNDED);
        setRefundDate(new Date());
    }
    @Transient
    public void applyRefund(){
        setStatus(PaymentConstant.PAYMENT_STATUS_REFUNDING);
        setRefundApplyDate(new Date());
    }
    @Transient
    public void refunding(){
        setStatus(PaymentConstant.PAYMENT_STATUS_REFUNDING);
    }
    @Transient
    public void fail(){
        setStatus(PaymentConstant.PAYMENT_STATUS_FAIL);
        setFailDate(new Date());
    }
    @Transient
    public void cancel(){
        setStatus(PaymentConstant.PAYMENT_STATUS_CANCEL);
        setCancelDate(new Date());
    }
    
	public long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	public String getChannelPaymentId() {
		return channelPaymentId;
	}
	public void setChannelPaymentId(String channelPaymentId) {
		this.channelPaymentId = channelPaymentId;
	}
	public String getPrepayId() {
		return prepayId;
	}
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public long getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public Date getSubmitDate() {
        return submitDate;
    }
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }
    public Date getSuccussDate() {
        return succussDate;
    }
    public void setSuccussDate(Date succussDate) {
        this.succussDate = succussDate;
    }
    public Date getFailDate() {
        return failDate;
    }
    public void setFailDate(Date failDate) {
        this.failDate = failDate;
    }
    public Date getCancelDate() {
        return cancelDate;
    }
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }
    public Date getRefundApplyDate() {
        return refundApplyDate;
    }
    public void setRefundApplyDate(Date refundApplyDate) {
        this.refundApplyDate = refundApplyDate;
    }
    public Date getRefundDate() {
        return refundDate;
    }
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }
    public int getPayType() {
        return payType;
    }
    public void setPayType(int payType) {
        this.payType = payType;
    }
    public Integer getSettleType() {
        return settleType;
    }
    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }
    public Long getSettleId() {
        return settleId;
    }
    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }
}
