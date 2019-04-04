/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.settle.SettleBill;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.CommonHomeService;
import com.yumu.hexie.service.payment.PaymentService;
import com.yumu.hexie.service.payment.SettleService;
import com.yumu.hexie.service.user.CouponService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CommonHomeServiceImpl.java, v 0.1 2016年5月20日 下午12:16:15  Exp $
 */
@Service("commonHomeService")
public class CommonHomeServiceImpl implements CommonHomeService {

    @Inject
    private CouponService couponService;
    @Inject
    private SettleService settleService;
    @Inject
    private PaymentService paymentService;
    @Override
    public PaymentOrder reqPay(BaseO2OService bill, String openId) {
        //校验订单状态
        if(!bill.payable()){
            throw new BizValidateException(bill.getId(),"订单状态不可支付，请重新查询确认订单状态！").setError();
        }
        if(bill.getCouponId() != null && bill.getCouponId() != 0) {
            Coupon c = couponService.findOne(bill.getCouponId());
            if(!couponService.lock(bill, c)){
                throw new BizValidateException(bill.getId(),"订单对应的红包已不可使用，请重新下单！").setError();
            }
        }
        
        return paymentService.fetchPaymentOrder(bill,openId);
    }
    
    public void udpateSettleInfo(BaseO2OService bill,Long merchantId,
                                 List<HomeBillItem> items, PaymentOrder pay) {
        if(merchantId == null) {
            return;
        }
        SettleBill settle = settleService.createSettle(bill, merchantId, items, pay);
        settleService.confirmBiz(bill.getSettleType(), bill.getId());
        pay.setSettleId(settle.getId());
        paymentService.save(pay);
    }
}
