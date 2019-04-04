package com.yumu.hexie.integration.wechat.service;

import java.util.HashMap;
import java.util.Map;

import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.message.req.CommonMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.BaseMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.MediaMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.MusicMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.NewsMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.TextMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.VideoMessage;
import com.yumu.hexie.integration.wechat.util.MessageUtil;

/**
 * 消息处理
 */
public class MessageService {

	public static Map<String, BaseMessage> bulidMessageMap = new HashMap<String, BaseMessage>();
	
	static {
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
	}
	
	/**
	 * 构建基本消息
	 * 
	 * @param requestMap
	 *            xml请求解析后的map
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public static Object bulidBaseMessage(CommonMessage msg,
			String msgType) {
		BaseMessage message = bulidMessageMap.get(msgType);
		message.setToUserName(msg.getFromUserName());
		message.setFromUserName(msg.getToUserName());
		message.setCreateTime(msg.getCreateTime());
		message.setMsgType(msgType);
		message.setFuncFlag(0);
		return message;
	}

	/**
	 * 发送消息接口
	 * 
	 * @param obj
	 *            消息对象
	 * @param msgType
	 *            消息类型
	 * @return 返回xml格式数据给微信
	 */
	public static String bulidSendMessage(Object obj, String msgType) {
		String responseMessage = "";

		// 图文消息处理
		if (msgType == ConstantWeChat.RESP_MESSAGE_TYPE_NEWS) {
			responseMessage = MessageUtil.newsMessageToXml((NewsMessage) obj);
		} else {// 其他消息处理
			responseMessage = MessageUtil.messageToXml(obj);
		}
		return responseMessage;
	}

}
