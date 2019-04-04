/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common;

import java.util.Set;

/**
 * <pre>
 * 系统参数统一获取
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SystemConfigService.java, v 0.1 2016年3月30日 上午11:51:34  Exp $
 */
public interface SystemConfigService {

    public int querySmsChannel();
    public String queryJsTickets();
    public String queryWXAToken();
    
    public String[] queryActPeriod();
    public Set<String> getUnCouponItems();

	String queryValueByKey(String key);
}
