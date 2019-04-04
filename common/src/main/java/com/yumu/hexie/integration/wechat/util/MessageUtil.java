package com.yumu.hexie.integration.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.message.req.CommonMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.Article;
import com.yumu.hexie.integration.wechat.entity.message.resp.NewsMessage;
import com.yumu.hexie.integration.wechat.entity.message.resp.TextMessage;
import com.yumu.hexie.model.user.User;

/**
 * 消息工具类
 */
public class MessageUtil {

	private static Map<String,String> wechatKeyMap = new HashMap<String, String>();
	static {
		wechatKeyMap.put("ToUserName", "toUserName");
		wechatKeyMap.put("FromUserName", "fromUserName");
		wechatKeyMap.put("CreateTime", "createTime");
		wechatKeyMap.put("MsgType", "msgType");
		wechatKeyMap.put("MsgId", "msgId");
		wechatKeyMap.put("Content", "content");
		
		wechatKeyMap.put("CreateTime", "createTime");
		wechatKeyMap.put("Event", "event");
		wechatKeyMap.put("Latitude", "latitude");
		wechatKeyMap.put("Longitude", "longitude");
		wechatKeyMap.put("Precision", "precision");
		wechatKeyMap.put("PicUrl", "picUrl");
		

		wechatKeyMap.put("ScanCodeInfo", "scanCodeInfo");
		wechatKeyMap.put("EventKey", "eventKey");
		

		wechatKeyMap.put("MediaId", "mediaId");
		wechatKeyMap.put("ThumbMediaId", "thumbMediaId");
		wechatKeyMap.put("Format", "format");
		
		wechatKeyMap.put("Title", "title");
		wechatKeyMap.put("Description", "description");
		wechatKeyMap.put("Url", "url");
		
		
		wechatKeyMap.put("ScanType", "scanType");
		wechatKeyMap.put("ScanResult", "scanResult");
		
		
		wechatKeyMap.put("Location_X", "location_X");
		wechatKeyMap.put("Location_Y", "location_Y");
		wechatKeyMap.put("Scale", "scale");
		wechatKeyMap.put("Label", "label");
		
	}
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	public static CommonMessage parseXml2(HttpServletRequest request) throws Exception {

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer sb = new StringBuffer();
		String line= r.readLine();
		while(line!=null) {
			sb.append(line);
			line= r.readLine();
		}
		String xmlContent = sb.toString();
		for(String key : wechatKeyMap.keySet()) {
			xmlContent = xmlContent.replace(key, wechatKeyMap.get(key));
		}
		
		return (CommonMessage)JacksonJsonUtil.jsonToBean(JacksonJsonUtil.xml2json(xmlContent),CommonMessage.class);
		
	}

	
	
	/**
	 * 基本消息对象转换成xml
	 * @param message 消息对象
	 * @return xml
	 */
	public static String messageToXml(Object message){
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
	
	public static void main(String args[]) {
		Map<String,String> a = new HashMap<String, String>();
		a.put("A", "啊");
		a.put("B", "啊1");
		a.put("C", "啊2");
		a.put("D", "啊3");
		System.out.println(messageToXml(a));
		User u = new User();
		u.setName("xx");
		u.setAge(100);
		System.out.println(messageToXml(u));
		
	}
	
	/**
	 * 创建请求xml
	 * @param map 请求以键值对形势封装成map，根据请求的map来组装xml
	 */
	public static String createPayRequestXML(Map<String,String>map){
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<xml>");
		
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String>entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtil.isNotEmpty(value)) {
				buffer.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
			}
			
		}
		buffer.append("</xml>");
		return buffer.toString();
		
	}
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 图文消息对象转换成xml
	 * @param newsMessage 图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
	
	/**
	 * emoji表情转换(hex to utf-16)
	 * @param hexEmoji
	 * @return String
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}