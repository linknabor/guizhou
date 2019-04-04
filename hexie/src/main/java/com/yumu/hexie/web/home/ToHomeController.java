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
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HomeService;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.ToHomeService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "toHomeController")
public class ToHomeController extends BaseController{
    @Inject
    private ToHomeService toHomeService;
	
	@RequestMapping(value = "/toHome/handpick", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<HomeService>> getHandpickService() throws Exception {
		return BaseResult.successResult(toHomeService.findHandpickService());
    }

	@RequestMapping(value = "/toHome/service/{serviceType}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<HomeService>> getServiceByServiceType(@PathVariable int serviceType) throws Exception {
		return BaseResult.successResult(toHomeService.findServiceType(serviceType));
    }
}
