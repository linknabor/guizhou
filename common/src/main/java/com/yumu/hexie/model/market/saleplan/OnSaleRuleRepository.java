package com.yumu.hexie.model.market.saleplan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OnSaleRuleRepository extends JpaRepository<OnSaleRule, Long> {
	List<OnSaleRule> findAllByProductId(long productId);
}
