/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DistanceUtil;
import com.yumu.hexie.model.distribution.ServiceRegionRepository;
import com.yumu.hexie.model.localservice.HomeServiceConstant;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.assign.AssignRecord;
import com.yumu.hexie.model.localservice.assign.AssignRecordRepository;
import com.yumu.hexie.model.localservice.repair.RepairConstant;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.o2o.BillAssignService;
import com.yumu.hexie.service.o2o.OperatorService;
import com.yumu.hexie.service.user.AddressService;

/**
 * @author tongqian.ni
 * @version $Id: BillAssignServiceImpl.java, v 0.1 2016年4月8日 上午10:56:19  Exp $
 */
@Service("billAssignService")
public class BillAssignServiceImpl implements BillAssignService {

    @Inject
    private AddressService addressService;
    @Inject
    private AssignRecordRepository assignRecordRepository;
    @Inject
    private OperatorService operatorService;
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
    public void assignRepairOrder(RepairOrder order) {
        Address address = addressService.queryAddressById(order.getAddressId());
        List<ServiceOperator> ops = findOperators(HomeServiceConstant.SERVICE_TYPE_REPAIR,address);
        if(ops == null || ops.size() == 0) {
            return;
        }
        if(order.getStatus() == RepairConstant.STATUS_CREATE 
                && order.getOperatorId() != null && order.getOperatorId() != 0){
            return;
        }
        List<AssignRecord> seeds = new ArrayList<AssignRecord>();
        for(ServiceOperator op : ops) {
            AssignRecord rs = new AssignRecord(op,order);
            assignRecordRepository.save(rs);
            //FIXME 发送消息
            seeds.add(rs);
            gotongService.sendRepairAssignMsg(rs.getOperatorId(),order,
                (int)DistanceUtil.distanceBetween(address.getLatitude(), op.getLatitude(),
                    address.getLongitude(), op.getLongitude()));
        }
    }

    private List<ServiceOperator> findOperators(long serviceType,Address address) {
        List<ServiceOperator> ops = null;
        List<Long> regionIds = new ArrayList<Long>();
        regionIds.add(1l);
        regionIds.add(address.getProvinceId());
        regionIds.add(address.getCityId());
        regionIds.add(address.getCountyId());
        regionIds.add(address.getXiaoquId());
        List<Long> operatorIds = serviceRegionRepository.findByOrderTypeAndRegionIds(serviceType,
            regionIds);
        if(operatorIds != null && operatorIds.size() > 0) {
            ops = operatorService.findByIds(operatorIds);
        }
        return ops;
    }
    /** 
     * @param type
     * @param orderId
     * @see com.yumu.hexie.service.o2o.BillAssignService#deleteAssignedOrder(int, long)
     */
    @Override
    public void deleteAssignedOrder(int type, long orderId) {
        assignRecordRepository.deleteByOrderTypeAndId(type, orderId);
    }
    /** 
     * @param type
     * @param orderId
     * @return
     * @see com.yumu.hexie.service.o2o.BillAssignService#queryAssignItem(int, long)
     */
    @Override
    public List<AssignRecord> queryByOperatorId(long operatorId) {
        return assignRecordRepository.findByOperatorId(operatorId);
    }

}
