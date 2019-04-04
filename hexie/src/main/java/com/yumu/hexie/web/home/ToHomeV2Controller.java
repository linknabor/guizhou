package com.yumu.hexie.web.home;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;
import com.yumu.hexie.model.localservice.HomeBillItem;
import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceType;
import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.o2o.HomeItemService;
import com.yumu.hexie.service.o2o.ShipFeeService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.home.resp.CartWithAddressVO;
import com.yumu.hexie.web.home.resp.ShipFeeTplVO;

@RequestMapping("home")
@Controller(value = "toHomeV2Controller")
public class ToHomeV2Controller extends BaseController{

    @Inject
    private RedisRepository redisRepository;
    @Inject
    private AddressService addressService;
    @Inject
    private HomeItemService homeItemService;
    @Inject
    private ShipFeeService shipFeeService;
    @RequestMapping(value = "/putToCart", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> putToCart(@RequestBody HomeCart cart,@ModelAttribute(Constants.USER)User user) throws Exception {
        if(cart.getItems() == null || cart.getItems().size() == 0){
            return new BaseResult<String>().failMsg("添加到购物车失败");
        }
        //collocationService.fillItemInfo4Cart(cart);
        for(HomeBillItem item : cart.getItems()) {
            ServiceItem i = homeItemService.queryById(item.getServiceId());
            item.setTitle(i.getTitle());
            item.setPrice(i.getPrice());
            item.setLogo(i.getImageUrl());
        }
        //购物车对应的商户
        if(cart.getItems().size() == 0) {
            cart.setBaseType(0);
            cart.setItemType(0);
        } else {
            ServiceType baseType = homeItemService.findBaseTypeByItem(cart.getItems().get(0).getServiceId());
            cart.setBaseType(baseType.getId());
            ServiceType type = homeItemService.findTypeByItem(cart.getItems().get(0).getServiceId());
            cart.setItemType(type.getId());
        }
        redisRepository.setHomeCart(Keys.uidHomeCardKey(user.getId()), cart);
        return new BaseResult<String>().success("success");
    }
    
    @RequestMapping(value = "/viewCartWithAddr", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<CartWithAddressVO> putToCart(@ModelAttribute(Constants.USER)User user) throws Exception {
        CartWithAddressVO vo = new CartWithAddressVO();
        vo.setCart(redisRepository.getHomeCart(Keys.uidHomeCardKey(user.getId())));
        if(vo.getCart() == null || vo.getCart().getItems() == null) {
            return new BaseResult<CartWithAddressVO>().failMsg("未选择需要购买的服务！");
        }
        if(user.getCurrentAddrId()>0) {
            vo.setAddress(addressService.queryDefaultAddress(user));
        }
        if(vo.getCart().getItems().size() > 0){
            ShipFeeTemplate tpl =shipFeeService.findTemplateByItem(vo.getCart().getItems().get(0).getServiceId());
            if(tpl != null)
                vo.setShipFee(new ShipFeeTplVO(tpl));
        }
        return new BaseResult<CartWithAddressVO>().success(vo);
    }
}
