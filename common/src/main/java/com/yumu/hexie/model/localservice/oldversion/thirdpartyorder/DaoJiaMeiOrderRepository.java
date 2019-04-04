package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DaoJiaMeiOrderRepository extends JpaRepository<DaoJiaMeiOrder, Long> {
	public DaoJiaMeiOrder findBySOrderId(long sOrderId);
	public DaoJiaMeiOrder findByYOrderId(long yOrderId);
}
