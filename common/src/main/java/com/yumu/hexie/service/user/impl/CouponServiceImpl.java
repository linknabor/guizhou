package com.yumu.hexie.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.commonsupport.info.ProductRepository;
import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.Collocation;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.OnSaleRule;
import com.yumu.hexie.model.market.saleplan.OnSaleRuleRepository;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.promotion.PromotionConstant;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.promotion.coupon.CouponCombination;
import com.yumu.hexie.model.promotion.coupon.CouponCombinationRepository;
import com.yumu.hexie.model.promotion.coupon.CouponRepository;
import com.yumu.hexie.model.promotion.coupon.CouponRule;
import com.yumu.hexie.model.promotion.coupon.CouponRuleRepository;
import com.yumu.hexie.model.promotion.coupon.CouponSeed;
import com.yumu.hexie.model.promotion.coupon.CouponSeedRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.sales.CollocationService;
import com.yumu.hexie.service.sales.impl.BaseOrderServiceImpl;
import com.yumu.hexie.service.user.CouponService;
import com.yumu.hexie.vo.CouponsSummary;

/**
 * 
 * @author ouyezi
 *
 */
@Transactional
@Service("couponService")
public class CouponServiceImpl implements CouponService {

	protected static final Logger log = LoggerFactory.getLogger(BaseOrderServiceImpl.class);

    @Inject
    private CouponRepository couponRepository;
    @Inject
    private SystemConfigService systemConfigService;
	@Inject
	private CollocationService collocationService;
	
	@Inject
	private CouponRuleRepository couponRuleRepository;
	@Inject
	private CouponSeedRepository couponSeedRepository;
	@Inject
	private ProductRepository productRepository;
	@Inject 
	private UserRepository userRepository;
	@Inject 
	private CouponCombinationRepository couponCombinationRepository;
    @Inject
    private OnSaleRuleRepository onSaleRuleRepository;
    @Inject
    private HomeItemService homeItemService;
	

	public CouponSeed findSeedByStr(String seedStr){
		return couponSeedRepository.findBySeedStr(seedStr);
	}
	
	@Async
	private void updateSeedForRuleUpdate(long seedId){
		CouponSeed oriSeed = couponSeedRepository.findOne(seedId);
		oriSeed.getSeedStr();
		oriSeed.updateTotal(couponRuleRepository.findBySeedId(seedId));
		couponSeedRepository.save(oriSeed);
	}

	@Async
	private void updateSeedAndRuleForCouponReceive(CouponSeed seed,CouponRule rule,Coupon coupon){
		rule.addReceived();
		saveRule(rule);
	}
	@Async
	private void updateSeedAndRuleForCouponUse(Coupon coupon){
		CouponRule rule = couponRuleRepository.findOne(coupon.getRuleId());
		rule.addUsed();
		saveRule(rule);
	}

	@Override
	public CouponSeed createOrderSeed(long userId,ServiceOrder order) {
		log.error("CREATE SEED:" + userId + " -- " +order.getId());
		List<CouponSeed> templates = couponSeedRepository.findBySeedType(ModelConstant.COUPON_SEED_ORDER_BUY_TEMPLATE);
		for(CouponSeed template : templates) {
			log.error("CREATE SEED:templateId:" + template.getId());
			if(template == null||!template.isCanUse()||!canUse(template,order)) {
			} else {
				List<CouponRule> rules = couponRuleRepository.findBySeedId(template.getId());
				if(rules.isEmpty()) {
					continue;
				}

				log.error("CREATE SEED: rules:" + rules.size());
				User user = userRepository.findOne(userId);
				CouponSeed cs = new CouponSeed();
				cs.update(template);
				cs.setUserId(userId);
				cs.setUserImgUrl(user.getHeadimgurl());
				cs.setBizId(order.getId());
				cs.setSeedType(ModelConstant.COUPON_SEED_ORDER_BUY);
				cs.setSeedStr(cs.getGeneratedCouponSeedStr());
				cs = couponSeedRepository.save(cs);
				for(CouponRule rule: rules) {
					CouponRule r = rule.copy(cs.getId());
					couponRuleRepository.save(r);
				}
				updateSeedForRuleUpdate(cs.getId());
				return cs;
			}
		}
		return null;
	}

