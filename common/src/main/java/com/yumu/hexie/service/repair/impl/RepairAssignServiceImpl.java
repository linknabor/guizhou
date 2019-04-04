/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DistanceUtil;
import com.yumu.hexie.model.distribution.ServiceRegionRepository;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.ServiceOperatorRepository;
import com.yumu.hexie.model.localservice.repair.RepairConstant;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.localservice.repair.RepairSeed;
import com.yumu.hexie.model.localservice.repair.RepairSeedRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.repair.RepairAssignService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairAssignServiceImpl.java, v 0.1 2016年1月11日 下午8:13:05  Exp $
 */
@Service("repairAssignService")
public class RepairAssignServiceImpl implements RepairAssignService {

    private static final Logger log = LoggerFactory.getLogger(RepairAssignService.class);
    @Inject
    private AddressRepository addressRepository;
    @Inject
    private RepairSeedRepository repairSeedRepository;
    @Inject
    private ServiceOperatorRepository serviceOperatorRepository;
    @Inject
    private GotongService gotongService;
    
    @Inject
    private ServiceRegionRepository serviceRegionRepository;
    /** 
     * @param order
     * @see com.yumu.hexie.service.repair.RepairAssignService#assignOrder(com.yumu.hexie.model.localservice.repair.RepairOrder)
     */
    @Async
    @Override
    public void assignOrder(RepairOrder order) {
        Address address = addressRepository.findOne(order.getAddressId());
        List<ServiceOperator> ops = null;
        List<Long> regionIds = new ArrayList<Long>();
        regionIds.add(1l);
        regionIds.add(address.getProvinceId());
        regionIds.add(address.getCityId());
        regionIds.add(address.getCountyId());
        regionIds.add(address.getXiaoquId());
        List<Long> operatorIds = serviceRegionRepository.findByOrderTypeAndRegionIds(HomeServiceConstant.SERVICE_TYPE_REPAIR,regionIds);
        log.error("维修单对应维修工数量" + operatorIds.size());
        if(operatorIds != null && operatorIds.size() > 0) {
            ops = serviceOperatorRepository.findOperators(operatorIds);
        }
        if(ops == null) {
//            ops = repairOperatorRepository.findByLongitudeAndLatitude(address.getLongitude(), address.getLatitude(),
//                new PageRequest(0, 2));
            //业务判断-通知下单失败
        }
        
        assign(address,order, ops);
    }
    private void assign(Address address,RepairOrder ro, List<ServiceOperator> ops) {
        if(ro.getStatus() == RepairConstant.STATUS_CREATE && ro.getOperatorId() != null && ro.getOperatorId() != 0){
            return;
        }
        List<RepairSeed> seeds = new ArrayList<RepairSeed>();
        for(ServiceOperator op : ops) {
            RepairSeed rs = new RepairSeed(op,ro);
            repairSeedRepository.save(rs);
            //FIXME 发送消息
            seeds.add(rs);
            gotongService.sendRepairAssignMsg(rs.getOperatorId(),ro,
                (int)DistanceUtil.distanceBetween(address.getLatitude(), op.getLatitude(),
                    address.getLongitude(), op.getLongitude()));
        }
    }
}
