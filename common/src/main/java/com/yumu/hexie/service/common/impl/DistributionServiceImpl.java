/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.distribution.HomeDistributionRepository;
import com.yumu.hexie.model.distribution.OnSaleAreaItem;
import com.yumu.hexie.model.distribution.OnSaleAreaItemRepository;
import com.yumu.hexie.model.distribution.RgroupAreaItem;
import com.yumu.hexie.model.distribution.RgroupAreaItemRepository;
import com.yumu.hexie.model.distribution.RuleDistribution;
import com.yumu.hexie.model.distribution.YuyueAreaItem;
import com.yumu.hexie.model.distribution.YuyueAreaItemRepository;
import com.yumu.hexie.model.market.saleplan.OnSaleRule;
import com.yumu.hexie.model.market.saleplan.RgroupRule;
import com.yumu.hexie.model.market.saleplan.YuyueRule;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.exception.BizValidateException;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: DistributionServiceImpl.java, v 0.1 2016年4月6日 下午4:45:15  Exp $
 */
@Service("distributionService")
public class DistributionServiceImpl implements DistributionService {

    @Inject
    private YuyueAreaItemRepository yuyueAreaItemRepository;
    @Inject
    private OnSaleAreaItemRepository onSaleAreaItemRepository;
    @Inject
    private RgroupAreaItemRepository rgroupAreaItemRepository;
    @Inject
    private HomeDistributionRepository homeDistributionRepository;