	//只为某商户订单生产现金券
	private boolean canUse(CouponSeed template, ServiceOrder order) {
		return template.getMerchantId() == null || template.getMerchantId() == 0
				|| template.getMerchantId() == order.getMerchantId();
	}

	//修改规则
	@Override
	public CouponRule saveRule(CouponRule rule) {
		rule = couponRuleRepository.save(rule);
		updateSeedForRuleUpdate(rule.getSeedId());
		return rule;
	}

    @Override
	public Coupon findCouponBySeedAndUser(long seedId,long userId) {
        List<Coupon> coupons = couponRepository.findByUserIdAndSeedId(userId, seedId);
        return (coupons!=null &&coupons.size()>0) ?  coupons.get(0) : null;
	}
	@Override
	public Coupon addCouponFromSeed(CouponSeed seed,User user){
		if(seed == null || !seed.isCanUse()){
			return null;
		}
		Coupon coupon  = findCouponBySeedAndUser(seed.getId(), user.getId());
		if(coupon != null) {
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"你已领取过该现金券！");
		}
		
		List<CouponRule> rules = couponRuleRepository.findBySeedIdAndStatusDuration(
				seed.getId(), ModelConstant.COUPON_RULE_STATUS_AVAILABLE,new Date(), new Date());
		CouponRule chosedRule = null;
		int couponCount = 0;
		for(CouponRule rule : rules) {
			couponCount+=(rule.getTotalCount()-rule.getReceivedCount());
		}
		if(couponCount <= 0) {
			return null;//现金券已领完
		}
		if(seed.getRate() < Math.random()){
			//没有抽中现金券
			coupon = Coupon.emptyCoupon(seed, user);
			coupon = couponRepository.save(coupon);
			return coupon;
		}
		int random = (int)(couponCount*Math.random());
		couponCount = 0;//从0开始算
		for(CouponRule rule : rules) {
			couponCount+=(rule.getTotalCount()-rule.getReceivedCount());
			if(couponCount>=random) {
				chosedRule = rule; 
				break;
			}
		}
		if(chosedRule == null) {
			return null;
		}
		
		coupon = new Coupon(seed, chosedRule,user);
		coupon = couponRepository.save(coupon);
		try {
            log.error("红包发放："+JacksonJsonUtil.beanToJson(coupon));
        } catch (Exception e) {
        }

