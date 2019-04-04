package com.yumu.hexie.model.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface CollocationRepository extends JpaRepository<Collocation, Long> {
	@Modifying
	@Query("update Collocation c set c.status = " + ModelConstant.COLLOCATION_STATUS_INVAILID + " where c.id = ?1")
	public void invalidById(long collocationId);

	@Query("from Collocation c left join fetch c.items where c.id=?1 ")
	public Collocation findOneWithProperties(long collId);
}
