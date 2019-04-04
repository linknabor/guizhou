/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.bill.BaojieBill;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.o2o.req.BaojieReq;

/**
 * <pre>
 * 保洁服务
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: BaojieService.java, v 0.1 2016年5月18日 上午10:56:39  Exp $
 */
public interface BaojieService {

    //创建订单
    public BaojieBill create(BaojieReq req,User user);
    //保洁支付
    public JsSign pay(long billId,User user);

    public void update4Payment(PaymentOrder payment);
    public void notifyPayed(long billId);
    
    //查询
    public BaojieBill get(long billId,User user);
    //确认
    public BaojieBill confirm(long billId,User user);
    //取消
    public BaojieBill cancel(long billId,User user);
    
    public void timeout(long billId);
    
    public List<BaojieBill> query(User user,int page);
}
