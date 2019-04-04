/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairOperatorRepository.java, v 0.1 2016年1月1日 上午6:55:38  Exp $
 */
public interface RepairOperatorRepository  extends JpaRepository<RepairOperator, Long> {

//    @Query("FROM RepairOperator ro where ro.id in ?1")
//    public List<RepairOperator> findOperators(List<Long> operatorIds);
//    
//    public List<RepairOperator> findByUserId(long userId);
//    
//    @Query("From RepairOperator r order by POWER(MOD(ABS(r.longitude - ?1),360),2) + POWER(ABS(r.latitude - ?2),2)")
//    public List<RepairOperator> findByLongitudeAndLatitude(Double longitude, Double latitude, Pageable pageable);

}
