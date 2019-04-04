package com.yumu.hexie.service.common.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.market.OrderItem;
import com.yumu.hexie.model.market.ServiceOrder;
import com.yumu.hexie.model.promotion.share.OrderShareRecord;
import com.yumu.hexie.model.promotion.share.OrderShareRecordRepository;
import com.yumu.hexie.model.promotion.share.ShareAccessRecord;
import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.common.ShareService;
import com.yumu.hexie.service.user.UserService;

@Service("shareService")
public class ShareServiceImpl implements ShareService {

	private static final Logger log = LoggerFactory.getLogger(ShareServiceImpl.class);
	@Inject
	private RedisRepository redisRepository;
	@Inject
	private OrderShareRecordRepository orderShareRecordRepository;
	@Inject
	private UserService userService;
	@Override
	public void access(User user, ShareAccessRecord record) {
		redisRepository.setShareRecord(Keys.uidShareAccRecordKey(user.getId()), record);
	}

	@Async
	@Override
	public void record(ServiceOrder order) {
		ShareAccessRecord record = redisRepository.getShareRecord(Keys.uidShareAccRecordKey(order.getUserId()));
		if(record != null&&!StringUtil.isEmpty(record.getShareCode())) {
			List<OrderShareRecord> records = orderShareRecordRepository.findByOrderId(order.getId());
			if(records != null && records.size()>0) {
				return;
			}
			OrderShareRecord r = new OrderShareRecord();
			r.setShareCode(record.getShareCode());
			r.setOrderId(order.getId());
			r.setToUserId(order.getUserId());
			User user = userService.queryByShareCode(record.getShareCode());
			if(user != null) {
				r.setFromUserId(user.getId());
			}
			if(record.getSalePlanId() != null) {
				for(OrderItem item : order.getItems()){
					if(record.getSalePlanId() == item.getRuleId() 
							&& item.getOrderType() == record.getSalePlanType()){
						r.setSalePlanId(record.getSalePlanId());
						r.setSalePlanType(record.getSalePlanType());
						break;
					}
				}
			}
			orderShareRecordRepository.save(r);

			log.info("记录一次分享结果："+order.getId());
		}
	}

}
