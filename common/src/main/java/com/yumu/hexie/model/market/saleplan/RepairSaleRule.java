package com.yumu.hexie.model.market.saleplan;

import com.yumu.hexie.model.ModelConstant;

//特卖规则
public class RepairSaleRule extends SalePlan  {

	private static final long serialVersionUID = 4808669460780339640L;
	
	public int getSalePlanType(){
		return ModelConstant.ORDER_TYPE_REPAIR;//默认特卖
	}
	
}
