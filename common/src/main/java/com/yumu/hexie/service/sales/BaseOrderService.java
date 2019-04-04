package com.yumu.hexie.service.sales;

import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.model.commonsupport.comment.Comment;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.vo.CreateOrderReq;
import com.yumu.hexie.vo.SingleItemOrder;

public interface BaseOrderService {
    //创建维修单
    public ServiceOrder createRepairOrder(RepairOrder order, float amount);
	//创建订单
	public ServiceOrder createOrder(SingleItemOrder order);
	//创建订单
	public ServiceOrder createOrder(CreateOrderReq req,Cart cart,long userId,String openId);
	//发起支付
	public JsSign requestPay(ServiceOrder order);
	//支付状态变更
	public void update4Payment(PaymentOrder payment);
	//通知支付成功
	public void notifyPayed(long orderId);
	//取消订单
	public ServiceOrder cancelOrder(ServiceOrder order);
	//确认订单
	public ServiceOrder confirmOrder(ServiceOrder order);
	//确认或签收
	public ServiceOrder signOrder(ServiceOrder order);
	//评价
	public void comment(ServiceOrder order,Comment comment);
	//退款
	public ServiceOrder refund(ServiceOrder order);
	//退款完成
	public void finishRefund(WxRefundOrder wxRefundOrder);
	
	public ServiceOrder findOne(long orderId);
}
