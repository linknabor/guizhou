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
import com.yumu.hexie.integration.daojia.gaofei.GaofeiReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.GaofeiService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "gaofeiController")
public class GaofeiController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(GaofeiController.class);

    @Inject
    private GaofeiService gaofeiService;

	@RequestMapping(value = "/gaofei/createGaofeiExperienceOrder", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<YuyueOrder> createGaofeiExperienceOrder(@RequestBody GaofeiReq gaofeiReq,@ModelAttribute(Constants.USER)User user) throws Exception {
		return BaseResult.successResult(gaofeiService.addGaofeiExperienceOrder(user,gaofeiReq));
    }

	@RequestMapping(value = "/gaofei/createGaofeiYuyueOrder/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<YuyueOrder> createGaofeiYuyueOrder(@RequestBody GaofeiReq gaofeiReq,@ModelAttribute(Constants.USER)User user, @PathVariable long addressId) throws Exception {
		return BaseResult.successResult(gaofeiService.addGaofeiYuyueOrder(user,gaofeiReq,addressId));
    }
	@RequestMapping(value = "/gaofei/checkByRule/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> checkByRule(@ModelAttribute(Constants.USER)User user, @PathVariable long ruleId) throws Exception {
		if(gaofeiService.checkIsExistenceByProduct(user, ruleId)){
			return BaseResult.fail("本活动每个微信用户仅限参与一次，请继续参与更多活动");
		}else{
			return BaseResult.successResult("success");
		}
    }
}
