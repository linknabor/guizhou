package com.yumu.hexie.service.home.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.daojia.weizhuangwang.WeiZhuangWangReq;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.distribution.region.MerchantRepository;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.WeiZhuangWangOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.WeiZhuangWangOrderRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.WeiZhuangWangService;
import com.yumu.hexie.service.user.UserNoticeService;

@Service("weiZhuangWangService")
public class WeiZhuangWangServiceImpl implements WeiZhuangWangService {

	@Inject 
	private AddressRepository addressRepository;
	@Inject
	private WeiZhuangWangOrderRepository weiZhuangWangOrderRepository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject
	private UserNoticeService userNoticeService;
	@Inject
	private MerchantRepository merchantRepository;

	@Override
	public YuyueOrder addNoNeedPayOrder(User user, WeiZhuangWangReq weiZhuangWangReq,
			long addressId) {
		Address address = addressRepository.findOne(addressId);
		weiZhuangWangReq.setStrMobile(address.getTel());
		weiZhuangWangReq.setStrName(address.getReceiveName());
		weiZhuangWangReq.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());

		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_WEIZHUANGWANG);

		//新增YuyueOrder
		YuyueOrder yOrder = new YuyueOrder();
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_SUCCESS);
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_WEIZHUANGWANG);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setProductName(weiZhuangWangReq.getServiceTypeName());
		yOrder.setPrice(weiZhuangWangReq.getPrices());
		yOrder.setPaymentType(weiZhuangWangReq.getPaymentType());
		yOrder.setAddress(weiZhuangWangReq.getStrWorkAddr());
		yOrder.setTel(weiZhuangWangReq.getStrMobile());
		yOrder.setReceiverName(weiZhuangWangReq.getStrName());
		yOrder.setWorkTime(weiZhuangWangReq.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder.setMemo(weiZhuangWangReq.getCustomerMemo());
		yOrder = yuyueOrderRepository.save(yOrder);

		//新增WeiZhuangWangOrder
		WeiZhuangWangOrder wOrder = new WeiZhuangWangOrder();
		wOrder.setyOrderId(yOrder.getId());
		wOrder.setServiceTypeName(weiZhuangWangReq.getServiceTypeName());
		wOrder.setUserId(user.getId());
		wOrder.setPaymentType(weiZhuangWangReq.getPaymentType());
		wOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		wOrder.setPrices(weiZhuangWangReq.getPrices());
		wOrder.setExpectedTime(weiZhuangWangReq.getExpectedTime());
		wOrder.setStrMobile(weiZhuangWangReq.getStrMobile());
		wOrder.setStrName(weiZhuangWangReq.getStrName());
		wOrder.setStrWorkAddr(weiZhuangWangReq.getStrWorkAddr());
		wOrder.setMemo(weiZhuangWangReq.getCustomerMemo());
		wOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
		wOrder = weiZhuangWangOrderRepository.save(wOrder);
		
		userNoticeService.yuyueSuccess(user.getId(), yOrder.getTel(), yOrder.getReceiverName(), yOrder.getId(), yOrder.getProductName(), ModelConstant.YUYUE_PAYMENT_TYPE_OFFLINE, 0);

		return yOrder;
	}
}
