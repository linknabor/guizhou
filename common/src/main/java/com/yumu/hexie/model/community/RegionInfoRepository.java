package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionInfoRepository extends JpaRepository<RegionInfo, String> {
	
	@Query("from RegionInfo r where r.regionType=3 and r.sect_id=?1")
	public List<RegionInfo> findAllByRegionType(String sectId);
	
	@Query("from RegionInfo r where r.regionType=0")
	public List<RegionInfo> queryRegionInfoByRegionType();
}
