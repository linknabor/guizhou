package com.yumu.hexie.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.community.Message;
import com.yumu.hexie.model.community.MessageRepository;
import com.yumu.hexie.model.community.MessageSect;
import com.yumu.hexie.model.community.MessageSectRepository;
import com.yumu.hexie.model.user.Feedback;
import com.yumu.hexie.model.user.FeedbackRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.user.MessageService;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {


	@Inject
	private MessageRepository messageRepository;
	@Inject
	private FeedbackRepository feedbackRepository;
	@Autowired
	private MessageSectRepository messageSectRepository;
	
	@Override
	public List<Message> queryMessages(int type, long provinceId, long cityId,
			long countyId, long xiaoquId,int page, int pageSize) {
		page = page<0?0:page;
		pageSize = pageSize<0?10:pageSize;
		return messageRepository.queryMessageByRegions(type, provinceId, cityId, countyId, xiaoquId, new PageRequest(page,pageSize));
	}
	
	@Override
	public List<Message> queryMessages(int page, int pageSize){
		
		return messageRepository.queryMessagesByStatus(new PageRequest(page,pageSize));
	}
	
	@Override
	public Message findOne(long messageId) {
		return messageRepository.findOne(messageId);
	}
	@Override
	public Feedback reply(long userId,String userName,String userHeader, long messageId, String content) {
		Feedback f = new Feedback(userId, userName,userHeader,messageId, content);
		return feedbackRepository.save(f);
	}
	@Override
	public List<Feedback> queryReplays(long messageId, int page, int pageSize) {
		return feedbackRepository.findAllByArticleId(messageId, new PageRequest(page,pageSize));
	}

	/**
	 * 管理端查询首页
	 */
	@Override
	public Page<Message> queryMessages(BaseRequestDTO<Message> baseRequestDTO) {

		List<String> sectList = baseRequestDTO.getSectList();
		Pageable pageable = new PageRequest(baseRequestDTO.getCurr_page(), baseRequestDTO.getPage_size());
		Message message = baseRequestDTO.getData();
		Page<Message> page = messageRepository.queryMessageMutipleCons(ModelConstant.MESSAGE_STATUS_VALID, message.getId(), message.getTitle(), 
				baseRequestDTO.getBeginDate(), baseRequestDTO.getEndDate(), sectList, pageable);
		
		return page;
	
	}

	/**
	 * 新增/保存公告
	 */
	@Transactional
	@Override
	public void saveMessage(BaseRequestDTO<Message> baseRequestDTO) {

		Message message = baseRequestDTO.getData();
		message = messageRepository.save(message);
		List<MessageSect> list = messageSectRepository.findByMessageId(message.getId());
		for (MessageSect messageSect : list) {
			messageSectRepository.delete(messageSect);
		}
		
		List<String> sectIds = baseRequestDTO.getSectList();
		for (String sectId : sectIds) {
			MessageSect messageSect = new MessageSect();
			messageSect.setMessageId(message.getId());
			messageSect.setSectId(Long.valueOf(sectId));
			messageSectRepository.save(messageSect);
		}
	}

	@Override
	public List<MessageSect> queryMessageSectList(Long messageId) {

		return messageSectRepository.findByMessageId(messageId);
	}

	/**
	 * 移动端物业首页公告、资讯查询
	 */
	@Override
	public List<Message> queryMessagesByUserAndType(User user, int msgType, int page, int pageSize) {

		List<Message> messageList = new ArrayList<Message>();
		Pageable pageable = new PageRequest(page, pageSize);
		switch (msgType) {
		case 0:
			messageList = messageRepository.queryMessagesByUserAndType(user.getSectId(), msgType, pageable);
			break;
		case 1:
			messageList = messageRepository.queryMessagesByUserAndType(user.getSectId(), msgType, pageable);
			break;
		case 2:
			messageList = messageRepository.queryMessagesByUserAndType(user.getSectId(), msgType, pageable);
			break;
		case 3:
			messageList = messageRepository.queryMessagesByUserAndType(user.getSectId(), msgType, pageable);
			break;	
		case 9:
			messageList = messageRepository.queryMessagesByStatusAndMsgType(pageable);
			break;
		default:
			break;
		}
		
		return messageList;
	}
	
	

}
