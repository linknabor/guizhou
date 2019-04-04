/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.localservice.basemodel.CancelAble;
import com.yumu.hexie.model.localservice.basemodel.HasMerchant;
import com.yumu.hexie.model.localservice.basemodel.HasOperator;
import com.yumu.hexie.model.localservice.basemodel.NeedShipFee;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.o2o.req.CommonBillReq;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeServiceBuilder.java, v 0.1 2016年3月24日 下午5:45:19  Exp $
 */
public class O2OServiceBuilder<T extends BaseO2OService>  {
    private static final Logger log = LoggerFactory.getLogger(O2OServiceBuilder.class);

    private T hs;
    public static <T extends BaseO2OService> O2OServiceBuilder<T> init(Class<T> t){
        O2OServiceBuilder<T> hb = new O2OServiceBuilder<T>();
        try {
            hb.hs = t.newInstance();
            hb.hs.setOrderNo(OrderNoUtil.generateO2OOrderNo());
            hb.hs.setStatus(HomeServiceConstant.ORDER_STATUS_CREATE);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return hb;
    }
    public static <T extends BaseO2OService> O2OServiceBuilder<T> init(T hs){
        O2OServiceBuilder<T> hb = new O2OServiceBuilder<T>();
        hb.hs = hs;
        return hb;
    }
    public T getBill() {
        return hs;
    }
    
    public O2OServiceBuilder<T> initCart(HomeCart cart) {
        hs.setItemType(cart.getItemType());
        return items(cart.getItems());
    }
    public O2OServiceBuilder<T> items(List<HomeBillItem> items) {
        if(items.size() == 0) {
            return this;
        }
        HomeBillItem i = items.get(0);
        hs.setItemId(i.getServiceId());
        hs.setItemType(i.getParentType());
        hs.setBillLogo(i.getLogo());
        if(items.size() == 1) {
            hs.setProjectName(i.getTitle());
        } else {
            hs.setProjectName(i.getTitle()+"等服务");
        }
        hs.setItemCount(i.getCount());
        hs.setItems(items);

        return this;
    }

    public O2OServiceBuilder<T> req(CommonBillReq req) {
        hs.setAddressId(req.getAddressId());
        hs.setCouponId(req.getCouponId());
        hs.setMemo(req.getMemo());
        
        hs.setRequireDate(DateUtil.parse(req.getReqTime(),"yyyy-MM-dd HH:mm"));
        return this;
    }

    public O2OServiceBuilder<T> userId(long userId) {
        hs.setUserId(userId);
        return this;
    }
    //如果logo不是取item项的图标，则自己设置logo
    public O2OServiceBuilder<T> logo(String logo) {
        hs.setBillLogo(logo);
        return this;
    }
    public O2OServiceBuilder<T> address(Address addr) {
        hs.setAddressId(addr.getId());
        hs.setAddress(addr.getRegionStr()+addr.getDetailAddress());
        hs.setTel(addr.getTel());
        hs.setReceiverName(addr.getReceiveName());
        hs.setXiaoquId(addr.getXiaoquId());
        hs.setXiaoquName(addr.getXiaoquName());
        return this;
    }
    public O2OServiceBuilder<T> operator(ServiceOperator op) {
        if(hs instanceof HasOperator) {
            HasOperator bos = (HasOperator)hs;
            bos.setOperatorCompanyName(op.getCompanyName());
            bos.setOperatorId(op.getId());
            bos.setOperatorName(op.getName());
            bos.setOperatorTel(op.getTel());
        }
        return this;
    }
    public O2OServiceBuilder<T> merchant(Merchant m) {
        if(hs instanceof HasMerchant) {
            HasMerchant bos = (HasMerchant)hs;
            bos.setMerchantId(m.getId());
            bos.setMerchantName(m.getName());
            bos.setMerchantTel(m.getTel());
        }
        return this;
    }

    public O2OServiceBuilder<T> cancelByUser(String reason) {
        if(hs instanceof CancelAble) {
            CancelAble ca = (CancelAble)hs;
            hs.setStatus(HomeServiceConstant.ORDER_STATUS_CANCELED_USER);
            ca.setCancelTime(new Date());
            ca.setCancelReason(reason);
        }
        return this;
    }

    public O2OServiceBuilder<T> cancelBySystem(String reason) {
        if(hs instanceof CancelAble) {
            CancelAble ca = (CancelAble)hs;
            hs.setStatus(HomeServiceConstant.ORDER_STATUS_CANCELED_TIMEOUT);
            ca.setCancelTime(new Date());
            ca.setCancelReason(reason);
        }
        return this;
    }

    public O2OServiceBuilder<T> calculateNoCoupon() {
        BigDecimal amount = new BigDecimal(0);
        for(HomeBillItem item  : hs.getItems()) {
            amount = amount.add(item.getPrice().multiply(BigDecimal.valueOf(item.getCount())));
            log.error("Add amount [" + item.getPrice() + "] * [" + item.getCount() + "]");
        }
        log.error("calculateNoCoupon [" + amount + "]");
        hs.setAmount(amount);
        hs.setRealAmount(amount);
        return this;
    }

    public O2OServiceBuilder<T> coupon(Coupon coupon) {
        if(coupon != null) {
            hs.setCoupon(coupon);
            hs.setCouponId(coupon.getId());
            hs.setDiscountAmount(BigDecimal.valueOf(coupon.getAmount()));
            hs.setRealAmount(hs.getRealAmount().subtract(BigDecimal.valueOf(coupon.getAmount())));
            hs.setRealAmount(hs.getRealAmount().max(BigDecimal.valueOf(0.01)));
        }
        return this;
    }
    
    public static void main(String[] args) {
        System.out.println(new BigDecimal(1).compareTo(new BigDecimal(2)));
        System.out.println(new BigDecimal(1).compareTo(new BigDecimal(1)));
        System.out.println(new BigDecimal(2).compareTo(new BigDecimal(1)));
    }

    public O2OServiceBuilder<T> shipFee(ShipFeeTemplate shipFee,Address addr) {
        if(shipFee != null && hs instanceof NeedShipFee) {
            NeedShipFee nsf = ((NeedShipFee)hs);
            nsf.setShipFeeTplId(shipFee.getId());
            BigDecimal ra = hs.getRealAmount();
            if(ra.compareTo(shipFee.getFreeFeeLimit())>=0){
                nsf.setShipFee(BigDecimal.ZERO);
            } else {
                List<Long> freeRegions = shipFee.extractFreeRegions();
                if(freeRegions.contains(addr.getProvinceId())
                        || freeRegions.contains(addr.getCityId())
                        || freeRegions.contains(addr.getCountyId())
                        || freeRegions.contains(addr.getXiaoquId())) {
                    nsf.setShipFee(BigDecimal.ZERO);
                } else {
                    nsf.setShipFee(shipFee.getShipFee());
                    hs.setRealAmount(hs.getRealAmount().add(shipFee.getShipFee()));
                }
            }

            if(ra.compareTo(shipFee.getFreeSettleLimit()) >= 0) {
                nsf.setShipSettleFee(BigDecimal.ZERO);
            } else {
                nsf.setShipSettleFee(shipFee.getSettleAmount());
            }
            hs.setRealAmount(hs.getRealAmount().max(BigDecimal.valueOf(0.01)));
        }
        return this;
    }
    
    public O2OServiceBuilder<T> status(int status) {
        hs.setStatus(status);
        return this;
    }
}
