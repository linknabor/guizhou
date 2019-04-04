package com.yumu.hexie.service.home.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.integration.daojia.flowerplus.FlowerPlusReq;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.commonsupport.info.ProductRepository;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.distribution.region.MerchantRepository;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrderRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.home.FlowerPlusService;
import com.yumu.hexie.service.user.AddressService;

@Service("flowerPlusService")
public class FlowerPlusServiceImpl implements FlowerPlusService {
	private static final Logger log = LoggerFactory.getLogger(FlowerPlusServiceImpl.class);
	
	@Inject
	private YuyueRuleRepository yuyueRuleRepository;
	@Inject 
	private FlowerPlusOrderRepository flowerPlusOrderRepository;
	@Inject 
	private AddressService addressService;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject
	private MerchantRepository merchantRepository;
	@Inject
	private ServiceOrderRepository serviceOrderRepository;
	@Inject
	private ProductRepository productRepository;

	public String datefomat = "yyyy-MM-dd";
	

	@Override
	public YuyueOrder addFlowerPlusOrder(User user, FlowerPlusReq flowerPlusReq, long addressId) {
		Address address = addressService.queryAddressById(addressId);
		YuyueRule rule = yuyueRuleRepository.findOne(flowerPlusReq.getRuleId());
		YuyueOrder yOrder = new YuyueOrder();
		
		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_FLOWERPLUS);

		//创建预约单
		yOrder.setTel(address.getTel());
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_FLOWERPLUS);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setReceiverName(address.getReceiveName());
		yOrder.setAddress(address.getRegionStr()+address.getDetailAddress());
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_INIT);
		yOrder.setPaymentType(flowerPlusReq.getPaymentType());
		yOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		yOrder.setProductName(flowerPlusReq.getServiceTypeName());
		yOrder.setPrice(flowerPlusReq.getPrices());
		yOrder.setWorkTime(flowerPlusReq.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder.setServiceNo(rule.getServiceNo());
		yOrder.setServiceUsedNo(0);
		yOrder = yuyueOrderRepository.save(yOrder);

		if(flowerPlusReq.getServiceIsSingle() == ModelConstant.YUYUE_SERVICE_SINGLE){
			FlowerPlusOrder fOrder = new FlowerPlusOrder();
			fOrder.setyOrderId(yOrder.getId());
			fOrder.setServiceTypeName(flowerPlusReq.getServiceTypeName());
			fOrder.setUserId(user.getId());
			fOrder.setStrMobile(address.getTel());
			fOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
			fOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
			fOrder.setPrices(flowerPlusReq.getPrices());
			fOrder.setExpectedTime(flowerPlusReq.getExpectedTime());
			fOrder.setStrName(address.getReceiveName());
			fOrder.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
			fOrder.setCustomerMemo(flowerPlusReq.getCustomerMemo());
			fOrder.setServiceIsSingle(flowerPlusReq.getServiceIsSingle());
			fOrder.setServiceNo(rule.getServiceNo());
			fOrder.setServiceCount(1);
			fOrder = flowerPlusOrderRepository.save(fOrder);			
		}else if(flowerPlusReq.getServiceIsSingle() == ModelConstant.YUYUE_SERVICE_CYCLE){
			//修改时间格式
			SimpleDateFormat sf = new SimpleDateFormat(DateUtil.dSimple);
			Date date = DateUtil.getSqlDateFromString(flowerPlusReq.getExpectedTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			for(int i=1; i<= rule.getServiceNo(); i++){
				//创建尚匠订单
				FlowerPlusOrder fOrder = new FlowerPlusOrder();
				fOrder.setyOrderId(yOrder.getId());
				fOrder.setServiceTypeName(flowerPlusReq.getServiceTypeName());
				fOrder.setUserId(user.getId());
				fOrder.setStrMobile(address.getTel());
				fOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
				fOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
				fOrder.setPrices(flowerPlusReq.getPrices());
				fOrder.setExpectedTime(sf.format(calendar.getTime()));
				fOrder.setStrName(address.getReceiveName());
				fOrder.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
				fOrder.setCustomerMemo(flowerPlusReq.getCustomerMemo());
				fOrder.setServiceIsSingle(flowerPlusReq.getServiceIsSingle());
				fOrder.setCycleTime(flowerPlusReq.getCycleTime());
				fOrder.setServiceNo(rule.getServiceNo());
				fOrder.setServiceCount(i);
				fOrder = flowerPlusOrderRepository.save(fOrder);
				//先换包月周期为一周
				calendar.add(Calendar.DAY_OF_MONTH, 7);
			}
		}else{
			yuyueOrderRepository.delete(yOrder.getId());
			log.error("validateFlowerPlusServiceType:"+flowerPlusReq.getServiceIsSingle());
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_DAOJIA,rule.getId(),"鲜花包月类型错误").setError();
		}


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

	@Override
	public boolean checkCountByProduct(long ruleId, int count) {
		YuyueRule yRule = yuyueRuleRepository.findOne(ruleId);
		Product product = productRepository.findOne(yRule.getProductId());
		
		log.error("checkCountByProduct yRuleId=" + ruleId + "productId=" + product.getId());

		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.ORDER_STATUS_PAYED);
		status.add(ModelConstant.ORDER_STATUS_SENDED);
		status.add(ModelConstant.ORDER_STATUS_RECEIVED);
		status.add(ModelConstant.ORDER_STATUS_CONFIRM);
		status.add(ModelConstant.ORDER_STATUS_REFUNDED);

		List<ServiceOrder> lists= serviceOrderRepository.CheckCountByStatusAndProductIdAndOrderType(status, product.getId(), ModelConstant.ORDER_TYPE_YUYUE);	
		if(lists.size() >= count){
			return false;
		}else{
			return true;
		}
	}

}
