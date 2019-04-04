package com.yumu.hexie.service.user;

import java.util.Date;
import java.util.List;

import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.basemodel.BaseO2OService;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.promotion.coupon.CouponCombination;
import com.yumu.hexie.model.promotion.coupon.CouponRule;
import com.yumu.hexie.model.promotion.coupon.CouponSeed;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.vo.CouponsSummary;

/**
 * 现金券服务
 * 
 * @author ouyezi
 */
public interface CouponService {

	public CouponSeed createOrderSeed(long userId,ServiceOrder order);
	
	public CouponRule saveRule(CouponRule rule);
	
	
	public Coupon addCoupon4Regist(User user);
	public Coupon addCoupon4Subscribe(User user);
	public Coupon addCouponFromSeed(String seedStr,User user);
	public Coupon addCouponFromSeed(CouponSeed seed,User user);

	public List<Coupon> findCouponsFromOrder(long orderId);
	public Coupon findCouponBySeedAndUser(long seedId,long userId);
	public List<Coupon> findCouponsBySeedStr(String seedStr);

    public boolean isAvaible(Cart cart,Coupon coupon);
    public boolean isAvaible(HomeCart cart,Coupon coupon);
    public boolean isAvaible(ServiceOrder order,Coupon coupon, boolean withLocked);
    public boolean isAvaible(String feePrice, Coupon coupon);
    public boolean isAvaible(int itemType, Long subItemType, Long serviceType, Long productId, 
                             Float amount, Coupon coupon, boolean locked);
    
	public List<Coupon> findAvaibleCoupon(ServiceOrder order);
	public List<Coupon> findAvaibleCoupon(long userId,SalePlan salePlan);
    public List<Coupon> findAvaibleCoupon(long userId,Cart cart);
    public List<Coupon> findAvaibleCoupon(long userId,HomeCart cart);
    //查看服务类型是否支持红包
    public List<Coupon> findAvaibleCoupon4ServiceType(long userId,long homeServiceType,Long parentType, Long itemId);
	public List<Coupon> findAvaibleCouponForWuye(long userId);
	
	public List<Coupon> findInvalidCoupons(long userId,int page);
	public CouponsSummary findCouponSummary(long userId);
	public CouponSeed findSeedByStr(String seedStr);

    public void lock(ServiceOrder order,Coupon coupon);//锁定现金券
    public boolean lock(BaseO2OService bill, Coupon coupon);//使用现金券
    public void comsume(ServiceOrder order);//使用现金券
    public boolean comsume(BaseO2OService bill);//使用现金券
	public void comsume(String feePrice, long couponId);
	public void unlock(Long couponId);//解锁现金券
	public void timeout(Coupon coupon);//现金券过期
	
	public Coupon findOne(long couponId);
	public List<Coupon> findTop100TimeoutCoupon();
	public List<Coupon> findTimeoutCouponByDate(Date fromDate, Date toDate);
	
	public List<CouponCombination> findCouponCombination(int combinationType);	//获取现金券组合
}
