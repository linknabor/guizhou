/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair;

import com.yumu.hexie.model.localservice.repair.RepairOrder;

/**
 * <pre>
 * 异步分配维修单
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairAssignService.java, v 0.1 2016年1月11日 下午8:10:57  Exp $
 */
public interface RepairAssignService {

    //异步分配维修单
    public void assignOrder(RepairOrder order);
}
