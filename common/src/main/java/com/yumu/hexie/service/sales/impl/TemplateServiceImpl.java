package com.yumu.hexie.service.sales.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.common.WechatResponse;
import com.yumu.hexie.integration.wechat.entity.templatemsg.TemplateItem;
import com.yumu.hexie.integration.wechat.entity.templatemsg.TemplateMsg;
import com.yumu.hexie.integration.wechat.util.WeixinUtil;
import com.yumu.hexie.model.user.TempUser;
import com.yumu.hexie.model.user.TempUserRepository;
import com.yumu.hexie.service.sales.TemplateService;

public class TemplateServiceImpl implements TemplateService {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class); 
	private static String TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	@Autowired
	private TempUserRepository tempUserRepository;
	
	@Override
	public void sendTemplate() {
		
		String templateId = "4SEaObhHSylmedZHe_VP7g2CZ4Js35FIiGx11rqXtWk";				//TODO 不同公众号需要更换
		String url = "https://www.e-shequ.com/weixin/group/rgroups.html?state=123";		//TODO 不同公众号需要更换
		String type = "0";			//TODO 不同公众号需要更换
		String accessToken = "";
		int currentPage = 10;
		try {
			sendTemplateMessage(templateId, url, type, accessToken, currentPage);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	public void sendTemplateMessage(String templateId, String url, String type, String accessToken, int currentPage) throws InterruptedException {
		
		long begin = System.currentTimeMillis();
		Pageable page = new PageRequest(currentPage, 1000);	//每页1000条
		List<TempUser> userList = tempUserRepository.findByType(type, page);
		ExecutorService pool = Executors.newFixedThreadPool(10);	//10个线程一起跑，在20分钟内跑完。不然要换token
		
		for (TempUser tempUser : userList) {
			pool.execute(new MessageWorker(templateId, url, accessToken, tempUser.getOpenid()));
		}
		pool.shutdown();
		while(!pool.awaitTermination(30l, TimeUnit.SECONDS)) {
		}
		long end = System.currentTimeMillis();
		logger.error("finished !");
		logger.error("use time : " + (end - begin));
	}
	
	class MessageWorker implements Runnable {
		
		public MessageWorker(String templateId, String url, String accessToken, String openid) {
			super();
			this.templateId = templateId;
			this.url = url;
			this.accessToken = accessToken;
			this.openid = openid;
		}

		private String templateId;
		private String url;
		private String accessToken;
		private String openid;
		
		@Override
		public void run() {

			if (StringUtil.isEmpty(openid)) {
				return;
			}
			
			Map<String, TemplateItem> map = new HashMap<>();
			map.put("first", new TemplateItem("尊敬的业主，您好！"));
			map.put("keyword1", new TemplateItem("“代扔垃圾”服务，推荐邻居享免单"));
			map.put("keyword2", new TemplateItem("合协社区"));
			map.put("remark", new TemplateItem("点击详情，立即报名！"));
			
			TemplateMsg<Map<String, TemplateItem>> msg = new TemplateMsg<Map<String, TemplateItem>>();
	    	msg.setData(map);
	    	msg.setTemplate_id(templateId);
	    	msg.setUrl(url);
	    	msg.setTouser(openid);
	    	
	    	try {
				WechatResponse wechatResponse = WeixinUtil.httpsRequest(TEMPLATE_MSG, "POST", 
						JacksonJsonUtil.beanToJson(msg), accessToken);
				ObjectMapper objectMapper = JacksonJsonUtil.getMapperInstance(false);
				String returnMsg = objectMapper.writeValueAsString(wechatResponse);
				logger.error(returnMsg);
			} catch (JsonProcessingException | JSONException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
		
	}

}
