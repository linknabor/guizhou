/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceType;

/**
 * <pre>
 * 到家服务产品项
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeItemService.java, v 0.1 2016年3月29日 上午11:19:42  Exp $
 */
public interface HomeItemService {

    //根据HomeDistribution来获取当前区域支持的服务类型
    public List<ServiceType> queryServiceTypeByRegion(long typeId,long regionId);

    //根据HomeDistribution来获取当前区域支持的服务类型
    public List<ServiceItem> queryServiceItemByRegion(long typeId,long regionId);
    public ServiceItem queryById(long itemId);
    public ServiceType queryTypeById(long typeId);
    public ServiceType findTypeByItem(long itemId);
    public ServiceType findBaseTypeByItem(long itemId);
    
    public List<ServiceItem> queryServiceItemByType(long typeId);
    
}
