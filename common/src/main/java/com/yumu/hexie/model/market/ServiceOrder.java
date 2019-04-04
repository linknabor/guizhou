package com.yumu.hexie.model.market;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.OrderNoUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.vo.CreateOrderReq;
import com.yumu.hexie.vo.SingleItemOrder;

//订单
@Entity
public class ServiceOrder  extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	
	/** 商品相关 **/
	//主商品ID
	private int orderType;//0.拼单单 1.单个订单 2.预约单 3. 特卖单 4.团购单

	private long productId;
	private long groupRuleId;
	private long userId;
	//201501011221xxxx
	private String orderNo;
	private Float totalAmount;//总价 =折扣+支付金额+运费
	private Float discountAmount;
	private float price;//需要支付的金额（如果存在多个商品，则根据item计算，若只有单个商品，则订单内计算）
	/** 订单基础信息 */
	private int count;//个数
	private float shipFee;//运费
	private String seedStr;//订单对应的现金券
	
	private int status = ModelConstant.ORDER_STATUS_INIT;;//0. 创建完成 1. 已支付 2. 已用户取消 3. 待退款 4. 已退款  5. 已使用/已发货 6.已签收 7. 已后台取消 8. 商户取消

	private int asyncStatus;//同步给商户 0 未同步，1已同步
	private int pingjiaStatus = ModelConstant.ORDER_PINGJIA_TYPE_N;//0 未评价 1 已评价
	
	//现金券
	private Long couponId;
	private Float couponAmount;
	
	/**用户信息**/
	private String openId;
	
	/**地址信息**/
	private long serviceAddressId;//FIXME 服务地址
	private int receiveTimeType;//周一至周五、周六周日、全周
	private String memo;
	
	private String address;
	private String tel;
	private String receiverName;
	private double lat;
	private double lng;
	private long xiaoquId;
	
	
	/**团购状态*/
	private int groupStatus = ModelConstant.GROUP_STAUS_GROUPING;//拼单状态
	/**团购状态*/

	//产品冗余信息
	private long merchantId;
	private String productName;
	private String productPic;
	private String productThumbPic;
	private String groupRuleName;
	
	private String gongzhonghao = "上海东湖e家园";
	
	
	/**物流信息**/
	private int logisticType;//0商户派送 1用户自提 2第三方配送
	private String logisticName;
	private String logisticNo;
	private String logisticCode;
	/**退货信息**/
	private String returnMemo;
	private int refundType;

	private long closeTime;//超时支付时间，超出则取消订单
	private long updateDate;
	private Date payDate;
	private Date refundDate;
	private Date returnDate;
	private Date confirmDate;
	private Date sendDate;
	private Date signDate;
	private Date cancelDate;
	private Date closeDate;
	private Date asyncDate;


	@JsonIgnore
    @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH}, mappedBy = "serviceOrder")
    @Fetch(FetchMode.SUBSELECT)
	private List<OrderItem> items = new ArrayList<OrderItem>();

	public ServiceOrder(){}
	public ServiceOrder(SingleItemOrder sOrder) {
		orderNo = OrderNoUtil.generateServiceOrderNo();
		this.memo = sOrder.getMemo();
		this.count = sOrder.getCount();
		this.orderType = sOrder.getOrderType();
		this.receiveTimeType = sOrder.getReceiveTimeType();
		this.groupRuleId = sOrder.getRuleId();
		this.serviceAddressId = sOrder.getServiceAddressId();
		this.userId = sOrder.getUserId();
		this.openId = sOrder.getOpenId();
		this.couponId = sOrder.getCouponId();
		
		OrderItem item = new OrderItem();
		item.setRuleId(sOrder.getRuleId());
		item.setCount(sOrder.getCount());
		items.add(item);
	}
	//维修单订单
	public ServiceOrder(RepairOrder sOrder,float amount) {
        orderNo = OrderNoUtil.generateServiceOrderNo();
        this.memo = sOrder.getMemo();
        this.count = 1;
        this.orderType = ModelConstant.ORDER_TYPE_REPAIR;
        this.receiveTimeType = 1;
        this.groupRuleId = 1;
        this.serviceAddressId = sOrder.getAddressId();
        this.userId = sOrder.getUserId();
        this.openId = sOrder.getOpenId();
        this.couponId = 0l;
        this.productName = sOrder.getProjectName();
        
        OrderItem item = new OrderItem();
        item.setRuleId(1l);
        item.setCount(1);
        item.setOrderType(ModelConstant.ORDER_TYPE_REPAIR);
        item.setPrice(amount);
        item.setAmount(amount);
        item.setRuleName("维修项目");

        item.setProductId(1l);
        item.setMerchantId(1l);
        item.setProductName("维修项目");
        item.setOriPrice(0f);
        items.add(item);
    }
	
	
	public ServiceOrder(CreateOrderReq req,Cart cart,long userId,String openId) {
		orderNo = OrderNoUtil.generateServiceOrderNo();
		this.memo = req.getMemo();
		this.receiveTimeType = req.getReceiveTimeType();
		this.serviceAddressId = req.getServiceAddressId();
		this.couponId = req.getCouponId();

		this.orderType = cart.getOrderType();
		
		this.userId = userId;
		this.openId = openId;
		this.items = cart.getItems();
	}
	@JsonIgnore
	@Transient
	public long getCollocationId(){
		Long result = 0l;
		if(items != null && items.size() > 0) {
			for(OrderItem item : items) {
				if(result!= 0 && item.getCollocationId() != result) {
					return 0l;
				} else if(item != null&&item.getCollocationId()!=null){
					result = item.getCollocationId() ;
				}
			}
		}
		return result;
	}
	@Transient
	public String getCreateDateStr(){
		return DateUtil.dtFormat(new Date(getCreateDate()), "MM-dd HH:mm");
	}
	private static Map<Integer,String> STATUSMAP = new HashMap<Integer,String>();
	static{
		STATUSMAP.put(ModelConstant.ORDER_STATUS_INIT,"待付款");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_PAYED,"已支付");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_CANCEL ,"已取消");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_APPLYREFUND,"退款中");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_REFUNDING ,"退款中");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_SENDED,"已发货");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_RECEIVED,"已签收");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_CANCEL_BACKEND,"已取消");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT,"已取消");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_CONFIRM ,"配货中");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_RETURNED,"已退货");    
		STATUSMAP.put(ModelConstant.ORDER_STATUS_REFUNDED  ,"已退款");        

	}
	@Transient
	public String getStatusStr(){
		return STATUSMAP.get(status);
	}
	@Transient
	public static String getStatusStr(int status) {
		return STATUSMAP.get(status);
	}
	@Transient
	public void fillProductInfo(Product product){
		setProductId(product.getId());
		setMerchantId(product.getMerchantId());
		if(items != null) {
			setProductName(product.getName()); //改为规则的名字
			if(items.size() > 1){
				setProductName(getProductName()+"等"+items.size()+"种商品");
			}
		}
		setProductPic(product.getMainPicture());
		setProductThumbPic(product.getSmallPicture());
	}
	@Transient
	public void fillAddressInfo(Address address) {
		setAddress(address.getRegionStr() + address.getDetailAddress());
		setTel(address.getTel());
		setXiaoquId(address.getXiaoquId());
		setReceiverName(address.getReceiveName());
		setLat(address.getLatitude());
		setLng(address.getLongitude());

	}

	//FIXME
	/**
	 * 必须在价格已经正确设置以后
	 * @param coupon
	 */
	public void configCoupon(Coupon coupon) {
		if(coupon == null) {
			setCouponId(null);
			setCouponAmount(null);
		}
		setCouponId(coupon.getId());
		setCouponAmount(coupon.getAmount());
		if(coupon.getAmount() > getPrice()) {
			setPrice(0.01f);
		} else {
			setPrice(getPrice() - coupon.getAmount());
		}
	}

	@Transient
	public void payed(){
		setStatus(ModelConstant.ORDER_STATUS_PAYED);
		setPayDate(new Date());
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void cancel(){
		setCancelDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_CANCEL);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void confirm() {
		setConfirmDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_CONFIRM);
		setGroupStatus(ModelConstant.GROUP_STAUS_FINISH);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void send(int logisticType,	String logisticName, String logisticNo){
		setSendDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_SENDED);
		setLogisticType(logisticType);
		setLogisticName(logisticName);
		setLogisticNo(logisticNo);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void sign(){
		setSignDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_RECEIVED);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void returnGood(String memo) {
		setReturnDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_RETURNED);
		setReturnMemo(memo);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void refunding(boolean groupCancel) {
		setRefundDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_REFUNDING);
		setRefundType(groupCancel ? ModelConstant.REFUND_REASON_GROUP_CANCEL:ModelConstant.REFUND_REASON_GROUP_BACKEND);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void refunding() {
		setRefundDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_REFUNDING);
		setUpdateDate(System.currentTimeMillis());
	}
	@Transient
	public void close() {
		setCloseDate(new Date());
		setStatus(ModelConstant.ORDER_STATUS_CANCEL);//FIXME 该处状态需要重新调整
		setUpdateDate(System.currentTimeMillis());
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public float getPrice() {
		BigDecimal b = new BigDecimal(price);  
		price = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		if(price < 0.01f) {
            price = 0.01f;
        }
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getServiceAddressId() {
		return serviceAddressId;
	}

	public void setServiceAddressId(long serviceAddressId) {
		this.serviceAddressId = serviceAddressId;
	}
	public String getProductPic() {
		return productPic;
	}
	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}
	public String getProductThumbPic() {
		return productThumbPic;
	}
	public void setProductThumbPic(String productThumbPic) {
		this.productThumbPic = productThumbPic;
	}
	public long getGroupRuleId() {
		return groupRuleId;
	}
	public void setGroupRuleId(long groupRuleId) {
		this.groupRuleId = groupRuleId;
	}
	public String getGroupRuleName() {
		return groupRuleName;
	}
	public void setGroupRuleName(String groupRuleName) {
		this.groupRuleName = groupRuleName;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public int getLogisticType() {
		return logisticType;
	}


	public void setLogisticType(int logisticType) {
		this.logisticType = logisticType;
	}


	public String getLogisticName() {
		return logisticName;
	}


	public void setLogisticName(String logisticName) {
		this.logisticName = logisticName;
	}


	public String getLogisticNo() {
		return logisticNo;
	}


	public void setLogisticNo(String logisticNo) {
		this.logisticNo = logisticNo;
	}



	public long getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}


	public long getCloseTime() {
		return closeTime;
	}


	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}


	public int getReceiveTimeType() {
		return receiveTimeType;
	}


	public void setReceiveTimeType(int receiveTimeType) {
		this.receiveTimeType = receiveTimeType;
	}


	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public long getMerchantId() {
		return merchantId;
	}


	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	public int getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(int groupStatus) {
		this.groupStatus = groupStatus;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getReturnMemo() {
		return returnMemo;
	}
	public void setReturnMemo(String returnMemo) {
		this.returnMemo = returnMemo;
	}

	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public int getAsyncStatus() {
		return asyncStatus;
	}

	public void setAsyncStatus(int asyncStatus) {
		this.asyncStatus = asyncStatus;
	}

	public Date getAsyncDate() {
		return asyncDate;
	}

	public void setAsyncDate(Date asyncDate) {
		this.asyncDate = asyncDate;
	}

	public int getPingjiaStatus() {
		return pingjiaStatus;
	}

	public void setPingjiaStatus(int pingjiaStatus) {
		this.pingjiaStatus = pingjiaStatus;
	}

	public long getXiaoquId() {
		return xiaoquId;
	}

	public void setXiaoquId(long xiaoquId) {
		this.xiaoquId = xiaoquId;
	}

	public String getGongzhonghao() {
		return gongzhonghao;
	}

	public void setGongzhonghao(String gongzhonghao) {
		this.gongzhonghao = gongzhonghao;
	}

	public float getShipFee() {
		return shipFee;
	}
	public void setShipFee(float shipFee) {
		this.shipFee = shipFee;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Float getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Float couponAmount) {
		this.couponAmount = couponAmount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	@Transient
	@JsonIgnore
	public List<Long> getProductIds(){
		List<Long> ids = new ArrayList<Long>();
		if(items == null) {
			return ids;
		}
		for(OrderItem item : items) {
			ids.add(item.getProductId());
		}
		return ids;
	}

	@Transient
	@JsonIgnore
	public List<Long> getMerchantIds(){
		List<Long> ids = new ArrayList<Long>();
		if(items == null) {
			return ids;
		}
		for(OrderItem item : items) {
			ids.add(item.getMerchantId());
		}
		return ids;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Float getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Float discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getSeedStr() {
		return seedStr;
	}
	public void setSeedStr(String seedStr) {
		this.seedStr = seedStr;
	}
	public String getLogisticCode() {
		return logisticCode;
	}
	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}
	
	public boolean payable() {
        return ModelConstant.ORDER_STATUS_INIT==getStatus();
    }

	public boolean cancelable() {
        return ModelConstant.ORDER_STATUS_INIT==getStatus();
    }

    public boolean asyncable() {
        return ModelConstant.ORDER_STATUS_CONFIRM==getStatus();
    }
    public boolean sendable() {
        return ModelConstant.ORDER_STATUS_CONFIRM==getStatus();
    }

    public boolean signable() {
        return (ModelConstant.ORDER_STATUS_SENDED == getStatus()
            || ModelConstant.ORDER_STATUS_CONFIRM == getStatus()
            || ModelConstant.ORDER_STATUS_CONFIRM == getStatus());
    }

    public boolean returnable() {
        return ModelConstant.ORDER_STATUS_RECEIVED == getStatus();
    }
    

    
    public boolean refundable() {
        return (ModelConstant.ORDER_STATUS_CONFIRM == getStatus()
                || ModelConstant.ORDER_STATUS_RETURNED == getStatus()
                | ModelConstant.ORDER_STATUS_PAYED == getStatus());
    }
}
