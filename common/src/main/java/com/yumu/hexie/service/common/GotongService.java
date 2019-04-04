/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common;

import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.user.User;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: GotongService.java, v 0.1 2016年1月8日 上午9:59:49  Exp $
 */
public interface GotongService {

    public void sendRepairAssignMsg(long opId,RepairOrder order,int distance);
    
    public void sendXiyiAssignMsg(long opId,YunXiyiBill bill);
    
    public void sendRepairAssignedMsg(RepairOrder order);
    
    public void sendSubscribeMsg(User user);
    
    public void sendCommonYuyueBillMsg(int serviceType,String title,String billName, String requireTime, String url);

    public void sendThreadPubNotify(User user, com.yumu.hexie.model.community.Thread thread);
}
