package com.yumu.hexie.integration.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;

/**
 * 二维码
 */
public class QRcodeService {

	private static final Logger log = LoggerFactory.getLogger(QRcodeService.class);

	/**
	 * 获取ticket
	 */
	static String QRCODE_ACTION = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

	/**
	 * 获取二维码url
	 */
	static String QRCODE_IMG_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/**
	 * 二维码类型，临时
	 */
	public static final String QRCODE_SCENE = "QR_SCENE";
	
	/**
	 * 二维码类型，永久
	 */
	public static final String QRCODE_LIMIT_SCENE = "QR_LIMIT_SCENE";

	/**
	 * 获取ticket
	 * 
	 * @param actionName
	 *            二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 * @param sceneId
	 *            场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return ticket
	 */
	public static String getTicket(String actionName, int sceneId, String accessToken) {
		String qrdata = "{\"action_name\": \"" + actionName
				+ "\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId
				+ "}}}";

		WechatResponse jsonObject = WeixinUtil.httpsRequest(QRCODE_ACTION, "POST", qrdata, accessToken);
		if (null != jsonObject&&jsonObject.getErrcode() == 0) {
			return jsonObject.getTicket();
				
		} else {
			if(jsonObject != null)
			log.error("二维码ticket请求失败，errcode:"
					+ jsonObject.getErrcode() + "，errmsg:"
					+ jsonObject.getErrmsg());
			return "";
		}
		

	}

	/**
	 * 获取二维码图片链接地址
	 * 
	 * @param ticket
	 * @return 二维码图片的url
	 */
	public static String getQrCodeImgURL(String ticket) {
		try {
			ticket = URLEncoder.encode(ticket, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return QRCODE_IMG_URL.replace("TICKET", ticket);
	}

}
