package com.yumu.hexie.service.user;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.community.Message;
import com.yumu.hexie.model.community.MessageSect;
import com.yumu.hexie.model.user.Feedback;
import com.yumu.hexie.model.user.User;

public interface MessageService {

	public List<Message> queryMessages(int type, long provinceId,long cityId,
			long countyId,long xiaoquId,int page, int pageSize);
	
	public List<Message> queryMessages(int page, int pageSize);
	
	public List<Message> queryMessagesByUserAndType(User user, int msgType, int page, int pageSize);
	
	public Message findOne(long messageId);
	
	public Feedback reply(long userId,String userName,String userHeader,long messageId,String content);
	
	public List<Feedback> queryReplays(long messageId,int page, int pageSize);
	
	public Page<Message> queryMessages(BaseRequestDTO<Message> baseRequestDTO);
	
	public void saveMessage(BaseRequestDTO<Message> baseRequestDTO);
	
	public List<MessageSect> queryMessageSectList(Long messageId);
}
