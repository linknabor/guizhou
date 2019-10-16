package com.yumu.hexie.service.common.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.integration.wechat.entity.AccessTokenOAuth;
import com.yumu.hexie.integration.wechat.entity.common.CloseOrderResp;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.PaymentOrderResult;
import com.yumu.hexie.integration.wechat.entity.common.PrePaymentOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundResp;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wechat.service.FundService;
import com.yumu.hexie.integration.wechat.service.OAuthService;
import com.yumu.hexie.integration.wechat.service.RefundService;
import com.yumu.hexie.integration.wechat.service.SignService;
import com.yumu.hexie.integration.wechat.service.UserService;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.payment.RefundOrder;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.common.WechatCoreService;
import com.yumu.hexie.service.exception.WechatException;

@Service(value = "wechatCoreService")
public class WechatCoreServiceImpl implements WechatCoreService {

	private static final Logger SCHEDULE_LOG = LoggerFactory.getLogger("com.yumu.hexie.schedule");
	public AccessToken at;
	public String jsTicket = "";
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatCoreServiceImpl.class);
	@Inject
	private SystemConfigService systemConfigService;
	
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


}
