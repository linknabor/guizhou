package com.yumu.hexie.model.localservice.repair;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepairAreaRepository extends JpaRepository<RepairArea, Long>{
	
	@Query(value = "select * from RepairArea ra where ra.cspId = ?1 and if( ?2 != '', ra.sectId = ?2, 1=1 )", 
			nativeQuery = true)
	public List<RepairArea> findByCspIdAndSectid(String cspId, String sectId);
	
	public List<RepairArea> findBySectId(String sectId);
	
	public List<RepairArea> findByCspId(String cspId);

}
