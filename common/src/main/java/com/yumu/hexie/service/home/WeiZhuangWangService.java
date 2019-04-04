package com.yumu.hexie.service.home;

import com.yumu.hexie.integration.daojia.weizhuangwang.WeiZhuangWangReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;

public interface WeiZhuangWangService {
	public YuyueOrder addNoNeedPayOrder(User user,WeiZhuangWangReq weiZhuangWangReq,long addressId);
}
