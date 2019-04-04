package com.yumu.hexie.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.localservice.bill.BaojieBill;
import com.yumu.hexie.model.localservice.bill.BaojieBillRepository;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.bill.YunXiyiBillRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.market.saleplan.RgroupRuleRepository;
import com.yumu.hexie.model.op.ScheduleRecord;
import com.yumu.hexie.model.op.ScheduleRecordRepository;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.PaymentOrderRepository;
import com.yumu.hexie.model.payment.RefundOrder;
import com.yumu.hexie.model.payment.RefundOrderRepository;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.system.BizError;
import com.yumu.hexie.model.system.BizErrorRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.ScheduleService;
import com.yumu.hexie.service.common.SmsService;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.BaojieService;
import com.yumu.hexie.service.o2o.XiyiService;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.service.sales.RgroupService;
import com.yumu.hexie.service.user.CouponService;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService{

	private static final Logger SCHEDULE_LOG = LoggerFactory.getLogger("com.yumu.hexie.schedule");
	@Inject
	private ServiceOrderRepository serviceOrderRepository;
	@Inject
	private PaymentOrderRepository paymentOrderRepository;
	@Inject
	private RefundOrderRepository refundOrderRepository;
	@Inject
	private WechatCoreService wechatCoreService;
    @Inject
    private RgroupService rgroupService;
    @Inject
    private BaseOrderService baseOrderService;
	@Inject
	private RgroupRuleRepository rgroupRuleRepository;

    @Inject
    private YunXiyiBillRepository yunXiyiBillRepository;
    @Inject
    private XiyiService xiyiService;
    @Inject
    private BaojieBillRepository baojieBillRepository;
    @Inject
    private BaojieService baojieService;

    @Inject
    private BizErrorRepository bizErrorRepository;
    
    @Inject 
    private ScheduleRecordRepository scheduleRecordRepository;
    
    @Inject
    private CouponService couponService;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private SmsService smsService;
	
	//1. 订单超时
    @Scheduled(cron = "50 1/3 * * * ?")
    public void executeOrderTimeoutJob() {
    	executeOrderTimeoutJob(serviceOrderRepository.findTimeoutServiceOrder(System.currentTimeMillis()));
    }
	//2. 退款状态更新
    @Scheduled(cron = "10 10/30 * * * ?")
    public void executeRefundStatusJob() {
    	executeRefundStatusJob(refundOrderRepository.findAllApplyedRefund(System.currentTimeMillis()-1800*1000));
    }
	//3. 商品支付状态更新
    @Scheduled(cron = "10 2/3 * * * ?")
    public void executePayOrderStatusJob() {
        executeMarketPayOrderStatusJob(paymentOrderRepository.queryAllUnpayMarketOrderIds());
    }
    
   //4. 团购团超时
    @Scheduled(cron = "11 2/5 * * * ?")
    public void executeRGroupTimeoutJob() {
    	SCHEDULE_LOG.debug("--------------------executeGroupTimeoutJob[B][R]-------------------");
    	List<RgroupRule> rules = rgroupRuleRepository.findTimeoutGroup(new Date());
    	if(rules.size() == 0) {
    		SCHEDULE_LOG.error("**************executeRGroupTimeoutJob没有记录");
    		return;
    	}
    	String ids = "";
    	for(RgroupRule rule : rules) {
    		ids += rule.getId()+",";
    	}
    	ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_TUANGOU_TIMEOUT,ids);
    	sr = scheduleRecordRepository.save(sr);
    	
    	for(RgroupRule rule : rules) {
    		try{
    	    	SCHEDULE_LOG.debug("refreshGroupStatus:" + rule.getId());
    	    	rgroupService.refreshGroupStatus(rule);
    		} catch(Exception e){
    			SCHEDULE_LOG.error("超时订单更新失败"+ rule.getId(),e);
    			recordError(e);
    			sr.addErrorCount(""+rule.getId());
    		}
    	}
    	sr.setFinishDate(new Date());
    	scheduleRecordRepository.save(sr);
    	SCHEDULE_LOG.debug("--------------------executeGroupTimeoutJob[E][R]-------------------");
    }
  //4. 保洁超时  YunXiyiBillRepository
    @Scheduled(cron = "50 1/2 * * * ?")
    public void executeBaojieTimeoutJob() {
        SCHEDULE_LOG.debug("--------------------executeBaojieTimeoutJob[B][R]-------------------");
        List<BaojieBill> bills = baojieBillRepository.findTimeoutBill(System.currentTimeMillis() - 30000);
        if(bills.size() == 0) {
            SCHEDULE_LOG.error("**************executeBaojieTimeoutJob没有记录");
            return;
        }
        String ids = "";
        for(BaojieBill rule : bills) {
            ids += rule.getId()+",";
        }
        ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_BAOJIE_TIMEOUT,ids);
        sr = scheduleRecordRepository.save(sr);
        
        for(BaojieBill rule : bills) {
            try{
                SCHEDULE_LOG.debug("XIYIBILL TimeOUt:" + rule.getId());
                baojieService.timeout(rule.getId());
            } catch(Exception e){
                SCHEDULE_LOG.error("超时保洁订单更新失败"+ rule.getId(),e);
                recordError(e);
                sr.addErrorCount(""+rule.getId());
            }
        }
        sr.setFinishDate(new Date());
        scheduleRecordRepository.save(sr);
        SCHEDULE_LOG.debug("--------------------executeBaojieTimeoutJob[E][R]-------------------");
    }
    
    //4. 洗衣超时  YunXiyiBillRepository
    @Scheduled(cron = "20 1/2 * * * ?")
    public void executeXiyiTimeoutJob() {
        SCHEDULE_LOG.debug("--------------------executeXiyiTimeoutJob[B][R]-------------------");
        List<YunXiyiBill> bills = yunXiyiBillRepository.findTimeoutBill(System.currentTimeMillis() - 30000);
        if(bills.size() == 0) {
            SCHEDULE_LOG.error("**************executeXiyiTimeoutJob没有记录");
            return;
        }
        String ids = "";
        for(YunXiyiBill rule : bills) {
            ids += rule.getId()+",";
        }
        ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_XIYI_TIMEOUT,ids);
        sr = scheduleRecordRepository.save(sr);
        
        for(YunXiyiBill rule : bills) {
            try{
                SCHEDULE_LOG.debug("XIYIBILL TimeOUt:" + rule.getId());
                xiyiService.timeout(rule.getId());
            } catch(Exception e){
                SCHEDULE_LOG.error("超时洗衣订单更新失败"+ rule.getId(),e);
                recordError(e);
                sr.addErrorCount(""+rule.getId());
            }
        }
        sr.setFinishDate(new Date());
        scheduleRecordRepository.save(sr);
        SCHEDULE_LOG.debug("--------------------executeXiyiTimeoutJob[E][R]-------------------");
    }
    

	/************************************定时任务，由各业务自行调用 **************************/
	//3. 支付状态查询
    private void executeMarketPayOrderStatusJob(List<Long> orderIds) {
    	SCHEDULE_LOG.debug("--------------------executePayOrderStatusJob[B]-------------------");
    	if(orderIds.size() == 0) {
    		SCHEDULE_LOG.error("**************executePayOrderStatusJob没有记录");
    		return;
    	}
    	String ids = "";
    	for(Long id : orderIds) {
    		ids += id+",";
    	}
    	ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_PAY_STATUS,ids);
    	sr = scheduleRecordRepository.save(sr);
    	
    	
    	for(Long id : orderIds) {
	    	SCHEDULE_LOG.debug("PayOrderNotify:" + id);
    		try{
    			baseOrderService.notifyPayed(id);
    		} catch(Exception e){
    			SCHEDULE_LOG.error("支付状态同步失败"+ id,e);
    			recordError(e);
    			sr.addErrorCount(""+id);
    		}
    	}
    	sr.setFinishDate(new Date());
    	scheduleRecordRepository.save(sr);
    	SCHEDULE_LOG.debug("--------------------executePayOrderStatusJob[E]-------------------");
    }
	
    
	//1. 支付单超时(25分一次，30分钟超时)
    private void executeOrderTimeoutJob(List<ServiceOrder> serviceOrders) {
    	SCHEDULE_LOG.debug("--------------------executeOrderTimeoutJob-------------------");
    	if(serviceOrders.size() == 0) {
    		SCHEDULE_LOG.error("**************executeOrderTimeoutJob没有记录");
    		return;
    	}
    	String ids = "";
    	for(ServiceOrder order : serviceOrders) {
    		ids += order.getId()+",";
    	}
    	ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_PAY_TIMEOUT,ids);
    	sr = scheduleRecordRepository.save(sr);
    	for(ServiceOrder order : serviceOrders) {
    		try{
    	    	SCHEDULE_LOG.debug("CancelOrder:" + order.getId());
    	    	baseOrderService.cancelOrder(order);
    		} catch(Exception e){
    			SCHEDULE_LOG.error("超时支付单失败orderID"+ order.getId(),e);
    			recordError(e);
    			sr.addErrorCount(""+order.getId());
    		}
    	}
    	sr.setFinishDate(new Date());
    	scheduleRecordRepository.save(sr);
    	SCHEDULE_LOG.debug("--------------------executeOrderTimeoutJob-------------------");
    }
	//2. 退款状态查询
    private void executeRefundStatusJob(List<RefundOrder> refundOrder) {
    	SCHEDULE_LOG.debug("--------------------executeRefundStatusJob-------------------");
    	if(refundOrder.size() == 0) {
    		SCHEDULE_LOG.error("**************executeRefundStatusJob没有记录");
    		return;
    	}
    	String ids = "";
    	for(RefundOrder order : refundOrder) {
    		ids += order.getId()+",";
    	}
    	ScheduleRecord sr = new ScheduleRecord(ModelConstant.SCHEDULE_TYPE_REFUND_STATUS,ids);
    	sr = scheduleRecordRepository.save(sr);
    	for(RefundOrder order : refundOrder) {
    		try{
    	    	SCHEDULE_LOG.debug("Refund:" + order.getId());
	    	    if(order.getOrderType() == PaymentConstant.TYPE_MARKET_ORDER){
	    	        baseOrderService.finishRefund(wechatCoreService.refundQuery(order.getPaymentNo()));
	            } else {
	                //xiyiService.update4Payment(payment);
	            }
    			
    		} catch(Exception e){
    			SCHEDULE_LOG.error("退款单更新失败orderID"+ order.getId(),e);
    			recordError(e);
    			sr.addErrorCount(""+order.getId());
    		}
    	}
    	sr.setFinishDate(new Date());
    	scheduleRecordRepository.save(sr);
    	SCHEDULE_LOG.debug("--------------------executeRefundStatusJob-------------------");
    }
    /************************************定时任务，由各业务自行调用 **************************/
    
    
    @Async
    private void recordError(Exception e) {
    	if(e instanceof BizValidateException){ 
    		if(((BizValidateException)e).getLevel() == ModelConstant.EXCEPTION_LEVEL_ERROR) {
		    	BizError be = new BizError(((BizValidateException)e));
		    	bizErrorRepository.save(be);
	    	}
    	} else {
    		BizError be = new BizError(e);
	    	bizErrorRepository.save(be);
    	}
	}
	@Override
    @Scheduled(cron = "15 */2 0 * * ?")
	public void executeCouponTimeoutJob() {
		List<Coupon> coupons = couponService.findTop100TimeoutCoupon();
		for(Coupon coupon : coupons) {
			try{
				couponService.timeout(coupon);
			}catch(Exception e) {
				SCHEDULE_LOG.error("[TIMEOUT]COUPON_ID:" + coupon.getId(),e);
			}
		}
	}
	
	/**
	 * 每天下午18:00触发 
	 * 1.优惠券到期智能提醒，在券到期前2天，提示用户券到期
	 * 2.只提醒全场通用券和社区红包(TODO 暂未实现，所有红包都发)
	 * 3.不管多少券，每个用户每个月最多只能发两条短信以免造成骚扰
	 */
	@Override
	@Scheduled(cron = "0 0 18 * * ? ")
	public void executeCoupinTimeoutHintJob() {
		
		String msg = "亲爱的邻居，您有amount元的优惠券即将过期，赶紧去“东湖e家园”看看吧！";

		SCHEDULE_LOG.debug("--------------------start executeCouponHintJob-------------------");
		
		Date currDate = new Date();
		
		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+2);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date fromDate = c.getTime();
		
		c = Calendar.getInstance();
		c.setTime(currDate);
		c.set(Calendar.DATE, c.get(Calendar.DATE)+2);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date toDate = c.getTime();
		
		List<Coupon> coupons = couponService.findTimeoutCouponByDate(fromDate, toDate);
		
		if (coupons.size()==0) {
			SCHEDULE_LOG.error("no coupon will be time out on" + currDate);
			return;
		}
		
		List<Long>userIdList = new ArrayList<Long>();
		
		for (int i = 0; i < coupons.size(); i++) {
			
			Coupon coupon = coupons.get(i);
			long userId = coupon.getUserId();
			
			//本次已经发送过短信的用户不再发送，以免短信黑名单
			if (userIdList.contains(userId)) {
				continue;
			}
			
			float couponAmt = coupon.getAmount();
			NumberFormat nf = new DecimalFormat("#");
			String displayAmt = nf.format(couponAmt);
			
			User user = userRepository.findOne(userId);
			
			if (user == null) {
				SCHEDULE_LOG.debug(" user does not exist " + userId);
			}else {
				
				String mobile = user.getTel();
				
				//找出最近发送的到期提醒短信，如果一个月内已有2次，则不再做提醒。
				c = Calendar.getInstance();
				c.setTime(currDate);
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
				Date lastDate = c.getTime();
				int sentCounts = smsService.getByPhoneAndMesssageTypeInOneMonth(mobile, 3, lastDate);
				if (sentCounts >= 2) {
					continue;
				}
				
				if (StringUtil.isEmpty(mobile)) {
					SCHEDULE_LOG.debug("user has no mobile , user_id :" + userId);
				}
				
				String sendMsg = msg.replace("amount", displayAmt);
				
				smsService.sendMsg(userId, mobile, sendMsg, 12, 3);
				SCHEDULE_LOG.debug("msg sent, mobile :" + mobile + ", userId: " + userId + "msg : " + sendMsg);
				
				userIdList.add(userId);
			}
		}
		SCHEDULE_LOG.debug("--------------------end executeCouponHintJob-------------------");
	}
	
	
}
