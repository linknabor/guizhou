package com.yumu.hexie.model.distribution;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yumu.hexie.model.ModelConstant;

public interface RgroupAreaItemRepository extends JpaRepository<RgroupAreaItem, Long> {

	@Query("from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5 "
			+ "order by m.sortNo asc,m.id desc ")
	public List<RgroupAreaItem> findAllByUserInfo(long provinceId,long cityId,long countyId,long xiaoquId,long current, Pageable pageable);

	@Query("from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and m.regionType!=4 and m.ruleCloseTime>?1 order by m.sortNo asc,m.id desc ")
	public List<RgroupAreaItem> findAllDefalut(long current,Pageable pageable);

	@Query("select count(*) from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and m.regionType!=4 and m.ruleCloseTime>?1")
	public int countAllDefalut(long current);
	
	@Query("select count(*) from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5")
	public int countByUserInfo(long provinceId,long cityId,long countyId,long xiaoquId,long current);
	
	@Query("from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" "
			+ "and m.ruleId=?1 "
			+ "and m.ruleCloseTime>?2 "
			+ "order by m.id desc ")
	public List<RgroupAreaItem> findAllAvaibleItemById(long id, long currentTimeMillis);
	
	public List<RgroupAreaItem> findByRuleId(long ruleId);
	
	@Query("from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5 and featured is true "
			+ "order by m.sortNo asc,m.id desc ")
	public List<RgroupAreaItem> findFeatured(long provinceId,long cityId,long countyId,long xiaoquId,long current, Pageable pageable);
	
	@Query("from RgroupAreaItem m where m.status="+ModelConstant.DISTRIBUTION_STATUS_ON+" and ((m.regionType=0) "
			+ "or (m.regionType=1 and m.regionId=?1) "
			+ "or (m.regionType=2 and m.regionId=?2) "
			+ "or (m.regionType=3 and m.regionId=?3) "
			+ "or (m.regionType=4 and m.regionId=?4)) "
			+ "and m.ruleCloseTime>?5 and productType=?6 "
			+ "order by m.sortNo asc,m.id desc ")
	public List<RgroupAreaItem> findByCusProductType(long provinceId,long cityId,long countyId,long xiaoquId,long current,int productType, Pageable pageable);
}
