package com.yumu.hexie.model.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionUrlRepository extends JpaRepository<RegionUrl, Long>{
	
	@Query(nativeQuery=true,value = "select * from regionurl where regionname = ?1")
	RegionUrl findregionname(String regionname);
}
