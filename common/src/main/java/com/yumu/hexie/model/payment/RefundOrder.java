package com.yumu.hexie.model.payment;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.BaseModel;

//退款单
@Entity
public class RefundOrder extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long userId;
	private int orderType;
	private long orderId;

    private Integer settleType;
    private Long settleId;
	
	private long paymentId;
	private String reason;//暂时没写
	private Date refundFinishDate; //退款完成时间
	private int initiatorType;//发起方类型 0 用户 1自动 2小二3商户
	private int refundStatus;//0初始化 1申请成功 2退款成功 3退款失败
	private String wxRefundStatus;
	//SUCCESS—退款成功
	//FAIL—退款失败
	//PROCESSING—退款处理中
	//NOTSURE—未确定，需要商户原退款单号重新发起
	//CHANGE
	private int refundType;//0拼单未成功 1用户主动发起退款 2退货
	
	private float totalFee;
	private float refundFee;
	private String paymentNo;
	private String refundNo;
	private String channelPaymentNo;
	private String channelRefundNo;
	
	public RefundOrder(){
		refundNo = OrderNoUtil.generateRefundOrderNo();
	}
	public RefundOrder(PaymentOrder po) {
		refundNo = OrderNoUtil.generateRefundOrderNo();
		setOrderType(po.getOrderType());
		setUserId(po.getUserId());
		setOrderId(po.getOrderId());
		setPaymentId(po.getId());
		setRefundStatus(PaymentConstant.REFUND_STATUS_INIT);
		
		setPaymentNo(po.getPaymentNo());
		setChannelPaymentNo(po.getChannelPaymentId());
		
		setTotalFee(po.getPrice());
		setRefundFee(po.getPrice());
		
		setSettleId(po.getSettleId());
		setSettleType(po.getSettleType());
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getRefundFinishDate() {
		return refundFinishDate;
	}
	public void setRefundFinishDate(Date refundFinishDate) {
		this.refundFinishDate = refundFinishDate;
	}
	public int getInitiatorType() {
		return initiatorType;
	}
	public void setInitiatorType(int initiatorType) {
		this.initiatorType = initiatorType;
	}
	public int getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(int refundStatus) {
		this.refundStatus = refundStatus;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public float getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}
	public float getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(float refundFee) {
		this.refundFee = refundFee;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getChannelRefundNo() {
		return channelRefundNo;
	}
	public void setChannelRefundNo(String channelRefundNo) {
		this.channelRefundNo = channelRefundNo;
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getChannelPaymentNo() {
		return channelPaymentNo;
	}
	public void setChannelPaymentNo(String channelPaymentNo) {
		this.channelPaymentNo = channelPaymentNo;
	}
	public String getWxRefundStatus() {
		return wxRefundStatus;
	}

	public void setWxRefundStatus(String wxRefundStatus) {
		this.wxRefundStatus = wxRefundStatus;
	}
    public int getOrderType() {
        return orderType;
    }
    public void setOrderType(int orderType) {
        this.orderType = orderType;
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
