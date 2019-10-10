package com.yumu.hexie.web.shequ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.service.TemplateMsgService;
import com.yumu.hexie.integration.wuye.WuyeUtil;
import com.yumu.hexie.integration.wuye.resp.BillListVO;
import com.yumu.hexie.integration.wuye.resp.CellListVO;
import com.yumu.hexie.integration.wuye.resp.CellVO;
import com.yumu.hexie.integration.wuye.resp.HouseListVO;
import com.yumu.hexie.integration.wuye.resp.PayWaterListVO;
import com.yumu.hexie.integration.wuye.vo.HexieHouse;
import com.yumu.hexie.integration.wuye.vo.HexieUser;
import com.yumu.hexie.integration.wuye.vo.InvoiceInfo;
import com.yumu.hexie.integration.wuye.vo.PayResult;
import com.yumu.hexie.integration.wuye.vo.PayWater;
import com.yumu.hexie.integration.wuye.vo.PaymentInfo;
import com.yumu.hexie.integration.wuye.vo.WechatPayInfo;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.promotion.coupon.Coupon;
import com.yumu.hexie.model.promotion.coupon.CouponCombination;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.common.SmsService;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.shequ.WuyeService;
import com.yumu.hexie.service.user.AddressService;
import com.yumu.hexie.service.user.CouponService;
import com.yumu.hexie.service.user.PointService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.user.resp.UserInfo;

