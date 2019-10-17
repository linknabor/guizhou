package com.yumu.hexie.model.localservice.repair;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



 
public interface ServiceOperatorSectRepository  extends JpaRepository<ServiceOperatorSect, Long> {
	
	@Query(nativeQuery=true,value=" select sectId from serviceoperatorSect where operatorId = ?1")
	public List<String> findByOperatorId(Long operatorId);
	
	@Modifying
	@Query(nativeQuery=true,value=" delete from serviceoperatorSect where operatorId = ?1")
	public void deleteByOperatorId(Long operatorId);
	
	@Modifying
	@Query(nativeQuery=true,value=" delete from serviceoperatorSect where operatorId = ?1 and sectId = ?2")
	public int deleteByOperatorIdAndSectId(Long operatorId,String sectId);

}
