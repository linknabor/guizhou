package com.yumu.hexie.model.promotion.share;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShareRecordRepository extends JpaRepository<OrderShareRecord, Long> {
	public List<OrderShareRecord> findByOrderId(Long orderId);
}
