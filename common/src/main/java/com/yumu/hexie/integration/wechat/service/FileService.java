package com.yumu.hexie.integration.wechat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.util.WechatConfig;
import com.yumu.hexie.service.exception.BizValidateException;

/**
 * 文件上传下载
 */
public class FileService {
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	/**
	 * 上传文件URL
	 */
	private static String uploadFileUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 下载文件URL
	 */
	private static String dwonloadFileURL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 上传多媒体文件
	 * 
	 * @param fileType
	 *            文件类型,分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filename
	 *            文件名称
	 * @param filePath
	 *            文件路径
	 * @return json
	 */
	public static WechatResponse uploadFile(String fileType, String filename, String filePath, String accessToken) {

		String requestUrl = uploadFileUrl.replace("ACCESS_TOKEN",
				accessToken).replace("TYPE", fileType);
		File file = new File(filePath);
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		URL submit = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + file
					+ "\";filename=\"" + filename + ";filelength=\"" + filePath
					+ ";" + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			dos.close();
			is.close();
		} catch (MalformedURLException e) {
			log.error("File upload fail..." + e);
		} catch (IOException e) {
			log.error("File upload fail..." + e);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(result, WechatResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 下载文件
	 * @param mediaId
	 */
	public static InputStream downloadFile(String mediaId, String accessToken){
		
		String requestUrl = dwonloadFileURL.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
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
				if (header.getValue().contains("image")) {	//返回图片
					
					return input;
				
				}else {	//返回错误信息
					
					byte[]bytes = new byte[1024];	//错误信息
					input.read(bytes);
				    responseStr = new String(bytes, WechatConfig.INPUT_CHARSET);	//转UTF-8
				    responseStr = responseStr.trim();
				    log.error("response : \n" + responseStr);
				    throw new BizValidateException(responseStr);
				    
				}
				
			}
			
		} catch (Exception e) {
			 throw new BizValidateException(e.getMessage());
		}
		
		return null;
	}

//	/**
//	 * 下载多媒体文件
//	 * 
//	 * @param mediaId
//	 * @return 媒体url地址
//	 */
//	public static InputStream downloadFile(String mediaId) {
////		mediaId = "m1vupOxeJTOcuBHwY8__1dq2EGZkbKu07geHhmJwPP3CaFL6IQkMIgweGYO3ZBxn";
//		String requestUrl = dwonloadFileURL.replace("ACCESS_TOKEN", WeixinUtil.getToken()).replace("MEDIA_ID", mediaId);
////		String requestUrl = dwonloadFileURL.replace("ACCESS_TOKEN", "0BCNsFIKcFKJ8CLJZpcW2S6foj1LkU3AjEje_hBsl_3s_Q_xRq-CoWwDijqti-AxuN-m3DmFwPrGHgfaO72Ms7NlPZ6Iw0lxm72oFI7sq-I").replace("MEDIA_ID", mediaId);
//		InputStream inStream = null;
//		try {
//			URL url =new URL(requestUrl);
//			 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//			 conn.setRequestProperty("Connection", "Keep-Alive");
//			 conn.setRequestMethod("GET");
//			 inStream = conn.getInputStream(); 
//			 String content_type = conn.getContentType();
//			 
//			 
//		} catch (MalformedURLException e) {
//			log.error("File download failed..." + e);
//		} catch (IOException e) {
//			log.error("File download failed..." + e);
//		} 
//		return inStream;
//		
//	}
	
	/**
	 * inputStream转文件
	 * @param is
	 * @param filePath
	 * @return
	 */
	public static void inputStream2File(InputStream is, String filePath){
		
		try {
			
			OutputStream os = new FileOutputStream(new File(filePath));
			int bytesRead = 0;
			byte[] buffer = new byte[1024*1024];
			while ((bytesRead = is.read(buffer, 0, 1024*1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
			os.close();
			is.close();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error("convert stream to file failed ...");
		}
		
	}
	

}
