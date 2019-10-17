package com.yumu.hexie.web.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.hexie.integration.wuye.resp.BaseResponse;
import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;
import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.community.Message;
import com.yumu.hexie.model.community.MessageSect;
import com.yumu.hexie.service.exception.IntegrationBizException;
import com.yumu.hexie.service.user.MessageService;
import com.yumu.hexie.web.BaseController;

/**
 * 新版社区资讯公告20190802
 * @author huym
 *
 */
@RestController
@RequestMapping(value = "/servplat/message")
public class NewMessageController extends BaseController {

	@Autowired
	private MessageService messageService;
	
	/**
	 * 管理端查询消息列表
	 * @param baseRequestDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public BaseResponseDTO<?> messages(@RequestBody BaseRequestDTO<Message> baseRequestDTO)
			throws Exception {

		Page<Message> page = null;
		try {
			page = messageService.queryMessages(baseRequestDTO);
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		return BaseResponse.success(baseRequestDTO.getRequestId(), page);
	}
	
	/**
	 * 管理端新增消息
	 * @param baseRequestDTO
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public BaseResponseDTO<String> saveMessage(@RequestBody BaseRequestDTO<Message> baseRequestDTO) {
		
		try {
			messageService.saveMessage(baseRequestDTO);
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		return BaseResponse.success(baseRequestDTO.getRequestId());
	}
	
	/**
	 * 管理端信息详情
	 * @param user
	 * @param messageId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	public BaseResponseDTO<Message> getMessageDetail(@RequestBody BaseRequestDTO<String> baseRequestDTO)
			throws Exception {
		Message message;
		List<String> sectList;
		try {
			message = messageService.findOne(Long.valueOf(baseRequestDTO.getData()));
			List<MessageSect> list = messageService.queryMessageSectList(Long.valueOf(baseRequestDTO.getData()));
			sectList = new ArrayList<String>(list.size());
			for (MessageSect messageSect : list) {
				sectList.add(String.valueOf(messageSect.getSectId()));
			}
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		return BaseResponse.success(baseRequestDTO.getRequestId(), message, sectList);
	}
}
