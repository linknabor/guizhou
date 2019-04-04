package com.yumu.hexie.service.sales.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.rgroup.RgroupUser;
import com.yumu.hexie.model.market.rgroup.RgroupUserRepository;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.sales.CacheableService;
import com.yumu.hexie.service.sales.ProductService;
import com.yumu.hexie.service.user.UserNoticeService;
import com.yumu.hexie.service.user.UserService;

@Service("customRgroupService")
public class CustomRgroupServiceImpl  extends CustomOrderServiceImpl {
    private static final Logger    log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);

    @Inject
    private UserService         userService;
    @Inject
    private CacheableService       cacheableService;
    @Inject
    private RgroupUserRepository   rgroupUserRepository;
    @Inject
    private DistributionService    distributionService;
    @Inject
    private ProductService         productService;
    @Inject
    private UserNoticeService      userNoticeService;

    @Override
    public void validateRule(ServiceOrder order, SalePlan plan, OrderItem item, Address address) {
        //校验
        if (!plan.valid(item.getCount())) {
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP, plan.getId(), "商品信息已过期，请重新下单！")
                .setError();
        }
        //校验规则限制每个用户的数量
        RgroupRule rule = (RgroupRule) plan;
        limitRuleByUser(rule, order.getUserId());
        distributionService.validRgroupPlan(rule, address);
    }

    private void limitRuleByUser(RgroupRule rule, long userId) {
        if (rule.getRuleLimitUserCount() == 0) {
            return;
        } else {
            if (orderItemRepository.countBuyedOrderItem(userId, rule.getId(), ModelConstant.ORDER_TYPE_RGROUP) < rule
                .getRuleLimitUserCount()) {
                return;
            } else {
                throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP, rule.getId(),
                    "感谢您的参与，每个用户限购" + rule.getRuleLimitUserCount() + "份，请参与其他团购").setError();
            }
        }
    }

    @Override
    public void postPaySuccess(PaymentOrder po, ServiceOrder so) {
        //支付成功订单为配货中状态，改商品库存
        so.payed();
        serviceOrderRepository.save(so);
        for (OrderItem item : so.getItems()) {
            productService.saledCount(item.getProductId(), item.getCount());
        }

        User u = userService.getById(so.getUserId());
        RgroupRule rule = findSalePlan(so.getGroupRuleId());

        log.error("postPaySuccess:" + rule.getId());
        if (rule.getOwnerId() == 0) {
            rule.setOwnerId(so.getUserId());
            rule.setOwnerAddr(so.getAddress());
            rule.setOwnerName(u.getName());
            rule.setOwnerImg(u.getHeadimgurl());
            RgroupUser gr = new RgroupUser(so, u, true);
            rgroupUserRepository.save(gr);
        } else {
            RgroupUser gr = new RgroupUser(so, u, false);
            rgroupUserRepository.save(gr);
        }
        rule.setCurrentNum(rule.getCurrentNum() + so.getCount());
        cacheableService.save(rule);
    }


    public RgroupRule findSalePlan(long ruleId) {
        return cacheableService.findRgroupRule(ruleId);
    }

    @Override
    public void postOrderConfirm(ServiceOrder o) {
        User u = userService.getById(o.getUserId());
        RgroupRule rule = cacheableService.findRgroupRule(o.getGroupRuleId());
        userNoticeService.groupSuccess(o.getUserId(), u.getTel(), o.getGroupRuleId(), rule.getGroupMinNum(),
            rule.getProductName(), rule.getName());
    }

    /** 
     * @param order
     * @see com.yumu.hexie.service.sales.CustomOrderService#postOrderCancel(com.yumu.hexie.model.market.ServiceOrder)
     */
    @Override
    public void postOrderCancel(ServiceOrder order) {
    }

}
