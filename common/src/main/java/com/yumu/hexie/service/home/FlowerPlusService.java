package com.yumu.hexie.service.home;

import com.yumu.hexie.integration.daojia.flowerplus.FlowerPlusReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;

public interface FlowerPlusService {
	//增加预约
	public YuyueOrder addFlowerPlusOrder(User user,FlowerPlusReq flowerPlusReq,long addressId);
	//检查用户是否购买过次商品
	public boolean checkIsExistenceByProduct(User user,long ruleId);
	//检查总数
	public boolean checkCountByProduct(long ruleId, int count);
}
