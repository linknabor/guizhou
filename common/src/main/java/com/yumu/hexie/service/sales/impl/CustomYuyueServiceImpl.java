package com.yumu.hexie.service.sales.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.FlowerPlusOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.GaofeiOrderRepository;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrder;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.JiuyeOrderRepository;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.market.saleplan.YuyueRuleRepository;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.tohome.AixiangbanOrder;
import com.yumu.hexie.model.tohome.AixiangbanOrderRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.user.UserNoticeService;

@Service("customYuyueService")
public class CustomYuyueServiceImpl extends CustomOrderServiceImpl {

	@Inject
	private YuyueRuleRepository yuyueRuleRepository;
	@Inject
	private YuyueOrderRepository yuyueOrderRepository;
	@Inject 
	private FlowerPlusOrderRepository flowerPlusOrderRepository;
	@Inject 
	private GaofeiOrderRepository gaofeiOrderRepository;
	@Inject 
	private JiuyeOrderRepository jiuyeOrderRepository;
	@Inject
	private AixiangbanOrderRepository aixiangbanOrderRepository;
	@Inject
	private DistributionService distributionService;
    @Inject
    private UserNoticeService userNoticeService;
	public void validateRule(SalePlan rule, ServiceOrder order,
			Address address) {
		
	}
	public void validateRule(ServiceOrder order,SalePlan plan, OrderItem item, Address address) {
	    if(!plan.valid(item.getCount())){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_DAOJIA,plan.getId(),"商品信息已过期，请重新下单！").setError();
        }
		distributionService.validYuyuePlan((YuyueRule)plan, address);
	}
	
	@Override
	public void postOrderConfirm(ServiceOrder order) {
		
	}

	@Override
	public void postPaySuccess(PaymentOrder po, ServiceOrder so) {
		//支付成功订单为配货中状态，改商品库存
		so.confirm();
		serviceOrderRepository.save(so);
		for(OrderItem item : so.getItems()){
			productService.saledCount(item.getProductId(), item.getCount());
		}

		YuyueOrder yuyueOrder = yuyueOrderRepository.findByServiceOrderId(so.getId());
		yuyueOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_PAYED);
		yuyueOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_SUCCESS);
		yuyueOrder.setMemo(so.getMemo());
		yuyueOrderRepository.save(yuyueOrder);
		
		if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_FLOWERPLUS){
			List<FlowerPlusOrder> flowerPlusOrders = flowerPlusOrderRepository.findByYOrderId(yuyueOrder.getId());
			for(FlowerPlusOrder flowerPlusOrder:flowerPlusOrders){
				flowerPlusOrder.setsOrderId(so.getId());
				flowerPlusOrder.setCustomerMemo(so.getMemo());
				flowerPlusOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_PAYED);
				flowerPlusOrderRepository.save(flowerPlusOrder);
			}
		}else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI){
			GaofeiOrder gaofeiOrder = gaofeiOrderRepository.findByYOrderId(yuyueOrder.getId());
			gaofeiOrder.setsOrderId(so.getId());
			gaofeiOrder.setMemo(so.getMemo());
			gaofeiOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_PAYED);
			gaofeiOrderRepository.save(gaofeiOrder);
		}else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_JIUYE){
			/*九曳*/
			List<JiuyeOrder> orders = jiuyeOrderRepository.findByYOrderId(yuyueOrder.getId());
			for(JiuyeOrder order:orders){
				order.setsOrderId(so.getId());
				order.setCustomerMemo(so.getMemo());
				order.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_PAYED);
				jiuyeOrderRepository.save(order);
			}
		}else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_AIXIANGBAN){
		    AixiangbanOrder order = aixiangbanOrderRepository.findByYOrderId(yuyueOrder.getId());
		    order.setsOrderId(so.getId());
		    order.setCustomerMemo(so.getMemo());
		    order.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_PAYED);
		    aixiangbanOrderRepository.save(order);
		}
		userNoticeService.yuyueSuccess(yuyueOrder.getUserId(), yuyueOrder.getTel(), yuyueOrder.getReceiverName(), yuyueOrder.getId(), yuyueOrder.getProductName(), ModelConstant.YUYUE_PAYMENT_TYPE_WEIXIN, so.getPrice() );
	}

	@Override
	public void postOrderCancel(ServiceOrder order) {
		super.postOrderCancel(order);
		
		YuyueOrder yuyueOrder = yuyueOrderRepository.findByServiceOrderId(order.getId());
		yuyueOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
		yuyueOrder.setStatus(ModelConstant.ORDER_STAUS_YUYUE_CANCEL);
		yuyueOrderRepository.save(yuyueOrder);
		
		if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_FLOWERPLUS){
			List<FlowerPlusOrder> flowerPlusOrders = flowerPlusOrderRepository.findByYOrderId(yuyueOrder.getId());
			for(FlowerPlusOrder flowerPlusOrder:flowerPlusOrders){
				flowerPlusOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
				flowerPlusOrderRepository.save(flowerPlusOrder);
			}
		}else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_GAOFEI){
			GaofeiOrder gaofeiOrder = gaofeiOrderRepository.findByYOrderId(yuyueOrder.getId());
			gaofeiOrder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
			gaofeiOrderRepository.save(gaofeiOrder);
		}else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_JIUYE){
			List<JiuyeOrder> orders = jiuyeOrderRepository.findByYOrderId(yuyueOrder.getId());
			for(JiuyeOrder thisorder:orders){
				thisorder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
				jiuyeOrderRepository.save(thisorder);
			}
		} else if(yuyueOrder.getProductType() == ModelConstant.YUYUE_PRODUCT_TYPE_AIXIANGBAN){
		    AixiangbanOrder thisorder = aixiangbanOrderRepository.findByYOrderId(yuyueOrder.getId());
		    thisorder.setPayStatus(ModelConstant.YUYUE_PAYSTATUS_UNPAYED);
		    aixiangbanOrderRepository.save(thisorder);
		}
	}

	@Override
	public SalePlan findSalePlan(long ruleId) {
		return yuyueRuleRepository.findOne(ruleId);
	}
}


