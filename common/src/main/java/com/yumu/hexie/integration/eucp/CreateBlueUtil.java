/**
 * 
 */
package com.yumu.hexie.integration.eucp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yumu.hexie.service.exception.BizValidateException;

/**
 * @author HuYM
 *
 */
public class CreateBlueUtil {

	private static final Logger log = LoggerFactory.getLogger(CreateBlueUtil.class);
	private static final String account = "shnbxxkj";
	private static final String password = "Sh666666";
	private static final String urlStr = "http://222.73.117.158/msg/";
	
	
	public static boolean sendMessage(String mobile,String message,long id ){
		
		return send(mobile, message);
		
	}
	
	/**
	 * 短信发送
	 * @param mobile
	 * @param message
	 * @return
	 */
	private static boolean send(String mobile, String message){
		
		HttpClient client = null;
		boolean ret = false;
		
		try {
			
			NameValuePair[]nvps = new org.apache.commons.httpclient.NameValuePair[5];
			nvps[0] = new NameValuePair("account", account);
			nvps[1] = new NameValuePair("pswd", password);
			nvps[2] = new NameValuePair("mobile", mobile);
			nvps[3] = new NameValuePair("needstatus", String.valueOf(true));
			nvps[4] = new NameValuePair("msg", message);
			
			URI uri = new URI(urlStr, false);
			GetMethod method = new GetMethod();
			method.setURI(new URI(uri, "HttpBatchSendSM", false));
			method.setQueryString(nvps);
			
			client = new org.apache.commons.httpclient.HttpClient();
			
			int result = client.executeMethod(method);
			String response = null;
			if (result == 200) {

				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				response = URLDecoder.decode(baos.toString(), "UTF-8");
			}else {
				throw new BizValidateException("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
			
			log.debug("response : " + response);
			
			/*
			 *	返回串有2行，其中第一行由逗号分割，逗号后面的值为短信状态，0表正常，其他值都为异常。
			 */
			if (null == response) {
				ret = false;
			}else {
				try {
					String status = response.substring(response.indexOf(",")+1, response.indexOf("\n"));
					log.debug("status : " + status);
	
					if (!"0".equals(status)) {
						ret = false;
					}else {
						ret = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ret = false;
				}
				
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			e.printStackTrace();
			
		} 
		
		return ret;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CreateBlueUtil.send("18116419486", "您好，您的预约已经收到，服务人员将尽快与您确认。");
		
	}

}
