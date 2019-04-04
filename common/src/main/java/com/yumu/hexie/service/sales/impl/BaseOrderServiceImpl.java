package com.yumu.hexie.service.sales.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.common.util.ConfigUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.integration.wechat.service.TemplateMsgService;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.comment.Comment;
import com.yumu.hexie.model.commonsupport.comment.CommentConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.OrderItemRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.promotion.coupon.CouponSeed;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.car.CarService;
import com.yumu.hexie.service.comment.CommentService;
import com.yumu.hexie.service.common.ShareService;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.payment.PaymentService;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.service.sales.ProductService;
import com.yumu.hexie.service.sales.SalePlanService;
import com.yumu.hexie.service.user.UserNoticeService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.vo.CreateOrderReq;
import com.yumu.hexie.vo.SingleItemOrder;

@Service("baseOrderService")
public class BaseOrderServiceImpl extends BaseOrderProcessor implements BaseOrderService {

    protected static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);
	public static String COUPON_URL = ConfigUtil.get("couponUrl");
	@Inject
	protected ServiceOrderRepository serviceOrderRepository;
	@Inject
	protected OrderItemRepository orderItemRepository;
	@Inject
	protected ProductService productService;
	@Inject
	protected PaymentService paymentService;
	@Inject
	protected UserService userService;
	@Inject 
	protected UserNoticeService userNoticeService;
	@Inject
	protected CommentService commentService;
	@Inject
	protected WechatCoreService wechatCoreService;
	@Inject
	protected ShareService shareService;
	
	@Inject
	private SalePlanService salePlanService;
	
	@Inject
	private CarService carService;
	
	@Inject
	private SystemConfigService systemconfigservice;

    @Value(value = "${testMode}")
    private boolean testMode;
	private void preOrderCreate(ServiceOrder order, Address address){
	    log.warn("[Create]创建订单OrderNo:" + order.getOrderNo());
		for(OrderItem item : order.getItems()){
			SalePlan plan = findSalePlan(order.getOrderType(),item.getRuleId());
			//校验规则
			salePlanService.getService(order.getOrderType()).validateRule(order, plan, item, address);
			//校验商品
			Product product = productService.getProduct(plan.getProductId());
			productService.checkSalable(product, item.getCount());
			//填充信息
			productService.freezeCount(product, item.getCount());
            item.fillDetail(plan, product);
            
			if(StringUtil.isEmpty(order.getProductName())){
                order.fillProductInfo(product);
                order.setGroupRuleId(plan.getId());
            }
		}
		computePrice(order);
		log.warn("[Create]创建订单OrderNo:" + order.getOrderNo()+"|" + order.getProductName() +"|"+order.getPrice());
	}

	@Override
	public ServiceOrder createRepairOrder(RepairOrder order, float amount) {
        ServiceOrder sOrder = null;
        OrderItem item = null;
        if(order.getOrderId() != null && order.getOrderId() != 0) {
            sOrder = serviceOrderRepository.findOne(order.getOrderId());
            if(sOrder!=null){
                if(sOrder.getStatus() == ModelConstant.ORDER_STATUS_CANCEL){
                    sOrder = null;
                } else  if(sOrder.getStatus() != ModelConstant.ORDER_STATUS_INIT){
                    throw new BizValidateException("该维修单无法线上支付");
                } else {
                    List<OrderItem> items = orderItemRepository.findByServiceOrder(sOrder);
                    item = items.get(0);
                }
            }
        }
        if(sOrder == null) {
            sOrder = new ServiceOrder(order,amount);
            item = sOrder.getItems().get(0);
        }
        fillAddressInfo(sOrder);

        sOrder.setPrice(amount);
        sOrder = serviceOrderRepository.save(sOrder);

        item.setServiceOrder(sOrder);
        item.setAmount(amount*1f);
        item.setUserId(sOrder.getUserId());
        orderItemRepository.save(item);
        return sOrder;
    }
	
	//创建订单
	@Override
	@Transactional
	public ServiceOrder createOrder(SingleItemOrder order){
		return createOrder(new ServiceOrder(order));
	}

	@Override
	@Transactional
	public ServiceOrder createOrder(CreateOrderReq req,Cart cart,long userId,String openId){
		return createOrder(new ServiceOrder(req, cart, userId, openId));
	}

	private ServiceOrder createOrder(ServiceOrder o) {
		//1. 填充地址信息
		Address address = fillAddressInfo(o);
		//2. 填充订单信息并校验规则,设置价格信息
		preOrderCreate(o, address);
		computeCoupon(o);
		//3. 订单创建
		o = serviceOrderRepository.save(o);
		for(OrderItem item : o.getItems()) {
			item.setServiceOrder(o);
			item.setUserId(o.getUserId());
			orderItemRepository.save(item);
		}
		//3.1保存车辆信息 20160721 车大大的车辆服务
		carService.saveOrderCarInfo(o);
		
        log.warn("[Create]订单创建OrderNo:" + o.getOrderNo());
		//4. 订单后处理
		commonPostProcess(ModelConstant.ORDER_OP_CREATE,o);
		return o;
		
	}

	@Async
	protected void commonPostProcess(int orderOp, ServiceOrder order) {

		log.error("commonPostProcess" + order.getOrderNo());
		if(orderOp==ModelConstant.ORDER_OP_CREATE){
			log.error("shareService.record" + order.getOrderNo());
			shareService.record(order);
			//userNoticeService.noticeUser(order.getUserId(), ModelConstant.NOTICE_TYPE_NOTICE, "订单"+order.getOrderNo()+"已创建", "");
		} else if(orderOp==ModelConstant.ORDER_OP_CANCEL){
			//userNoticeService.noticeUser(order.getUserId(), ModelConstant.NOTICE_TYPE_NOTICE, "订单"+order.getOrderNo()+"已取消！", "");
		} else if(orderOp == ModelConstant.ORDER_OP_UPDATE_PAYSTATUS
				&&(order.getStatus()==ModelConstant.ORDER_STATUS_PAYED||order.getStatus()==ModelConstant.ORDER_STATUS_CONFIRM)){
			if(order.getOrderType() != ModelConstant.ORDER_TYPE_YUYUE){
				User user = userService.getById(order.getUserId());//短信发送号码修改为用户注册号码 20160120
				userNoticeService.orderSuccess(order.getUserId(), user.getTel(),order.getId(), order.getOrderNo(), order.getProductName(), order.getPrice());
			}
			String token = systemconfigservice.queryWXAToken();
			TemplateMsgService.sendPaySuccessMsg(order, token);
		} else if(orderOp == ModelConstant.ORDER_OP_SEND){
			userNoticeService.orderSend(order.getUserId(), order.getTel(),order.getId(), order.getOrderNo(), order.getLogisticName(), order.getLogisticNo());
		}
	}




	@Override
	public JsSign requestPay(ServiceOrder order) {
        log.warn("[requestPay]OrderNo:" + order.getOrderNo());
		//校验订单状态
		if(!order.payable()){
            throw new BizValidateException(order.getId(),"订单状态不可支付，请重新查询确认订单状态！").setError();
        }
		//获取支付单
		PaymentOrder pay = paymentService.fetchPaymentOrder(order);
        log.warn("[requestPay]PaymentId:" + pay.getId());
		//发起支付
		JsSign sign = paymentService.requestPay(pay);
        log.warn("[requestPay]NonceStr:" + sign.getNonceStr());
		//操作记录
		commonPostProcess(ModelConstant.ORDER_OP_REQPAY,order);
		return sign;
	}




	@Transactional
    @Override
    public void update4Payment(PaymentOrder payment) {

        log.warn("[update4Payment]Payment:" + payment.getId());
        ServiceOrder order = serviceOrderRepository.findOneWithItem(payment.getOrderId());
		switch(payment.getStatus()) {
            case PaymentConstant.PAYMENT_STATUS_CANCEL:
            case PaymentConstant.PAYMENT_STATUS_FAIL:
            case PaymentConstant.PAYMENT_STATUS_INIT:
                break;
            case PaymentConstant.PAYMENT_STATUS_REFUNDING:
                order.refunding();
                serviceOrderRepository.save(order);
                log.warn("[update4Payment]Refunding");
                break;
            case PaymentConstant.PAYMENT_STATUS_SUCCESS:
                if(order.getStatus()==ModelConstant.ORDER_STATUS_INIT){
                    salePlanService.getService(order.getOrderType()).postPaySuccess(payment, order);
                    couponService.comsume(order);
                    createCouponSeedIfExist(order);
                    commonPostProcess(ModelConstant.ORDER_OP_UPDATE_PAYSTATUS,order);
                    log.warn("[update4Payment]Success");
                }
                break;
            default:
                break;
                
        }
    }
	
	@Async
	protected void createCouponSeedIfExist(ServiceOrder so) {
		CouponSeed cs = couponService.createOrderSeed(so.getUserId(), so);
		if(cs != null) {
			so.setSeedStr(cs.getSeedStr());
			serviceOrderRepository.save(so);
		}
	}

	@Override
	public void notifyPayed(long orderId) {
        log.warn("[notifyPayed]orderId:"+orderId);
		ServiceOrder so = serviceOrderRepository.findOne(orderId);
		if(so == null || so.getStatus() == ModelConstant.ORDER_STATUS_PAYED) {
		    return;
		}
        PaymentOrder payment = paymentService.fetchPaymentOrder(so);
        payment = paymentService.refreshStatus(payment);
        update4Payment(payment);
	}

	@Override
	@Transactional
	public ServiceOrder confirmOrder(ServiceOrder order) {
        log.warn("[confirmOrder]orderId:"+order.getId());
		order.confirm();
		order = serviceOrderRepository.save(order);
		salePlanService.getService(order.getOrderType()).postOrderConfirm(order);
        log.warn("[confirmOrder]PostConfirm:"+order.getId());
		commonPostProcess(ModelConstant.ORDER_OP_CONFIRM,order);
		return order;
	}
	//订单取消，如果团购单，消减人员，修改团购人员数量
	@Override
	@Transactional
	public ServiceOrder cancelOrder(ServiceOrder order) {
        log.warn("[cancelOrder]req:"+order.getId());
	    //1. 校验
		if(!order.cancelable()) {
            throw new BizValidateException(order.getId(),"该订单不能取消！").setError();
        }
		//2. 取消支付单
	    paymentService.cancelPayment(PaymentConstant.TYPE_MARKET_ORDER, order.getId());
        log.warn("[cancelOrder]payment:"+order.getId());
		order.cancel();
		serviceOrderRepository.save(order);
        log.warn("[cancelOrder]order:"+order.getId());
		//3.解锁红包
		couponService.unlock(order.getCouponId());
        log.warn("[cancelOrder]coupon:"+order.getCouponId());
		//4.商品中取消冻结
		salePlanService.getService(order.getOrderType()).postOrderCancel(order);
        log.warn("[cancelOrder]unfrezee:"+order.getCouponId());
		//5.操作后处理
		commonPostProcess(ModelConstant.ORDER_OP_CANCEL,order);
		return order;
	}


	@Override
	public ServiceOrder signOrder(ServiceOrder order) {
        log.warn("[signOrder]order:"+order.getId());
		if(!order.signable()) {
            throw new BizValidateException(order.getId(),"该订单无法签收！").setError();
        }
		order.sign();
		order = serviceOrderRepository.save(order);
        log.warn("[signOrder]order-signed:"+order.getId());
		commonPostProcess(ModelConstant.ORDER_OP_SIGN,order);
		return order;
	}
	@Transactional
	@Override
	public void comment(ServiceOrder order, Comment comment) {
        log.warn("[comment]order-signed:"+order.getId());
		if(order.getPingjiaStatus() == ModelConstant.ORDER_PINGJIA_TYPE_Y) {
			throw new BizValidateException(order.getId(),"该订单已评价").setError();
		} else if(order.getStatus() != ModelConstant.ORDER_STATUS_RECEIVED){
			throw new BizValidateException(order.getId(),"订单不是签收状态，您无法进行评价！").setError();
		}
		comment = commentService.comment(CommentConstant.TYPE_MARKET_ORDER, order.getId(), comment);
        log.warn("[comment]comment-finish:"+comment.getId());
		order.setPingjiaStatus(ModelConstant.ORDER_PINGJIA_TYPE_Y);
		serviceOrderRepository.save(order);
        log.warn("[comment]order-finish:"+order.getId());
		commonPostProcess(ModelConstant.ORDER_OP_COMMENT,order);
	}


	//FIXME 注意该方法不应该被外部用户调用
	@Transactional
	@Override
	public ServiceOrder refund(ServiceOrder order) {
        log.warn("[refund]refund-begin:"+order.getId());
		if(!order.refundable()) {
            throw new BizValidateException(order.getId(),"该订单无法退款！").setError();
        }
		PaymentOrder po = paymentService.fetchPaymentOrder(order);
		if(paymentService.refundApply(po)){
			//FIXME 支付单直接从支付成功到已退款状态  po.setStatus(ModelConstant.PAYMENT_STATUS_REFUND);
			order.refunding(true);
		}
		order = serviceOrderRepository.save(order);
        log.warn("[refund]refund-finish:"+order.getId());
		commonPostProcess(ModelConstant.ORDER_OP_REFUND_REQ,order);
		return order;
	}
	
	

	@Transactional
	@Override
	public void finishRefund(WxRefundOrder wxRefundOrder) {
        log.warn("[finishRefund]refund-begin:"+wxRefundOrder.getOut_trade_no());
		PaymentOrder po = paymentService.updateRefundStatus(wxRefundOrder);
		ServiceOrder serviceOrder = serviceOrderRepository.findOne(po.getOrderId());
		if(po.getStatus() == PaymentConstant.PAYMENT_STATUS_REFUNDED) {
			serviceOrder.setStatus(ModelConstant.ORDER_STATUS_REFUNDED);
			serviceOrderRepository.save(serviceOrder);
	        log.warn("[finishRefund]refund-saved:"+serviceOrder.getId());
			commonPostProcess(ModelConstant.ORDER_OP_REFUND_FINISH,serviceOrder);
		}
        log.warn("[finishRefund]refund-end:"+wxRefundOrder.getOut_trade_no());
	}
	public ServiceOrder findOne(long orderId){
	    return serviceOrderRepository.findOne(orderId);
	}
}
