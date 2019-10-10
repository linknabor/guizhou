package com.yumu.hexie.web.shequ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResponse;
import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;
import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.community.Thread;
import com.yumu.hexie.model.community.ThreadComment;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.IntegrationBizException;
import com.yumu.hexie.service.shequ.CommunityService;
import com.yumu.hexie.service.user.UserService;
import com.yumu.hexie.web.BaseController;

@Controller
@RequestMapping(value = "/servplat/thread")
public class ThreadController extends BaseController {

	@Inject
	private CommunityService communityService;
	
	@Inject
	private UserService userService;
    
	@RequestMapping(value = "/getThreadList", method = RequestMethod.POST)
	@ResponseBody
	public BaseResponseDTO<Map<String,Object>> getThreadList(@RequestBody BaseRequestDTO<Map<String,String>> baseRequestDTO) {
		Map<String,Object> map=new HashMap<>();
		try {
		    Sort sort = new Sort(Direction.DESC , "stickPriority", "createDate", "createTime");
			int currPage=baseRequestDTO.getCurr_page();
			int pageSize=baseRequestDTO.getPage_size();
			Pageable pageable = new PageRequest(currPage, pageSize, sort);
			String nickName=baseRequestDTO.getData().get("nickName");
			String createDate=baseRequestDTO.getData().get("createDate");
			String sectId=baseRequestDTO.getData().get("sectIds");
			if(!StringUtil.isEmpty(createDate)){
				createDate=createDate.replaceAll("-", "");
			}
			Page<Thread> page=communityService.getThreadList(nickName,createDate,sectId,baseRequestDTO.getSectList(),pageable);
			map.put("list", page.getContent());
			map.put("count", page.getTotalElements());	
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
		return BaseResponse.success(baseRequestDTO.getRequestId(), map);
	}
	
	@RequestMapping(value = "/deleteThread", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public BaseResponseDTO<String> deleteThread(@RequestBody BaseRequestDTO<Map<String,String>> baseRequestDTO) {
		try {
		    String threadIds=baseRequestDTO.getData().get("threadIds");
			String[] threda_ids=threadIds.split(",");
			communityService.deleteThread(threda_ids);
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}
			return BaseResponse.success(baseRequestDTO.getRequestId());
	}
	
	@RequestMapping(value = "/getThreadDetail", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public BaseResponseDTO<Map<String,Object>> getThreadDetail(@RequestBody BaseRequestDTO<Map<String,String>> baseRequestDTO) {
		Map<String,Object> map=new HashMap<>();
		try {
			String  threadId=baseRequestDTO.getData().get("threadId");
			Thread  thread=communityService.getThreadByTreadId(Long.parseLong(threadId));
			List<ThreadComment>  list=communityService.getCommentListByThreadId(Long.parseLong(threadId));
			map.put("list", list);
			map.put("thread", thread);
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}	
			return BaseResponse.success(baseRequestDTO.getRequestId(), map);
	}
	
	@RequestMapping(value = "/saveThreadComment", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public BaseResponseDTO<String> saveThreadComment(@RequestBody BaseRequestDTO<Map<String,String>> baseRequestDTO) {
		try {
			Map<String,String> map=baseRequestDTO.getData();
			String threadId=map.get("threadId");
			String content=map.get("content");
			String userId=map.get("userId");
			String userName=map.get("userName");
			communityService.saveThreadComment(Long.parseLong(threadId),content,Long.parseLong(userId),userName);		
		} catch (Exception e) {
			throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
		}	
		return BaseResponse.success(baseRequestDTO.getRequestId());
	}
	
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public BaseResponseDTO<Map<String,Object>> getUserInfo(@RequestBody BaseRequestDTO<Map<String,String>> baseRequestDTO) {
		     	Map<String,Object> map=new HashMap<>();	
		    try {
			    String userId=baseRequestDTO.getData().get("userId");
				User user=userService.getById(Long.parseLong(userId));
				map.put("userInfo", user);
			} catch (Exception e) {
				throw new IntegrationBizException(e.getMessage(), e, baseRequestDTO.getRequestId());
			}
			return BaseResponse.success(baseRequestDTO.getRequestId(), map);
	}

}
