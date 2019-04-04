package com.yumu.hexie.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.integration.wechat.entity.AccessTokenOAuth;
import com.yumu.hexie.integration.wechat.entity.common.CloseOrderResp;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.PaymentOrderResult;
import com.yumu.hexie.integration.wechat.entity.common.PrePaymentOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundResp;
import com.yumu.hexie.integration.wechat.entity.customer.Text;
import com.yumu.hexie.integration.wechat.entity.message.req.CommonMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.TextMessage;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wechat.service.CustomService;
import com.yumu.hexie.integration.wechat.service.FundService;
import com.yumu.hexie.integration.wechat.service.MessageService;
import com.yumu.hexie.integration.wechat.service.OAuthService;
import com.yumu.hexie.integration.wechat.service.RefundService;
import com.yumu.hexie.integration.wechat.service.SignService;
import com.yumu.hexie.integration.wechat.service.UserService;
import com.yumu.hexie.integration.wechat.util.MessageUtil;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.payment.RefundOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.WechatException;
import com.yumu.hexie.service.user.CouponService;

@Service(value = "wechatCoreService")
public class WechatCoreServiceImpl implements WechatCoreService {

	private static final Logger SCHEDULE_LOG = LoggerFactory.getLogger("com.yumu.hexie.schedule");
	public AccessToken at;
	public String jsTicket = "";
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatCoreServiceImpl.class);
	@Inject
	private com.yumu.hexie.service.user.UserService userService;
	@Inject
	private SystemConfigService systemConfigService;
	@Inject 
	private CouponService couponService;


	// FIXME 暂时没用
	@Override
	public String processWebchatRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			CommonMessage requestMap = MessageUtil.parseXml2(request);
			LOGGER.error("processWebchatRequest:"
					+ JacksonJsonUtil.beanToJson(requestMap));

			// 消息类型
			String msgType = requestMap.getMsgType();

			TextMessage textMessage = (TextMessage) MessageService
					.bulidBaseMessage(requestMap,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);

			String respContent = "";
			// wait
			switch (msgType) {
			case ConstantWeChat.REQ_MESSAGE_TYPE_TEXT:// 文本消息
				break;
			case ConstantWeChat.REQ_MESSAGE_TYPE_EVENT:// 事件消息

				// 事件类型
				switch (requestMap.getEvent()) {
				case ConstantWeChat.EVENT_TYPE_SUBSCRIBE:// 订阅
					respContent = "欢迎来到东湖e家园\n"
							+ "【我的房子】物业费缴纳、账单查询\n"
							+ "【到家服务】一键预约，随叫随到\n"
							+ "【限时特卖】每日限时劲爆商品抢鲜\n"
							+ "【客服】如有问题咨询021-50876295\n";
					LOGGER.error("用户关注！");
					// userService.getOrSubscibeUserByOpenId();

					User userAccount = userService.getByOpenId(requestMap.getFromUserName());
					if(userAccount == null) {
						User u = userService.getByOpenId(requestMap.getFromUserName());
						couponService.addCoupon4Subscribe(u);
					}
					break;
				case ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE:// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息
					LOGGER.error("用户取消关注！");
					break;
				case ConstantWeChat.EVENT_TYPE_CLICK:
					LOGGER.error("用户点击按钮：" + requestMap.getEventKey());
					break;
				case ConstantWeChat.EVENT_TYPE_LOCATION:
					LOGGER.error("用户定位：" + requestMap.getLatitude() + ","
							+ requestMap.getLongitude());
					break;
				case ConstantWeChat.EVENT_TYPE_VIEW:
					LOGGER.error("用户点击跳转：" + requestMap.getEventKey());
					break;
				case ConstantWeChat.EVENT_TYPE_SCAN:
					LOGGER.error("用户扫码："
							+ JacksonJsonUtil.beanToJson(requestMap
									.getScanCodeInfo()));
					com.yumu.hexie.integration.wechat.entity.customer.TextMessage tm = (com.yumu.hexie.integration.wechat.entity.customer.TextMessage) CustomService
							.bulidCustomerBaseMessage(
									"oMRpmtwU9wJ6nfngPYjg_PX66RTg",
									ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
					tm.setText(new Text("有用户扫码："
							+ JacksonJsonUtil.beanToJson(requestMap
									.getScanCodeInfo())));
					CustomService.sendCustomerMessage(tm, systemConfigService.queryWXAToken());
					LOGGER.error("用户扫码：发送完毕");
					break;
				}

				if (respMessage == null) {
					textMessage.setContent(respContent);
					respMessage = MessageService.bulidSendMessage(textMessage,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				}

				break;
			default:
				;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}


	@Override
	public JsSign getPrepareSign(String prepay_id) {
		try {
			return FundService.getPrepareSign(prepay_id);
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

	private void processError(Exception e) {
		LOGGER.error("微信异常----------------------------------------------！！！！！！！！！！！", e);
		if (e instanceof WechatException) {
			SCHEDULE_LOG.error("process error" , e);
		}
	}

	@Override
	public JsSign getJsSign(String url) {
		try {
			return WeixinUtil.getJsSign(url,systemConfigService.queryJsTickets());
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

	@Override
	public boolean checkSignature(String signature, String timestamp,
			String nonce) {
		try {
			return SignService.checkSignature(signature, timestamp, nonce);
		} catch (Exception e) {
			processError(e);
		}
		return false;
	}
	@Override
	public UserWeiXin getUserInfo(String openid) {
		try {
			return UserService.getUserInfo(openid, systemConfigService.queryWXAToken());
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}
	@Override
	public List<UserWeiXin> getUserList() {
		try {
			return UserService.getUserList(systemConfigService.queryWXAToken());
		} catch (Exception e) {
			LOGGER.error("获取用户列表失败",e);
			processError(e);
		}
		return new ArrayList<UserWeiXin>();
	}

	@Override
	public UserWeiXin getByOAuthAccessToken(String code) {
		try {
		    AccessTokenOAuth auth =  OAuthService.getOAuthAccessToken(code);
	        return OAuthService.getUserInfoOauth(auth.getAccessToken(),auth.getOpenid());
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

	@Override
	public PrePaymentOrder createOrder(PaymentOrder payOrder) {
		try {
			return FundService.createOrder(payOrder);
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}
	@Override
	public CloseOrderResp closeOrder( PaymentOrder payOrder) {
		try {
			return FundService.closeOrder(payOrder.getPaymentNo());
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}
	

	@Override
	public PaymentOrderResult queryOrder(String out_trade_no) {
		try {
			return FundService.queryOrder(out_trade_no);
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

	@Override
	public WxRefundResp requestRefund(RefundOrder refund) {
		try {
			return RefundService.requestRefund(refund);
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

	@Override
	public WxRefundOrder refundQuery(String outTradeNo) {
		try {
			return RefundService.refundQuery(outTradeNo);
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}


	@Override
	public String getBindOpenId(String code) {
		try {
		    AccessTokenOAuth auth =  OAuthService.getBindOAuthAccessToken(code);
	        return auth.getOpenid();
		} catch (Exception e) {
			processError(e);
		}
		return null;
	}

}
