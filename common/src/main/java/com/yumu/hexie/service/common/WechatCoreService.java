package com.yumu.hexie.service.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yumu.hexie.integration.wechat.entity.common.CloseOrderResp;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.PaymentOrderResult;
import com.yumu.hexie.integration.wechat.entity.common.PrePaymentOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundOrder;
import com.yumu.hexie.integration.wechat.entity.common.WxRefundResp;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.model.payment.PaymentOrder;
import com.yumu.hexie.model.payment.RefundOrder;

public interface WechatCoreService {

	public String processWebchatRequest(HttpServletRequest request);
	public JsSign getJsSign(String url);
	public boolean checkSignature(String signature, String timestamp,
			String nonce);

	public UserWeiXin getUserInfo(String openid);
	public UserWeiXin getByOAuthAccessToken(String code);
	public String getBindOpenId(String code);
    public List<UserWeiXin> getUserList();
	

	public PrePaymentOrder createOrder(PaymentOrder payOrder);
	public CloseOrderResp closeOrder(PaymentOrder payOrder);
	public PaymentOrderResult queryOrder(String out_trade_no);
	public JsSign getPrepareSign(String prepay_id) ;
	
	public WxRefundResp requestRefund(RefundOrder refund);
	public WxRefundOrder refundQuery(String outTradeNo);
	

}