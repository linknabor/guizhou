/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.payment.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundResp;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.RefundOrder;
import com.yumu.hexie.model.payment.RefundOrderRepository;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.payment.RefundService;

/**
 * <pre>
 * 退款实现
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RefundServiceImpl.java, v 0.1 2016年4月5日 下午8:29:18  Exp $
 */
@Service("refundService")
public class RefundServiceImpl implements RefundService {

    @Inject
    protected WechatCoreService wechatCoreService;
    @Inject
    protected RefundOrderRepository refundOrderRepository;
    /** 
     * @param refundOrder
     * @return
     * @see com.yumu.hexie.service.payment.RefundService#refundApply(com.yumu.hexie.model.payment.RefundOrder)
     */
    @Override
    public boolean refundApply(RefundOrder ro) {
        WxRefundResp wro = wechatCoreService.requestRefund(ro);
        if(wro.isSuccess()) {
            if(wro.isRefundSuccess()){
                ro.setChannelRefundNo(wro.getRefund_id());
                ro.setRefundStatus(PaymentConstant.REFUND_STATUS_APPLYED);
                refundOrderRepository.save(ro);
            }
        } else {
            //ro.setChannelRefundNo(wro.getRefund_id());
            ro.setRefundStatus(PaymentConstant.REFUND_STATUS_FAIL);
            refundOrderRepository.save(ro);
        }
        return wro.isSuccess();
    }

    /** 
     * @param wxRefundOrder
     * @return
     * @see com.yumu.hexie.service.payment.RefundService#updateRefundStatus(com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder)
     */
    @Override
    public RefundOrder updateRefundStatus(WxRefundOrder wxRefundOrder) {
        if(!wxRefundOrder.isSuccess()) {
            return null;
        } 
        String refundNo = wxRefundOrder.getOut_refund_no_0();
        List<RefundOrder> ros = refundOrderRepository.findAllByRefundNo(refundNo);
        if(ros == null || ros.size()==0) {
            throw new BizValidateException("没有找到对应退款记录！").setError();
        }
        RefundOrder refundOrder = ros.get(0);
        refundOrder.setChannelRefundNo(wxRefundOrder.getTransaction_id());
        refundOrder.setWxRefundStatus(wxRefundOrder.getRefund_status_0());
        if(wxRefundOrder.isRefundProcessing()) {
            return refundOrderRepository.save(refundOrder);
        } else if(wxRefundOrder.isRefundSuccess()) {
            refundOrder.setRefundStatus(PaymentConstant.REFUND_STATUS_SUCCESS);
        } else if(wxRefundOrder.isRefundFail()){
            refundOrder.setRefundStatus(PaymentConstant.REFUND_STATUS_FAIL);
        }
        return refundOrderRepository.save(refundOrder);
    }
}
