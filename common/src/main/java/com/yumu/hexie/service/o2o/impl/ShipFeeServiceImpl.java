/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;
import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplateRepository;
import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.ShipFeeService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ShipFeeServiceImpl.java, v 0.1 2016年5月26日 下午7:15:33  Exp $
 */
@Service("shipFeeService")
public class ShipFeeServiceImpl implements ShipFeeService {

    @Inject
    private ShipFeeTemplateRepository shipFeeTemplateRepository;

    @Inject
    private HomeItemService homeItemService;
    
    /** 
     * @param tplId
     * @return
     * @see com.yumu.hexie.service.o2o.ShipFeeService#findTemplate(long)
     */
    @Override
    public ShipFeeTemplate findTemplate(long tplId) {
        return shipFeeTemplateRepository.findOne(tplId);
    }

    /** 
     * @param tplId
     * @param amount
     * @param regionIds
     * @return
     * @see com.yumu.hexie.service.o2o.ShipFeeService#getShipFee(long, java.math.BigDecimal, java.util.List)
     */
    @Override
    public BigDecimal getShipFee(long tplId, BigDecimal amount, List<Long> regionIds) {

        ShipFeeTemplate tpl = findTemplate(tplId);
        if(tpl.getFreeFeeLimit().compareTo(amount)>0) {
            return BigDecimal.ZERO;
        }
        List<Long> ids = tpl.extractFreeRegions();
        for(Long regionId : regionIds) {
            for(Long freeRegionId : ids) {
                if(regionId == freeRegionId) {
                    return BigDecimal.ZERO;
                }
            }
        }
        return tpl.getShipFee();
    }

    

    /** 
     * @param itemId
     * @return
     * @see com.yumu.hexie.service.o2o.ShipFeeService#findTemplateByItem(long)
     */
    @Override
    public ShipFeeTemplate findTemplateByItem(long itemId) {
        ServiceItem item = homeItemService.queryById(itemId);
        if(item.getShipTemplate()!=null && item.getShipTemplate() > 0) {
            return shipFeeTemplateRepository.findOne(item.getShipTemplate());
        }
        ServiceType type;
        long parentId = item.getType();
        do {
            type = homeItemService.queryTypeById(parentId);
            if(type.getShipTemplate()!=null && type.getShipTemplate() > 0) {
                return shipFeeTemplateRepository.findOne(type.getShipTemplate());
            }
            parentId = type.getParentId();
        } while (parentId > 0);
        return null;
    }

}
