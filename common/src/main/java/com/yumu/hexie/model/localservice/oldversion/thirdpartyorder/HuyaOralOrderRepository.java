package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HuyaOralOrderRepository extends JpaRepository<HuyaOralOrder, Long> {
	public HuyaOralOrder findBySOrderId(long sOrderId);
	public HuyaOralOrder findByYOrderId(long yOrderId);
}
