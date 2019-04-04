package com.yumu.hexie.service.home.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.daojia.gaofei.GaofeiReq;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.commonsupport.info.ProductRepository;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.distribution.region.MerchantRepository;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrderRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.GaofeiService;

@Service("gaofeiService")
public class GaofeiServiceImpl implements GaofeiService {
	private static final Logger log = LoggerFactory.getLogger(GaofeiServiceImpl.class);

	@Inject 
	private GaofeiOrderRepository gaofeiOrderRepository;
	@Inject 
	private AddressRepository addressRepository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject
	private MerchantRepository merchantRepository;
	@Inject
	private YuyueRuleRepository yuyueRuleRepository;
	@Inject
	private ServiceOrderRepository serviceOrderRepository;
	@Inject
	private ProductRepository productRepository;

	@Override
	public YuyueOrder addGaofeiExperienceOrder(User user, GaofeiReq gaofeiReq) {
		YuyueOrder yOrder = new YuyueOrder();

		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI);

		//创建预约单
		yOrder.setTel(gaofeiReq.getStrMobile());
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setReceiverName(gaofeiReq.getStrName());
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_INIT);
		yOrder.setPaymentType(gaofeiReq.getPaymentType());
		yOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		yOrder.setProductName(gaofeiReq.getServiceTypeName());
		yOrder.setPrice(gaofeiReq.getPrices());
		yOrder.setWorkTime(gaofeiReq.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder = yuyueOrderRepository.save(yOrder);

		//创建高飞体验订单
		GaofeiOrder gOrder = new GaofeiOrder();
		gOrder.setyOrderId(yOrder.getId());
		gOrder.setServiceTypeName(gaofeiReq.getServiceTypeName());
		gOrder.setUserId(user.getId());
		gOrder.setStrMobile(gaofeiReq.getStrMobile());
		gOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
		gOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
		gOrder.setExpectedTime(gaofeiReq.getExpectedTime());
		gOrder.setPrices(gaofeiReq.getPrices());
		gOrder.setStrName(gaofeiReq.getStrName());
		gOrder = gaofeiOrderRepository.save(gOrder);

		return yOrder;
	}

	@Override
	public YuyueOrder addGaofeiYuyueOrder(User user, GaofeiReq gaofeiReq, long addressId) {
		Address address = addressRepository.findOne(addressId);
		YuyueOrder yOrder = new YuyueOrder();

		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI);

		//创建预约单
		yOrder.setTel(address.getTel());
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setReceiverName(address.getReceiveName());
		yOrder.setAddress(address.getRegionStr()+address.getDetailAddress());
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_INIT);
		yOrder.setPaymentType(gaofeiReq.getPaymentType());
		yOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		yOrder.setProductName(gaofeiReq.getServiceTypeName());
		yOrder.setPrice(gaofeiReq.getPrices());
		yOrder.setWorkTime(gaofeiReq.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder = yuyueOrderRepository.save(yOrder);

		//创建高飞预约订单
		GaofeiOrder gOrder = new GaofeiOrder();
		gOrder.setyOrderId(yOrder.getId());
		gOrder.setServiceTypeName(gaofeiReq.getServiceTypeName());
		gOrder.setUserId(user.getId());
		gOrder.setStrMobile(address.getTel());
		gOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
		gOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
		gOrder.setExpectedTime(gaofeiReq.getExpectedTime());
		gOrder.setPrices(gaofeiReq.getPrices());
		gOrder.setStrName(address.getReceiveName());
		gOrder.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
		gOrder = gaofeiOrderRepository.save(gOrder);

		return yOrder;
	}

	@Override
	public boolean checkIsExistenceByProduct(User user, long ruleId) {
		YuyueRule yRule = yuyueRuleRepository.findOne(ruleId);
		Product product = productRepository.findOne(yRule.getProductId());
		
		log.error("checkIsExperience userId" + user.getId() + ", yRuleId=" + ruleId + "productId=" + product.getId());

		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.ORDER_STATUS_PAYED);
		status.add(ModelConstant.ORDER_STATUS_SENDED);
		status.add(ModelConstant.ORDER_STATUS_RECEIVED);
		status.add(ModelConstant.ORDER_STATUS_CONFIRM);
		status.add(ModelConstant.ORDER_STATUS_REFUNDED);
		
		List<ServiceOrder> lists= serviceOrderRepository.findByUserAndStatusAndProductIdAndOrderType(user.getId(), status, product.getId(), ModelConstant.ORDER_TYPE_YUYUE);
		
		if(lists.size() == 0){
			return false;
		}else{
			return true;
		}
	}
}
