package com.yumu.hexie.service.home.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.daojia.haojiaan.HaoJiaAnReq;
import com.yumu.hexie.integration.wechat.service.TemplateMsgService;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.ServiceRegionRepository;
import com.yumu.hexie.model.distribution.region.Merchant;
import com.yumu.hexie.model.distribution.region.MerchantRepository;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.ServiceOperatorRepository;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HaoJiaAnOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HaoJiaAnOrderRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.home.HaoJiaAnService;
import com.yumu.hexie.service.user.UserNoticeService;
import com.yumu.hexie.vo.YuyueQueryOrder;
@Service("haoJiaAnService")
public class HaoJiaAnServiceImpl implements HaoJiaAnService{
	
	private static final Logger log = LoggerFactory.getLogger(HaoJiaAnServiceImpl.class);
	@Inject 
	private AddressRepository addressRepository;
	@Inject
	private HaoJiaAnOrderRepository haoJiaAnOrderRespository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject
	private UserNoticeService userNoticeService;
	@Inject
	private MerchantRepository merchantRepository;
	@Inject
	private SystemConfigService systemConfigService;
	@Inject
    private ServiceRegionRepository serviceRegionRepository;
	@Inject
    private ServiceOperatorRepository serviceOperatorRepository;

	@Override
	@Transactional
	public Long addNoNeedPayOrder(User user, HaoJiaAnReq haoJiaAnReq,
			long addressId) {
		Address address = addressRepository.findOne(addressId);
		haoJiaAnReq.setStrMobile(address.getTel());
		haoJiaAnReq.setStrName(address.getReceiveName());
		haoJiaAnReq.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
		
		Merchant merchant = merchantRepository.findMerchantByProductType(ModelConstant.YUYUE_PRODUCT_TYPE_HAOJIAAN);
		
		//新增YuyueOrder
		YuyueOrder yOrder = new YuyueOrder();
		yOrder.setAddressId(addressId);
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_SUCCESS);
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_HAOJIAAN);
		yOrder.setMerchantId(merchant.getId());
		yOrder.setProductName(haoJiaAnReq.getServiceTypeName());
		yOrder.setPrice(haoJiaAnReq.getPrices());
		yOrder.setPaymentType(haoJiaAnReq.getPaymentType());
		yOrder.setAddress(haoJiaAnReq.getStrWorkAddr());
		yOrder.setTel(haoJiaAnReq.getStrMobile());
		yOrder.setReceiverName(haoJiaAnReq.getStrName());
		yOrder.setWorkTime(haoJiaAnReq.getExpectedTime());
		yOrder.setUserId(user.getId());
		yOrder.setMemo(haoJiaAnReq.getCustomerMemo());
		yOrder = yuyueOrderRepository.save(yOrder);
		
		//新增HaoJiaAnOrder
		HaoJiaAnOrder hOrder = new HaoJiaAnOrder();
		hOrder.setyOrderId(yOrder.getId());
		hOrder.setServiceTypeName(haoJiaAnReq.getServiceTypeName());
		hOrder.setUserId(user.getId());
		hOrder.setPaymentType(haoJiaAnReq.getPaymentType());
		hOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		hOrder.setPrices(haoJiaAnReq.getPrices());
		hOrder.setExpectedTime(haoJiaAnReq.getExpectedTime());
		hOrder.setStrMobile(haoJiaAnReq.getStrMobile());
		hOrder.setStrName(haoJiaAnReq.getStrName());
		hOrder.setStrWorkAddr(haoJiaAnReq.getStrWorkAddr());
		hOrder.setMemo(haoJiaAnReq.getCustomerMemo());
		hOrder.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
		hOrder = haoJiaAnOrderRespository.save(hOrder);
		
		userNoticeService.yuyueSuccess(user.getId(), yOrder.getTel(), yOrder.getReceiverName(), yOrder.getId(), yOrder.getProductName(), ModelConstant.YUYUE_PAYMENT_TYPE_OFFLINE, 0);
		String accessToken = systemConfigService.queryWXAToken();

        List<ServiceOperator> ops = null;
        List<Long> regionIds = new ArrayList<Long>();
        regionIds.add(1l);
        regionIds.add(address.getProvinceId());
        regionIds.add(address.getCityId());
        regionIds.add(address.getCountyId());
        regionIds.add(address.getXiaoquId());
        //查找对应服务类型和服务区的操作员
        List<Long> operatorIds = serviceRegionRepository.findByOrderTypeAndRegionIds(HomeServiceConstant.SERVICE_TYPE_BAOJIE,regionIds);
        log.error("预约订单对应操作员数量" + operatorIds.size());
        if(operatorIds != null && operatorIds.size() > 0) {
        	//查找操作员的基础信息
            ops = serviceOperatorRepository.findOperators(operatorIds);
            for (ServiceOperator op : ops) {
            	//循环发送短信模板
            	 log.error("发送短信给" + op.getName()+",userId为"+op.getUserId());
            	TemplateMsgService.sendHaoJiaAnAssignMsg(hOrder, user, accessToken,op.getOpenId());//发送模板消息给操作员
			}
        }
