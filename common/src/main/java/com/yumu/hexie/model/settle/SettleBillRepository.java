/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.settle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SettleBillRepository.java, v 0.1 2016年4月25日 下午5:07:02  Exp $
 */
public interface SettleBillRepository  extends JpaRepository<SettleBill, Long> {

    public List<SettleBill> findByOrderTypeAndOrderId(int type,long orderId);
}
