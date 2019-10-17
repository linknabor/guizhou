package com.yumu.hexie.web.home;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.daojia.haojiaan.HaoJiaAnReq;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.HaoJiaAnService;
import com.yumu.hexie.service.home.impl.HaoJiaAnServiceImpl;
import com.yumu.hexie.vo.YuyueQueryOrder;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "haoJiaAnController")
public class HaoJiaAnController extends BaseController{
	
	@Inject
	private HaoJiaAnService haoJiaAnService;
	
	private static final Logger log = LoggerFactory.getLogger(HaoJiaAnController.class);
	
	@RequestMapping(value = "/haojiaan/createHaoJiaAnYuyueOrder/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<Long> createHaoJiaAnYuyueOrder(HaoJiaAnReq haojiaanReq,@ModelAttribute(Constants.USER)User user, @PathVariable long addressId) throws Exception {
		Long oId = haoJiaAnService.addNoNeedPayOrder(user, haojiaanReq, addressId);
		if(oId!=null){
            return new BaseResult<Long>().success(oId);
        } else {
            return new BaseResult<Long>().failMsg("好家安预约订单提交失败，请稍后再试");
        }
    }
	
	
	@RequestMapping(value = "/haojiaan/queryOrder/{orderId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<YuyueQueryOrder> queryHaoJiaAnYuyueOrder(@ModelAttribute(Constants.USER)User user, @PathVariable long orderId) throws Exception {
		
		YuyueQueryOrder order = haoJiaAnService.queryYuYueOrder(user, orderId);
		if(order!=null){
            return new BaseResult<YuyueQueryOrder>().success(order);
        } else {
            return new BaseResult<YuyueQueryOrder>().failMsg("未查询到预约订单。");
        }
    }
	
	//订单访问权限
	@RequestMapping(value = "/haojiaan/orderAccessAuthority/{orderId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Long> orderAccessAuthority(@ModelAttribute(Constants.USER)User user, @PathVariable long orderId) throws Exception {
		log.error("进入订单访问权限接口");
		return haoJiaAnService.orderAccessAuthority(orderId);
    }
	
}
