package com.yumu.hexie.service.home;

import com.yumu.hexie.integration.daojia.home.AixiangbanReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;


/** 
 * <p>项目：东湖e家园</p>
 * <p>模块：到家服务</p>
 * <p>描述：</p>
 * <p>版    权: Copyright (c) 2016</p>
 * <p>公    司: 上海奈博信息科技有限公司</p>
 * @author hwb_work 
 * @version 1.0 
 * 创建时间：2016年4月15日 下午2:02:08
 */
public interface AixiangbanService {
    public YuyueOrder addOrder(User user, AixiangbanReq req, long addressId);
}
