package com.yumu.hexie.web.home;

import java.util.List;

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
import com.yumu.hexie.model.extreinfo.CarBrandName;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserCarInfo;
import com.yumu.hexie.service.home.UserCarInfoService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "userCarInfoController")
public class UserCarInfoController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(UserCarInfoController.class);

	@Inject
	private UserCarInfoService userCarInfoService;

	@RequestMapping(value = "/userCarInfo/delete/{userCarInfoId}", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> deleteUserCarInfo(@ModelAttribute(Constants.USER)User user,@PathVariable long userCarInfoId) throws Exception {
		userCarInfoService.deleteUserCarInfo(userCarInfoId, user.getId());
        return BaseResult.successResult("删除车辆信息成功");
    }

	@RequestMapping(value = "/userCarInfo/query/{userCarInfoId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<UserCarInfo> queryUserCarInfoById(@ModelAttribute(Constants.USER)User user,@PathVariable long userCarInfoId) throws Exception {
		return BaseResult.successResult(userCarInfoService.queryUserCarInfoById(userCarInfoId));
	}

	@RequestMapping(value = "/userCarInfo/default/{userCarInfoId}", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<String> defaultUserCarInfo(@ModelAttribute(Constants.USER)User user,@PathVariable long userCarInfoId) throws Exception {
		boolean r = userCarInfoService.configDefaultUserCarInfo(user, userCarInfoId);
        if(!r) {
        	BaseResult.fail("设置默认车辆失败！");
        }

		return BaseResult.successResult("设置默认车辆信息成功");
    }
	@RequestMapping(value = "/userCarInfos", method = RequestMethod.GET)
	@ResponseBody
    public BaseResult<List<UserCarInfo>> queryUserCarInfoList(@ModelAttribute(Constants.USER)User user) throws Exception {
		List<UserCarInfo> userCarInfos = userCarInfoService.queryUserCarInfoByUser(user.getId());
		return BaseResult.successResult(userCarInfos);
    }

	@RequestMapping(value = "/addUserCarInfo", method = RequestMethod.POST)
	@ResponseBody
    public BaseResult<UserCarInfo> save(@ModelAttribute(Constants.USER)User user,@RequestBody UserCarInfo userCarInfo) throws Exception {
		userCarInfo.setUserId(user.getId());
		return BaseResult.successResult(userCarInfoService.addUserCarInfo(userCarInfo));
    }

	@RequestMapping(value = "/userCarInfo/getBrandName", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<CarBrandName>> getBrandName() throws Exception {
		return BaseResult.successResult(userCarInfoService.getMakeName());
    }

	@RequestMapping(value = "/userCarInfo/getModelName/{brandName}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<String>> getModelName(@PathVariable String brandName) throws Exception {
		return BaseResult.successResult(userCarInfoService.getModelByMakeName(brandName));
    }

}
