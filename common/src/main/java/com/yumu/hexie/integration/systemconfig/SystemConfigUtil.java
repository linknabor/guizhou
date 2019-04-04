package com.yumu.hexie.integration.systemconfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.MyHttpClient;

/**
 * @author huym
 *
 */
public class SystemConfigUtil {

	private static final Logger log = LoggerFactory.getLogger(SystemConfigUtil.class);
	
	private static String REQUEST_ADDRESS = null;
	private static String BIND_APPID = null;
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("wechat.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		REQUEST_ADDRESS = props.getProperty("updateCacheUrl");
		BIND_APPID = props.getProperty("bindAppId");
	}
	
	/**
	 * 通知刷新缓存
	 * @return
	 */
	public static int notifyRefreshing(String key){
		
		int ret = 0;
		String value = "";
		String url = String.format(REQUEST_ADDRESS, BIND_APPID, key);
		Object object = httpGet(url, String.class);
		
		if (object != null) {
			value = (String)object;
		}
		
		try {
			Map<String, Object> map = JacksonJsonUtil.json2map(value);
			Object code = map.get("code");
			ret = (Integer)code;
			
		} catch (Exception e) {

			log.error(e.getMessage());
		}
		return ret;
		
	}
	
	@SuppressWarnings("rawtypes")
	private static Object httpGet(String reqUrl, Class c) {

		HttpGet get = new HttpGet(reqUrl);
		
		Object retObj = null;
		try {
			log.error("REQ:" + reqUrl);
			retObj = MyHttpClient.getStringFromResponse(MyHttpClient.execute(get), "UTF-8");
			log.error("RESP:" + retObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retObj;
	}
	

}
