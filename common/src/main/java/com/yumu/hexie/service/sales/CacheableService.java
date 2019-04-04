package com.yumu.hexie.service.sales;

import com.yumu.hexie.model.market.saleplan.RgroupRule;

public interface CacheableService {

	public RgroupRule save(RgroupRule rule);
	public RgroupRule findRgroupRule(long ruleId);
}
