package com.yumu.hexie.service.sales.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.Collocation;
import com.yumu.hexie.model.market.CollocationItem;
import com.yumu.hexie.model.market.CollocationItemRepository;
import com.yumu.hexie.model.market.CollocationRepository;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.service.sales.CollocationService;
import com.yumu.hexie.service.sales.ProductService;
import com.yumu.hexie.service.sales.SalePlanService;
@Service("collocationService")
public class CollocationServiceImpl implements CollocationService {

    @Inject
    private SalePlanService salePlanService;
    @Inject
    private ProductService productService;
    @Inject
    private CollocationRepository collocationRepository;
    @Inject
    private CollocationItemRepository collocationItemRepository;

	public void fillItemInfo4Cart(Cart cart){
		for(OrderItem item : cart.getItems()){
			SalePlan salePlan = salePlanService.getService(item.getOrderType()).findSalePlan(Long.valueOf(item.getRuleId()));
			Product product  = productService.getProduct(salePlan.getProductId());
			item.fillDetail(salePlan, product);
		}
	}
	
	@Override
	public Collocation findLatestCollocation(int salePlanType, long ruleId) {
		List<CollocationItem> items = collocationItemRepository.findByPlanTypeAndId(salePlanType, ruleId,
				new Sort(Direction.DESC, "createDate"));
		if(items == null || items.size()==0) {
			return null;
		}
		CollocationItem item = items.get(0);
		return collocationRepository.findOneWithProperties(item.getCollocation().getId());
	}
	@Override
	public Collocation findOneWithItem(long collId){
		return collocationRepository.findOneWithProperties(collId);
	}

	@Override
	public Collocation findOne(long collId){
		return collocationRepository.findOne(collId);
	}
	
}
