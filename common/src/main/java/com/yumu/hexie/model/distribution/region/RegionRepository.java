package com.yumu.hexie.model.distribution.region;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository extends JpaRepository<Region, Long> {
    public List<Region> findAllByRegionType(int regionType);
    public List<Region> findAllByRegionType(int regionType,Pageable page);
	
	public List<Region> findAllByRegionTypeAndParentId(int regionType,long parentId);
	public List<Region> findAllByParentId(long parentId);
	
	public List<Region> findAllByParentIdAndName(long countyId,String xiaoquName);
    public List<Region> findByAmapId(long amapId);
    
    public Region findByName(String name);
    
    public List<Region> findAllByNameAndParentName(String name,String parentName);
    
    public Region findByNameAndRegionType(String name,int regionType);
    
    @Query(" from Region  where regionType < 4")
    public List<Region> findNeedRegion();
    
    public List<Region> findAllBySectId(String sectId);
    
    @Query(" from Region  where regionType = 4 and sectId is null ")
    public List<Region> getRegionList();
    
    @Query(nativeQuery=true,value="select id from region  where  sectId in ?1")
	public List<String> getRegionBySectid(List<String> sect_ids);
    
    public List<Region> findBySectIdIn(List<String> sectId);
    
}