		//更新统计数据
		updateSeedAndRuleForCouponReceive(seed, chosedRule, coupon);

		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
		status.add(ModelConstant.COUPON_STATUS_LOCKED);
		//status.add(ModelConstant.COUPON_STATUS_USED);
		//status.add(ModelConstant.COUPON_STATUS_TIMEOUT);
		int validNum = couponRepository.countByUserIdAndStatusIn(user.getId(),status);
		user.setCouponCount(validNum);
		userRepository.save(user);
		return coupon;
	}
	@Override
	public Coupon addCoupon4Regist(User user) {
		if(couponRepository.countByUserAndSeedType(user.getId(), ModelConstant.COUPON_SEED_USER_REGIST) > 0){
			return null;
		}
		List<CouponSeed> cs = couponSeedRepository.findBySeedType(ModelConstant.COUPON_SEED_USER_REGIST);
		for(CouponSeed c:cs){
			Coupon coupon = addCouponFromSeed(c, user);
			if(coupon != null) {
	            log.warn("添加注册红包 User["+user.getId()+"]Coupon["+coupon.getId()+"]");
				return coupon;
			}
		}
		return null;
	}

	@Override
	public Coupon addCoupon4Subscribe(User user) {
		
		if(couponRepository.countByUserAndSeedType(user.getId(), ModelConstant.COUPON_SEED_USER_SUBSCRIB) > 0){
			return null;
		}
		List<CouponSeed> cs = couponSeedRepository.findBySeedType(ModelConstant.COUPON_SEED_USER_SUBSCRIB);
		
		log.error("cs size is : " + cs.size());
		
		Coupon coupon = null;
		for(CouponSeed c:cs){
			
			coupon = addCouponFromSeed(c, user);

            log.warn("添加关注红包 User["+user.getId()+"]Coupon["+coupon.getId()+"]");
		}
		//此处返回最后一个红包，调用处需要根据返回值 空/非空 做业务处理
		return coupon;
	}

	@Override
	public Coupon addCouponFromSeed(String seedStr,User user) {
		CouponSeed cs = couponSeedRepository.findBySeedStr(seedStr);
		return addCouponFromSeed(cs, user);
	}
	//不管是不是有效现金券
	@Override
	public List<Coupon> findCouponsFromOrder(long orderId) {
		CouponSeed cs = couponSeedRepository.findBySeedTypeAndBizId(ModelConstant.COUPON_SEED_ORDER_BUY, orderId);
		if(cs != null) {
			return couponRepository.findBySeedIdOrderByIdDesc(cs.getId(), new PageRequest(0,20));
		} else {
			return new ArrayList<Coupon>();
		}
	}
	

	//不管是不是有效现金券
	public List<Coupon> findCouponsBySeedStr(String seedStr){
		CouponSeed cs = couponSeedRepository.findBySeedStr(seedStr);
		if(cs != null) {
			return couponRepository.findBySeedIdOrderByIdDesc(cs.getId(), new PageRequest(0,20));
		} else {
			return new ArrayList<Coupon>();
		}
	}
	@Override
	public List<Coupon> findAvaibleCoupon(long userId,Cart cart) {
		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
		List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(userId,status, new PageRequest(0,200));
		
		List<Coupon> result = new ArrayList<Coupon>();
		for(Coupon coupon : coupons) {
			if(isAvaible(cart, coupon)){
				result.add(coupon);
			}
		}
		return result;
	}

    @Override
	public List<Coupon> findAvaibleCoupon(long userId,HomeCart cart) {
        if(cart == null) {
            return new ArrayList<Coupon>();
        }
        List<Integer> status = new ArrayList<Integer>();
        status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
        List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(userId,status, new PageRequest(0,200));
        
        List<Coupon> result = new ArrayList<Coupon>();
        for(Coupon coupon : coupons) {
            if(isAvaible(cart, coupon)){
                result.add(coupon);
            }
        }
        return result;
	}
    @Override//FIXME 红包使用范围需要限定更小值
    public List<Coupon> findAvaibleCoupon4ServiceType(long userId,long homeServiceType,Long parentType, Long itemId){
        List<Integer> status = new ArrayList<Integer>();
        status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
        List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(userId,status, new PageRequest(0,200));
        
        List<Coupon> result = new ArrayList<Coupon>();
        for(Coupon coupon : coupons) {
            if(isAvaible(PromotionConstant.COUPON_ITEM_TYPE_SERVICE, homeServiceType,
                parentType, itemId, null, coupon, false)){
                result.add(coupon);
            }
        }
        return result;
    }
	@Override
	public List<Coupon> findAvaibleCoupon(long userId,SalePlan salePlan){
		List<Coupon> result = new ArrayList<Coupon>();
		if(salePlan == null) {
			return result;
		}
		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
		List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(userId,status, new PageRequest(0,200));
		
		Integer onsaleType = !(salePlan instanceof OnSaleRule) ? null : ((OnSaleRule)salePlan).getProductType();
		if (onsaleType==null) {
			onsaleType = 0;
		}
		
		Long orderType = 3l;
		if (ModelConstant.ORDER_TYPE_RGROUP==salePlan.getSalePlanType()) {
			orderType = Long.valueOf(ModelConstant.ORDER_TYPE_RGROUP);
		}
		
		for(Coupon coupon : coupons) {
			
			if(isAvaible(PromotionConstant.COUPON_ITEM_TYPE_MARKET, 
					orderType, new Long(onsaleType), salePlan.getProductId(), null, coupon,false)){
				result.add(coupon);
			}
		}
		return result;
	}

	//服务-洗衣-服务项父类型-服务项 商户ID
	public boolean isAvaible(int itemType, Long subItemType, Long serviceType, Long productId, 
	                          Float amount, Coupon coupon, boolean locked) {
	    if(coupon == null) {
	        return false;
	    }
	    log.warn("Check Coupon["+itemType+"]["+subItemType+"]["+serviceType+"]["+productId
	        +"]["+amount+"]["+coupon.getId()+"]["+locked+"]");
	    //状态验证
        if(!locked && coupon.getStatus() != ModelConstant.COUPON_STATUS_AVAILABLE){
            log.warn("不可用（状态验证）");
            return false;
        }
        if(locked && coupon.getStatus() != ModelConstant.COUPON_STATUS_LOCKED 
                && coupon.getStatus() != ModelConstant.COUPON_STATUS_AVAILABLE){
            log.warn("不可用（锁定状态）");
            return false;
        }
        if(amount != null && coupon.getUsageCondition()-0.009 > amount) {
            log.warn("不可用（金额不支持）");
            return false;
        }

        if(coupon.isAvailableForAll()){
            log.warn("可以用（面向全部可用）");
            return true;
        }
        
	    //系统参数 FIXME
	    if(itemType != PromotionConstant.COUPON_ITEM_TYPE_ALL
                && productId != null && productId != 0) {
            if(systemConfigService.getUnCouponItems().contains(itemType+"-"+productId)){
                log.warn("不可以用（系统配置）");
                return false;
            }
        }

//	    String typeStr = itemType + "-" + subItemType + "-" + serviceType + "-" + productId;
        if(StringUtil.isNotEmpty(coupon.getPassTypePrefix())) {
            
        	int cItemType = coupon.getItemType();
        	long cSubItemType = coupon.getSubItemType();
        	long cServiceType = coupon.getServiceType();
        	long cProductId = coupon.getProductId();
        	
        	if (cItemType!=itemType) {
        		log.warn("itemType 不可用");
        		return false;
			}
        	
        	if (cSubItemType!=0 && cSubItemType!=subItemType) {
        		log.warn("subItemType 不可用");
        		return false;
			}
        	
        	if (cServiceType!=0 && cServiceType!=serviceType) {
        		log.warn("serviceType 不可用");
        		return false;
			}
        	
        	if (cProductId!=0 && cProductId!=productId) {
        		log.warn("productId 不可用");
        		return false;
			}
        	
        }
	    
        if(StringUtil.isNotEmpty(coupon.getUnPassTypePrefix())) {
        	
            //反向验证
        	int uItemType = coupon.getuItemType();
        	long uSubItemType = coupon.getuSubItemType();
        	long uServiceType = coupon.getuServiceType();
        	long uProductId = coupon.getuProductId();
        	
        	if (uItemType == itemType) {
        		
        		if (uSubItemType == 0) {
        			
        			if (uProductId==productId) {
        				log.warn("productId:"+uProductId+"不可用");
                		return false;
					}else if(uProductId==0) {
						log.warn("subItemType:"+uSubItemType+"不可用");
	            		return false;
					}
        			
				}
        		
        		if (uSubItemType == subItemType) {
        			
        			if (uServiceType == 0) {
        				
        				if (uProductId==productId) {
            				log.warn("productId:"+uProductId+"不可用");
                    		return false;
    					}else if(uProductId==0) {
    						log.warn("serviceType:"+uServiceType+"不可用");
    	            		return false;
    					}
        				
    				}
        			
        			if (uServiceType == serviceType) {
						
        				if (uProductId==productId) {
            				log.warn("productId:"+uProductId+"不可用");
                    		return false;
    					}else if(uProductId==0) {
    						log.warn("serviceType:"+uServiceType+"不可用");
    	            		return false;
    					}
        				
					}
        			
        			
				}
        		
        		
			}
        	
        	
        }

        /*可用商户校验*/
		if(coupon.getMerchantId() != null && coupon.getMerchantId() != 0 ){
		    Long merchantId = getMerchatId(new Long(itemType), serviceType, productId);
		    log.error("merchantId:" + merchantId);
		    if(merchantId == null || merchantId != coupon.getMerchantId()) {
                log.warn("不可用（商户正向验证）");
                return false;
		    }
		}
		
		/*不可用商户校验*/
		if(coupon.getuMerchantId() != null && coupon.getuMerchantId() != 0 ){
		    Long merchantId = getMerchatId(new Long(itemType), serviceType, productId);
		    log.error("merchantId:" + merchantId);
		    if(merchantId == coupon.getuMerchantId()) {
                log.warn("不可用（商户逆向验证）");
                return false;
		    }
		}
		
        log.warn("可以用（全部通过）");
		return true;
	}

	//itemType:1, serviceType:-0, productId:10
	public Long getMerchatId(Long mainType, Long subType, Long itemId) {
		
		log.error("mainType:"+mainType+",subType:"+subType+",itemId:"+itemId);
        if(new Long(PromotionConstant.COUPON_ITEM_TYPE_MARKET).equals(mainType) && itemId != null && !itemId.equals(0)){
            Product product = productRepository.findOne(itemId);
            return product == null ? 0 : product.getMerchantId();
        }
        if(new Long(PromotionConstant.COUPON_ITEM_TYPE_SERVICE).equals(mainType) && subType != null && !subType.equals(0)) {
            ServiceType type = homeItemService.queryTypeById(subType);
            return type == null ? 0 : type.getMerchantId();
        }
        return 0l;
	}

	@Override//FIXME 如果特卖类型是0，如何处理
	public boolean isAvaible(Cart cart, Coupon coupon) {
	    Float amount = null;
        if(cart.getCollocationId()>0) {
            Collocation coll = collocationService.findOne(cart.getCollocationId());
	        amount = coll.getSatisfyAmount() <= cart.getTotalAmount() ? cart.getTotalAmount() - coll.getDiscountAmount() : cart.getTotalAmount();
        }
	    for(long productId : cart.getProductIds()) {
	        if(isAvaible(PromotionConstant.COUPON_ITEM_TYPE_MARKET,new Long(ModelConstant.ORDER_TYPE_ONSALE), 0l, 
	            productId, 
	            amount, coupon,false)){
	            return true;
	        }
	    }
		return false;
	}

    @Override
    public boolean isAvaible(HomeCart cart, Coupon coupon) {
        return isAvaible(PromotionConstant.COUPON_ITEM_TYPE_SERVICE, cart.getBaseType(),
            cart.getItemType(),  cart.getItems().get(0).getServiceId(), cart.getAmount().floatValue(), coupon, false);
    }
	@Override
    public boolean isAvaible(String feePrice, Coupon coupon) {
	    if(coupon.getItemType() != PromotionConstant.COUPON_ITEM_TYPE_ALL 
                && coupon.getItemType() != PromotionConstant.COUPON_ITEM_TYPE_WUYE){
            return false;
        }
    	if(coupon.getStatus() != ModelConstant.COUPON_STATUS_AVAILABLE){
    		return false;
    	}
    	if (Float.parseFloat(feePrice) < 0.01){
    		return false;
    	}
    	if(coupon.isAvailableForAll()){
    		return true;
    	}
    	return true;
    }

    @Override
    public boolean isAvaible(ServiceOrder order, Coupon coupon, boolean withLocked) {
    	if(withLocked) {
    		if(coupon.getOrderId() != 0&& coupon.getOrderId() != order.getId()) {
    			return false;
    		}
    	}
    	if(order.getItems() != null) {
    	    for(OrderItem item : order.getItems()) {
    	        Integer onsaleType = 0;
    	        if(item.getOrderType() == ModelConstant.ORDER_TYPE_ONSALE) {
    	            OnSaleRule plan = onSaleRuleRepository.findOne(item.getRuleId());
    	            onsaleType = plan.getProductType();
    	        }
                if(isAvaible(PromotionConstant.COUPON_ITEM_TYPE_MARKET, new Long(item.getOrderType()),new Long(onsaleType),item.getProductId(), 
                     order.getTotalAmount(), coupon,withLocked)){
                    return true;
                }
            }
    	}
        
    	return false;
    }

    @Override
	public List<Coupon> findAvaibleCoupon(ServiceOrder order) {
		List<Integer> status = new ArrayList<Integer>();
		status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
		List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(order.getUserId(),status, new PageRequest(0,200));
		
		List<Coupon> result = new ArrayList<Coupon>();
		for(Coupon coupon : coupons) {
			if(isAvaible(order, coupon,false)){
				result.add(coupon);
			}
		}
		return result;
	}
	
	private static final int COUPON_PAGE_SIZE = 20;
	@Override
	public List<Coupon> findInvalidCoupons(long userId,int page){
		List<Integer> invalidStatus = new ArrayList<Integer>();
		invalidStatus.add(ModelConstant.COUPON_STATUS_USED);
		invalidStatus.add(ModelConstant.COUPON_STATUS_TIMEOUT);
		return couponRepository.findByUserIdAndStatusIn(userId, invalidStatus, new PageRequest(page, COUPON_PAGE_SIZE));
 	}
	
	@Override
    public CouponsSummary findCouponSummary(long userId){
        CouponsSummary r = new CouponsSummary();
        List<Integer> validStatus = new ArrayList<Integer>();
        validStatus.add(ModelConstant.COUPON_STATUS_AVAILABLE);
        validStatus.add(ModelConstant.COUPON_STATUS_LOCKED);
        int validNum = couponRepository.countByUserIdAndStatusIn(userId,validStatus);
        
        List<Integer> invalidStatus = new ArrayList<Integer>();
        invalidStatus.add(ModelConstant.COUPON_STATUS_USED);
        invalidStatus.add(ModelConstant.COUPON_STATUS_TIMEOUT);
        int invalidNum = couponRepository.countByUserIdAndStatusIn(userId,invalidStatus);
        
        r.setInvalidCount(invalidNum);
        r.setValidCount(validNum);
        r.setValidCoupons(couponRepository.findByUserIdAndStatusIn(userId, validStatus, new PageRequest(0, 40)));
        r.setInvalidCoupons(couponRepository.findByUserIdAndStatusIn(userId, invalidStatus, new PageRequest(0, 2)));;
        
        return r;
    }
	
	@Override
	public void lock(ServiceOrder order, Coupon coupon){

        log.warn("lock红包["+order.getId()+"]Coupon["+coupon.getId()+"]");
		if(!isAvaible(order, coupon, false)){
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券不可用于本订单");
		}
		coupon.lock(order.getId());
		couponRepository.save(coupon);
	}
    @Override
	public boolean lock(BaseO2OService bill, Coupon coupon){
        log.warn("lock红包["+bill.getId()+"]Coupon["+bill.getId()+"]");
        if(!isAvaible(PromotionConstant.COUPON_ITEM_TYPE_SERVICE,
            new Long(bill.getOrderType()), bill.getItemType(), bill.getItemId(),bill.getAmount().floatValue(), coupon, true)){
            return false;
        }
        coupon.lock(bill.getId());
        couponRepository.save(coupon);
        return true;
	}
	@Override
    public void comsume(ServiceOrder order) {
        log.warn("comsume红包["+order.getId()+"]");
        if(order.getCouponId() == null || order.getCouponId() == 0){
            return;
        }
        Coupon coupon = couponRepository.findOne(order.getCouponId());
        if(!isAvaible(order, coupon,true)){
            throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券不可用于本订单");
        }
        log.warn("comsume红包before["+order.getId()+"]Coupon["+coupon.getId()+"]");
        coupon.cousume(order.getId());
        couponRepository.save(coupon);
        
        User user = userRepository.findOne(coupon.getUserId());
        if(user.getCouponCount()>0) {
            user.setCouponCount(user.getCouponCount()-1);
            userRepository.save(user);
        }

        log.warn("comsume红包END["+order.getId()+"]Coupon["+coupon.getId()+"]");
        updateSeedAndRuleForCouponUse(coupon);
    }
	
	@Override
	public boolean comsume(BaseO2OService bill) {
	    if(bill.getCouponId() == null || bill.getCouponId() == 0){
            return true;
        }
        log.warn("comsume红包Bill[BEG]["+bill.getId()+"]Coupon["+bill.getId()+"]");
	    Coupon coupon = couponRepository.findOne(bill.getCouponId());
		if(!isAvaible(PromotionConstant.COUPON_ITEM_TYPE_SERVICE,
            new Long(bill.getOrderType()), bill.getItemType(), bill.getItemId(), bill.getAmount().floatValue(), coupon, true)){
			//throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券不可用于本订单");
		    return false;
		}
        log.warn("comsume红包Bill[END]["+bill.getId()+"]Coupon["+bill.getId()+"]");
		coupon.cousume(bill.getId());
		couponRepository.save(coupon);
		
		User user = userRepository.findOne(coupon.getUserId());
		if(user.getCouponCount()>0) {
			user.setCouponCount(user.getCouponCount()-1);
			userRepository.save(user);
		}
		
		updateSeedAndRuleForCouponUse(coupon);
		return true;
	}

	@Override
	public void unlock(Long couponId){
        log.warn("unlock红包Coupon["+couponId+"]");
	    if(couponId ==null || couponId == 0) {
	        return;
	    }
	    Coupon coupon = couponRepository.findOne(couponId);
	    if(coupon == null) {
	        return;
	    }
		if(coupon.getStatus() == ModelConstant.COUPON_STATUS_TIMEOUT
				|| coupon.getStatus() == ModelConstant.COUPON_STATUS_USED) {
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券已使用，无法解锁");
		}
		coupon.unlock();
		couponRepository.save(coupon);

        log.warn("unlock红包Bill[BEG]Coupon["+coupon.getId()+"]");
	}
	
	@Override
	public Coupon findOne(long id) {
	    return couponRepository.findOne(id);
	}
	@Override
	public void timeout(Coupon coupon){
		if(coupon.getStatus() == ModelConstant.COUPON_STATUS_LOCKED
				|| coupon.getStatus() == ModelConstant.COUPON_STATUS_USED) {
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券已被使用或正在被使用，无法超时失效");
		}
		log.error("timeout红包已过期["+coupon.getId()+"]["+coupon.getTitle()+"]");
		coupon.timeout();
		couponRepository.save(coupon);
		
		User user = userRepository.findOne(coupon.getUserId());
		if(user.getCouponCount()>0) {
			user.setCouponCount(user.getCouponCount()-1);
			userRepository.save(user);
		}
	}
	
	public List<Coupon> findTop100TimeoutCoupon(){
		return couponRepository.findTimeoutByPage(DateUtil.getDateFromString(DateUtil.dtFormat(new Date())), new PageRequest(0, 100));
	}

	@Override
	public List<Coupon> findAvaibleCouponForWuye(long userId) {

		List<Coupon> result = new ArrayList<Coupon>();
        List<Integer> status = new ArrayList<Integer>();
        status.add(ModelConstant.COUPON_STATUS_AVAILABLE);
        List<Coupon> coupons = couponRepository.findByUserIdAndStatusIn(userId,status, new PageRequest(0,200));
        for(Coupon coupon : coupons) {
            if(isAvaible(PromotionConstant.COUPON_ITEM_TYPE_WUYE, 0l, 0l, 
                0l, null, coupon,false)){
                result.add(coupon);
            }
        }
        return result;
    
	}

	@Override
	public List<CouponCombination> findCouponCombination(int combinationType) {
		
		return couponCombinationRepository.findByCombinationType(combinationType);
	}

	@Override
	public void comsume(String feePrice, long couponId) {

		Coupon coupon = couponRepository.findOne(couponId);

        log.warn("comsume红包Bill[BEG]feePrice["+feePrice+"]["+couponId+"]");
		if(!isAvaible(feePrice, coupon)){
			throw new BizValidateException(ModelConstant.EXCEPTION_BIZ_TYPE_COUPON,coupon.getId(),"该现金券不可用于本订单");
		}
		log.error("consume coupon:" + coupon.getId());
		coupon.cousume(0);
		couponRepository.save(coupon);

        log.warn("comsume红包Bill[END]feePrice["+feePrice+"]["+couponId+"]");
		User user = userRepository.findOne(coupon.getUserId());
		if(user.getCouponCount()>0) {
			user.setCouponCount(user.getCouponCount()-1);
			userRepository.save(user);
		}
		
		updateSeedAndRuleForCouponUse(coupon);
		
	}

	@Override
	public List<Coupon> findTimeoutCouponByDate(Date fromDate, Date toDate) {

		return couponRepository.findTimeoutCouponByDate(fromDate, toDate, new PageRequest(0, 10000));
	
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(new Long(0) == 0);
		
	}
	
}
