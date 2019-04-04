package com.yumu.hexie.web.common;

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
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.promotion.share.ShareAccessRecord;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.ShareService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.common.req.ShareInfoReq;

@Controller(value = "shareController")
public class ShareController extends BaseController {

	private static final Logger Log = LoggerFactory.getLogger(ShareController.class);

    @Inject
    private ShareService shareService;
	
	@RequestMapping(value = "/shared/{shareCode}", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> shared(@ModelAttribute(Constants.USER)User user,@PathVariable String shareCode,
			@RequestBody ShareInfoReq req) throws Exception {
		Log.info("shareInfo[userName:" + user.getName()+ ",code:" + shareCode+"]");
		if(!StringUtil.isEmpty(shareCode)){
			shareService.access(user, new ShareAccessRecord(shareCode, req.getSalePlanId(), req.getSalePlanType()));
		}
		return new BaseResult<String>().success("");
    }
}
