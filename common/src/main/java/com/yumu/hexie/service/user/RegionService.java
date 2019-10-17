package com.yumu.hexie.service.user;

import java.util.List;

import com.yumu.hexie.model.distribution.region.Region;

public interface RegionService {
	
	public List<Region> findByNameAndParentId(String name,Long parentId);
	
	public Region getRegionInfoById(long id);
	
	public Region saveRegion(Region r);
	
	public List<Region> getRegionAll();
	
	public List<Region> findAllBySectId(String sectId);

}
