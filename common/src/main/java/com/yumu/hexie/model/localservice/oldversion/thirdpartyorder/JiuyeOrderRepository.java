package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JiuyeOrderRepository extends JpaRepository<JiuyeOrder, Long> {
	public List<JiuyeOrder> findBySOrderId(long sOrderId);
	public List<JiuyeOrder> findByYOrderId(long yOrderId);
}
