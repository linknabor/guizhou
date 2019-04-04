package com.yumu.hexie.web.sales;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.Collocation;
import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.sales.CollocationService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.sales.resp.MultiBuyInfo;

//搭配
@Controller(value = "collocationController")
public class CollocationController extends BaseController{
    @Inject
    private CollocationService collocationService;
	@Inject
	private AddressService addressService;
    @Inject
    private RedisRepository redisRepository;
    
	@RequestMapping(value = "/collocation/{salePlanType}/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Collocation> collocation(@PathVariable int salePlanType
			,@PathVariable long ruleId) throws Exception {
		Collocation c = collocationService.findLatestCollocation(salePlanType, ruleId);
		if(c!=null) {
			c.setProducts(c.getItems());
		}
		return new BaseResult<Collocation>().success(c);
    }
	

	@RequestMapping(value = "/collocation/{collId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Collocation> collocation(@PathVariable long collId) throws Exception {
		Collocation c = collocationService.findOneWithItem(collId);
		if(c!=null) {
			c.setProducts(c.getItems());
		}
		return new BaseResult<Collocation>().success(c);
    }
	

	@RequestMapping(value = "/collocation/saveToCart", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> saveToCart(@RequestBody Cart cart,@ModelAttribute(Constants.USER)User user) throws Exception {
		if(cart.getItems() == null || cart.getItems().size() == 0){
			return new BaseResult<String>().failMsg("添加到购物车失败");
		}
		collocationService.fillItemInfo4Cart(cart);
		redisRepository.setCart(Keys.uidCardKey(user.getId()), cart);
		
		return new BaseResult<String>().success("success");
	}

	@RequestMapping(value = "/collocation/getCartWithAddr", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<MultiBuyInfo> getCart( @ModelAttribute(Constants.USER)User user) throws Exception {
		Cart cart = redisRepository.getCart(Keys.uidCardKey(user.getId()));
		if(cart == null || cart.getItems() == null || cart.getItems().size() == 0) {
			return new BaseResult<MultiBuyInfo>().failMsg("请重新选择商品进行购买！");
		}
		MultiBuyInfo i = new MultiBuyInfo();
		
		i.setCart(cart);
		i.setAddress(addressService.queryDefaultAddress(user));
		if(cart.getItems().get(0).getCollocationId() <= 0){
			return new BaseResult<MultiBuyInfo>().failMsg("请重新选择商品进行购买！");
		}
		i.setCollocation(collocationService.findOne(cart.getItems().get(0).getCollocationId()));
		return new BaseResult<MultiBuyInfo>().success(i);
	}
	
}
