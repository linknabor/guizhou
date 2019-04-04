/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.o2o.req.CommonBillReq;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: XiyiService.java, v 0.1 2016年4月11日 上午12:20:06  Exp $
 */
public interface XiyiService {
    
    public YunXiyiBill createBill(User user,CommonBillReq req,HomeCart cart);
    public JsSign pay(YunXiyiBill bill,String openId);
    public void update4Payment(PaymentOrder payment);
    public void notifyPayed(long billId);
    //public void accept(long billId,long userId);
    public void cancel(long billId, long userId);
    //public void received(long billId);
    //public void serviced(long billId);
    //public void sended(long billId);
    public void signed(long billId,long userId);
    
    public void timeout(long billId);
    
    public List<YunXiyiBill> queryBills(long userId, int page);
    
    public YunXiyiBill queryById(long id);
    public List<HomeBillItem> findItems(long billId);
}
