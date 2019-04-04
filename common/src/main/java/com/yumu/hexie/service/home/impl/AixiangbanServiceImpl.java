package com.yumu.hexie.service.home.impl;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.daojia.home.AixiangbanReq;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.model.tohome.AixiangbanOrder;
import com.yumu.hexie.model.tohome.AixiangbanOrderRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.AixiangbanService;

/** 
 * <p>项目：东湖e家园</p>
 * <p>模块：到家服务</p>
 * <p>描述：</p>
 * <p>版    权: Copyright (c) 2016</p>
 * <p>公    司: 上海奈博信息科技有限公司</p>
 * @author hwb_work 
 * @version 1.0 
 * 创建时间：2016年4月15日 下午2:06:58
 */
@Service("aixiangbanService")
public class AixiangbanServiceImpl implements AixiangbanService {

	@Inject
	private YuyueRuleRepository yuyueRuleRepository;
	@Inject 
	private AixiangbanOrderRepository aixiangbanOrderRepository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;

    @Inject 
    private AddressRepository addressRepository;

	@Override
	public YuyueOrder addOrder(User user, AixiangbanReq req, long addressId) {
		Address address = addressRepository.findOne(addressId);
		List<Object> ruleList = yuyueRuleRepository.queryRuleAndProductInfoByRuleId(req.getRuleId());
		Object[] ruleInfo = (Object[])ruleList.get(0);//TODO 这里需要搞清楚Object的结构
		YuyueOrder yOrder = new YuyueOrder();
		//创建预约单
		yOrder.setTel(address.getTel());
		yOrder.setProductType(ModelConstant.YUYUE_PRODUCT_TYPE_AIXIANGBAN);
		yOrder.setMerchantId(((BigInteger)ruleInfo[2]).longValue());
		yOrder.setReceiverName(address.getReceiveName());
		yOrder.setAddress(address.getRegionStr()+address.getDetailAddress());
		yOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_INIT);
		yOrder.setPaymentType(req.getPaymentType());
		yOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_INIT);
		yOrder.setProductName((String)ruleInfo[3]);//页面和后台都取规则的名称（商品的名称和规则的名称一般情况下是相同的）
		yOrder.setPrice(ruleInfo[4]+"");
		yOrder.setUserId(user.getId());
		yOrder.setServiceNo(1);//单次服务
		yOrder.setServiceUsedNo(1);
		yOrder = yuyueOrderRepository.save(yOrder);

		//创建服务单
		AixiangbanOrder order = new AixiangbanOrder();
		order.setyOrderId(yOrder.getId());
		order.setServiceTypeName((String)ruleInfo[3]);
		order.setUserId(user.getId());
		order.setStrMobile(address.getTel());
		order.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
		order.setServiceStatus(ModelConstant.YUYUE_SERVICE_STATUS_UNUSED);
		order.setPrices(ruleInfo[4]+"");
		order.setStrName(address.getReceiveName());
		order.setStrWorkAddr(address.getRegionStr()+address.getDetailAddress());
		order.setCustomerMemo(req.getCustomerMemo());
		order = aixiangbanOrderRepository.save(order);			

		return yOrder;
	}

}