//        TemplateMsgService.sendHaoJiaAnAssignMsg(hOrder, user, accessToken,user.getOpenid());//发送模板消息给用户自己
		return yOrder.getId();
	}

	@Override
	public YuyueQueryOrder queryYuYueOrder(User user, long orderId) {
		
		YuyueOrder yuyueOrder = yuyueOrderRepository.findOne(orderId);
		if (yuyueOrder==null) {
			return null;
		}
		
		if (user != null) {
			log.error("userId : " + user.getId());
			log.error("orderUserid:" + yuyueOrder.getUserId());
		}
		
		if (yuyueOrder.getUserId()!= user.getId()) {
			throw new BizValidateException("当前用户没有查看订单权限。");
		}
		
		YuyueQueryOrder yuyueQueryOrder = new YuyueQueryOrder();
		yuyueQueryOrder.setOrderId(yuyueOrder.getId());
		yuyueQueryOrder.setAddress(yuyueOrder.getAddress());
		yuyueQueryOrder.setMemo(yuyueOrder.getMemo());
		yuyueQueryOrder.setReceiverName(yuyueOrder.getReceiverName());
		yuyueQueryOrder.setServiceTypeName(yuyueOrder.getProductName());
		yuyueQueryOrder.setTel(yuyueOrder.getTel());
		yuyueQueryOrder.setWorkTime(yuyueOrder.getWorkTime());
		return yuyueQueryOrder;
	}

	//订单访问权限
	@Override
	public List<Long> orderAccessAuthority(long orderId) {
		log.error("进来了");
		YuyueOrder yorder = yuyueOrderRepository.findOne(orderId);
		Address address = addressRepository.findOne(yorder.getAddressId());
		List<Long> regionIds = new ArrayList<Long>();
        regionIds.add(1l);
        regionIds.add(address.getProvinceId());
        regionIds.add(address.getCityId());
        regionIds.add(address.getCountyId());
        regionIds.add(address.getXiaoquId());
        List<ServiceOperator> ops = null;
        List<Long> userIds = new ArrayList<Long>(); //拥有当前订单查看权限的用户
        userIds.add(yorder.getUserId());//创建订单的用户
        //查找对应服务类型和服务区的操作员
        List<Long> operatorIds = serviceRegionRepository.findByOrderTypeAndRegionIds(HomeServiceConstant.SERVICE_TYPE_BAOJIE,regionIds);
        log.error("订单访问权限对应操作员数量" + operatorIds.size());
        if(operatorIds != null && operatorIds.size() > 0) {
        	//查找操作员的基础信息
            ops = serviceOperatorRepository.findOperators(operatorIds);
            for (ServiceOperator op : ops) {
            	userIds.add(op.getUserId());
			}
        }
		return userIds;
	}
	
}
