/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.assign;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AssignRecordRepository.java, v 0.1 2016年4月8日 上午10:52:12  Exp $
 */
public interface AssignRecordRepository extends JpaRepository<AssignRecord, Long> {

    @Modifying
    @Query("delete from AssignRecord ar where ar.orderType=?1 and ar.orderId = ?2")
    public void deleteByOrderTypeAndId(int type, long id);

    @Query("from AssignRecord ar where ar.operatorId=?1 order by id desc")
    public List<AssignRecord> findByOperatorId(long id);
}
