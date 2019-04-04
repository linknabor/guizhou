package com.yumu.hexie.model.localservice.oldversion.thirdpartyorder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeiZhuangWangOrderRepository extends JpaRepository<WeiZhuangWangOrder, Long> {
	public WeiZhuangWangOrder findBySOrderId(long sOrderId);
	public WeiZhuangWangOrder findByYOrderId(long yOrderId);
}
