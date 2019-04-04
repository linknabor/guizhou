/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.aftersale.impl;

import com.yumu.hexie.model.commonsupport.comment.AfterSaleBack;
import com.yumu.hexie.model.commonsupport.comment.AfterSaleBill;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.aftersale.AfterSaleService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AfterSaleServiceImpl.java, v 0.1 2016年4月10日 下午9:58:18  Exp $
 */
public class AfterSaleServiceImpl implements AfterSaleService {

    /** 
     * @param bill
     * @param user
     * @see com.yumu.hexie.service.aftersale.AfterSaleService#complain(com.yumu.hexie.model.commonsupport.comment.AfterSaleBill, com.yumu.hexie.model.user.User)
     */
    @Override
    public void complain(AfterSaleBill bill, User user) {
    }

    /** 
     * @param billId
     * @param feedback
     * @param user
     * @see com.yumu.hexie.service.aftersale.AfterSaleService#feedBackComplain(long, com.yumu.hexie.model.commonsupport.comment.AfterSaleBack, com.yumu.hexie.model.user.User)
     */
    @Override
    public void feedBackComplain(long billId, AfterSaleBack feedback, User user) {
    }

}
