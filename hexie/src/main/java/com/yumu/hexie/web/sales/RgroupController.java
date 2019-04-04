package com.yumu.hexie.web.sales;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.distribution.RgroupAreaItem;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.DistributionService;
import com.yumu.hexie.service.sales.CustomOrderService;
import com.yumu.hexie.service.sales.RgroupService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@SuppressWarnings("unchecked")
@Controller(value = "rgroupController")
public class RgroupController extends BaseController{
    @Inject
    private RgroupService rgroupService;
    @Inject
    private CustomOrderService customRgroupService;
    @Inject
    private DistributionService distributionService;

	@RequestMapping(value = "/rgroups/{page}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<RgroupAreaItem>> rgroups(@ModelAttribute(Constants.USER)User user,@PathVariable int page) throws Exception {
		List<RgroupAreaItem> items = distributionService.queryRgroups(user, page);
        return new BaseResult<List<RgroupAreaItem>>().success(rgroupService.addProcessStatus(items));
    }
	
	@RequestMapping(value = "/getRgroupRule/{ruleId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<RgroupAreaItem> getRgroupRule(@PathVariable long ruleId,@ModelAttribute(Constants.USER)User user) throws Exception {
		return BaseResult.successResult(customRgroupService.findSalePlan(ruleId));
    }
	
}
