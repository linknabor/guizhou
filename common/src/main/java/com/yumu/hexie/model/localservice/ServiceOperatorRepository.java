/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceOperatorRepository.java, v 0.1 2016年1月1日 上午6:55:38  Exp $
 */
public interface ServiceOperatorRepository  extends JpaRepository<ServiceOperator, Long> {

    @Query("FROM ServiceOperator ro where ro.id in ?1")
    public List<ServiceOperator> findOperators(List<Long> operatorIds);

    public List<ServiceOperator> findByUserId(long userId);
    public List<ServiceOperator> findByTypeAndUserId(int type,long userId);
    
    public List<ServiceOperator> findByType(int type);
    
    @Query("From ServiceOperator r order by POWER(MOD(ABS(r.longitude - ?1),360),2) + POWER(ABS(r.latitude - ?2),2)")
    public List<ServiceOperator> findByLongitudeAndLatitude(Double longitude, Double latitude, Pageable pageable);
    
    @Query(nativeQuery = true,value="select * from serviceoperator a join serviceoperatorSect b on a.id=b.operatorId where b.sectId = ?1 ")
    public List<ServiceOperator> findBySectId(String sectId);
    
    @Query(value="select a.*,b.sectId from serviceoperator a "
    		+ "join serviceoperatorSect b on a.id=b.operatorId where 1=1"
			+ " and IF (?1!='', name like CONCAT('%',?1,'%'), 1=1)"
			+ " and IF (?2!='', tel like CONCAT('%',?2,'%'), 1=1)"
			+ " and IF (?3!='', b.sectId =?3, 1=1)"
			+ " and b.sectId in ?4 GROUP BY b.operatorId \n#pageable\n",
			countQuery="select count(*) from serviceoperator a "
    		+ "join serviceoperatorSect b on a.id=b.operatorId where 1=1 "
			+ " IF (?1!='', name like CONCAT('%',?1,'%'), 1=1)"
			+ " and IF (?2!='', tel like CONCAT('%',?2,'%'), 1=1)"
			+ " and IF (?3!='', b.sectId =?3, 1=1)"
			+ " and b.sectId in ?4 GROUP BY b.operatorId " 
			,nativeQuery = true)
    public Page<Object>  getServiceoperator(String name,String tel,String sectId, List<String> sectIds,Pageable pageable);
}
