package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HaorenshengOrderRepository extends JpaRepository<HaorenshengOrder, Long> {
	public HaorenshengOrder findBySOrderId(long sOrderId);
	public HaorenshengOrder findByYOrderId(long yOrderId);
}
