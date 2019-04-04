package com.yumu.hexie.integration.wechat.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.common.CloseOrderResp;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.PaymentOrderResult;
import com.yumu.hexie.integration.wechat.entity.common.PrePaymentOrder;
import com.yumu.hexie.integration.wechat.util.MessageUtil;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.model.payment.PaymentOrder;

public class FundService {
	
	private static final String UNIPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	private static final String QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	private static final String CLOSE_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	/**
	 * 创建订单，调用微信统一支付接口
	 * @param map	map里须要包含:
	 * 交易金额:total_fee
	 * 用户标识:openid
	 * 商户订单号:out_trade_no
	 * 商品描述:body
	 * @param db
	 * @return
	 */
	public static PrePaymentOrder createOrder(PaymentOrder payOrder){
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			//throw new AppSystemException(e);
		} 
		
//		notify_url = "http://180.168.39.14/";	//TODO 暂时用财付通的测试
		
		String spbill_create_ip = addr.getHostAddress().toString();	//订单生成的机器IP
		String nonce_str = WeixinUtil.buildRandom();	//随机字符串
		Map<String,String> map = new HashMap<String, String>();
		map.put("appid", ConstantWeChat.APPID_PAY);
		map.put("mch_id", ConstantWeChat.MERCHANTID);
		map.put("notify_url", ConstantWeChat.NOTIFYURL);
		map.put("trade_type", ConstantWeChat.TRADETYPE);
		
		map.put("spbill_create_ip", spbill_create_ip);
		map.put("nonce_str", nonce_str);
		
		map.put("out_trade_no", payOrder.getPaymentNo());

		DecimalFormat decimalFormat=new DecimalFormat("0");
		String price = decimalFormat.format(payOrder.getPrice()*100);
		map.put("total_fee", price);
		map.put("body", payOrder.getProductName());
		map.put("openid", payOrder.getOpenId());
		
		String sign = WeixinUtil.createSign(map, ConstantWeChat.KEY);	//生成签名
		map.put("sign", sign);
		map.remove("key");	//只有生成签名的时候需要将key加入，组建XML时无须使用KEY，不然会报错。
		String requestXml = MessageUtil.createPayRequestXML(map);
		PrePaymentOrder r = (PrePaymentOrder)WeixinUtil.httpsRequestXml(
				UNIPAY_URL,  "POST", requestXml,PrePaymentOrder.class);
		return r; 
	}
	
	/**
	 * 订单查询接口
	 * @param out_trade_no
	 * @param db
	 * @return Map<String , String>  
	 * 	return_code 通信结果
	 * 	return_msg	返回错误信息（有错误才有）
	 * 	result_code 业务结果
	 * 	err_code	错误代码（对应业务结果）
	 * 	err_code_des错误描述（对应业务结果）
	 * 	trade_state 交易状态
	 * 
	 */
	public static PaymentOrderResult queryOrder(String out_trade_no){
		Map<String, String>map = new TreeMap<String, String>();
		String nonceStr = WeixinUtil.buildRandom();	//随机字符串
		map.put("appid", ConstantWeChat.APPID_PAY);
		map.put("mch_id", ConstantWeChat.MERCHANTID);
		map.put("key", ConstantWeChat.KEY);
		map.put("nonce_str", String.valueOf(nonceStr));
		map.put("out_trade_no", out_trade_no);
		String sign = WeixinUtil.createSign(map, ConstantWeChat.KEY);
		//组装发送的XML
		map.put("sign", sign);
		map.remove("key");
		String requestXml = JacksonJsonUtil.mapToXml(map);
		PaymentOrderResult r = (PaymentOrderResult)WeixinUtil.httpsRequestXml(
				QUERY_URL, "POST", requestXml, PaymentOrderResult.class);
		return r;
	}
	
	
	/**
	 * 订单查询接口
	 * @param out_trade_no
	 * @param db
	 * @return Map<String , String>  
	 * 	return_code 通信结果
	 * 	return_msg	返回错误信息（有错误才有）
	 * 	result_code 业务结果
	 * 	err_code	错误代码（对应业务结果）
	 * 	err_code_des错误描述（对应业务结果）
	 * 	trade_state 交易状态
	 * 
	 */
	public static CloseOrderResp closeOrder(String out_trade_no){
		Map<String, String>map = new TreeMap<String, String>();
		String nonceStr = WeixinUtil.buildRandom();	//随机字符串
		map.put("appid", ConstantWeChat.APPID_PAY);
		map.put("mch_id", ConstantWeChat.MERCHANTID);
		map.put("key", ConstantWeChat.KEY);
		map.put("nonce_str", String.valueOf(nonceStr));
		map.put("out_trade_no", out_trade_no);
		String sign = WeixinUtil.createSign(map, ConstantWeChat.KEY);
		//组装发送的XML
		map.put("sign", sign);
		map.remove("key");
		String requestXml = JacksonJsonUtil.mapToXml(map);
		CloseOrderResp r = (CloseOrderResp)WeixinUtil.httpsRequestXml(
				CLOSE_URL, "POST", requestXml, CloseOrderResp.class);
		return r;
	}
	public static void main(String[] args) {

		float a = 10.00008f;
		System.out.println(""+a);
		System.out.println(""+a*100);
		System.out.println(""+(int)(100*a));
		
		DecimalFormat decimalFormat=new DecimalFormat("10");
		System.out.println(""+decimalFormat.format(a*100));
		//queryOrder("20150724173347933979");
/*		ServiceOrder so = new ServiceOrder();
		so.setOrderNo("xxx0000002");
		so.setProductName("两只老虎");
		so.setPrice(0.01f);
		so.setOpenId("ogB9nt8HLQUwi2zqdAVawqmEEgHI");
		PaymentOrder po = new PaymentOrder(so);
		po.setPaymentNo("xxx0000002");
		//wx201508081606545ce396038b0671869461
		//wx2015080816071024956cd5080956731730
		createOrder(so,po);*/
		
		
		
		//		closeOrder("20150724173347933979");
//		System.out.println("----------------------");
		
		//queryOrder("201508101803R65475");
		//queryOrder("201508101809P11817");
		//queryOrder("201508111356P91118");
		
	}
	
	public static JsSign getPrepareSign(String prepay_id) {
		Map<String,String> map = new HashMap<String, String>();
		JsSign r = new JsSign();
		r.setAppId(ConstantWeChat.APPID_PAY);
		r.setTimestamp(""+(int)(System.currentTimeMillis()/1000));
		r.setNonceStr(WeixinUtil.buildRandom());
		r.setPkgStr("prepay_id=" + prepay_id);
		
		map.put("appId", r.getAppId());
		map.put("timeStamp", r.getTimestamp());
		map.put("nonceStr", r.getNonceStr());
		map.put("package", "prepay_id=" + prepay_id);
		map.put("signType", "MD5");
		r.setSignature(WeixinUtil.createSign(map, ConstantWeChat.KEY));
		return r;
	}
	/**
	 * 获取微信服务器端返回的支付结果通知
	 * @param requet
	 * @param response
	 * @return
	 */
//	public static Map<String, String> getNotify(HttpServletRequest request, HttpServletResponse response){
//		return null;//FIXME
//	}
}
