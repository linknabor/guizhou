/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.ConfigUtil;
import com.yumu.hexie.integration.wechat.constant.ConstantWeChat;
import com.yumu.hexie.integration.wechat.entity.customer.Article;
import com.yumu.hexie.integration.wechat.entity.customer.News;
import com.yumu.hexie.integration.wechat.entity.customer.NewsMessage;
import com.yumu.hexie.integration.wechat.service.CustomService;
import com.yumu.hexie.integration.wechat.service.TemplateMsgService;
import com.yumu.hexie.model.community.ThreadOperator;
import com.yumu.hexie.model.community.ThreadOperatorRepository;
import com.yumu.hexie.model.localservice.ServiceOperator;
import com.yumu.hexie.model.localservice.ServiceOperatorRepository;
import com.yumu.hexie.model.localservice.bill.YunXiyiBill;
import com.yumu.hexie.model.localservice.repair.RepairOrder;
import com.yumu.hexie.model.user.User;
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
    
    public static String WEIXIU_NOTICE = ConfigUtil.get("weixiuNotice");

    public static String XIYI_NOTICE = ConfigUtil.get("weixiuNotice");
    
    public static String WEIXIU_DETAIL = ConfigUtil.get("weixiuDetail");
    
    public static String SUBSCRIBE_IMG = ConfigUtil.get("subscribeImage");
    
    public static String SUBSCRIBE_DETAIL = ConfigUtil.get("subscribeDetail");
    
    public static String THREAD_NOTICE_URL = ConfigUtil.get("threadUrl");
    
    public static String THREAD_NOTICE_DESC = "业主姓名：NAME\r联系方式：TEL\r业主地址：CELL_ADDR\r消息类型：CATEGORY\r消息内容：CONTENT";
    
    public static Map<String, String>categoryMap;
    
    @PostConstruct   
    public void init(){
    	
    	categoryMap = new HashMap<String, String>();
    	categoryMap.put("0", "服务需求");
    	categoryMap.put("1", "意见建议");
    	categoryMap.put("2", "报修");
    
    }
    
    @Inject
    private ServiceOperatorRepository  serviceOperatorRepository;
    @Inject
    private UserService  userService;
    @Inject
    private OperatorService  operatorService;
    @Inject
    private SystemConfigService systemConfigService;
    @Inject
    private ThreadOperatorRepository threadOperatorRepository;

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
         
         article.setTitle("欢迎加入东湖e家园！");
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
    
    @Async
	@Override
	public void sendThreadPubNotify(User user, com.yumu.hexie.model.community.Thread thread) {

    	LOG.error("发送管家帖子发布通知, threadId: ["+thread.getThreadId()+"]");
    	 
    	List<ThreadOperator> list = threadOperatorRepository.findAll();
		for (ThreadOperator threadOperator : list) {
			if ("3".equals(threadOperator.getRegionType())) {
				if (!user.getSect_id().equals(threadOperator.getRegionSectId())) {
					continue;
				}
			}
			LOG.error("发送到操作员 id:" + threadOperator.getId() + ", name : " + threadOperator.getUserName());
			
			Article article = new Article();
			article.setTitle("管家服务有新消息发布");
			
//			String name = user.getName();
//			String tel = user.getTel();
//			String cell_addr = user.getCell_addr();
//			String category = thread.getThreadCategory();
//			String content = thread.getThreadContent();
//			
//			LOG.error("name:"+name+",tel:"+tel+",cell_addr:"+cell_addr+"category:"+category+"content:"+content);
//			LOG.error(String.valueOf(categoryMap.entrySet().size()));
			
			String desc = THREAD_NOTICE_DESC.replace("NAME", user.getName()).
					replace("TEL", user.getTel()==null?"":user.getTel()).replace("CELL_ADDR", user.getCell_addr()).
					replace("CATEGORY", categoryMap.get(thread.getThreadCategory())).
					replace("CONTENT", thread.getThreadContent());
			
			article.setDescription(desc);
			article.setUrl(THREAD_NOTICE_URL+thread.getThreadId());
			
			News news = new News(new ArrayList<Article>());
			news.getArticles().add(article);
			NewsMessage msg = new NewsMessage(news);
			msg.setTouser(threadOperator.getOpenId());
			msg.setMsgtype(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
			
			String accessToken = systemConfigService.queryWXAToken();
			CustomService.sendCustomerMessage(msg, accessToken);
		}
    	 
    }
    
    public static void main(String[] args) {
	
    	Article article = new Article();
		article.setTitle("管家服务有新消息");
		article.setDescription("业主姓名：yiming\r联系方式：18116419486\r业主地址：浦东新区三林路128弄1单元103室\r类型:测试 \r阿朵司法所飞洒发放的说法as范德萨发送发放阿斯蒂芬撒法撒旦法撒 阿朵司法所飞洒发放的说法as范德萨发送发放阿斯蒂芬撒法撒旦法撒");
		article.setUrl("https://www.e-shequ.com/dhzj3/weixin/communities/threadDetail.html?threadId=11");
		
		News news = new News(new ArrayList<Article>());
		news.getArticles().add(article);
		NewsMessage msg = new NewsMessage(news);
		msg.setTouser("o_3DlwdnCLCz3AbTrZqj4HtKeQYY");
		msg.setMsgtype(ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);
		
		String accessToken = "ZunBWUlbDfqe2AN4rVMOST70fD_kEImeDWyEORcqmtEKo6TxWgkP6IWQWsIJPP4jrFVo0OYtlOSABpV1sLDsD9QE-O_fMQAFAErTpO-xONrzVS_vKchuKSN57AHhRwDUIDIhAIAOJO";
		CustomService.sendCustomerMessage(msg, accessToken);
    	
    }
    
}
