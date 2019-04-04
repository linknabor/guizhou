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
 * @version $Id: RepairOrderRepository.java, v 0.1 2016年1月1日 上午6:56:47  Exp $
 */
public interface RepairOrderRepository  extends JpaRepository<RepairOrder, Long> {

    public List<RepairOrder> findByOrderId(long orderId);

    @Query("FROM RepairOrder ro where ro.operatorUserId = ?1 and ro.operatorDeleted = false and ro.status in ?2 order by ro.id desc")
    public List<RepairOrder> queryByOperatorUser(long operatorUserId,List<Integer> statuses,Pageable page);
    
    @Query("FROM RepairOrder ro where ro.userId = ?1 and ro.userDeleted = false order by ro.id desc")
    public List<RepairOrder> queryByUser(long userId,Pageable page);
}
