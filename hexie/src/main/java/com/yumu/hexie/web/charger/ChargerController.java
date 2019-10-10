package com.yumu.hexie.web.charger;

import javax.xml.bind.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yumu.hexie.common.Constants;
import com.yumu.hexie.integration.charger.ChargerUtil;
import com.yumu.hexie.integration.charger.vo.ChargerType;
import com.yumu.hexie.integration.charger.vo.PayStatusResult;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.charger.ChargerService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

/**
 * 云充
 * @author liuzj
 *
 */
@Controller(value="chargerController")
public class ChargerController extends BaseController {

	@Autowired
	private ChargerService chargerService;
	
	private static final Logger logger = LoggerFactory.getLogger(ChargerController.class);
	
	@RequestMapping(value = "/mosParam",method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> getParam(@ModelAttribute(Constants.USER)User user, @RequestParam String sn)
	{
		String paramUrl = ChargerUtil.getAppKeyUrl(user.getOpenid(), user.getTel(), sn);
		if(paramUrl!=null && paramUrl !="")
		{
			return BaseResult.successResult(paramUrl);
		}else
		{
			return BaseResult.successResult("获取AppKey失败！");
		}
	}
	
	/**
	 * 1.云充用户注册(该方法只在“原来已经在公众号注册过的用户”使用)
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/getChargerUser",method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> getChargerUser(@ModelAttribute(Constants.USER)User user, 
			@RequestParam String sn, @RequestParam String sectId)
	{
		String paramUrl = "";
		try {
			boolean istrue = chargerService.saveChargerUser(user.getOpenid(), user.getTel(), sn, sectId);
			paramUrl = ChargerUtil.getAppKeyUrl(user.getOpenid(), user.getTel(), sn);
			System.out.println(paramUrl==null || paramUrl=="" || !istrue);
			if(paramUrl==null || paramUrl=="" || !istrue)
			{
				return  BaseResult.fail("创建账户失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BaseResult.successResult(paramUrl);
	}
	
	/**
	 * 2.获取充值金额类型（惠充电直接跳转到充值画面时 使用）
	 * @return
	 */
	@RequestMapping(value = "/getChargerType",method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<ChargerType> getChargerType()
	{
		try {
			return BaseResult.successResult(ChargerUtil.getChargerType());
		} catch (ValidationException e) {
			return BaseResult.fail(e.getMessage());
		}
	}
	
	/**
	 * 3.充值支付
	 * @param user
	 * @param phone
	 * @param money
	 * @return
	 */
	@RequestMapping(value = "/getChargerPay", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<WechatPayInfo> getChargerPayInfo(@ModelAttribute(Constants.USER)User user,
			@RequestParam(required=false) String phone,@RequestParam(required=false) String money)
	{
		try {
			WechatPayInfo wechatPay = chargerService.getChargerPayInfo(user.getWuyeId(), phone, user.getOpenid(), money);
			return  BaseResult.successResult(wechatPay);
		} catch (ValidationException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	
	/**
	 * 查询支付情况
	 * @param user
	 * @param phone
	 * @param money
	 * @param tradeWaterId
	 * @return
	 */
	@RequestMapping(value = "/noticeCharger", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> noticeCharger(@ModelAttribute(Constants.USER)User user,
		@RequestParam(required=false) String phone,@RequestParam(required=false) String packageId,
		@RequestParam(required=false) String tradeWaterId)
	{
		PayStatusResult payStatus = chargerService.noticeChargerPay(user.getOpenid(), phone, user.getWuyeId(), tradeWaterId, packageId);
		
		if ("01".equals(payStatus.getMerger_status())) {	//01表示支付成功，02表示未支付成功
			return BaseResult.successResult(payStatus.getParmUrl());
		}else {
			return BaseResult.fail("支付结果未知，请稍后在支付记录界面查询。");
		}
	}
}