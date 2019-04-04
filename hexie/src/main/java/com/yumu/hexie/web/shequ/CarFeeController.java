package com.yumu.hexie.web.shequ;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.wuye.vo.CarFeeInfo;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.shequ.CarFeeService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
@Controller(value = "carFeeController")
public class CarFeeController extends BaseController{

	@Inject
	private CarFeeService carFeeService;
	
	@RequestMapping(value = "/stopcar/tempcar/{device_no}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<CarFeeInfo> tempcar(@PathVariable String device_no)
			throws Exception {
		CarFeeInfo result = carFeeService.getCarFeeByDriveNo(device_no);
		return BaseResult.successResult(result);
	}
	
	@RequestMapping(value = "/pay/temppaycar/{water_id}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<WechatPayInfo> getPayTempCar(@ModelAttribute(Constants.USER)User user,
			@PathVariable String water_id)
			throws Exception {
		WechatPayInfo result = carFeeService.getCarPayByWaterId(user.getWuyeId(),water_id,user.getOpenid());
		return BaseResult.successResult(result);
	}
}
