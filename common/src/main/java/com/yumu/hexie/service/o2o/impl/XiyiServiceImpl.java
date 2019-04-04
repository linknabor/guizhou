/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.HomeBillItemRepository;
import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.O2OServiceBuilder;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.bill.YunXiyiBillRepository;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.CommonHomeService;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.ShipFeeService;
import com.yumu.hexie.service.o2o.XiyiService;
import com.yumu.hexie.service.o2o.req.CommonBillReq;
import com.yumu.hexie.service.payment.PaymentService;
import com.yumu.hexie.service.sales.impl.BaseOrderServiceImpl;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.CouponService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: XiyiServiceImpl.java, v 0.1 2016年4月11日 上午12:40:35  Exp $
 */
@Service("xiyiService")
public class XiyiServiceImpl implements XiyiService {

    protected static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);
    @Inject
    private PaymentService paymentService;
    @Inject
    private YunXiyiBillRepository yunXiyiBillRepository;
    @Inject
    private HomeBillItemRepository homeBillItemRepository;
    @Inject
    private AddressService addressService;
    @Inject
    private CouponService couponService;
    @Inject
    private HomeItemService homeItemService;
    
    @Inject
    private ShipFeeService shipFeeService;
    
    @Inject
    private CommonHomeService commonHomeService;
    @Inject
    private GotongService gotongService;

    /** 
     * @param req
     * @param cart
     * @return
     * @see com.yumu.hexie.service.o2o.XiyiService#createBill(com.yumu.hexie.vo.CreateOrderReq, com.yumu.hexie.model.localservice.HomeCart)
     */
    @Transactional
    @Override
    public YunXiyiBill createBill(User user,CommonBillReq req, HomeCart cart) {
        Date d = DateUtil.parse(req.getReqTime(),"yyyy-MM-dd HH:mm");
        long time = d.getTime()-System.currentTimeMillis();
        if(time>7*24*3600000 || time < 7200000) {
            throw new BizValidateException("服务时间不支持！");
        }
        Address addr = addressService.queryAddressById(req.getAddressId());
        O2OServiceBuilder<YunXiyiBill> ob = O2OServiceBuilder.init(YunXiyiBill.class);
        ob.initCart(cart).userId(user.getId()).req(req)
            .address(addr).calculateNoCoupon();

        if(cart.getItems().size() > 0){
            log.error("查询运费模板");
            ShipFeeTemplate tpl = shipFeeService.findTemplateByItem(cart.getItems().get(0).getServiceId());
            log.error("查询运费模板:" + tpl.getId());
            ob.shipFee(tpl, addr);
        }
        
        Coupon coupon = (req.getCouponId() == null || req.getCouponId() == 0)?null:couponService.findOne(req.getCouponId());
        if(couponService.isAvaible(cart, coupon)){
            ob.coupon(coupon);
        }
        
        //非抢单模式设置商户ID
        ServiceType baseType = homeItemService.findBaseTypeByItem(cart.getItems().get(0).getServiceId());
        Merchant m = new Merchant();
        m.setId(baseType.getMerchantId());
        ob.merchant(m);
        
        ob.getBill().setItemType(cart.getItemType());
        YunXiyiBill bill = yunXiyiBillRepository.save(ob.getBill());
        for(HomeBillItem item : ob.getBill().getItems()) {
            ServiceType type = homeItemService.findTypeByItem(item.getServiceId());
            item.setBillType(HomeServiceConstant.SERVICE_TYPE_XIYI);
            item.setParentType(type.getId());
            item.setBillId(bill.getId());
        }
        homeBillItemRepository.save(ob.getBill().getItems());
        log.warn("创建洗衣订单" + bill.getId()); 
        return bill;
    }

    /** 
     * @param bill
     * @return
     * @see com.yumu.hexie.service.o2o.XiyiService#pay(com.yumu.hexie.model.localservice.bill.YunXiyiBill)
     */
    @Override
    public JsSign pay(YunXiyiBill bill, String openId) {
        log.warn("发起支付[BEG]" + bill.getId()); 
        //获取支付单
        PaymentOrder pay = commonHomeService.reqPay(bill,openId);
        //发起支付
        bill.pay(pay.getId());
        bill = yunXiyiBillRepository.save(bill);
        log.warn("发起支付[END]" + bill.getId()); 
        return paymentService.requestPay(pay);
    }

    private void paySuccess(YunXiyiBill bill,PaymentOrder pay) {
        log.warn("支付成功[BEG]" + bill.getId()); 
        bill.setStatus(HomeServiceConstant.ORDER_STATUS_PAYED);
        yunXiyiBillRepository.save(bill);
        //billAssignService.assignXiyiOrder(bill);
        commonHomeService.udpateSettleInfo(bill, bill.getMerchantId(),
            findItems(bill.getId()),pay);
        notify2Operators(bill);
        log.warn("支付成功[END]" + bill.getId()); 
    }

    private void notify2Operators(YunXiyiBill bill){
        gotongService.sendCommonYuyueBillMsg(HomeServiceConstant.SERVICE_TYPE_XIYI,
                "您有一条新的订单消息",bill.getProjectName(), DateUtil.dtFormat(bill.getRequireDate(),"yyyy-MM-dd HH:mm"), "");    
    }
    /** 
     * @param payment
     * @see com.yumu.hexie.service.o2o.XiyiService#update4Payment(com.yumu.hexie.model.payment.PaymentOrder)
     */
    @Override
    public void update4Payment(PaymentOrder payment) {
        switch(payment.getStatus()) {
            case PaymentConstant.PAYMENT_STATUS_CANCEL:
            case PaymentConstant.PAYMENT_STATUS_FAIL:
            case PaymentConstant.PAYMENT_STATUS_INIT:
                break;
            case PaymentConstant.PAYMENT_STATUS_SUCCESS:
                YunXiyiBill bill = yunXiyiBillRepository.findOne(payment.getOrderId());
                if(bill.getStatus()==HomeServiceConstant.ORDER_STATUS_CREATE){
                    paySuccess(bill,payment);
                }
                couponService.comsume(bill);
                break;
            default:
                break;
                
        }
    }

    /** 
     * @param billId
     * @see com.yumu.hexie.service.o2o.XiyiService#notifyPayed(long)
     */
    @Async
    @Override
    public void notifyPayed(long billId) {
        log.warn("到家notifyPayed成功[BEG]" + billId); 
        try {
            Thread.sleep(1000);//等待微信端处理完成
        } catch (InterruptedException e) {
        }
        YunXiyiBill bill = yunXiyiBillRepository.findOne(billId);
        if(bill == null || bill.getStatus() != HomeServiceConstant.ORDER_STATUS_CREATE) {
            return;
        }
        PaymentOrder payment = paymentService.queryPaymentOrder(PaymentConstant.TYPE_XIYI_ORDER,billId);
        payment = paymentService.refreshStatus(payment);
        update4Payment(payment);
    }

    @Override
    public void cancel(long billId, long userId) {
        log.warn("洗衣取消[BEG]" + billId); 
        YunXiyiBill bill = yunXiyiBillRepository.findOne(billId);
        checkOwner(userId, bill);
        if(bill == null || bill.getStatus() != HomeServiceConstant.ORDER_STATUS_CREATE) {
            throw new BizValidateException("该订单无法取消！");
        }

        O2OServiceBuilder.init(bill).cancelByUser("");
        couponService.unlock(bill.getCouponId());
        yunXiyiBillRepository.save(bill);
        log.warn("洗衣取消[END]" + billId); 
    }

    @Override
    public void signed(long billId, long userId) {
        log.warn("洗衣签收[BEG]" + billId); 
        YunXiyiBill bill = yunXiyiBillRepository.findOne(billId);
        checkOwner(userId, bill);
        if(bill == null || bill.getStatus() != HomeServiceConstant.ORDER_STATUS_SERVICED
                || bill.getStatus() != HomeServiceConstant.ORDER_STATUS_BACKED) {
            return;
        }
        bill.signed();
        yunXiyiBillRepository.save(bill);
        log.warn("洗衣取消[END]" + billId); 
    }

    private void checkOwner(long userId, YunXiyiBill bill) {
        if(bill.getUserId() != userId) {
            throw new BizValidateException("无法操作他人订单！");
        }
    }

    @Override
    public List<YunXiyiBill> queryBills(long userId, int page) {
        return yunXiyiBillRepository.findByUserId(userId,new PageRequest(page, 20,new Sort(Direction.DESC, "id"))).getContent();
    }

    /** 
     * @param id
     * @return
     * @see com.yumu.hexie.service.o2o.XiyiService#queryById(long)
     */
    @Override
    public YunXiyiBill queryById(long id) {
        return yunXiyiBillRepository.findOne(id);
    }


    public List<HomeBillItem> findItems(long billId){
        return homeBillItemRepository.findByBillIdAndBillType(billId, HomeServiceConstant.SERVICE_TYPE_XIYI);
    }


    private static final long XIYI_TIMEOUT = 3600000l;
    /** 
     * @param billId
     * @see com.yumu.hexie.service.o2o.XiyiService#timeout(long)
     */
    @Override
    public void timeout(long billId) {
        YunXiyiBill bill = yunXiyiBillRepository.findOne(billId);

        log.warn("洗衣超时[BEG]" + billId);
        PaymentOrder payment = paymentService.queryPaymentOrder(PaymentConstant.TYPE_XIYI_ORDER,billId);
        if(payment != null) {
            payment = paymentService.refreshStatus(payment);
            update4Payment(payment);
        }
        if((payment == null || payment.getStatus() == PaymentConstant.PAYMENT_STATUS_INIT) 
                && bill.getCreateDate() + XIYI_TIMEOUT > System.currentTimeMillis()) {

            log.warn("洗衣超时[BEG]" + billId); 
            paymentService.cancelPayment(PaymentConstant.TYPE_XIYI_ORDER,billId);
            couponService.unlock(bill.getCouponId());
            O2OServiceBuilder.init(bill).cancelBySystem("");
            yunXiyiBillRepository.save(bill);
            log.warn("洗衣超时[END]" + billId); 
        }
    }

}
