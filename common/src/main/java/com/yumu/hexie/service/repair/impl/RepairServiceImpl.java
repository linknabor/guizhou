/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.ServiceOperatorRepository;
import com.yumu.hexie.model.localservice.repair.RepairConstant;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.localservice.repair.RepairOrderRepository;
import com.yumu.hexie.model.localservice.repair.RepairProject;
import com.yumu.hexie.model.localservice.repair.RepairProjectRepository;
import com.yumu.hexie.model.localservice.repair.RepairSeed;
import com.yumu.hexie.model.localservice.repair.RepairSeedRepository;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.AddressRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.common.UploadService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.repair.RepairAssignService;
import com.yumu.hexie.service.repair.RepairService;
import com.yumu.hexie.service.repair.req.RepairCancelReq;
import com.yumu.hexie.service.repair.req.RepairComment;
import com.yumu.hexie.service.repair.resp.RepairListItem;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.vo.req.RepairOrderReq;

/**
 * <pre>
 * 维修服务
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairServiceImpl.java, v 0.1 2016年1月1日 上午10:18:13  Exp $
 */
@Service("repairService")
public class RepairServiceImpl implements RepairService {

    @Inject
    private RepairProjectRepository repairProjectRepository;
    @Inject
    private RepairOrderRepository repairOrderRepository;
    @Inject
    private AddressRepository addressRepository;
    @Inject
    private BaseOrderService baseOrderService;
    @Inject
    private RepairSeedRepository repairSeedRepository;
    @Inject
    private ServiceOperatorRepository serviceOperatorRepository;
    @Inject
    private UploadService uploadService;
    @Inject
    private GotongService gotongService;

    @Inject
    private RepairAssignService repairAssignService;
    /** 
     * @param repairType
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#queryProject(int)
     */
    @Override
    public List<RepairProject> queryProject(int repairType) {
        return repairProjectRepository.queryByRepairTypeAndStatus(repairType, RepairConstant.PROJECT_STATUS_AVALIBLE);
    }

    /** 
     * @param req
     * @param user
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#repair(com.yumu.hexie.vo.req.RepairOrderReq, com.yumu.hexie.model.user.User)
     */
    @Override
    public Long repair(RepairOrderReq req, User user) {
        RepairProject project = repairProjectRepository.findOne(req.getProjectId());
        Address address = addressRepository.findOne(req.getAddressId());
        
        RepairOrder order = new RepairOrder(req, user, project, address);
        order = repairOrderRepository.save(order);
        uploadService.updateRepairImg(order);
        repairAssignService.assignOrder(order);
        return order.getId();
    }

    /** 
     * @param orderId
     * @param user
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#finish(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public boolean finish(long orderId, User user) {
        RepairOrder order = repairOrderRepository.findOne(orderId);
        if(order.getUserId() != user.getId()){
            return false;
        }
        if(order.getStatus() == RepairConstant.STATUS_CREATE
                ||order.getStatus() == RepairConstant.STATUS_CANCEL
                ){
            throw new BizValidateException("该维修单无法结束！");
        }
        if(order.canFinish(true)){
            order.finish(true);
            repairOrderRepository.save(order);
        }
        return true;
    }

    /** 
     * @param orderId
     * @param amount
     * @param user
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#requestPay(long, int, com.yumu.hexie.model.user.User)
     */
    @Override
    public JsSign requestPay(long orderId, float amount, User user) {
        RepairOrder ro = repairOrderRepository.findOne(orderId);
        ro.setAmount(amount);
        ServiceOrder so = baseOrderService.createRepairOrder(ro, amount);
        return baseOrderService.requestPay(so);
    }

    /** 
     * @param orderId
     * @param amount
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#payOffline(long, int, com.yumu.hexie.model.user.User)
     */
    @Override
    public void payOffline(long orderId, float amount, User user) {
        RepairOrder ro = repairOrderRepository.findOne(orderId);
        ro.payOffline(amount);
        repairOrderRepository.save(ro);
    }

    /** 
     * @param orderId
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#notifyPaySuccess(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public void notifyPaySuccess(long orderId, User user) {
        RepairOrder ro = repairOrderRepository.findOne(orderId);
        if(ro.getOrderId()!=null&&ro.getOrderId()!=0&&ro.getUserId() == user.getId()){
            baseOrderService.notifyPayed(ro.getOrderId());
        }
    }

    /** 
     * @param req
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#cancel(com.yumu.hexie.service.repair.req.RepairCancelReq, com.yumu.hexie.model.user.User)
     */
    @Override
    @Transactional
    public void cancel(RepairCancelReq req, User user) {
        RepairOrder ro = repairOrderRepository.findOne(req.getOrderId());
        if(ro.getUserId() == user.getId()){
            ro.cancel(req.getCancelReasonType(), req.getCancelReason());
            repairOrderRepository.save(ro);
            repairSeedRepository.deleteByRepairOrderId(ro.getId());
        }
        
    }

