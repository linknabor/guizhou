/**
 * 
 */
package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HuYM
 * 社区百事通
 */
public interface CommunityInfoRepository extends JpaRepository<CommunityInfo, Long> {

	
	public List<CommunityInfo>findBySectId(long sectId, Sort sort);
	
	public List<CommunityInfo>findByRegionId(long regionId, Sort sort);
	
	public List<CommunityInfo>findByCityIdAndInfoType(long cityId, String infoType, Sort sort);
	
}