@Controller(value = "wuyeController")
public class WuyeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(WuyeController.class);

	@Inject
	private WuyeService wuyeService;
	@Inject
	private PointService pointService;
	@Inject
	protected SmsService smsService;
	@Inject
	protected CouponService couponService;

	@Inject
	protected UserService userService;

	@Inject
	protected AddressService addressService;

	@Inject
	protected UserRepository userRepository;

	@Inject
	private SystemConfigService systemConfigService;

	/***************** [BEGIN]房产 ********************/
	@RequestMapping(value = "/hexiehouses", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<HexieHouse>> hexiehouses(@ModelAttribute(Constants.USER) User user) throws Exception {
		log.error("User的物业id是：" + user.getWuyeId());
		if (StringUtil.isEmpty(user.getWuyeId())) {
			// FIXME 后续可调转绑定房子页面
			return BaseResult.successResult(new ArrayList<HexieHouse>());
		}
		HouseListVO listVo = wuyeService.queryHouse(user.getWuyeId());
		if (listVo != null && listVo.getHou_info() != null) {
			return BaseResult.successResult(listVo.getHou_info());
		} else {
			return BaseResult.successResult(new ArrayList<HexieHouse>());
		}
	}

	@RequestMapping(value = "/hexiehouse/delete/{houseId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<HexieHouse>> deleteHouse(@ModelAttribute(Constants.USER) User user,
			@PathVariable String houseId) throws Exception {
		if (StringUtil.isEmpty(user.getWuyeId())) {
			return BaseResult.fail("删除房子失败！请重新访问页面并操作！");
		}
		com.yumu.hexie.integration.wuye.resp.BaseResult<String> r = wuyeService.deleteHouse(user.getWuyeId(), houseId);
		// boolean r = wuyeService.deleteHouse(user.getWuyeId(), houseId);
		if ((boolean) r.isSuccess()) {
			// 添加电话到user表
			log.error("这里是删除房子后保存的电话");
			log.error("保存电话到user表==》开始");
			user.setOfficeTel(r.getData());
			user.setSectId("0");
			user.setCspId("0");
			userService.save(user);
			log.error("保存电话到user表==》成功");
			return BaseResult.successResult("删除房子成功！");
		} else {
			return BaseResult.fail("删除房子失败！");
		}
	}

	@RequestMapping(value = "/hexiehouse", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<HexieHouse> hexiehouses(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String stmtId, @RequestParam(required = false) String house_id)
			throws Exception {

		if (StringUtil.isEmpty(user.getWuyeId())) {
			// FIXME 后续可调转绑定房子页面
			return BaseResult.successResult(null);
		}
		return BaseResult.successResult(wuyeService.getHouse(user.getWuyeId(), stmtId));
	}

	@RequestMapping(value = "/hexiehouse2", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<HexieHouse> hexiehouses2(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String house_id) throws Exception {

		if (StringUtil.isEmpty(user.getWuyeId())) {
			// FIXME 后续可调转绑定房子页面
			return BaseResult.successResult(null);
		}
		return BaseResult.successResult(wuyeService.getHouse(user.getWuyeId(), house_id));
	}

	@RequestMapping(value = "/addhexiehouse", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<HexieHouse> addhouses(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String stmtId, @RequestParam(required = false) String houseId,
			@RequestParam(required = false) String area) throws Exception {
		HexieUser u = wuyeService.bindHouse(user.getWuyeId(), stmtId, houseId);
		log.error("HexieUser u = " + u);
		if (u != null) {
			pointService.addZhima(user, 1000, "zhima-house-" + user.getId() + "-" + houseId);
			// 添加电话到user表
			log.error("这里是添加房子后保存的电话");
			log.error("保存电话到user表==》开始");
			user.setOfficeTel(u.getOffice_tel());
			// userService.save(user);
			log.error("保存电话到user表==》成功");
			wuyeService.saveRegion(u);
			wuyeService.setDefaultAddress(user, u);
		}
		return BaseResult.successResult(u);
	}

	// 无账单绑定
	@RequestMapping(value = "/addhexiehouse2", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<HexieHouse> addhousesnostmt(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String houseId, @RequestParam(required = false) String area)
			throws Exception {
		HexieUser u = wuyeService.bindHouseNoStmt(user.getWuyeId(), houseId, area);
		log.error("HexieUser2 u = " + u);
		if (u != null) {
			pointService.addZhima(user, 1000, "zhima-house-" + user.getId() + "-" + houseId);
			// 添加电话到user表
			log.error("这里是添加房子后保存的电话");
			log.error("保存电话到user表==》开始");
			user.setOfficeTel(u.getOffice_tel());
			// userService.save(user);
			log.error("保存电话到user表==》成功");
			wuyeService.saveRegion(u);
			wuyeService.setDefaultAddress(user, u);
		}
		return BaseResult.successResult(u);
	}

	// 支付完成后设置默认地址
	@RequestMapping(value = "/setDefaultAdressByBill", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> setDefaultAdressByBill(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String billId) throws Exception {
		HexieUser u = wuyeService.getAddressByBill(billId);
		wuyeService.saveRegion(u);
		wuyeService.setDefaultAddress(user, u);
		return BaseResult.successResult("");
	}

	/***************** [END]房产 ********************/

	/***************** [BEGIN]缴费记录 ********************/
	@RequestMapping(value = "/paymentHistory", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<PayWater>> paymentHistory(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate)
			throws Exception {
		PayWaterListVO listVo = wuyeService.queryPaymentList(user.getWuyeId(), startDate, endDate);
		if (listVo != null && listVo.getBill_info_water() != null) {
			return BaseResult.successResult(listVo.getBill_info_water());
		} else {
			return BaseResult.successResult(null);
		}
	}

	/**
	 * 查询缴费详情
	 * 
	 * @param session
	 * @param trade_water_id
	 *            流水号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPaymentDetail/{trade_water_id}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<PaymentInfo> queryPaymentDetail(@ModelAttribute(Constants.USER) User user,
			@PathVariable String trade_water_id) throws Exception {
		PaymentInfo info = wuyeService.queryPaymentDetail(user.getWuyeId(), trade_water_id);
		// PayWaterListVO listVo =
		// wuyeService.queryPaymentList(user.getWuyeId(), startDate, endDate);
		if (info != null) {
			return BaseResult.successResult(info);
		} else {
			return BaseResult.successResult(null);
		}
	}

	// FIXME 详情查询等记录有了再加
	/***************** [END]缴费记录 ********************/

	/***************** [BEGIN]账单查询 ********************/
	@RequestMapping(value = "/billList", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<BillListVO> billList(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String payStatus, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String currentPage,
			@RequestParam(required = false) String totalCount, @RequestParam(required = false) String house_id, @RequestParam(required = false) String sect_id)
			throws Exception {
		BillListVO listVo = wuyeService.queryBillList(user.getWuyeId(), payStatus, startDate, endDate, currentPage,
				totalCount, house_id,sect_id);
		if (listVo != null && listVo.getBill_info() != null) {
			return BaseResult.successResult(listVo);
		} else {
			return BaseResult.successResult(null);
		}
	}

	/***************** [END]账单查询 ********************/

	/***************** [BEGIN]缴费 ********************/
	@RequestMapping(value = "/getBillDetail", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<PaymentInfo> getBillDetail(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String billId, @RequestParam(required = false) String stmtId) {
		return BaseResult.successResult(WuyeUtil.getBillDetail(user.getWuyeId(), stmtId, billId).getData());
	}

	// stmtId在快捷支付的时候会用到
	@RequestMapping(value = "/getPrePayInfo", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<WechatPayInfo> getPrePayInfo(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String billId, @RequestParam(required = false) String stmtId,
			@RequestParam(required = false) String couponUnit, @RequestParam(required = false) String couponNum,
			@RequestParam(required = false) String couponId, @RequestParam(required = false) String mianBill,
			@RequestParam(required = false) String mianAmt, @RequestParam(required = false) String reduceAmt,
			@RequestParam(required = false) String invoice_title_type,
			@RequestParam(required = false) String credit_code, @RequestParam(required = false) String invoice_title)
			throws Exception {
		WechatPayInfo result;
		try {
			result = wuyeService.getPrePayInfo(user, billId, stmtId, couponUnit,
					couponNum, couponId, mianBill, mianAmt, reduceAmt, invoice_title_type, credit_code,
					invoice_title);
		} catch (Exception e) {

			e.printStackTrace();
			return BaseResult.fail(e.getMessage());
		}
		return BaseResult.successResult(result);
	}

	// stmtId在快捷支付的时候会用到
	@RequestMapping(value = "/getPrePayInfoo", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<WechatPayInfo> getPrePayInfoo(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String billId) throws Exception {
		WechatPayInfo result;
		try {
			System.out.println("try");
		} catch (Exception e) {
			System.out.println("catch");
			e.printStackTrace();
			return BaseResult.fail(e.getMessage());
		}
		System.out.println("no try catch");
		return BaseResult.successResult(null);
	}

	/**
	 * 通知支付成功，并获取支付查询的返回结果
	 * 
	 * @param user
	 * @param billId
	 * @param stmtId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/noticePayed", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<String> noticePayed(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String billId, @RequestParam(required = false) String stmtId,
			@RequestParam(required = false) String tradeWaterId, @RequestParam(required = false) String packageId,
			@RequestParam(required = false) String feePrice, @RequestParam(required = false) String couponId)
			throws Exception {
		PayResult payResult = wuyeService.noticePayed(user.getWuyeId(), billId, stmtId, tradeWaterId, packageId);

		String trade_status = payResult.getMerger_status();
		// if ("01".equals(trade_status)) { //01表示支付成功，02表示未支付成功
		//
		//
		// }else {
		// return BaseResult.fail("支付结果未知，请稍后在支付记录界面查询。");
		// }
		pointService.addZhima(user, 10, "zhima-bill-" + user.getId() + "-" + billId);
		if (couponId != null && couponId != "") {
			couponService.comsume(feePrice, Long.valueOf(couponId));
		}

		return BaseResult.successResult("支付成功。");
	}

	@RequestMapping(value = "/quickPayBillList/{stmtId}/{currPage}/{totalCount}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<BillListVO> quickPayBillList(@ModelAttribute(Constants.USER) User user,
			@PathVariable String stmtId, @PathVariable String currPage, @PathVariable String totalCount)
			throws Exception {
		BillListVO listVo = wuyeService.quickPayInfo(stmtId, currPage, totalCount);
		if (listVo != null) {
			return BaseResult.successResult(listVo);
		} else {
			return BaseResult.successResult(null);
		}
	}

	/***************** [END]缴费 ********************/

	/**
	 * 获取系统参数表中配置的活动时间 如果当前时间为活动时间段内，则: 1.推送短信给用户 2.推送微信模版消息给用户
	 * 3.跳转到成功页提示用户已获取代金券
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sendNotification", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult sendNotification(HttpSession session) {

		User user = (User) session.getAttribute(Constants.USER);
		String retValue = "false";
		String[] dates = systemConfigService.queryActPeriod();
		if (dates.length == 2) {
			String startDate = dates[0];
			String endDate = dates[1];

			Date sDate = DateUtil.getDateFromString(startDate);
			Date eDate = DateUtil.getDateFromString(endDate);
			Date currDate = new Date();
			if (currDate.after(sDate) && currDate.before(eDate)) {
				retValue = "true";
			}
			/* 如果在活动日期内，则：1发送短信告知用户。2推送微信模版消息 */
			if ("true".equals(retValue)) {
				sendMsg(user);
				sendRegTemplateMsg(user);
			}
		}

		return BaseResult.successResult(retValue);

	}

	@Async
	private void sendMsg(User user) {
		String msg = "您好，欢迎加入合协社区。您已获得价值10元红包一份。感谢您对合协社区的支持。";
		smsService.sendMsg(user, user.getTel(), msg, 11, 3);
	}

	@Async
	private void sendRegTemplateMsg(User user) {
		TemplateMsgService.sendRegisterSuccessMsg(user, systemConfigService.queryWXAToken(user.getAppId()));
	}

	/**
	 * 获取支付物业费时可用的红包
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCouponsPayWuYe", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<Coupon>> getCoupons(HttpSession session) {
		User user = (User) session.getAttribute(Constants.USER);
		List<Coupon> list = couponService.findAvaibleCouponForWuye(user.getId());

		if (list == null) {
			list = new ArrayList<Coupon>();
		}
		return BaseResult.successResult(list);

	}

	/**
	 * 为物业缴费成功的用户发放红包
	 * 
	 * @param session
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "sendCoupons4WuyePay", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult sendCoupons(HttpSession session, @RequestParam(required = false) String tradeWaterId,
			@RequestParam(required = false) String feePrice) {

		User user = (User) session.getAttribute(Constants.USER);
		int couponCombination = 1;
		List<CouponCombination> list = couponService.findCouponCombination(couponCombination);

		addCouponsFromSeed(user, list);

		sendPayTemplateMsg(user, tradeWaterId, feePrice);

		return BaseResult.successResult("send succeeded !");
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "updateCouponStatus", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult updateCouponStatus(HttpSession session) {

		if (session == null) {
			return BaseResult.fail("no session info ...");
		}

		User user = (User) session.getAttribute(Constants.USER);
		if (user == null) {
			return BaseResult.fail("user is null ...");
		}
		List<Coupon> list = couponService.findAvaibleCouponForWuye(user.getId());

		if (list.size() > 0) {
			String result = wuyeService.queryCouponIsUsed(user.getWuyeId());
			for (int i = 0; i < list.size(); i++) {
				Coupon coupon = list.get(i);
				if ((coupon.getStatus() == ModelConstant.COUPON_STATUS_AVAILABLE)) {

					if (!StringUtil.isEmpty(result)) {

						if ("99".equals(result)) {
							return BaseResult.fail("网络异常，请刷新后重试");
						}

						String[] couponArr = result.split(",");

						for (int j = 0; j < couponArr.length; j++) {
							String coupon_id = couponArr[j];
							try {
								couponService.comsume("20", Integer.parseInt(coupon_id)); // 这里写死20
							} catch (Exception e) {
								log.error("couponId : " + coupon_id + ", " + e.getMessage());
							}
						}

					}

				}

			}
		}

		return BaseResult.successResult("succeeded");

	}

	@Async
	private void addCouponsFromSeed(User user, List<CouponCombination> list) {

		try {

			for (int i = 0; i < list.size(); i++) {
				couponService.addCouponFromSeed(list.get(i).getSeedStr(), user);
			}

		} catch (Exception e) {

			log.error("add Coupons for wuye Pay : " + e.getMessage());
		}

	}

	@Async
	private void sendPayTemplateMsg(User user, String tradeWaterId, String feePrice) {

		TemplateMsgService.sendWuYePaySuccessMsg(user, tradeWaterId, feePrice, systemConfigService.queryWXAToken(user.getAppId()));
	}

	@RequestMapping(value = "/applyInvoice", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult applyInvoice(@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String invoice_title, @RequestParam(required = false) String yzm,
			@RequestParam(required = false) String trade_water_id,
			@RequestParam(required = false) String invoice_title_type,
			@RequestParam(required = false) String credit_code) {
		boolean isCheck = smsService.checkVerificationCode(mobile, yzm);
		if (!isCheck) {
			return new BaseResult<UserInfo>().failMsg("校验失败！");
		} else {
			String result = wuyeService.updateInvoice(mobile, invoice_title, invoice_title_type, credit_code,
					trade_water_id);
			if ("99".equals(result)) {
				return BaseResult.fail("网络异常，请刷新后重试");
			}
			return BaseResult.successResult("succeeded");
		}
	}

	@RequestMapping(value = "/getInvoice", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<InvoiceInfo> getInvoice(@RequestParam(required = false) String trade_water_id) {
		InvoiceInfo invoice = wuyeService.getInvoiceByTradeId(trade_water_id);

		if (invoice != null) {
			return BaseResult.successResult(invoice);
		} else {
			return BaseResult.successResult(null);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "initSession4Test/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult initSessionForTest(HttpSession session, @PathVariable String userId) {

		User user = (User) session.getAttribute(Constants.USER);
		if (session != null) {

			if (user == null) {
				user = new User();
				user.setCity("上海市");
				user.setCityId(20);
				user.setProvince("上海");
				user.setProvinceId(19);
				if (!StringUtil.isEmpty(userId)) {
					user.setId(Long.valueOf(userId));
				} else {
					user.setId(10);
				}
				user.setTel("18116419486");
				user.setOpenid("ofDNH0o8JD8qC1n2P9KU5qq0UeWc");
				user.setName("yiming");
				user.setNickname("yiming");
				user.setXiaoquName("西部花苑");
				user.setXiaoquId(26387);
				user.setCountyId(0);
				user.setWuyeId("180613400000291915");
				user.setHeadimgurl(
						"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBIY2Jial97RCIIyq0P4L8dhGicoYDlbNXqW5GJytxmkRDFdFlX9GScrsvo7vBuJuaEoMZeiaBPnb6AA/0");
			} else {
				if (!StringUtil.isEmpty(userId)) {

					user = userRepository.findOne(Long.valueOf(userId));
				}
			}

			// TODO set value on redis
			session.setAttribute("sessionUser", user);
		}

		return BaseResult.successResult("succeeded");

	}

	// 根据ID查询指定类型的合协社区物业信息
	@RequestMapping(value = "/getHeXieCellById", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<CellVO> getHeXieCellById(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String sect_id, @RequestParam(required = false) String build_id,
			@RequestParam(required = false) String unit_id, @RequestParam(required = false) String data_type)
			throws Exception {
		CellListVO cellMng = wuyeService.querySectHeXieList(sect_id, build_id, unit_id, data_type);
		if (cellMng != null) {
			return BaseResult.successResult(cellMng);
		} else {
			return BaseResult.successResult(new ArrayList<CellVO>());
		}
	}

	// 根据名称模糊查询合协社区小区列表
	@RequestMapping(value = "/getVagueSectByName", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<CellVO> getVagueSectByName(@ModelAttribute(Constants.USER) User user,
			@RequestParam(required = false) String sect_name) throws Exception {
		log.error("ceshi");

		CellListVO cellMng = wuyeService.getVagueSectByName(sect_name);
		if (cellMng != null) {
			return BaseResult.successResult(cellMng);
		} else {
			return BaseResult.successResult(new ArrayList<CellVO>());
		}
	}

	// 已绑定房子的用户设置默认地址
	@RequestMapping(value = "/setHasHouseUserDefaultAddr", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> setHasHouseUserDefaultAddr(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
		     wuyeService.addSectToRegion();
		     
		     wuyeService.addDefaultAddressAndUser();
		     
			log.error("默认地址设置完成11111111");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}
	
	// 更改默认地址
	@RequestMapping(value = "/updateAddr", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> updateAddr(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
		     wuyeService.updateAddr();
			log.error("操作完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}

    // 设置用户shareCode
	@RequestMapping(value = "/updateRepeatUserShareCode", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> updateRepeatUserShareCode(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
		     wuyeService.updateRepeatUserShareCode();
			log.error("操作完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}
	
	// 设置用户shareCode
	@RequestMapping(value = "/updateUserShareCode", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> updateUserShareCode(@RequestParam String code) throws Exception {
		
		if ("hexieCode".equals(code)) {
		     wuyeService.updateUserShareCode();
			log.error("操作完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}

   // 设置用户shareCode
	@RequestMapping(value = "/updateNonBindUser", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> updateNonBindUser(@RequestParam String code) throws Exception {
					if ("hexieCode".equals(code)) {
					     wuyeService.updateNonBindUser();
						log.error("操作完成!!!");
						return BaseResult.successResult("");
					} else {
						return BaseResult.fail("请求错误！！！");
					}

	} 
	
	//用户设置小区id公司id
	@RequestMapping(value = "/setHasHouseUserSectId", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> setHasHouseUserSectId(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
		     wuyeService.setHasHouseUserSectId();
			log.error("用户设置完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}
		
	//region表添加sectId
	@RequestMapping(value = "/addSectIdToRegion", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> addSectIdToRegion(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
		     wuyeService.addSectIdToRegion();
			log.error("region设置完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}
	
	//region表添加tempsect表的区域
	@RequestMapping(value = "/addTempSectToRegion", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> addTempSectToRegion(@RequestParam String code) throws Exception {
		if ("hexieCode".equals(code)) {
			  wuyeService.addSectToRegion();
			log.error("添加完成!!!");
			return BaseResult.successResult("");
		} else {
			return BaseResult.fail("请求错误！！！");
		}

	}
	

}
