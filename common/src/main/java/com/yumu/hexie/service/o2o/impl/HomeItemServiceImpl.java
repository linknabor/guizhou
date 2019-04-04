/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceItemRepository;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.localservice.ServiceTypeRepository;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.o2o.HomeItemService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: HomeItemServiceImpl.java, v 0.1 2016年4月10日 下午7:16:29  Exp $
 */
@Service("homeItemService")
public class HomeItemServiceImpl implements HomeItemService {

    @Inject
    private DistributionService distributionService;
    @Inject
    private ServiceTypeRepository serviceTypeRepository;
    @Inject
    private ServiceItemRepository serviceItemRepository;
    /** 
     * @param regionId
     * @return
     * @see com.yumu.hexie.service.o2o.HomeItemService#queryServiceTypeByRegion(long)
     */
    @Override
    public List<ServiceType> queryServiceTypeByRegion(long typeId,long regionId) {
        List<Long> ids = distributionService.queryO2OServiceIds(regionId, typeId);
        if(ids.size() > 0) {
            return serviceTypeRepository.findByIdIn(ids);
        } else {
            return new ArrayList<ServiceType>();
        }
    }

    /** 
     * @param typeId
     * @param regionId
     * @return
     * @see com.yumu.hexie.service.o2o.HomeItemService#queryServiceItemByRegion(long, long)
     */
    @Override
    public List<ServiceItem> queryServiceItemByRegion(long typeId, long regionId) {
        List<Long> ids = distributionService.queryO2OItemIds(regionId, typeId);
        if(ids.size() > 0) {
            return serviceItemRepository.findByIdIn(ids);
        } else {
            return new ArrayList<ServiceItem>();
        }
        
    }

    /** 
     * @param itemId
     * @return
     * @see com.yumu.hexie.service.o2o.HomeItemService#queryById(long)
     */
    @Override
    public ServiceItem queryById(long itemId) {
        return serviceItemRepository.findOne(itemId);
    }

    /** 
     * @param typeId
     * @return
     * @see com.yumu.hexie.service.o2o.HomeItemService#queryTypeById(long)
     */
    @Override
    public ServiceType queryTypeById(long typeId) {
        return serviceTypeRepository.findOne(typeId);
    }

    @Override
    public ServiceType findTypeByItem(long itemId) {
        ServiceItem i = queryById(itemId);
        return queryTypeById(i.getType());
    }
    /** 
     * @param itemId
     * @return
     * @see com.yumu.hexie.service.o2o.HomeItemService#findBaseTypeByItem(long)
     */
    @Override
    public ServiceType findBaseTypeByItem(long itemId) {
        ServiceType t = null;
        int count = 0;
        ServiceItem i = queryById(itemId);
        long parentId = i.getType();
        while(parentId != 0) {
            t = queryTypeById(parentId);
            if(t == null) {
                break;
            } else {
                parentId = t.getParentId();
            }
            if(++count>10) {
                break;//防止错误配置
            }
        }
        return t;
    }

    @Override
    public List<ServiceItem> queryServiceItemByType(long typeId) {
        return serviceItemRepository.findByType(typeId);
    }

}
