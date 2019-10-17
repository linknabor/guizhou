package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HaoJiaAnOrderRepository extends JpaRepository<HaoJiaAnOrder, Long>{
	public HaoJiaAnOrder findBySOrderId(long sOrderId);
	public HaoJiaAnOrder findByYOrderId(long yOrderId);
}
