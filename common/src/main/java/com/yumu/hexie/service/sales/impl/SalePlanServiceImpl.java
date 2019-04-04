package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.CustomOrderService;
import com.yumu.hexie.service.sales.SalePlanService;

@Service("salePlanService")
public class SalePlanServiceImpl implements SalePlanService {
    @Inject
    private CustomOrderService customOnSaleService;
    @Inject
    private CustomOrderService customRgroupService;
    @Inject
    private CustomOrderService customYuyueService;
    @Inject
    private CustomOrderService customRepairService;
	@Override
	public CustomOrderService getService(int orderType) {
		if (orderType == 0 || orderType == 1) {
			return customOnSaleService;//FIXME 删除拼单相关代码
		} else if (orderType == ModelConstant.ORDER_TYPE_ONSALE) {
			return customOnSaleService;
		} else if (orderType == ModelConstant.ORDER_TYPE_RGROUP) {
			return customRgroupService;
		} else if (orderType == ModelConstant.ORDER_TYPE_YUYUE) {
			return customYuyueService;
		} else if (orderType == ModelConstant.ORDER_TYPE_REPAIR) {
		    return customRepairService;
		} else {
			throw new BizValidateException("订单类型错误！");
		}
	}

}
