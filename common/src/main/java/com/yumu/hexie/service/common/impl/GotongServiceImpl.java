/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.ConfigUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.customer.Article;
import com.yumu.hexie.integration.wechat.entity.customer.DataJsonVo;
import com.yumu.hexie.integration.wechat.entity.customer.DataVo;
import com.yumu.hexie.integration.wechat.entity.customer.News;
import com.yumu.hexie.integration.wechat.entity.customer.NewsMessage;
import com.yumu.hexie.integration.wechat.entity.customer.Template;
import com.yumu.hexie.integration.wechat.service.CustomService;
import com.yumu.hexie.integration.wechat.service.TemplateMsgService;
import com.yumu.hexie.model.community.Thread;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.ServiceOperatorRepository;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserRepository;
import com.yumu.hexie.service.common.GotongService;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.o2o.OperatorService;
import com.yumu.hexie.service.user.UserService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: GotongServiceImple.java, v 0.1 2016年1月8日 上午10:01:41  Exp $
 */
@Service("gotongService")
public class GotongServiceImpl implements GotongService {

    private static final Logger LOG = LoggerFactory.getLogger(GotongServiceImpl.class);
    
    public static String YUYUE_NOTICE = ConfigUtil.get("yuyueNotice");
    
    public static String COMPLAIN_DETAIL = ConfigUtil.get("complainDetail");
    
    public static String WEIXIU_NOTICE = ConfigUtil.get("weixiuNotice");

    public static String XIYI_NOTICE = ConfigUtil.get("weixiuNotice");
    
    public static String WEIXIU_DETAIL = ConfigUtil.get("weixiuDetail");
    
    public static String SUBSCRIBE_IMG = ConfigUtil.get("subscribeImage");
    
    public static String SUBSCRIBE_DETAIL = ConfigUtil.get("subscribeDetail");
    
    public static String TEMPLATE_NOTICE_URL = ConfigUtil.get("templateUrl");
    
    public static String TEMPLATE_NOTICE_ID = ConfigUtil.get("templateId");
    
    @Inject
    private ServiceOperatorRepository  serviceOperatorRepository;
    @Inject
    private UserService  userService;
    @Inject
    private OperatorService  operatorService;
    @Inject
    private SystemConfigService systemConfigService;
    @Inject
    private UserRepository userRepository;

