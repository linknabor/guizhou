package com.yumu.hexie.service.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.user.UserNotice;
import com.yumu.hexie.model.user.UserNoticeRepository;
import com.yumu.hexie.service.common.SmsService;
import com.yumu.hexie.service.user.UserNoticeService;

@Service("userNoticeService")
public class UserNoticeServiceImpl implements UserNoticeService {

	@Inject
	private UserNoticeRepository userNoticeRepository;
	@Inject
	protected SmsService smsService;

	@Override
	public List<UserNotice> queryByUserId(long userId,Pageable page) {
		return userNoticeRepository.queryByUserId(userId,page);
	}


	public void readNotice(long userId,long noticeId){
		userNoticeRepository.read(userId, noticeId);
	}
	
	private long getKey(long id1,long id2, int type) {
		return (id1*100+type)*10000000+id2;
	}
	@Override
	public void orderSuccess(long userId, String tel,long orderId, String orderNo, String productName, float prices) {
		String msg = "您好，您购买的"+productName+"已支付成功，支付总额" + prices + "元,关注微信号“东湖e家园”,了解更多订单信息。";
		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_ORDER, ModelConstant.NOTICE_SUB_TYPE_ORDERSUCCESS,
				msg, orderId));
		smsService.sendMsg(userId, tel, msg, getKey(userId,orderId,1));
	}

	@Override
	public void orderSend(long userId, String tel,long orderId, String orderNo,
			String logistics, String logisticsNo) {
		String msg = "您好，你购买的商品已经发货。物流单号"+logisticsNo+",（"+logistics+"）。";
		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_ORDER, 
				ModelConstant.NOTICE_SUB_TYPE_ORDERSENDGOODS,msg, orderId));
		smsService.sendMsg(userId, tel, msg, getKey(userId,orderId,2));
	}

	@Override
	public void groupSuccess(long userId, String tel,long groupId, int ruleMinNum,
			String productName, String ruleName) {
		String msg = "恭喜您，您参与的"+ruleName+"已经顺利成团，我们将尽快为您发货或服务。";
		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_RGROUP, ModelConstant.NOTICE_SUB_TYPE_GROUPSUCCESS,
				msg, groupId));
		smsService.sendMsg(userId, tel, msg, getKey(userId,groupId,4));
		
	}

	@Override
	public void groupFail(long userId, String tel,long groupId,  String productName, int ruleMinNum, String ruleName) {
		String msg = "非常遗憾，您参与的"+ruleName+"因未达到的目标份数，团购，系统将自动为您退款退款。您可以通过微信支付通知进行核实。";
		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_RGROUP, ModelConstant.NOTICE_SUB_TYPE_GROUPFAIL,
				msg, groupId));
		smsService.sendMsg(userId, tel, msg,  getKey(userId,groupId,5));
	}

	@Override
	public void groupTimeoutTip(long userId, String tel,long groupId, String groupNo,
			String productName, int ruleMinNum,int needNum) {
		String msg = "您好，你参与的（"+productName+"）"+ruleMinNum+"人团还差"+needNum+"人才能成团，赶快约着您的朋友邻居一起加入吧。";
		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_RGROUP, ModelConstant.NOTICE_SUB_TYPE_GROUPNOTIFY,
				msg, groupId));
		smsService.sendMsg(userId, tel, msg,  getKey(userId,groupId,6));
		
	}

	@Override
	public void yuyueSuccess(long userId,String tel, String userName, long yuyueId, String serviceName, int paymentType, float prices) {
		String msg;
		if(paymentType != ModelConstant.YUYUE_PAYMENT_TYPE_OFFLINE){
			msg ="您好，您预约的"+serviceName + "已成功支付" + prices + "元。我们将尽快为您发货或服务，感谢您的信任。关注微信公众号“东湖e家园”，了解更多服务信息";
		}else{
			msg ="您好，您的"+serviceName+"服务已成功预约，感谢您的信任。关注微信公众号“东湖e家园”，了解更多服务信息";
		}

		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_YUYUE, 1,
				msg, yuyueId));
		smsService.sendMsg(userId, tel, msg,  getKey(userId,yuyueId,7));
	}

	@Override
	public void commentNotice(long userId, long threadId, String replyerName,
			String replyInfo) {

		userNoticeRepository.save(new UserNotice(userId, ModelConstant.NOTICE_TYPE_COMMENT, 1,
				replyerName+"回复了我的发布：（"+replyInfo+"）", threadId));
		
	}

}
