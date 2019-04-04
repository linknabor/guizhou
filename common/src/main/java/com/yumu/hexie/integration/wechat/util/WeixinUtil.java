package com.yumu.hexie.integration.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MD5Util;
import com.yumu.hexie.common.util.MyX509TrustManager;
import com.yumu.hexie.common.util.Sha1Util;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.integration.wechat.entity.common.JsSign;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.service.exception.WechatException;

/**
 * 微信通用接口工具类
 */
public class WeixinUtil {

	private static final Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 获取access_token的接口地址（GET） 限2000（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public final static String JS_TICKET ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	private final static String NONCESTR = "Wm3WZYTP1z0wzccnW";
	private final static String JS_T_PARAM = "jsapi_ticket=JS_TICKET&noncestr="+NONCESTR+"&timestamp=TIMESTAMP&url=URL";
	public static AccessToken at;
	public static String jsTicket="";
	
	public static String getRefreshJsTicket(String accessToken) {
		if(!ConstantWeChat.isMainServer())return jsTicket;
		WechatResponse jsonObject = httpsRequest(JS_TICKET, "GET", null, accessToken);
		// 如果请求成功
		if (null != jsonObject && StringUtil.isNotEmpty(jsonObject.getTicket())) {
			return jsonObject.getTicket();
		}
		return null;
	}
	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken getAccessToken() {
		if(!ConstantWeChat.isMainServer())return at;
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", ConstantWeChat.APPID).replace(
				"APPSECRET", ConstantWeChat.APPSECRET);
		WechatResponse jsonObject = httpsRequest(requestUrl, "GET", null, null);
		// 如果请求成功
		if (null != jsonObject && StringUtil.isNotEmpty(jsonObject.getAccess_token())) {
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getAccess_token());
			accessToken.setExpiresIn(jsonObject.getExpires_in());
		}
		return accessToken;
	}
	public static void setJsTicket(String jsTicker){
		WeixinUtil.jsTicket=jsTicker;
	}
	
	public static void setAccessToken(AccessToken t) {
		WeixinUtil.at = t;
	}
	
	public static String getJsTicket() {
		if(StringUtil.isEmpty(jsTicket)){
			throw new WechatException(1,"JSToken获取失败");
		}
		return jsTicket;
	}
	
	public static JsSign getJsSign(String url, String jsTicket) {
		long timestamp = new Date().getTime();
		String param = JS_T_PARAM.replace("JS_TICKET",jsTicket).replace("TIMESTAMP", ""+timestamp).replace("URL", url);
		JsSign r = new JsSign();
		r.setAppId(ConstantWeChat.APPID);
		r.setNonceStr(NONCESTR);
		r.setSignature(Sha1Util.getSha1(param));
		r.setTimestamp(""+timestamp);
		return r;
	}


	/**
	 * 获取token值
	 * 
	 * @return token
	 */
	public static String getToken() {
		if(at == null){
			throw new WechatException(2,"Token获取失败");
		}else {
			return at.getToken();
		}
	}
//	public static String getToken() {
//		if(at == null) {
//			at = WeixinUtil.getAccessToken();
//			return at.getToken();
//		} else {
//			return at.getToken();
//		}
//	}
	/**
	 * 获取token值
	 * 
	 * @return token
	 */
	
	public static WechatResponse httpsRequest(String requestUrl,
			String requestMethod, String outputStr, String accessToken) {
		if(StringUtil.isNotEmpty(accessToken)){
	        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
	    }
		WechatResponse jsonObject = null;
		try {
			String result = queryForString(requestUrl, requestMethod, outputStr);
			jsonObject = (WechatResponse)JacksonJsonUtil.jsonToBean(result, WechatResponse.class);
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}

	private static String queryForString(String requestUrl,
			String requestMethod, String outputStr)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			KeyManagementException, MalformedURLException, IOException,
			ProtocolException, UnsupportedEncodingException {
		log.error("wechat request:"+requestUrl );
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		StringBuffer buffer = new StringBuffer();
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
				.openConnection();
		httpUrlConn.setSSLSocketFactory(ssf);

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);

		if ("GET".equalsIgnoreCase(requestMethod)) {
			httpUrlConn.connect();
		}

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}

		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();
		
		String result = buffer.toString();

		log.error("wechat response:" + result);
		return result;
	}

	public static Object httpsRequestXml(String requestUrl,
			String requestMethod, String outputStr,Class c) {
		Object jsonObject = null;
		try {
			String result = queryForString(requestUrl, requestMethod, outputStr);
			log.error("wechat response:" + result+"  |||  request:"+requestUrl +"("+outputStr+")");
			jsonObject = JacksonJsonUtil.jsonToBean(JacksonJsonUtil.xml2json(result), c);
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}
	

	public static Object httpsRequestXmlWithStore(String requestUrl,
			String requestMethod, String outputStr,Class c) {
		Object jsonObject = null;
		try {
			String result = queryByKeyStore(requestUrl, requestMethod, outputStr);
			jsonObject = JacksonJsonUtil.jsonToBean(JacksonJsonUtil.xml2json(result), c);
		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:", e);
		}
		return jsonObject;
	}
	
	public static String queryByKeyStore(String requestUrl,
			String requestMethod, String requestStr){
		KeyStore keyStore = null;
		FileInputStream instream = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String responseStr = null;
		
		try {
			keyStore  = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(ConstantWeChat.KEYSTORE));
			keyStore.load(instream, ConstantWeChat.MERCHANTID.toCharArray());
			
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, ConstantWeChat.MERCHANTID.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        
	        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			
	        HttpPost httpPost = new HttpPost(requestUrl);
	        StringEntity reqEntity  = new StringEntity(requestStr, WechatConfig.INPUT_CHARSET);

            reqEntity.setContentType("application/x-www-form-urlencoded"); 
            httpPost.setEntity(reqEntity);
            
            response = httpclient.execute(httpPost);

        	HttpEntity entity = response.getEntity();
            
            if (entity != null) {
            	InputStream input = entity.getContent();
            	byte[]bytes = new byte[1024];
            	input.read(bytes);
                responseStr = new String(bytes, WechatConfig.INPUT_CHARSET);	//תUTF-8
                responseStr = responseStr.trim();
            }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return responseStr;
            
	}
	
	/**
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param createTime
	 *            消息创建时间
	 * @return String
	 */
	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}
	
	public static String buildRandom() {
		
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 元转分
	 * @param money
	 * @return
	 */
	public static String yuan2fen(String money){
		
		BigDecimal tran_amt = new BigDecimal(money);
		tran_amt = tran_amt.multiply(new BigDecimal("100"));	//元转分
		tran_amt = tran_amt.setScale(0);	//去掉.00的小数位，财付通以分计单位
		return tran_amt.toString();
	}
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String createSign(Map<String ,String>map, String key) {
		
		String charset = (String)map.get("input_charset");
		if (StringUtil.isEmpty(charset)) {
			charset = WechatConfig.INPUT_CHARSET;
		}
		StringBuffer sb = new StringBuffer();
		List<String> es = new ArrayList<String>(map.keySet());
		Collections.sort(es);
		
		for(String k : es) {
			String v = map.get(k);
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		return MD5Util.MD5Encode(sb.toString(),charset).toUpperCase();
	}
}