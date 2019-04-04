package com.yumu.hexie.web.home;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.daojia.flowerplus.FlowerPlusReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.FlowerPlusService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "flowerPlusController")
public class FlowerPlusController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(FlowerPlusController.class);

    @Inject
    private FlowerPlusService flowerPlusService;

	@RequestMapping(value = "/flowerPlus/createFasuperOrder/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<YuyueOrder> createFasuperOrder(@RequestBody FlowerPlusReq flowerPlusReq,@ModelAttribute(Constants.USER)User user, @PathVariable long addressId) throws Exception {
		return BaseResult.successResult(flowerPlusService.addFlowerPlusOrder(user, flowerPlusReq, addressId));
    }

	@RequestMapping(value = "/flowerPlus/checkByRule/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> checkByRule(@ModelAttribute(Constants.USER)User user, @PathVariable long ruleId) throws Exception {
		if(flowerPlusService.checkIsExistenceByProduct(user, ruleId)){
			return BaseResult.fail("本活动每个微信用户仅限参与一次，请继续参与更多活动");
		}else{
			return BaseResult.successResult("success");
		}
    }
	
	@RequestMapping(value = "/flowerPlus/checkByRuleAndCount/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> checkByRuleAndCount(@ModelAttribute(Constants.USER)User user, @PathVariable long ruleId) throws Exception {

//		Date date = new Date();
//		String timeline = "1447164000000";
		
//		if(date.compareTo(new Date(Long.valueOf(timeline)))<=0){
//			return BaseResult.fail("本活动11月11日0时0分生效");
//		}

		if(!flowerPlusService.checkCountByProduct(ruleId, 100)){
			return BaseResult.fail("感谢您的参与，该服务已被抢购完，请选择其他服务！");
		}
		if(flowerPlusService.checkIsExistenceByProduct(user, ruleId)){
			return BaseResult.fail("本活动每个用户仅能参与一次，请选择其他服务！");
		}else{
			return BaseResult.successResult("success");
		}
    }
}