    @Async
    @Override
    public void sendRepairAssignMsg(long opId,RepairOrder order,int distance){
        ServiceOperator op = serviceOperatorRepository.findOne(opId);
        String accessToken = systemConfigService.queryWXAToken();
        TemplateMsgService.sendRepairAssignMsg(order, op, accessToken);
    }
    @Async
    @Override
    public void sendRepairAssignedMsg(RepairOrder order){
        User user = userService.getById(order.getUserId());
        News news = new News(new ArrayList<Article>());
        Article article = new Article();
        article.setTitle("您的维修单已被受理");
        article.setDescription("点击查看详情");
        article.setUrl(WEIXIU_DETAIL+order.getId());
        news.getArticles().add(article);
        NewsMessage msg = new NewsMessage(news);
        msg.setTouser(user.getOpenid());
        msg.setMsgtype(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
        String accessToken = systemConfigService.queryWXAToken();
        CustomService.sendCustomerMessage(msg, accessToken);
    }
    
    @Async
    @Override
	public void sendSubscribeMsg(User user) {

         Article article = new Article();
         article.setTitle("欢迎加入贵州幸福家园！");
         article.setDescription("您已获得关注红包，点击查看。");
         article.setPicurl(SUBSCRIBE_IMG);
         article.setUrl(SUBSCRIBE_DETAIL);
         News news = new News(new ArrayList<Article>());
         news.getArticles().add(article);
         NewsMessage msg = new NewsMessage(news);
         msg.setTouser(user.getOpenid());
         msg.setMsgtype(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
         String accessToken = systemConfigService.queryWXAToken();
         CustomService.sendCustomerMessage(msg, accessToken);
	}

    /** 
     * @param opId
     * @param bill
     * @see com.yumu.hexie.service.common.GotongService#sendXiyiAssignMsg(long, com.yumu.hexie.model.localservice.bill.YunXiyiBill)
     */
    @Override
    public void sendXiyiAssignMsg(long opId, YunXiyiBill bill) {
        ServiceOperator op = serviceOperatorRepository.findOne(opId);
        News news = new News(new ArrayList<Article>());
        Article article = new Article();
        article.setTitle(op.getName()+":您有新的洗衣订单！");
        article.setDescription("有新的维修单"+bill.getProjectName()+"快来抢单吧");
        //article.setPicurl(so.getProductPic());
        article.setUrl(XIYI_NOTICE+bill.getId());
        news.getArticles().add(article);
        NewsMessage msg = new NewsMessage(news);
        msg.setTouser(op.getOpenId());
        msg.setMsgtype(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
        String accessToken = systemConfigService.queryWXAToken();
        CustomService.sendCustomerMessage(msg, accessToken);
    }
    /** 
     * @param count
     * @param billName
     * @param requireTime
     * @param url
     * @see com.yumu.hexie.service.common.GotongService#sendYuyueBillMsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Async
    @Override
    public void sendCommonYuyueBillMsg(int serviceType,String title, String billName, String requireTime, String url) {
        LOG.error("发送预约通知！["+serviceType+"]" + billName + " -- " + requireTime);
        List<ServiceOperator> ops = operatorService.findByType(serviceType);
        String accessToken = systemConfigService.queryWXAToken();
        for(ServiceOperator op: ops) {
            LOG.error("发送到操作员！["+serviceType+"]" + billName + " -- " + op.getName() + "--" + op.getId());
            TemplateMsgService.sendYuyueBillMsg(op.getOpenId(), title, billName, requireTime, url, accessToken);    
        }
        
    }
    
    @Override
    public void pushweixinAll() {
		List<User> useropenId = userRepository.findAll();
		for (int i = 0; i < useropenId.size(); i++) {
			
			Template msg = new Template();
	    	msg.setTouser(useropenId.get(i).getOpenid());
	    	msg.setUrl("");//跳转地址 threadid
	    	msg.setTemplate_id("");//模板id template
			DataVo data = new DataVo();
			data.setFirst(new DataJsonVo(""));
			data.setKeyword1(new DataJsonVo(""));
			data.setKeyword2(new DataJsonVo(""));
			data.setKeyword3(new DataJsonVo(""));
			data.setKeyword4(new DataJsonVo(""));
			data.setRemark(new DataJsonVo(""));
			msg.setData(data);
			String accessToken = systemConfigService.queryWXAToken();
			CustomService.sendCustomerMessage(msg, accessToken);
		}
		
    }
    
    /**
     * 意见投诉发布后，通知管理人员
     * @param user
     * @param thread
     */
	@Override
	public void sendThreadPubNotify(User user, Thread thread) {
		
		List<ServiceOperator> ops = serviceOperatorRepository.findBySectId(user.getSectId());
		for (ServiceOperator serviceOperator : ops) {
			sendThreadMsg(serviceOperator, thread, user);
		}
	}
	
    public void sendThreadMsg(ServiceOperator serviceOperator, Thread thread, User pubUser) {
		
    	String msgUrl = TEMPLATE_NOTICE_URL + thread.getThreadId();
    	String msgTitle = "您好，您有新的消息";
    	String msgRemark = "请点击查看具体信息";
    	String msgColor = "#173177";
    	
		Template msg = new Template();
    	msg.setTouser(serviceOperator.getOpenId());//openID
    	msg.setUrl(msgUrl);//跳转地址
    	msg.setTemplate_id(TEMPLATE_NOTICE_ID);//模板id
    	
		DataVo data = new DataVo();
		DataJsonVo vo = new DataJsonVo();
		vo.setValue(msgTitle); //标题
		vo.setColor(msgColor);
		data.setFirst(vo);
		
		DataJsonVo keyword1 = new DataJsonVo();
		keyword1.setValue(String.valueOf(thread.getThreadId()));//内容1
		keyword1.setColor(msgColor); 
		data.setKeyword1(keyword1);
		
		DataJsonVo keyword2 = new DataJsonVo();
		keyword2.setValue(thread.getUserName());//内容2
		keyword2.setColor(msgColor);
		data.setKeyword2(keyword2);
		
		DataJsonVo keyword3 = new DataJsonVo();
		keyword3.setValue(pubUser.getTel());//内容3
		keyword3.setColor(msgColor);
		data.setKeyword3(keyword3);
		
		DataJsonVo keyword4 = new DataJsonVo();
		keyword4.setValue(thread.getUserSectName());//内容4
		keyword4.setColor(msgColor);
		data.setKeyword4(keyword4);
		
		DataJsonVo remark = new DataJsonVo();
		remark.setValue(msgRemark);//结尾
		remark.setColor(msgColor);
		data.setRemark(remark);
		
		msg.setData(data);
		String accessToken = systemConfigService.queryWXAToken();
		CustomService.sendCustomerMessage(msg, accessToken);
    }
	
	
}
