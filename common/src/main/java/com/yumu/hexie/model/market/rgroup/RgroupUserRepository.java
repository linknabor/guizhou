package com.yumu.hexie.model.market.rgroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RgroupUserRepository extends JpaRepository<RgroupUser, Long> {
	public List<RgroupUser> findAllByUserIdAndOrderId(long userId,long orderId);
	public List<RgroupUser> findAllByRuleId(long ruleId);
}
