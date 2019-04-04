/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.HomeBillItemRepository;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.O2OServiceBuilder;
import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.localservice.bill.BaojieBill;
import com.yumu.hexie.model.localservice.bill.BaojieBillRepository;
import com.yumu.hexie.model.payment.PaymentConstant;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.promotion.PromotionConstant;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.BaojieService;
import com.yumu.hexie.service.o2o.CommonHomeService;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.req.BaojieReq;
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
 * @version $Id: BaojieServiceImpl.java, v 0.1 2016年5月19日 上午11:18:36  Exp $
 */
@Service("baojieService")
public class BaojieServiceImpl implements BaojieService {

    private static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);

    @Inject
    private AddressService addressService;
    @Inject
    private CouponService couponService;
    @Inject
    private HomeItemService homeItemService;
    @Inject
    private BaojieBillRepository baojieBillRepository;
    @Inject
    private CommonHomeService commonHomeService;
    @Inject
    private HomeBillItemRepository homeBillItemRepository;
    @Inject
    private GotongService gotongService;
    @Inject
    private PaymentService paymentService;
    /** 
     * @param req
     * @param user
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#create(com.yumu.hexie.service.o2o.req.BaojieReq, com.yumu.hexie.model.user.User)
     */
    @Override
    public BaojieBill create(BaojieReq req, User user) {
        Address addr = addressService.queryAddressById(req.getAddressId());
        ServiceItem sItem = homeItemService.queryById(req.getServiceItemId());
        //保洁的logo设置为一级父类型的logo
        ServiceType type = homeItemService.findTypeByItem(req.getServiceItemId());
        Merchant m = new Merchant();
        m.setId(type.getMerchantId());
        HomeBillItem item = new HomeBillItem();
        
        item.setBillType(type.getId());
        item.setCount(req.getCount());
        item.setLogo(type.getTypeIcon());
        item.setPrice(sItem.getPrice());
        item.setServiceId(sItem.getId());
        item.setTitle(sItem.getTitle());
        List<HomeBillItem> items = new ArrayList<HomeBillItem>();
        items.add(item);
        
        
        O2OServiceBuilder<BaojieBill> ob = O2OServiceBuilder.init(BaojieBill.class);
        ob.userId(user.getId()).req(req).merchant(m)
            .items(items).address(addr).calculateNoCoupon();
            
        Coupon coupon = req.getCouponId() != null && req.getCouponId() > 0 ? couponService.findOne(req.getCouponId()) : null;
        if(couponService.isAvaible(PromotionConstant.COUPON_ITEM_TYPE_SERVICE,
            new Long(HomeServiceConstant.SERVICE_TYPE_BAOJIE), item.getParentType(),item.getServiceId(), ob.getBill().getAmount().floatValue(), coupon, false)){
            ob.coupon(coupon);
        }
        ob.getBill().setTotalAmount(ob.getBill().getRealAmount());
        ob.getBill().setTypeName(type.getTypeName());
        ob.getBill().setItemType(type.getId());
        BaojieBill bill =  baojieBillRepository.save(ob.getBill());
        for(HomeBillItem i : ob.getBill().getItems()) {
            ServiceType t = homeItemService.findTypeByItem(item.getServiceId());
            i.setBillType(HomeServiceConstant.SERVICE_TYPE_BAOJIE);
            i.setParentType(t.getId());
            i.setBillId(bill.getId());
        }
        homeBillItemRepository.save(ob.getBill().getItems());
        return bill;
    }

    /** 
     * @param billId
     * @param user
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#pay(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public JsSign pay(long billId, User user) {
        BaojieBill bill = baojieBillRepository.findOne(billId);
        log.warn("发起支付[BEG]" + billId); 
        //获取支付单
        PaymentOrder pay = commonHomeService.reqPay(bill, user.getOpenid());
        //发起支付
        bill.setPaymentId(pay.getId());
        bill = baojieBillRepository.save(bill);
        log.warn("发起支付[END]" + bill.getId()); 
        return paymentService.requestPay(pay);
    }

    /** 
     * @param billId
     * @param user
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#get(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public BaojieBill get(long billId, User user) {
        BaojieBill bill = baojieBillRepository.findOne(billId);
        if(bill.getUserId() != user.getId()) {
            throw new BizValidateException("不能查看他人的订单");
        }
        return bill;
    }


    /** 
     * @param billId
     * @param user
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#confirm(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public BaojieBill confirm(long billId, User user) {
        BaojieBill bill = baojieBillRepository.findOne(billId);
        if(bill.getUserId() != user.getId()) {
            throw new BizValidateException("不能操作他人的订单");
        }
        bill.signed();
        return baojieBillRepository.save(bill);
    }

    /** 
     * @param user
     * @param page
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#query(com.yumu.hexie.model.user.User, int)
     */
    @Override
    public List<BaojieBill> query(User user, int page) {
        return baojieBillRepository.findByUserId(user.getId(), new PageRequest(0, 20, new Sort(Direction.DESC,"id")));
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
                BaojieBill bill = baojieBillRepository.findOne(payment.getOrderId());
                if(bill.getStatus()==HomeServiceConstant.ORDER_STATUS_CREATE){
                    paySuccess(bill,payment);
                }
                couponService.comsume(bill);
                break;
            default:
                break;
                
        }
    }
    
    private void paySuccess(BaojieBill bill,PaymentOrder pay) {
        log.warn("支付成功[BEG]" + bill.getId()); 
        bill.setStatus(HomeServiceConstant.ORDER_STATUS_PAYED);
        bill = baojieBillRepository.save(bill);
        //billAssignService.assignXiyiOrder(bill);
        commonHomeService.udpateSettleInfo(bill, bill.getMerchantId(),
            homeBillItemRepository.findByBillIdAndBillType(bill.getId(), HomeServiceConstant.SERVICE_TYPE_BAOJIE),pay);
        notify2Operators(bill);
        log.warn("支付成功[END]" + bill.getId()); 
    }

    private void notify2Operators(BaojieBill bill){
        gotongService.sendCommonYuyueBillMsg(HomeServiceConstant.SERVICE_TYPE_BAOJIE,
                "您有一条新的订单消息",bill.getProjectName(), DateUtil.dtFormat(bill.getRequireDate(),"yyyy-MM-dd HH:mm"), "");    
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
        BaojieBill bill = baojieBillRepository.findOne(billId);
        if(bill == null || bill.getStatus() != HomeServiceConstant.ORDER_STATUS_CREATE) {
            return;
        }
        PaymentOrder payment = paymentService.queryPaymentOrder(PaymentConstant.TYPE_BAOJIE_ORDER,billId);
        payment = paymentService.refreshStatus(payment);
        update4Payment(payment);
    }
    private static final long BAOJIE_TIMEOUT = 3600000l;
    /** 
     * @param billId
     * @see com.yumu.hexie.service.o2o.BaojieService#timeout(long)
     */
    @Override
    public void timeout(long billId) {

        BaojieBill bill = baojieBillRepository.findOne(billId);

        log.warn("保洁超时[BEG]" + billId);
        PaymentOrder payment = paymentService.queryPaymentOrder(PaymentConstant.TYPE_BAOJIE_ORDER,billId);
        if(payment != null) {
            payment = paymentService.refreshStatus(payment);
            update4Payment(payment);
        }
        if((payment == null || payment.getStatus() == PaymentConstant.PAYMENT_STATUS_INIT) 
                && bill.getCreateDate() + BAOJIE_TIMEOUT > System.currentTimeMillis()) {

            log.warn("保洁超时[BEG]" + billId); 
            paymentService.cancelPayment(PaymentConstant.TYPE_BAOJIE_ORDER,billId);
            couponService.unlock(bill.getCouponId());
            O2OServiceBuilder.init(bill).cancelBySystem("");
            baojieBillRepository.save(bill);
            log.warn("保洁超时[END]" + billId); 
        }
    }

    /** 
     * @param billId
     * @param user
     * @return
     * @see com.yumu.hexie.service.o2o.BaojieService#cancel(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public BaojieBill cancel(long billId, User user) {
        log.warn("取消洗衣订单" + billId); 
        BaojieBill bill = baojieBillRepository.findOne(billId);
        if(bill.getUserId() != user.getId()) {
            throw new BizValidateException("不能操作他人的订单");
        }
        O2OServiceBuilder.init(bill).cancelByUser("手动操作");
        return baojieBillRepository.save(bill);
    }

}
