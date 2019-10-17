package com.yumu.hexie.integration.eucp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MyHttpClient;

public class YimeiUtil {
	private static final Logger Log = LoggerFactory.getLogger(YimeiUtil.class);

	private static final String cdkey = "6SDK-EMY-6688-KFYSS";
	private static final String password = "096092";
	private static final String urlStr = "http://sdk4report.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
	private static final String MSG_CHARSET = "UTF-8";
	public static boolean sendMessage(String mobile,String message,long id) {
		String msgId = ""+id;
		String sendContent;
		try {
			sendContent = URLEncoder.encode(message, MSG_CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			sendContent = message;
		}
		StringBuffer bufUrl = new StringBuffer();
		bufUrl.append(urlStr).append("?cdkey=").append(cdkey).append("&password=").append(password);
		bufUrl.append("&phone=").append(mobile).append("&message=").append(sendContent).append("&addserial=").append(msgId);
		String urlContent = bufUrl.toString();
		YimeiResult r = (YimeiResult)httpGet(urlContent, YimeiResult.class);
		if(r == null){
			return false;
		}
		return r.isSuccess();
	}
	
	public static void main(String args[]) {
		sendMessage("18019236112", "测试222",12);
	}
	
	private static Object httpGet(String reqUrl, Class c) {

		HttpGet get = new HttpGet(reqUrl);
		String resp;
		try {
			Log.error("REQ:" + reqUrl);
			resp = MyHttpClient.getStringFromResponse(
					MyHttpClient.execute(get), "UTF-8").trim();
			Log.error("RESP:" + resp);
			String json = JacksonJsonUtil.xml2json(resp);
			return JacksonJsonUtil.jsonToBean(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
