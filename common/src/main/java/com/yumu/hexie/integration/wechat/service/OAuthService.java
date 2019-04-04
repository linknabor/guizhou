package com.yumu.hexie.integration.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.AccessTokenOAuth;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.user.UserWeiXin;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;

/**
 * oAuth服务
 */
public class OAuthService {

	private static final Logger log = LoggerFactory.getLogger(OAuthService.class);
	
	/**
	 * wechat oauth url
	 */
	public static String OAUTH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	/**
	 * 通过oauth获取用户详细信息
	 */
	public static String GET_USER_INFO_OAUTH = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/**
	 * 获取oauth网页认证的token
	 */
	public static String GET_ACCESS_TOKEN_OAUTH = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 获得Oauth认证的URL
	 * @param redirectUrl	跳转的url
	 * @param charset	字符集格式
	 * @param scope	OAUTH scope
	 * @return oauth url
	 */
	public static String getOauthUrl(String redirectUrl,String charset,String scope){
		String url = "";
		try {
			url = OAUTH
					.replace("APPID", ConstantWeChat.APPID)
					.replace("REDIRECT_URI",
							URLEncoder.encode(redirectUrl, charset))
					.replace("SCOPE", scope);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * 获取Access_Token（oAuth认证,此access_token与基础支持的access_token不同）
	 * 
	 * @param code
	 *            用户授权后得到的code
	 * @return AccessTokenOAuth对象
	 */
	public static AccessTokenOAuth getOAuthAccessToken(String code) {
		String url = GET_ACCESS_TOKEN_OAUTH
				.replace("APPID", ConstantWeChat.APPID)
				.replace("SECRET", ConstantWeChat.APPSECRET)
				.replace("CODE", code);

		WechatResponse jsonObject = WeixinUtil.httpsRequest(url, "POST", null, null);

		AccessTokenOAuth accessTokenOAuth = null;

		if (null != jsonObject&&jsonObject.getErrcode() == 0) {
			accessTokenOAuth = new AccessTokenOAuth();
			accessTokenOAuth.setAccessToken(jsonObject.getAccess_token());
			accessTokenOAuth.setExpiresIn(jsonObject.getExpires_in());
			accessTokenOAuth.setRefreshToken(jsonObject.getRefresh_token());
			accessTokenOAuth.setOpenid(jsonObject.getOpenid());
			accessTokenOAuth.setScope(jsonObject.getScope());
		}
		return accessTokenOAuth;
	}

	/**
	 * 通过oauth获取用户详细信息
	 * 
	 * @param token
	 * @param openid
	 * @return UserWeiXin对象
	 */
	public static UserWeiXin getUserInfoOauth(String token, String openid) {
		UserWeiXin user = null;
		if (token != null) {

			String url = GET_USER_INFO_OAUTH.replace("ACCESS_TOKEN", token)
					.replace("OPENID", openid);

			WechatResponse jsonObject = WeixinUtil.httpsRequest(url, "POST", null, null);

			if (null != jsonObject&&jsonObject.getErrcode() == 0){
				user = new UserWeiXin();
				
				user.setOpenid(jsonObject.getOpenid());
				user.setNickname(jsonObject.getNickname());
				user.setSex(jsonObject.getSex());
				user.setCity(jsonObject.getCity());
				user.setCountry(jsonObject.getCountry());
				user.setProvince(jsonObject.getProvince());
				user.setLanguage(jsonObject.getLanguage());
				user.setPrivilege(jsonObject.getPrivilege());
				user.setHeadimgurl(jsonObject.getHeadimgurl());
			}
		}
		return user;
	}
	
	/**
	 * 获取 主公众号（承担支付通道的主公众号） 授权TOKEN
	 * @param code
	 * @return
	 */
	public static AccessTokenOAuth getBindOAuthAccessToken(String code) {
		
		String appId = ConstantWeChat.BIND_APPID;
		String secret = ConstantWeChat.BIND_APPSECRET;
		
		log.error("bindAppId: " + appId);
		log.error("bindAppSecret: " + secret);
		
		String url = GET_ACCESS_TOKEN_OAUTH
				.replace("APPID", ConstantWeChat.BIND_APPID)
				.replace("SECRET", ConstantWeChat.BIND_APPSECRET)
				.replace("CODE", code);
		
		log.error("url: " + url);

		WechatResponse jsonObject = WeixinUtil.httpsRequest(url, "POST", null, null);

		AccessTokenOAuth accessTokenOAuth = null;

		if (null != jsonObject&&jsonObject.getErrcode() == 0) {
			accessTokenOAuth = new AccessTokenOAuth();
			accessTokenOAuth.setAccessToken(jsonObject.getAccess_token());
			accessTokenOAuth.setExpiresIn(jsonObject.getExpires_in());
			accessTokenOAuth.setRefreshToken(jsonObject.getRefresh_token());
			accessTokenOAuth.setOpenid(jsonObject.getOpenid());
			accessTokenOAuth.setScope(jsonObject.getScope());
		}
		return accessTokenOAuth;
	}
	
}
