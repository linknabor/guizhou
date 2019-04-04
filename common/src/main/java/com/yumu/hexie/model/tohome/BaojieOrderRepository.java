package com.yumu.hexie.model.tohome;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaojieOrderRepository extends JpaRepository<BaojieOrder, Long> {
	public BaojieOrder findByYOrderId(long yOrderId);
}
