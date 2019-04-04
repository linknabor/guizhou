package com.yumu.hexie.model.market.saleplan;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface RgroupRuleRepository extends JpaRepository<RgroupRule, Long> {
	
	@Query("from RgroupRule p where p.status = "+ModelConstant.RULE_STATUS_ON+" and p.endDate<=?1 and p.groupStatus="+ModelConstant.RGROUP_STAUS_GROUPING)
	public List<RgroupRule> findTimeoutGroup(Date date);
	

	public List<RgroupRule> findAllByProductId(long productId);

}
