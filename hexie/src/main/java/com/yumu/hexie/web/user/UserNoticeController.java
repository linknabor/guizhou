package com.yumu.hexie.web.user;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.model.user.UserNotice;
import com.yumu.hexie.service.user.UserNoticeService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@Controller(value = "userNoticeController")
@SessionAttributes(Constants.USER)
public class UserNoticeController extends BaseController {
	private static final Logger Log = LoggerFactory.getLogger(UserNoticeController.class);
	@Inject
	private UserNoticeService userNoticeService;
	//消息列表
	@RequestMapping(value = "/notices/{page}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<List<UserNotice>> queryByNum(@ModelAttribute(Constants.USER)User user,@PathVariable int page)
			throws Exception {
		List<UserNotice> notices = userNoticeService.queryByUserId(user.getId(),new PageRequest(page,10,
				new Sort(Direction.DESC, "id")));
		return BaseResult.successResult(notices);
	}
	@RequestMapping(value = "/notices/read/{noticeId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseResult<String> read(@ModelAttribute(Constants.USER)User user,@PathVariable long noticeId)
			throws Exception {
		userNoticeService.readNotice(user.getId(), noticeId);
		return BaseResult.successResult("success");
	}
}
