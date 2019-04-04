package com.yumu.hexie.model.market;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface CollocationItemRepository extends JpaRepository<CollocationItem, Long> {
	@Modifying
	@Query("delete from CollocationItem c where c.collocation.id = ?1")
	public void deleteByCollocationId(long collocationId);
	
	@Modifying
	@Query("update CollocationItem c set c.status = " + ModelConstant.COLLOCATION_STATUS_INVAILID + " where c.collocation.id = ?1")
	public void invalidByCollocationId(long collocationId);

	public List<CollocationItem> findByCollocation(Collocation collocation);
	
	@Query("from CollocationItem c left join fetch c.collocation where c.collocation.salePlanType=?1 "
			+ "and c.salePlanId=?2 and c.status="+ModelConstant.COLLOCATION_STATUS_AVAILABLE)
	public List<CollocationItem> findByPlanTypeAndId(int type,long planId,Sort sort);
}
