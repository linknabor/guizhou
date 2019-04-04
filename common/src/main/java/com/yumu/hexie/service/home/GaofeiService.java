package com.yumu.hexie.service.home;

import com.yumu.hexie.integration.daojia.gaofei.GaofeiReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;

public interface GaofeiService {
	//高飞订单体验
	public YuyueOrder addGaofeiExperienceOrder(User user,GaofeiReq gaofeiReq);
	
	//高飞上门体验
	public YuyueOrder addGaofeiYuyueOrder(User user,GaofeiReq gaofeiReq, long addressId);
	
	//检查用户是否购买过次商品
	public boolean checkIsExistenceByProduct(User user,long ruleId);
}
