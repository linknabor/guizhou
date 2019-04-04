package com.yumu.hexie.model.promotion.coupon;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRuleRepository extends JpaRepository<CouponRule, Long> {
	@Query("from CouponRule cr where cr.seedId = ?1")
	public List<CouponRule> findBySeedId(long seedId);
	@Query("from CouponRule cr where cr.seedId = ?1 and cr.status = ?2 and cr.startDate<= ?3 and endDate >=?4 ")
	public List<CouponRule> findBySeedIdAndStatusDuration(long seedId, int status, Date now1,Date now2);
	
	public List<CouponRule> findBySeedIdAndStatus(long seedId,int status);
}
