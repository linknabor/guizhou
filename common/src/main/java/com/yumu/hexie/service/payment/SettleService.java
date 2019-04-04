/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.payment;

import java.util.List;

import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.settle.SettleBill;

/**
 * <pre>
 * 结算单落地
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SettleService.java, v 0.1 2016年5月4日 下午5:57:58  Exp $
 */
public interface SettleService {
    //结算单落地
    public SettleBill createSettle(BaseO2OService bill, long merchantId,List<HomeBillItem> items, PaymentOrder pay);
    
    //订单用户确认
    public void confirmBiz(int orderType, long orderId);
    
    //发起退款
    public void refundApply(int orderType, long orderId);
    
    //退款完成更新结算单
    public void refudned(int orderType, long orderId);
}