    /** 
     * @param orderId
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#deleteByUser(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public void deleteByUser(long orderId, User user) {
        RepairOrder ro = repairOrderRepository.findOne(orderId);
        if(ro.getUserId() == user.getId()){
            ro.deleteByUser();
            repairOrderRepository.save(ro);
        }
    }
    @Override
    public void deleteByOperator(long orderId, User user) {
        RepairOrder ro = repairOrderRepository.findOne(orderId);
        if(ro.getStatus() != RepairConstant.STATUS_CANCEL
                && ro.getStatus() != RepairConstant.STATUS_FININSH
                        && ro.getStatus() != RepairConstant.STATUS_PAYED
                ){
            throw new BizValidateException("该订单还在处理中，无法删除！");
        }
        List<ServiceOperator>  os = serviceOperatorRepository.findByUserId(user.getId());
        if(os.size()<=0){
            throw new BizValidateException("你不是系统的维修工！");
        } else {
            for(ServiceOperator o : os) {
                if(o.getId() == ro.getOperatorId()) {
                    ro.deleteByOperator();
                    repairOrderRepository.save(ro);
                    break;
                }
            }
        }
    }
    

    /** 
     * @param comment
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#comment(com.yumu.hexie.service.repair.req.RepairComment, com.yumu.hexie.model.user.User)
     */
    @Override
    public void comment(RepairComment comment, User user) {
        RepairOrder ro = repairOrderRepository.findOne(comment.getRepairId());
        if(ro != null && ro.getUserId() == user.getId()){
            ro.comment(comment);
            ro = repairOrderRepository.save(ro);
            uploadService.updateRepairImg(ro);
        }
    }

    /** 
     * @param user
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#queryTop20ByUser(com.yumu.hexie.model.user.User)
     */
    @Override
    public List<RepairListItem> queryTop20ByUser(User user) {
        List<RepairListItem> r = new ArrayList<RepairListItem>();
        List<RepairOrder> orders = repairOrderRepository.queryByUser(user.getId(), new PageRequest(0, 20));;
        for(RepairOrder order : orders) {
            r.add(new RepairListItem(order));
        }     
        return r;
    }

    /** 
     * @param id
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#queryById(long)
     */
    @Override
    public RepairOrder queryById(long id) {
        return repairOrderRepository.findOne(id);
    }


    /** 
     * @param repairOrderId
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#accept(long, com.yumu.hexie.model.user.User)
     */
    @Override
    @Transactional
    public void accept(long repairOrderId, User user) {
        RepairOrder ro = repairOrderRepository.findOne(repairOrderId);
        List<ServiceOperator> ops = serviceOperatorRepository.findByUserId(user.getId());
        if(ops != null && ops.size() >0) {
            ServiceOperator op = ops.get(0);
            ro.accept(op);
            ro = repairOrderRepository.save(ro);
            gotongService.sendRepairAssignedMsg(ro);
        }
        repairSeedRepository.deleteByRepairOrderId(repairOrderId);
    }

    /** 
     * @param repairOrderId
     * @param user
     * @see com.yumu.hexie.service.repair.RepairService#finishByOperator(long, com.yumu.hexie.model.user.User)
     */
    @Override
    public void finishByOperator(long repairOrderId, User user) {
        RepairOrder ro = repairOrderRepository.findOne(repairOrderId);
        if(ro.getOperatorUserId() == user.getId() && ro.canFinish(false)){
            ro.finish(false);
            repairOrderRepository.save(ro);
        }
    }

    /** 
     * @param user
     * @param status
     * @return
     * @see com.yumu.hexie.service.repair.RepairService#queryTop20ByOperatorAndStatus(com.yumu.hexie.model.user.User, int)
     */
    @Override
    public List<RepairListItem> queryTop20ByOperatorAndStatus(User user, int status) {
        List<RepairListItem> r = new ArrayList<RepairListItem>();
        if(RepairConstant.ORDER_OP_STATUS_UNACCEPT == status) {
            List<RepairSeed> seeds = repairSeedRepository.findByOperatorUserId(user.getId());
            for(RepairSeed seed : seeds) {
                r.add(new RepairListItem(seed));
            }
        } else {
            List<Integer> statuses = new ArrayList<Integer>();
            if(RepairConstant.ORDER_OP_STATUS_UNFINISH == status) {
                statuses.add(RepairConstant.STATUS_CREATE);
                statuses.add(RepairConstant.STATUS_ACCEPT);
            } else if(RepairConstant.ORDER_OP_STATUS_FINISHED == status) {
                statuses.add(RepairConstant.STATUS_FININSH);
                statuses.add(RepairConstant.STATUS_PAYED);
            }
            List<RepairOrder> orders = repairOrderRepository
                    .queryByOperatorUser(user.getId(),statuses,new PageRequest(0, 20));
            for(RepairOrder order : orders) {
                r.add(new RepairListItem(order));
            }      
        }
        return r;
    }
    
    @Override
	public Long reassgin(long orderId, User user) {
		
		RepairOrder order = queryById(orderId);
		repairAssignService.assignOrder(order);
        return order.getId();
	}

}
