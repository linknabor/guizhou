package com.yumu.hexie.service.home;

import java.util.List;

import com.yumu.hexie.integration.daojia.haojiaan.HaoJiaAnReq;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HaoJiaAnOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.vo.YuyueQueryOrder;

public interface HaoJiaAnService {

	public Long addNoNeedPayOrder(User user, HaoJiaAnReq haoJiaAnReq, long addressId);
	
	public YuyueQueryOrder queryYuYueOrder(User user, long orderId);
	
	List<Long> orderAccessAuthority(long orderId);

}
