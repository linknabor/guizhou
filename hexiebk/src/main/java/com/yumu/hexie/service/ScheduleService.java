package com.yumu.hexie.service;


public interface ScheduleService {

	//1. 支付单超时未支付
    public void executeOrderTimeoutJob();
	//2. 退款状态查询更新
    public void executeRefundStatusJob();
	//3. 支付状态更新
    public void executePayOrderStatusJob();
    //4. 拼单团超时
    //public void executeGroupTimeoutJob();
    //5. 团购团超时
    public void executeRGroupTimeoutJob();
    //6. 红包超时
    public void executeCouponTimeoutJob();
    //7.优惠券到期提醒
    public void executeCoupinTimeoutHintJob();
   
}
