/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common;

import java.util.List;

import com.yumu.hexie.model.distribution.OnSaleAreaItem;
import com.yumu.hexie.model.distribution.RgroupAreaItem;
import com.yumu.hexie.model.market.saleplan.OnSaleRule;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: DistributionService.java, v 0.1 2016年4月6日 下午4:41:38  Exp $
 */
public interface DistributionService {

    public void validYuyuePlan(YuyueRule rule,Address address);
    public void validOnSalePlan(OnSaleRule onSale,Address address);
    public void validRgroupPlan(RgroupRule rule,Address address);
    
    public List<OnSaleAreaItem> queryOnsales(User user,int type,int page);
    public List<RgroupAreaItem> queryRgroups(User user,int page);
    public List<Long> queryO2OServiceIds(long regionId, long type);
    public List<Long> queryO2OItemIds(long regionId, long type);
}