    /** 
     * @param rule
     * @param address
     * @see com.yumu.hexie.service.common.DistributionService#validYuyuePlan(com.yumu.hexie.model.market.saleplan.YuyueRule, com.yumu.hexie.model.user.Address)
     */
    @Override
    public void validYuyuePlan(YuyueRule rule, Address address) {
        List<YuyueAreaItem> items = yuyueAreaItemRepository.findAllAvaibleItemById(rule.getId(),
            System.currentTimeMillis());
        if (items.size() == 0) {
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_DAOJIA, rule.getId(), "您选择的服务已过期，请选择其他服务");
        }
        if (!hasMatchDistribution(address, items)) {
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_DAOJIA, rule.getId(),
                "该服务暂不支持您所在区域，请选择其它地址作为您的服务地址或选择其它商品！");
        }
    }

    /** 
     * @param onSale
     * @param address
     * @see com.yumu.hexie.service.common.DistributionService#validOnSalePlan(com.yumu.hexie.model.market.saleplan.OnSaleRule, com.yumu.hexie.model.user.Address)
     */
    @Override
    public void validOnSalePlan(OnSaleRule rule, Address address) {
        List<OnSaleAreaItem> items = onSaleAreaItemRepository.findAllAvaibleItemById(rule.getId(), System.currentTimeMillis());
        if(items.size()==0){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_ONSALE,rule.getId(),"该商品已下架，快去看看其它拼单商品吧！").setError();
        }
        if(!hasMatchDistribution(address, items)){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_ONSALE,rule.getId(),"该商品暂不支持您所在区域，请选择其它地址作为您的服务地址或选择其它商品！").setError();
        }
    }

    /** 
     * @param rule
     * @param address
     * @see com.yumu.hexie.service.common.DistributionService#validRgroupPlan(com.yumu.hexie.model.market.saleplan.RgroupRule, com.yumu.hexie.model.user.Address)
     */
    @Override
    public void validRgroupPlan(RgroupRule rule, Address address) {
        List<RgroupAreaItem> items = rgroupAreaItemRepository.findAllAvaibleItemById(rule.getId(), System.currentTimeMillis());
        if(items.size()==0){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP,rule.getId(),"该商品已下架，快去看看其它拼单商品吧！").setError();
        }
        if(!hasMatchDistribution(address, items)){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_RGROUP,rule.getId(),"该商品暂不支持您所在区域，请选择其它地址作为您的服务地址或选择其它商品！").setError();
        }
    
    }

    private boolean hasMatchDistribution(Address address, List<? extends RuleDistribution> items) {
        for (RuleDistribution item : items) {
            if (item.getRegionType() == ModelConstant.REGION_ALL) {
                return true;
            } else if (item.getRegionType() == ModelConstant.REGION_PROVINCE
                       && item.getRegionId() == address.getProvinceId()) {
                return true;
            } else if (item.getRegionType() == ModelConstant.REGION_CITY && item.getRegionId() == address.getCityId()) {
                return true;
            } else if (item.getRegionType() == ModelConstant.REGION_COUNTY
                       && item.getRegionId() == address.getCountyId()) {
                return true;
            } else if (item.getRegionType() == ModelConstant.REGION_XIAOQU
                       && item.getRegionId() == address.getXiaoquId()) {
                return true;
            }
        }
        return false;
    }
    

    //1、已注册用户显示实际的城市区域和全国的特卖
    //2、未注册用户显示的上海市和全国的特卖（上海的默认id为19）
    @Override
    public List<OnSaleAreaItem> queryOnsales(User user, int type,int page) {
        if(user.getProvinceId() != 0){
        if(type == ModelConstant.PRODUCT_FEATURED){
            return onSaleAreaItemRepository.findFeatured(user.getProvinceId(), user.getCityId(), user.getCountyId(), user.getXiaoquId(),System.currentTimeMillis(),new PageRequest(page, 100));
        } else if(type>0){
            return onSaleAreaItemRepository.findByCusProductType(user.getProvinceId(), user.getCityId(), user.getCountyId(), user.getXiaoquId(),System.currentTimeMillis(),type,new PageRequest(page, 100));
        }
        }else{
            if(type == ModelConstant.PRODUCT_FEATURED){
                return onSaleAreaItemRepository.findFeatured(19, user.getCityId(), user.getCountyId(), user.getXiaoquId(),System.currentTimeMillis(),new PageRequest(page, 100));
            } else if(type>0){
                return onSaleAreaItemRepository.findByCusProductType(19, user.getCityId(), user.getCountyId(), user.getXiaoquId(),System.currentTimeMillis(),type,new PageRequest(page, 100));
            }   
        }

        return new ArrayList<OnSaleAreaItem>();
    }
    
    public List<RgroupAreaItem> queryRgroups(User user,int page){
        List<RgroupAreaItem> result ;
        if(user==null||user.getXiaoquId() == 0){
            result = rgroupAreaItemRepository.findAllDefalut(System.currentTimeMillis(), new PageRequest(page, 12));
        } else {
            result = rgroupAreaItemRepository.findAllByUserInfo(user.getProvinceId(), user.getCityId(), user.getCountyId(), user.getXiaoquId(),System.currentTimeMillis(),new PageRequest(page, 12));
        }
        List<RgroupAreaItem> r = filterByRuleId(result);
        return r;
    }
    
    private List<RgroupAreaItem> filterByRuleId(List<RgroupAreaItem> result) {
        List<RgroupAreaItem> r = new ArrayList<RgroupAreaItem>();
        Set<Long> rules = new HashSet<Long>();
        for(RgroupAreaItem i : result){
            if(!rules.contains(i.getRuleId())){
                rules.add(i.getRuleId());
                r.add(i);
            }
        }
        return r;
    }

    /** 
     * @param regionId
     * @param type
     * @return
     * @see com.yumu.hexie.service.common.DistributionService#queryO2OServiceIds(long, long)
     */
    @Override
    public List<Long> queryO2OServiceIds(long regionId, long typeId) {
        return homeDistributionRepository.queryTypeIdsByParent(regionId, typeId);
    }

    /** 
     * @param regionId
     * @param type
     * @return
     * @see com.yumu.hexie.service.common.DistributionService#queryO2OItemIds(long, long)
     */
    @Override
    public List<Long> queryO2OItemIds(long regionId, long typeId) {
        return homeDistributionRepository.queryItemIdsByParent(regionId, typeId);
    }
}
