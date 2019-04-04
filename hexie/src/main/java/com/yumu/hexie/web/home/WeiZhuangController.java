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
import com.yumu.hexie.integration.daojia.weizhuangwang.WeiZhuangWangReq;
import com.yumu.hexie.model.localservice.oldversion.YuyueOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.WeiZhuangWangService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "weiZhuangController")
public class WeiZhuangController extends BaseController{
	private static final Logger Log = LoggerFactory.getLogger(WeiZhuangController.class);

    @Inject
    private WeiZhuangWangService weiZhuangWangService;

	@RequestMapping(value = "/weizhuang/createWeiZhuangYuyueOrder/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<YuyueOrder> createWeiZhuangYuyueOrder(@RequestBody WeiZhuangWangReq weiZhuangWangReq,@ModelAttribute(Constants.USER)User user, @PathVariable long addressId) throws Exception {
		return BaseResult.successResult(weiZhuangWangService.addNoNeedPayOrder(user, weiZhuangWangReq, addressId));
    }
}
