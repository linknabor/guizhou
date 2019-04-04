package com.yumu.hexie.integration.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.common.Openids;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
/**
 * 用户管理
 */
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	/**
	 * 获取用户详细信息
	 */
	public static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取用户openid列表
	 */
	public static String GET_USER_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

	/**
	 * 获取用户详细信息
	 * 
	 * @param openid
	 * @return UserWeiXin 用户详细信息
	 */
	public static UserWeiXin getUserInfo(String openid, String accessToekn) {

		UserWeiXin user = null;

		String url = GET_USER_INFO.replace( "OPENID", openid);
		WechatResponse jsonObject = WeixinUtil.httpsRequest(url, "POST", null, accessToekn);

		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getErrcode())
					&& jsonObject.getErrcode() != 0) {
				log.error("获取用户信息失败 errcode:"
						+ jsonObject.getErrcode() + "，errmsg:"
						+ jsonObject.getErrmsg());
			} else {
				user = new UserWeiXin();
				user.setSubscribe(jsonObject.getSubscribe());
				user.setOpenid(jsonObject.getOpenid());
				user.setNickname(jsonObject.getNickname());
				user.setSex(jsonObject.getSex());
				user.setCity(jsonObject.getCity());
				user.setCountry(jsonObject.getCountry());
				user.setProvince(jsonObject.getProvince());
				user.setLanguage(jsonObject.getLanguage());
				user.setHeadimgurl(jsonObject.getHeadimgurl());
				long subscibeTime = jsonObject.getSubscribe_time();
				Date st = new Date(subscibeTime * 1000);
				user.setSubscribe_time(st);

			}
		}

		return user;
	}

	/**
	 * 获取关注者OpenID列表
	 * 
	 * @return List<String> 关注者openID列表
	 */
	public static List<String> getUserOpenIdList(String accessToken) {
		String url = GET_USER_OPENID_LIST.replace("NEXT_OPENID", "");
		WechatResponse jsonObject = WeixinUtil.httpsRequest(url, "POST", null, accessToken);
		if (null != jsonObject) {
			Openids data = jsonObject.getData();
			return data.getOpenid();
		}

		return new ArrayList<String>();
	}

	/**
	 * 获取关注者列表
	 * 
	 * @return List<UserWeiXin> 关注者列表信息
	 */
	public static List<UserWeiXin> getUserList(String accessToken) {
		List<UserWeiXin> list = new ArrayList<UserWeiXin>();

		// 获取关注用户openid列表
		List<String> listStr = getUserOpenIdList(accessToken);

		for (int i = 0; i < listStr.size(); i++) {
			// 根据openid查询用户信息
			UserWeiXin user = getUserInfo(listStr.get(i), accessToken);
			if (user != null) {
				list.add(user);
			}
		}
		return list;
	}

}
