package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.OnSaleRule;
import com.yumu.hexie.model.market.saleplan.OnSaleRuleRepository;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.ProductService;

@Service("customOnSaleService")
public class CustomOnSaleServiceImpl extends CustomOrderServiceImpl {
	@Inject
	private OnSaleRuleRepository onSaleRuleRepository;
    @Inject
    private DistributionService distributionService;
    @Inject
    private ProductService         productService;

	@Override
	public void validateRule(ServiceOrder order,SalePlan rule, OrderItem item, Address address) {
		if(!rule.valid(item.getCount())){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_ONSALE,rule.getId(),"商品信息已过期，请重新下单！").setError();
        }
        distributionService.validOnSalePlan((OnSaleRule)rule, address);
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
	}
	

	@Override
	public SalePlan findSalePlan(long ruleId) {
		return onSaleRuleRepository.findOne(ruleId);
	}


    /** 
     * @param order
     * @see com.yumu.hexie.service.sales.CustomOrderService#postOrderCancel(com.yumu.hexie.model.market.ServiceOrder)
     */
    @Override
    public void postOrderCancel(ServiceOrder order) {
    }
	
}
