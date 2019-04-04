package com.yumu.hexie.service.user;

import java.util.List;

import com.yumu.hexie.model.community.Message;
import com.yumu.hexie.model.user.Feedback;

public interface MessageService {

	public List<Message> queryMessages(int type, long provinceId,long cityId,
			long countyId,long xiaoquId,int page, int pageSize);
	
	public List<Message> queryMessages(String sect_id, int type, int page, int pageSize);
	
	public Message findOne(long messageId);
	
	public Message findOneByregionId(int msgType, String regionId, boolean type);
	
	public Feedback reply(long userId,String userName,String userHeader,long messageId,String content);
	
	public List<Feedback> queryReplays(long messageId,int page, int pageSize);
}
