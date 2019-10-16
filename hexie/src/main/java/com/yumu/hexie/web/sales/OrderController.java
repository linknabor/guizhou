package com.yumu.hexie.web.sales;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.commonsupport.comment.Comment;
import com.yumu.hexie.model.commonsupport.info.Product;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.market.ServiceOrderRepository;
import com.yumu.hexie.model.market.saleplan.SalePlan;
import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.Address;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.sales.BaseOrderService;
import com.yumu.hexie.service.sales.ProductService;
import com.yumu.hexie.service.sales.RgroupService;
import com.yumu.hexie.service.sales.SalePlanService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.vo.CreateOrderReq;
import com.yumu.hexie.vo.RgroupOrder;
import com.yumu.hexie.vo.SingleItemOrder;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.sales.resp.BuyInfoVO;

@Controller(value = "orderController")
public class OrderController extends BaseController{
    @Inject
    private ServiceOrderRepository serviceOrderRepository;
    @Inject
    private ProductService productService;
    @Inject
    private SalePlanService salePlanService;
    @Inject
    private BaseOrderService baseOrderService;
    @Inject
    private RedisRepository redisRepository;
    @Inject
    private RgroupService rgroupService;
	@Inject
	private AddressService addressService;
	

	@RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Product> getProduct(@PathVariable long productId) throws Exception {
		return new BaseResult<Product>().success(productService.getProduct(productId));
    }
    
