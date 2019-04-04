package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.commonsupport.info.ProductRepository;
import com.yumu.hexie.model.distribution.RgroupAreaItemRepository;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.market.saleplan.RgroupRuleRepository;
import com.yumu.hexie.service.sales.CacheableService;

@Service("cacheableService")
public class CacheableServiceImpl implements CacheableService {
	private static final Logger log = LoggerFactory.getLogger(CacheableServiceImpl.class);
	
	@Inject
	private RgroupRuleRepository rgroupRuleRepository;

	@Inject
	protected ProductRepository productRepository;
	
	@Inject
	protected RgroupAreaItemRepository rgroupAreaItemRepository;
	
	//@Cacheable(value = "rgroupRule", key = "#ruleId.toString()")
	public RgroupRule findRgroupRule(long ruleId) {
		log.error("RgroupServiceImpl#findSalePlan#"+ruleId);
		return rgroupRuleRepository.findOne(ruleId);
	}
	//@CachePut(value = "rgroupRule", key = "#rgroupRule.id.toString()")
	public RgroupRule save(RgroupRule rgroupRule){
		log.error("RgroupServiceImpl#save#"+rgroupRule.getId());
		if(rgroupRule.getOriPrice()<=0){
			Product p = productRepository.findOne(rgroupRule.getProductId());
			rgroupRule.fillProductInfo(p);
		}
		rgroupRule = rgroupRuleRepository.save(rgroupRule);
		return rgroupRule;
	}

}
