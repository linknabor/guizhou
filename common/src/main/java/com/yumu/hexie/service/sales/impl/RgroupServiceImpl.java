package com.yumu.hexie.service.sales.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.RgroupAreaItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.service.sales.CacheableService;
import com.yumu.hexie.service.sales.RgroupService;
import com.yumu.hexie.service.user.UserNoticeService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.vo.RgroupOrder;

@Service("rgroupService")
public class RgroupServiceImpl implements RgroupService {
	private static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);
	
	@Inject
	private UserService userService;
	@Inject
	private CacheableService cacheableService;
    @Inject
    private ServiceOrderRepository serviceOrderRepository;
    @Inject
    private UserNoticeService userNoticeService;
    @Inject
    private BaseOrderService baseOrderService;

    private void cancelValidate(RgroupRule rule) {
        if(rule.getGroupStatus() == ModelConstant.RGROUP_STAUS_FINISH){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP,rule.getId(),"该团购已完成！").setError();
        }
    }
	private void cancelGroup(RgroupRule rule) {
		log.error("cancelGroup:"+rule.getId());
		cancelValidate(rule);
		List<ServiceOrder> orders = serviceOrderRepository.findByRGroup(rule.getId());
		for(ServiceOrder o : orders){
			try{
				o.setGroupStatus(ModelConstant.GROUP_STAUS_CANCEL);
				if(ModelConstant.ORDER_STATUS_PAYED == o.getStatus()) {
				    baseOrderService.refund(o);
				} else {
				    baseOrderService.cancelOrder(o);
				}
				User u = userService.getById(o.getUserId());

				userNoticeService.groupFail(o.getUserId(),u.getTel(), o.getGroupRuleId(), rule.getProductName(), rule.getGroupMinNum(), rule.getName());
			}catch(Exception e) {
			    log.error("cancelGroupError",e);
			}
		}

		rule.setGroupStatus(ModelConstant.RGROUP_STAUS_CANCEL);
		cacheableService.save(rule);
	}

	@Override
	public void refreshGroupStatus(RgroupRule rule) {
		if(System.currentTimeMillis()>rule.getEndDate().getTime()) {
			if(rule.getCurrentNum() < rule.getGroupMinNum()) {
				cancelGroup(rule);
			} else {
				finishGroup(rule);
			}
		} else {
			log.error("该团购未到结束时间！" + rule.getId());
		}
	}
	private void finishValidate(RgroupRule rule) {
        if(rule.getGroupStatus() == ModelConstant.RGROUP_STAUS_CANCEL || rule.getCurrentNum() < rule.getGroupMinNum()){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP,rule.getId(),"该团购已取消或人数不足！").setError();
        }
    }
	private void finishGroup(RgroupRule rule) {
        log.error("finishGroup:"+rule.getId());
        finishValidate(rule);
        List<ServiceOrder> orders = serviceOrderRepository.findByRGroup(rule.getId());
        for(ServiceOrder o : orders){
            try{
                o.setGroupStatus(ModelConstant.GROUP_STAUS_FINISH);
                if(ModelConstant.ORDER_STATUS_INIT == o.getStatus()) {
                    baseOrderService.cancelOrder(o);
                } else if(ModelConstant.ORDER_STATUS_PAYED == o.getStatus()) {
                    baseOrderService.confirmOrder(o);
                } else {
                    log.error("finishGroup:"+rule.getId());
                }
            }catch(Exception e) {
                log.error("finishGroup:"+rule.getId(),e);
            }
            
        }

        rule.setGroupStatus(ModelConstant.RGROUP_STAUS_FINISH);
        cacheableService.save(rule);
    }
	public List<RgroupAreaItem> addProcessStatus(List<RgroupAreaItem> result) {
        for(RgroupAreaItem item : result){
            RgroupRule rule = findSalePlan(item.getRuleId());
            if(rule!=null) {
                item.setProcess(rule.getProcess());
            } else {
                item.setProcess(0);
            }
        }
        return result;
    }
	//FIXME
	public RgroupRule findSalePlan(long ruleId) {
		return cacheableService.findRgroupRule(ruleId);
	}
	
	

	@Override
	public List<RgroupOrder> queryMyRgroupOrders(long userId,List<Integer> status) {
		List<ServiceOrder> orders = serviceOrderRepository.findByUserAndStatusAndType(userId,status, ModelConstant.ORDER_TYPE_RGROUP);
		List<RgroupOrder> result = new ArrayList<RgroupOrder>();
		for(ServiceOrder so : orders) {
			result.add(new RgroupOrder(cacheableService.findRgroupRule(so.getGroupRuleId()), so));
		}
		return result;
	}

}


