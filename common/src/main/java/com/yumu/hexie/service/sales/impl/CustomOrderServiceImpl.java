/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.sales.impl;

import java.util.List;

import javax.inject.Inject;

import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.OrderItemRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.sales.CustomOrderService;
import com.yumu.hexie.service.sales.ProductService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CustomOrderServiceImpl.java, v 0.1 2016年4月9日 下午5:38:11  Exp $
 */
public abstract class CustomOrderServiceImpl implements CustomOrderService {

    @Inject
    protected ProductService productService;
    @Inject
    protected OrderItemRepository    orderItemRepository;
    @Inject
    protected ServiceOrderRepository serviceOrderRepository;
    @Override
    public abstract SalePlan findSalePlan(long ruleId);

    /** 
     * @param po
     * @param so
     * @see com.yumu.hexie.service.sales.CustomOrderService#postPaySuccess(com.yumu.hexie.model.payment.PaymentOrder, com.yumu.hexie.model.market.ServiceOrder)
     */
    @Override
    public abstract void postPaySuccess(PaymentOrder po, ServiceOrder so);
    /** 
     * @param order
     * @see com.yumu.hexie.service.sales.CustomOrderService#postOrderCancel(com.yumu.hexie.model.market.ServiceOrder)
     */
    @Override
    public void postOrderCancel(ServiceOrder order) {
        List<OrderItem> items = orderItemRepository.findByServiceOrder(order);
        for(OrderItem item : items){
            productService.unfreezeCount(item.getProductId(), item.getCount());
        }
    }
    /** 
     * @param order
     * @see com.yumu.hexie.service.sales.CustomOrderService#postOrderConfirm(com.yumu.hexie.model.market.ServiceOrder)
     */
    @Override
    public abstract void postOrderConfirm(ServiceOrder order);

    /** 
     * @param order
     * @param rule
     * @param item
     * @param address
     * @see com.yumu.hexie.service.sales.CustomOrderService#validateRule(com.yumu.hexie.model.market.ServiceOrder, com.yumu.hexie.model.market.saleplan.SalePlan, com.yumu.hexie.model.market.OrderItem, com.yumu.hexie.model.user.Address)
     */
    @Override
    public abstract void validateRule(ServiceOrder order, SalePlan rule, OrderItem item, Address address);

}
