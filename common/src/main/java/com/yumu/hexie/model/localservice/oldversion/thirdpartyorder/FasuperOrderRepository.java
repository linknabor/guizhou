package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FasuperOrderRepository extends JpaRepository<FasuperOrder, Long> {
	public FasuperOrder findBySOrderId(long sOrderId);
	public FasuperOrder findByYOrderId(long yOrderId);
}
