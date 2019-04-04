package com.yumu.hexie.service.home;

import com.yumu.hexie.integration.daojia.jiuye.JiuyeReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;

/**
 * 九曳
 * @author hwb_work
 *
 */
public interface JiuyeService {

	/**
	 * 增加预约
	 * @param user
	 * @param req
	 * @param addressId
	 * @return
	 */
	public YuyueOrder addOrder(User user,JiuyeReq req,long addressId);
}
