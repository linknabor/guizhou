package com.yumu.hexie.model.commonsupport.logistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
	
	@Query("from Logistics l where l.logisticsno=?1")
	public Logistics findByLogistics(String nu);
}
