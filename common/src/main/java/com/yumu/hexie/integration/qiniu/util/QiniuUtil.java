/**
 * 
 */
package com.yumu.hexie.integration.qiniu.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.util.WechatConfig;

/**
 * @author HuYM
 *
 */
public class QiniuUtil {

	private static final Logger log = LoggerFactory.getLogger(QiniuUtil.class);

	private static Properties props = new Properties();
	private static String upToken = null;
	private static String accessKey = null;
	private static String secretKey = null;
	public static final String BUCKET_NAME = "e-shequ";
	private static Mac mac = null;
	private static RSClient client = null;
	private static QiniuUtil instance = null;
	
	private static final String DEFAULT_WIDTH = "290";	//缩略图的默认长度，iphone4以及5s 一屏为320长度，此处取280
	private static final String DEFAULT_HEIGHT = "";	//缩略图的默认高度，此处不给值。会根据宽度作等比例调整
	
	private static final String PREVIEW_WIDTH = "94";	//预览图尺寸
	private static final String PREVIEW_HEIGHT = "94";	//预览图尺寸
	
	private QiniuUtil(){
	
		init();
		
	}

	private void init() {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		accessKey = props.getProperty("qiniu.access.key");
		secretKey = props.getProperty("qiniu.secret.key");
		
	}
	
	/**
	 * 获取util实例
	 * @return
	 */
	public static QiniuUtil getInstance(){
		
		if (instance == null) {
			
			synchronized (QiniuUtil.class) {
				
				instance = new QiniuUtil();
			}
		}
		return instance;
	}
	
	/**
	 * 初始化token
	 * @return
	 */
	private void initToken(){
		
        PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
        try {
        	mac = new Mac(accessKey, secretKey);
        	upToken = putPolicy.token(mac);
        	
		} catch (AuthException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 初始化rsclient
	 */
	private void initRsClient(){
		
		client = new RSClient(mac);
	}
	
	/**
	 * 获取qiniu的uptoken
	 * @return
	 */
	public String getUpToken(){
		
		initToken();
		initRsClient();
		return upToken;
	}

	/**
	 * 获取RSCLIENT
	 * @return
	 */
	public RSClient getRsClient(){
		
			
		return client;
		
	}
	
	/**
	 * 获取带有interlace效果的图片
	 * @param origLink
	 * @param interlace
	 * @return
	 */
	public String getInterlaceImgLink(String origLink, String interlace){
		
		String retLink = origLink;
		if ("1".equals(interlace)) {
			retLink+="/interlace/1";
		}
		return retLink;
	}
	
	/**
	 * 获取缩略图的链接
	 * @param origLink
	 * @param mode
	 * @param interlace
	 * @return
	 */
	public String getThumbnailLink(String origLink, String mode, String interlace){
		
		
		if (StringUtil.isEmpty(origLink)) {
			return "";
		}
		
		if (StringUtil.isEmpty(mode)) {
			return origLink;
		}
		
		String retLink = origLink;
		
		/*
		 * 此处提供0-3，共4种模式。另有两种模式，请自行登录七牛网查询。	http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
		 */
		if ("0".equals(mode)||"1".equals(mode)||"2".equals(mode)||"3".equals(mode)) {
			retLink+="?imageView2/"+mode+"/w/"+DEFAULT_WIDTH+"/h";
			if (!StringUtil.isEmpty(DEFAULT_HEIGHT)) {
				retLink+="/"+DEFAULT_HEIGHT;
			}
		}
		
		if ("1".equals(interlace)) {
			retLink+="/interlace/1";
		}
		
		return retLink;
		
	}
	
	/**
	 * 获取预览图的链接
	 * @param origLink		图片链接
	 * @param mode	//缩略模式
	 * @param interlace	//是否模糊渐进
	 * @return
	 */
	public String getPreviewLink(String origLink, String mode, String interlace){
		
		
		if (StringUtil.isEmpty(origLink)) {
			return "";
		}
		
		if (StringUtil.isEmpty(mode)) {
			return origLink;
		}
		
		String retLink = origLink;
		
		/*
		 * 此处提供0-3，共4种模式。另有两种模式，请自行登录七牛网查询。	http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
		 */
		if ("0".equals(mode)||"1".equals(mode)||"2".equals(mode)||"3".equals(mode)) {
			retLink+="?imageView2/"+mode+"/w/"+PREVIEW_WIDTH+"/h";
			if (!StringUtil.isEmpty(PREVIEW_HEIGHT)) {
				retLink+="/"+PREVIEW_HEIGHT;
			}
		}
		
		
		
		if ("1".equals(interlace)) {
			retLink+="/interlace/1";
		}
		
		return retLink;
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map getImgs(String imgLink){
		
		String requestUrl = imgLink+"?imageInfo";
		Map retMap = null;
		try {
			HttpGet httpGet = new HttpGet(requestUrl);
			HttpClient httpclient = HttpClients.createDefault();
			
			log.debug("start to call httpclient ... ");
			HttpResponse response = httpclient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			log.info("start to get response ...");
			log.info(response.getStatusLine().toString());
			
			String responseStr = null;
			if (entity != null) {

				log.info("response content length: " + entity.getContentLength());
				Header header = entity.getContentType();
				
				log.info("header :" + header.getName()+":"+header.getValue());
				
				InputStream input = entity.getContent();
				Long contentLength = entity.getContentLength();
				byte[]bytes = new byte[contentLength.intValue()];	//错误信息
				input.read(bytes);
			    responseStr = new String(bytes, WechatConfig.INPUT_CHARSET);	//转UTF-8
			    responseStr = responseStr.trim();
			    log.info("response : \n" + responseStr);
			    retMap = JacksonJsonUtil.json2map(responseStr);
				
			}
			
		} catch (Exception e) {
			log.error("images download failed..." + e.getMessage());
		}
		
		return retMap;
	}
		
	

}
