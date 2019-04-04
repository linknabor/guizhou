package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import com.yumu.hexie.model.market.Collocation;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.CollocationService;
import com.yumu.hexie.service.sales.SalePlanService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.CouponService;

public abstract class BaseOrderProcessor {
	@Inject
	protected CollocationService collocationService;
	@Inject
	protected AddressService addressService;
	@Inject 
	protected CouponService couponService;
	@Inject 
    protected SalePlanService salePlanService;
	

    protected SalePlan findSalePlan(int type, long ruleId){
        return salePlanService.getService(type).findSalePlan(ruleId);
    }

	// 计算总价 运费 折扣 支付价格 最后支付时间 （不包含）
	protected void computePrice(ServiceOrder order) {
		Float totalAmount = 0f;
		Float discount = 0f;
		Float shipfee = 0f;
		Float price = 0f;
		int count = 0;
		long closeTime = System.currentTimeMillis() + 900000;// 默认15分
		// 设定价格运费等
		if (order.getCollocationId() > 0) {
			// 1.优惠组合
			// 算总价

			for (OrderItem item : order.getItems()) {
				totalAmount += item.getAmount();
				count += item.getCount();
			}
			price += totalAmount;

			Collocation c = collocationService.findOne(order
					.getCollocationId());
			int discountTimes = (int)Math.floor(price/c.getSatisfyAmount());
			// 算优惠
			discount = c.getDiscountAmount()*discountTimes;
			
			price -= discount;
			// 算邮费
			if (c.getFreeShipAmount() < 0.01 ||
					c.getFreeShipAmount()-price>=0.01) {//浮点精度存在问题
				shipfee = c.getShipAmount();
			}
			price += shipfee;

			closeTime = c.getTimeoutForPay() + System.currentTimeMillis();
		} else if (order.getItems().size() == 1) {
			// 2.单个商品
			OrderItem item = order.getItems().get(0);
			SalePlan plan = findSalePlan(order.getOrderType(), item.getRuleId());

			totalAmount = item.getAmount();
			price = totalAmount;
			if (item.getCount() < plan.getFreeShippingNum()) {
				shipfee = plan.getPostageFee();
			}
			count += item.getCount();
			price += shipfee;
			closeTime = plan.getTimeoutForPay() + System.currentTimeMillis();
		} else {
			throw new BizValidateException("下单失败，请重试！");
		}
		order.setTotalAmount(totalAmount);
		order.setShipFee(shipfee);
		order.setDiscountAmount(discount);
		order.setPrice(price);
		order.setCloseTime(closeTime);
		order.setCount(count);
	}

	protected void computeCoupon(ServiceOrder order) {
		if(order.getCouponId() == null || order.getCouponId() ==0) {
			return;
		}
		Coupon coupon = couponService.findOne(order.getCouponId());
		if(coupon == null) {
		    return;
		}
		if(!couponService.isAvaible(order, coupon, false)){
			throw new BizValidateException("该现金券已被其它订单锁定或不可使用，请选择其它可用现金券"+coupon.getId());
		}
		order.configCoupon(coupon);
		couponService.lock(order, coupon);
	}
	
	protected Address fillAddressInfo(ServiceOrder o) {
		Address address = addressService.queryAddressById(o.getServiceAddressId());
		if (address == null) {
			throw new BizValidateException("请选择可用地址！");
		}
		o.fillAddressInfo(address);
		return address;
	}
}
