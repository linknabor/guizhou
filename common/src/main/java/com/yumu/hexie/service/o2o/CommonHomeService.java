/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.payment.PaymentOrder;

/**
 * <pre>
 * 通用到家服务功能
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: CommonHomeService.java, v 0.1 2016年5月20日 上午11:08:15  Exp $
 */
public interface CommonHomeService {

    public PaymentOrder reqPay(BaseO2OService bill, String openId);
    public void udpateSettleInfo(BaseO2OService bill,Long merchantId,List<HomeBillItem> items,PaymentOrder pay);
}