	@RequestMapping(value = "/orders/status/{statusType}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<ServiceOrder>> orders(@ModelAttribute(Constants.USER)User user,@PathVariable String statusType) throws Exception {
		//订单中排除服务单
		List<Integer> types = new ArrayList<Integer>();
		types.add(ModelConstant.ORDER_TYPE_GROUP);
		types.add(ModelConstant.ORDER_TYPE_GROUP_SINGLE);
		types.add(ModelConstant.ORDER_TYPE_ONSALE);
		types.add(ModelConstant.ORDER_TYPE_RGROUP);

		List<Integer> status = new ArrayList<Integer>();
		if("NEEDPAY".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
		}else if("NEEDRECEIVE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
		}else if("CANCELD".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
		}else if("PAYED".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_PAYED);
		}else if("PREPARE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
		}else{//if("ALL".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
			status.add(ModelConstant.ORDER_STATUS_PAYED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_REFUNDING);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
			status.add(ModelConstant.ORDER_STATUS_RECEIVED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
			status.add(ModelConstant.ORDER_STATUS_RETURNED);
			status.add(ModelConstant.ORDER_STATUS_REFUNDED);
		} 
		return new BaseResult<List<ServiceOrder>>().success(serviceOrderRepository.findByUserAndStatusAndTypes(user.getId(),status, types));
    }
	@RequestMapping(value = "/orders/status/onsale/{statusType}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<ServiceOrder>> onSaleOrders(@ModelAttribute(Constants.USER)User user,@PathVariable String statusType) throws Exception {
		List<Integer> status = new ArrayList<Integer>();
		if("NEEDPAY".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
		}else if("NEEDRECEIVE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
		}else if("CANCELD".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
		}else if("PAYED".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_PAYED);
		}else if("PREPARE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
		}else{//if("ALL".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
			status.add(ModelConstant.ORDER_STATUS_PAYED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_REFUNDING);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
			status.add(ModelConstant.ORDER_STATUS_RECEIVED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
			status.add(ModelConstant.ORDER_STATUS_RETURNED);
			status.add(ModelConstant.ORDER_STATUS_REFUNDED);
		} 
		return new BaseResult<List<ServiceOrder>>().success(serviceOrderRepository.findByUserAndStatusAndType(user.getId(),status,ModelConstant.ORDER_TYPE_ONSALE));
    }
	@RequestMapping(value = "/orders/status/group/{statusType}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<RgroupOrder>> groupOrders(@ModelAttribute(Constants.USER)User user,@PathVariable String statusType) throws Exception {
		List<Integer> status = new ArrayList<Integer>();
		if("NEEDPAY".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
		}else if("NEEDRECEIVE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
		}else if("CANCELD".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
		}else if("PAYED".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_PAYED);
		}else if("PREPARE".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
		}else{//if("ALL".equalsIgnoreCase(statusType)){
			status.add(ModelConstant.ORDER_STATUS_INIT);
			status.add(ModelConstant.ORDER_STATUS_PAYED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL);
			status.add(ModelConstant.ORDER_STATUS_APPLYREFUND);
			status.add(ModelConstant.ORDER_STATUS_REFUNDING);
			status.add(ModelConstant.ORDER_STATUS_SENDED);
			status.add(ModelConstant.ORDER_STATUS_RECEIVED);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_BACKEND);
			status.add(ModelConstant.ORDER_STATUS_CANCEL_MERCHANT);
			status.add(ModelConstant.ORDER_STATUS_CONFIRM);
			status.add(ModelConstant.ORDER_STATUS_RETURNED);
			status.add(ModelConstant.ORDER_STATUS_REFUNDED);
		} 
		
		return new BaseResult<List<RgroupOrder>>().success(rgroupService.queryMyRgroupOrders(user.getId(),status));
    }
	
	@RequestMapping(value = "/queryBuyInfo/{type}/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<BuyInfoVO> queryBuyInfo(@ModelAttribute(Constants.USER)User user,@PathVariable int type,@PathVariable long ruleId) throws Exception {
		SalePlan sp = salePlanService.getService(type).findSalePlan(ruleId);
		BuyInfoVO vo = new BuyInfoVO();
		vo.setRule(sp);
		vo.setProduct(productService.getProduct(sp.getProductId()));
		
		List<Address> addrList = addressService.getAddressByMain(user.getId(), true);
		Address address = new Address();
		if (addrList == null || addrList.size() == 0) {
			addrList = addressService.queryAddressByUser(user.getId());
		}
		if (addrList!=null && addrList.size()>0) {
			address = addrList.get(0);
		}
		vo.setAddress(address);
		return new BaseResult<BuyInfoVO>().success(vo);
    }
	
	@RequestMapping(value = "/getOrder/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<ServiceOrder> getOrder(@ModelAttribute(Constants.USER)User user,@PathVariable long orderId) throws Exception {
		ServiceOrder order = baseOrderService.findOne(orderId);
		if(order.getUserId() != user.getId()){
			return new BaseResult<ServiceOrder>().failMsg("你没有权限查看该订单！");
		}
		return new BaseResult<ServiceOrder>().success(order);
    }

	@RequestMapping(value = "/requestPay/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<JsSign> requestPay(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user) throws Exception {
		ServiceOrder order = baseOrderService.findOne(orderId);
		if(user.getId() != order.getUserId()) {
			return new BaseResult<JsSign>().failMsg("无法支付他人订单");
		}
		return new BaseResult<JsSign>().success(baseOrderService.requestPay(order));
	}


	@RequestMapping(value = "/notifyPayed/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> notifyPayed(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user) throws Exception {
		baseOrderService.notifyPayed(orderId);
		return new BaseResult<String>().success("通知成功");
	}
	

	@RequestMapping(value = "/cancelOrder/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<ServiceOrder> cancelOrder(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user) throws Exception {
		ServiceOrder order = baseOrderService.findOne(orderId);
		if(user.getId() != order.getUserId()) {
			return new BaseResult<ServiceOrder>().failMsg("无法操作他人订单");
		}
		return new BaseResult<ServiceOrder>().success(baseOrderService.cancelOrder(order));
	}
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<ServiceOrder> createOrder(@RequestBody SingleItemOrder sOrder,@ModelAttribute(Constants.USER)User user) throws Exception {
		sOrder.setUserId(user.getId());
		sOrder.setOpenId(user.getOpenid());
		return new BaseResult<ServiceOrder>().success(baseOrderService.createOrder(sOrder));
	}

	@RequestMapping(value = "/createOrder4Cart", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<ServiceOrder> createOrder4Package(@RequestBody CreateOrderReq req,@ModelAttribute(Constants.USER)User user) throws Exception {
		Cart cart = redisRepository.getCart(Keys.uidCardKey(user.getId()));
		if(cart == null) {
			return new BaseResult<ServiceOrder>().failMsg("购物车为空，请重新选择你需要购买的商品！");
		}
		if(cart.getOrderType() < 0){
			return new BaseResult<ServiceOrder>().failMsg("商品信息获取异常，请重新选择你需要购买的商品！");
		}
		ServiceOrder o = baseOrderService.createOrder(req,cart,user.getId(),user.getOpenid());
		if(o == null) {
			return new BaseResult<ServiceOrder>().failMsg("订单提交失败，请稍后重试！");
		} else {
			redisRepository.removeCart(Keys.uidCardKey(user.getId()));
		}
		return new BaseResult<ServiceOrder>().success(o);
	}
	
	@RequestMapping(value = "/signOrder/{orderId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<ServiceOrder> signOrder(@PathVariable long orderId,@ModelAttribute(Constants.USER)User user) throws Exception {
		ServiceOrder order = baseOrderService.findOne(orderId);
		if(user.getId() != order.getUserId()) {
			return new BaseResult<ServiceOrder>().failMsg("无法操作他人订单");
		}
		return new BaseResult<ServiceOrder>().success(baseOrderService.signOrder(order));
	}
	
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> comment(@RequestBody Comment comment,@ModelAttribute(Constants.USER)User user) throws Exception {
		ServiceOrder order = baseOrderService.findOne(comment.getOrderId());
		if(order == null || user.getId() != order.getUserId()) {
			return new BaseResult<String>().failMsg("无法操作他人订单");
		}
		comment.setUserId(user.getId());
		comment.setUserName(user.getName());
		comment.setUserHeadImg(user.getHeadimgurl());
		baseOrderService.comment(order, comment);
		return new BaseResult<String>().success("评价成功");
	}
	
}
