/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.distribution;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceRegionRepository.java, v 0.1 2016年1月1日 上午6:55:38  Exp $
 */
public interface ServiceRegionRepository  extends JpaRepository<ServiceRegion, Long> {

    @Query("select r.operatorId from ServiceRegion r where r.orderType = ?1 and r.regionId in ?2")
    public List<Long> findByOrderTypeAndRegionIds(long orderType, List<Long> idx);
}
