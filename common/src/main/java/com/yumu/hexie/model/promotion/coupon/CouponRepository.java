package com.yumu.hexie.model.promotion.coupon;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	public List<Coupon> findByUserIdAndSeedId(long userId,long seedId);
	@Query("from Coupon c where c.seedId=?1 and c.empty=false order by c.id desc")
	public List<Coupon> findBySeedIdOrderByIdDesc(long seedId, Pageable page);

	@Query("from Coupon c where c.userId=?1 and c.status in ?2 and c.empty=false order by c.id desc")
	public List<Coupon> findByUserIdAndStatusIn(long userId, List<Integer> status,Pageable page);


	@Query("from Coupon c where c.status = "+ModelConstant.COUPON_STATUS_AVAILABLE+" and c.expiredDate<?1")
	public List<Coupon> findTimeoutByPage(Date today,Pageable page);

	
	@Query("select count(c.id) from Coupon c where c.userId=?1 and c.status in ?2 and c.empty=false")
	public int countByUserIdAndStatusIn(long userId, List<Integer> status);

	@Query("from Coupon c where c.ruleId=?1 and c.empty=false order by c.id desc")
	public List<Coupon> findByRuleId(long ruleId);

	@Query("select count(c.id) from Coupon c where c.seedId=?1 and c.empty=false")
	public int countBySeedId(long seedId);
	@Query("select count(c.id) from Coupon c where c.seedType=?1 and c.empty=false")
	public int countBySeedType(int seedType);
	
	@Query("select count(c.id) from Coupon c where c.userId=?1 and c.seedType=?2 and c.empty=false")
	public int countByUserAndSeedType(long userId, int seedType);
	
	@Query("from Coupon c where c.userId=?1 and c.status in ?2 and c.seedType=?3 and c.empty=false order by c.id desc")
	public List<Coupon> findByStatusInAndSeedType(long userId, List<Integer>status, int seedType);
	
	@Query("from Coupon c where c.status = "+ModelConstant.COUPON_STATUS_AVAILABLE+" and c.expiredDate >=?1 and c.expiredDate<= ?2")
	public List<Coupon> findTimeoutCouponByDate(Date fromDate, Date toDate, Pageable page);
	
}
