package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerPlusOrderRepository extends JpaRepository<FlowerPlusOrder, Long> {
	public List<FlowerPlusOrder> findBySOrderId(long sOrderId);
	public List<FlowerPlusOrder> findByYOrderId(long yOrderId);
}
