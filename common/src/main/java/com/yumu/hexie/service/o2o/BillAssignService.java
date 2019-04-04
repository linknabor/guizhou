/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.model.localservice.assign.AssignRecord;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.repair.RepairOrder;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BillAssignService.java, v 0.1 2016年4月8日 上午10:54:51  Exp $
 */
public interface BillAssignService {

    //异步分配维修单
    public void assignRepairOrder(RepairOrder order);
    //public void assignXiyiOrder(YunXiyiBill order);
    

    public void deleteAssignedOrder(int type,long orderId);
    public List<AssignRecord> queryByOperatorId(long operatorId);
}
