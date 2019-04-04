/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.aftersale;

import com.yumu.hexie.model.commonsupport.comment.AfterSaleBack;
import com.yumu.hexie.model.commonsupport.comment.AfterSaleBill;
import com.yumu.hexie.model.user.User;

/**
 * <pre>
 * 售后服务
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AfterSaleService.java, v 0.1 2016年4月1日 下午4:26:28  Exp $
 */
public interface AfterSaleService {

    //发起售后信息
    public void complain(AfterSaleBill bill,User user);
    //回复售后信息
    public void feedBackComplain(long billId, AfterSaleBack feedback, User user);
}
