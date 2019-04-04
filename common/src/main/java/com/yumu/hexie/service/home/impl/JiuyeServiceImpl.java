package com.yumu.hexie.service.home.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.integration.daojia.jiuye.JiuyeReq;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.distribution.region.MerchantRepository;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrderRepository;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.home.JiuyeService;

@Service("jiuyeService")
public class JiuyeServiceImpl implements JiuyeService {
	private static final Logger log = LoggerFactory.getLogger(JiuyeServiceImpl.class);
	
	@Inject
	private YuyueRuleRepository yuyueRuleRepository;
	@Inject 
	private JiuyeOrderRepository jiuyeOrderRepository;
	@Inject 
	private AddressRepository addressRepository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject
	private MerchantRepository merchantRepository;
	public String datefomat = "yyyy-MM-dd";
	

	@Override
	public YuyueOrder addOrder(User user, JiuyeReq req, long addressId) {
		Address address = addressRepository.findOne(addressId);
		YuyueRule rule = yuyueRuleRepository.findOne(req.getRuleId());
		YuyueOrder yOrder = new YuyueOrder();
		
		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_JIUYE);

		//创建预约单
		yOrder.setTel(address.getTel());
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_JIUYE);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setReceiverName(address.getReceiveName());
		yOrder.setAddress(address.getRegionStr()+address.getDetailAddress());
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_INIT);
		yOrder.setPaymentType(req.getPaymentType());
		yOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		yOrder.setProductName(req.getServiceTypeName());
		yOrder.setPrice(req.getPrices());
		yOrder.setWorkTime(req.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder.setServiceNo(rule.getServiceNo());
		yOrder.setServiceUsedNo(0);
		yOrder = yuyueOrderRepository.save(yOrder);

		if(req.getServiceIsSingle() == ModelConstant.YUYUE_SERVICE_SINGLE){
			JiuyeOrder order = new JiuyeOrder();
			order.setyOrderId(yOrder.getId());
			order.setServiceTypeName(req.getServiceTypeName());
			order.setUserId(user.getId());
			order.setStrMobile(address.getTel());
			order.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
			order.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
			order.setPrices(req.getPrices());
			order.setExpectedTime(req.getExpectedTime());
			order.setStrName(address.getReceiveName());
			order.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
			order.setCustomerMemo(req.getCustomerMemo());
			order.setServiceIsSingle(req.getServiceIsSingle());
			order.setServiceNo(rule.getServiceNo());
			order.setServiceCount(1);
			order = jiuyeOrderRepository.save(order);			
		}else if(req.getServiceIsSingle() == ModelConstant.YUYUE_SERVICE_CYCLE){
			//修改时间格式
			SimpleDateFormat sf = new SimpleDateFormat(DateUtil.dSimple);
			Date date = DateUtil.getSqlDateFromString(req.getExpectedTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			for(int i=1; i<= rule.getServiceNo(); i++){
				//创建服务订单
				JiuyeOrder order = new JiuyeOrder();
				order.setyOrderId(yOrder.getId());
				order.setServiceTypeName(req.getServiceTypeName());
				order.setUserId(user.getId());
				order.setStrMobile(address.getTel());
				order.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
				order.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
				order.setPrices(req.getPrices());
				order.setExpectedTime(sf.format(calendar.getTime()));
				order.setStrName(address.getReceiveName());
				order.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
				order.setCustomerMemo(req.getCustomerMemo());
				order.setServiceIsSingle(req.getServiceIsSingle());
				order.setCycleTime(req.getCycleTime());
				order.setServiceNo(rule.getServiceNo());
				order.setServiceCount(i);
				order = jiuyeOrderRepository.save(order);
				//先换包月周期为一周
				calendar.add(Calendar.DAY_OF_MONTH, 7);
			}
		}else{
			yuyueOrderRepository.delete(yOrder.getId());
			log.error("validateFlowerPlusServiceType:"+req.getServiceIsSingle());
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_DAOJIA,rule.getId(),"鲜花包月类型错误").setError();
		}


		return yOrder;
	}

}
