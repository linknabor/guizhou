package com.yumu.hexie.model.distribution;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface YuyueAreaItemRepository extends JpaRepository<YuyueAreaItem, Long> {

	@Query("from YuyueAreaItem m where m.status=0 and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5 "
			+ "order by m.sortNo asc,m.id desc ")
	public List<YuyueAreaItem> findAllByUserInfo(long provinceId,long cityId,long countyId,long xiaoquId,long current, Pageable pageable);

	@Query("from YuyueAreaItem m where m.status=0 and m.regionType!=4 and m.ruleCloseTime>?1 order by m.id desc ")
	public List<OnSaleAreaItem> findAllDefalut(long current,Pageable pageable);

	@Query("select count(*) from YuyueAreaItem m where m.status=0 and m.regionType!=4 and m.ruleCloseTime>?1")
	public int countAllDefalut(long current);
	
	@Query("select count(*) from YuyueAreaItem m where m.status=0 and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5")
	public int countByUserInfo(long provinceId,long cityId,long countyId,long xiaoquId,long current);
	
	@Query("from YuyueAreaItem m where m.status=0 "
			+ "and m.ruleId=?1 "
			+ "and m.ruleCloseTime>?2 "
			+ "order by m.id desc ")
	public List<YuyueAreaItem> findAllAvaibleItemById(long ruleId,long current);
}
