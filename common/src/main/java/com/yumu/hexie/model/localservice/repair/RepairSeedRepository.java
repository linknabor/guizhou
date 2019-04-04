/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairSeedRepository.java, v 0.1 2016年1月1日 下午12:36:57  Exp $
 */
public interface RepairSeedRepository extends JpaRepository<RepairSeed, Long> {
    
    @Modifying
    @Query("delete from RepairSeed s where s.repairOrderId = ?1")
    @Transactional
    public void deleteByRepairOrderId(long orderId);

    @Query("FROM RepairSeed s where s.operatorUserId = ?1 order by s.id desc")
    public List<RepairSeed> findByOperatorUserId(long opUserId);
}
