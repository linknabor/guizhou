package com.yumu.hexie.model.market.saleplan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YuyueRuleRepository extends JpaRepository<YuyueRule, Long> {
	List<YuyueRule> findAllByProductId(long productId);
	
	/**
	 * 查询预约规则对应商品ID[0],商品名称[1],商家ID[2],规则名称[3],规则价格[4]
	 * @param yuyueRuleId
	 * @return Object是一个数组
	 */
	@Query(value="select p.id productId,p.name productName,p.merchantId,r.name ruleName,r.price rulePrice from product p join yuyuerule r on p.id = r.productId where r.id= ?1", nativeQuery = true)
	public List<Object> queryRuleAndProductInfoByRuleId(long yuyueRuleId);
	
}
