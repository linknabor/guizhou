package com.yumu.hexie.common.util;

import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;

public class AppUtil {

	 /**
     * 是否为主公众号的appId
     * @param appId
     * @return
     */
    public static boolean isMainApp(String appId) {
    	
    	boolean flag = false;
    	String mainAppId = ConstantWeChat.APPID;
    	if (mainAppId.equals(appId)) {
			flag = true;
		}
    	return flag;
    }
    
    /**
     * 给链接添加oriApp参数，如果是vue的路由，则需要拼接在#前
     * @param url
     * @param appId
     * @return
     */
    public static String addAppOnUrl(String url, String appId) {
    	
    	int index = url.indexOf("#");
		if (index>-1) {
			String[]tmp = url.split("#");
			tmp[0] += "?oriApp=" + appId;
			tmp[0] += "#";
			url = tmp[0] + tmp[1];
		}else {
			if (url.indexOf("?")>-1) {
				url += "&oriApp=" + appId;
			}else {
				url += "?oriApp=" + appId;
			}
			
		}
		
		return url;
    }
    
    public static void main(String[] args) {
		
    	String url = "https://test.e-shequ.com/weixin/person/index.html#/operatorOrdersDetail?ordersID=309";
    	
    	url = "https://test.e-shequ.com/weixin/coupon.html?o=123";
    	url = "https://test.e-shequ.com/weixin/person/coupons.html";
    	
    	String str = addAppOnUrl(url, "wxa48ca61b68163483");
    	System.out.println(str);
    	
	}
    
}
