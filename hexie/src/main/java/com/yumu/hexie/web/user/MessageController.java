package com.yumu.hexie.web.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.community.Message;
import com.yumu.hexie.model.user.Feedback;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.user.MessageService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;
import com.yumu.hexie.web.user.req.ReplyReq;

@Controller(value = "messageController")
public class MessageController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(MessageController.class);

	private static final int PAGE_SIZE = 10;
	@Inject
	private MessageService messageService;
	@Inject
	private UserService userService;
	//消息列表
	@RequestMapping(value = "/messages/{currentPage}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<Message>> messages(@ModelAttribute(Constants.USER)User user, @PathVariable int currentPage)
			throws Exception {
		user = userService.getById(user.getId());
		List<List<Message>> totle_message = new ArrayList<List<Message>>();
		List<Message> message0 = messageService.queryMessages(user.getSect_id(), 0, currentPage, PAGE_SIZE);
		totle_message.add(message0);
		List<Message> message1 = messageService.queryMessages(user.getSect_id(), 1, currentPage, PAGE_SIZE);
		totle_message.add(message1);
		List<Message> message2 = messageService.queryMessages(user.getSect_id(), 2, currentPage, PAGE_SIZE);
		totle_message.add(message2);
		
		return BaseResult.successResult(totle_message);
	}

	//消息详情
	@RequestMapping(value = "/messageDetail/{messageId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Message> getMessageDetail(@ModelAttribute(Constants.USER) User user,@PathVariable long messageId)
			throws Exception {
		Message message = messageService.findOne(messageId);
		return BaseResult.successResult(message);
	}
	
	//消息详情
	@RequestMapping(value = "/getmessages", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<Message> getMessageBySectId(@ModelAttribute(Constants.USER) User user)
				throws Exception {
		user = userService.getById(user.getId());
		//查询便民信息
		Message message = new Message();
		if(!StringUtil.isEmpty(user.getSect_id()) && !"0".equals(user.getSect_id()))
		{
			message = messageService.findOneByregionId(3, user.getSect_id(), true);
		}else
		{
			message = messageService.findOneByregionId(3, user.getSect_id(), false);
		}
		
		return BaseResult.successResult(message);
	}
	

	//feedback
	@RequestMapping(value = "/feedbacks/{messageId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<Feedback>> queryFeedback(@PathVariable long messageId)
			throws Exception {
		//暂时没分页，需要分页
		return BaseResult.successResult(messageService.queryReplays(messageId, 0, 20));
	}

	@RequestMapping(value = "/pushFeedback", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult<Feedback> pushFeedback(@ModelAttribute(Constants.USER)User user,@RequestBody ReplyReq req)
			throws Exception {
		return BaseResult.successResult(messageService.reply(user.getId(),user.getNickname(),user.getHeadimgurl(), req.getMessageId(), req.getContent()));
	}
	
}
