package com.yumu.hexie.model.promotion.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponCombinationRepository  extends JpaRepository<CouponCombination, Long>{

	public List<CouponCombination> findByCombinationType(int combinationType);
	
}
