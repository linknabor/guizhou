package com.yumu.hexie.integration.wechat.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.customer.CustomerBaseMessage;
import com.yumu.hexie.integration.wechat.entity.customer.MediaMessage;
import com.yumu.hexie.integration.wechat.entity.customer.MusicMessage;
import com.yumu.hexie.integration.wechat.entity.customer.NewsMessage;
import com.yumu.hexie.integration.wechat.entity.customer.TextMessage;
import com.yumu.hexie.integration.wechat.entity.customer.VideoMessage;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;

/**
 * 发送客服消息
 */
public class CustomService {

	private static String CUSTOME_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public static Map<String, CustomerBaseMessage> bulidMessageMap = new HashMap<String, CustomerBaseMessage>();
	
	static {
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
	}
	
	/**
	 * 发送客服消息
	 * @param obj	消息对象
	 * @return 是否发送成功
	 */
	public static boolean sendCustomerMessage(Object obj, String accessToken) {
		boolean bo = false;
		String jsonStr;
		try {
			jsonStr = JacksonJsonUtil.beanToJson(obj);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		WechatResponse jsonObject = WeixinUtil.httpsRequest(CUSTOME_URL, "POST", jsonStr, accessToken);
		if (null != jsonObject&&jsonObject.getErrcode()==0) {
			bo = true;
		}
		return bo;
	}

	
	/**
	 * 构建基本消息
	 * 
	 * @param toUser
	 *            接受者用户openId
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public static Object bulidCustomerBaseMessage(String toUser,
			String msgType) {
		CustomerBaseMessage message = bulidMessageMap.get(msgType);
		message.setTouser(toUser);
		message.setMsgtype(msgType);
		return message;
	}

}
