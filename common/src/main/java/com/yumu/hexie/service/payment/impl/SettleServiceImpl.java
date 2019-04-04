/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.payment.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.localservice.basemodel.NeedShipFee;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.settle.SettleBill;
import com.yumu.hexie.model.settle.SettleBillItem;
import com.yumu.hexie.model.settle.SettleBillItemRepository;
import com.yumu.hexie.model.settle.SettleBillRepository;
import com.yumu.hexie.model.settle.SettleConstant;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.payment.SettleService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SettleServiceImpl.java, v 0.1 2016年5月4日 下午8:14:55  Exp $
 */
@Service("settleService")
public class SettleServiceImpl implements SettleService {
    
    @Inject
    private SettleBillRepository settleBillRepository;
    @Inject
    private SettleBillItemRepository settleBillItemRepository;

    @Override
    public SettleBill createSettle(BaseO2OService bill, long merchantId,List<HomeBillItem> items, PaymentOrder pay) {
        SettleBill sb = initSettleBill(bill, merchantId, pay);
        sb = settleBillRepository.save(sb);
        for(HomeBillItem item : items) {
            SettleBillItem i = new SettleBillItem();
            i.setBillItemId(item.getId());
            i.setSettleBillId(sb.getId());
            settleBillItemRepository.save(i);
        }
        return sb;
    }

    private SettleBill initSettleBill(BaseO2OService bill, long merchantId, PaymentOrder pay) {
        SettleBill sb = new SettleBill();
        sb.setAmount(BigDecimal.valueOf(pay.getPrice()));
        sb.setConfirmDate(new Date(pay.getCreateDate()));
        sb.setCouponId(bill.getCouponId());
        sb.setMerchantId(merchantId);
        sb.setOrderId(bill.getId());
        if(bill instanceof NeedShipFee) {
            sb.setShipFee(((NeedShipFee)bill).getShipFee());
            sb.setShipSettleFee(((NeedShipFee)bill).getShipSettleFee());
        }
        sb.setOrderNo(OrderNoUtil.generateSettleOrderNo());
        sb.setOrderType(bill.getSettleType());
        sb.setPaymentId(pay.getId());
        sb.setStatus(SettleConstant.STATUS_PAYED);
        return sb;
    }

    /** 
     * @param orderType
     * @param orderId
     * @see com.yumu.hexie.service.payment.SettleService#confirmBiz(int, long)
     */
    @Override
    public void confirmBiz(int orderType, long orderId) {
        SettleBill bill = findSettleBill(orderType, orderId);
        //bill.setServiceFinishDate(new Date());//20160802 这一步操作是错的，不应该有，结算时会用到该字段
        bill.setStatus(SettleConstant.STATUS_CONFIRM);
        settleBillRepository.save(bill);
    }

    private SettleBill findSettleBill(int orderType, long orderId) {
        List<SettleBill> bills = settleBillRepository.findByOrderTypeAndOrderId(orderType, orderId);
        if(bills.size() > 1) {
            //记录错误
            throw new BizValidateException("结算单记录不正确");
        }
        SettleBill bill = bills.get(0);
        return bill;
    }

    /** 
     * @param orderType
     * @param orderId
     * @see com.yumu.hexie.service.payment.SettleService#refundApply(int, long)
     */
    @Override
    public void refundApply(int orderType, long orderId) {
        SettleBill bill = findSettleBill(orderType, orderId);
        bill.setStatus(SettleConstant.STATUS_REFUNDING);
        settleBillRepository.save(bill);
    }

    /** 
     * @param orderType
     * @param orderId
     * @see com.yumu.hexie.service.payment.SettleService#refudned(int, long)
     */
    @Override
    public void refudned(int orderType, long orderId) {
        SettleBill bill = findSettleBill(orderType, orderId);
        bill.setStatus(SettleConstant.STATUS_REFUNDED);
        settleBillRepository.save(bill);
    }

}
