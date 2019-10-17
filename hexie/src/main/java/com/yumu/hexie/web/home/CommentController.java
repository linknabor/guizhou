package com.yumu.hexie.web.home;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.hexie.common.Constants;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HaoJiaAnComment;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.home.HaoJiaAnCommentService;
import com.yumu.hexie.web.BaseController;
import com.yumu.hexie.web.BaseResult;

@RestController
@RequestMapping("/home/comment")
public class CommentController extends BaseController{
	@Inject
	private HaoJiaAnCommentService haoJiaAnCommentService;
	
	//添加评论或投诉
	@RequestMapping(value="/saveComment",method = RequestMethod.GET)
	public BaseResult<?> saveComment(@ModelAttribute(Constants.USER)User user , HaoJiaAnComment comment) {
		//保存评论
		int count = haoJiaAnCommentService.saveComment(user,comment);
		if(count == 1) {
		return BaseResult.successResult("保存评论成功！");
		}
		return BaseResult.fail(500, "保存评论失败！");
	}
	
	//投诉详情页
	@RequestMapping(value = "/getComplainDetail", method = RequestMethod.GET)
	public Map<String,Object> getComplainDetail (long commentId) {
		return haoJiaAnCommentService.getComplainDetail(commentId);
	}
	
	//处理投诉
	@RequestMapping(value = "/solveComplain", method = RequestMethod.GET)
	public BaseResult<?> solveComplain(@ModelAttribute(Constants.USER) User user,String feedBack,@RequestParam(required = false, defaultValue = "1")int complainStatus,long commentId) {
		int count = haoJiaAnCommentService.solveComplain(user, feedBack, complainStatus, commentId);
		if (count == 1) {
			return BaseResult.successResult("处理投诉成功！");
		}
		return BaseResult.fail(500, "处理投诉失败！");
	}
	
	//根据订单id和评论类型查看当前订单是否有被评论和投诉
	@RequestMapping(value = "/getCommentByOrderNoAndType", method = RequestMethod.GET)
	public BaseResult<?> getCommentByOrderNoAndType(@ModelAttribute(Constants.USER) User user,String yuyueOrderNo,int commentType) {
		HaoJiaAnComment hjac = haoJiaAnCommentService.getCommentByOrderNoAndType(yuyueOrderNo, commentType);
		if(hjac != null) {
			return BaseResult.successResult(hjac);
		}
		return BaseResult.fail(500, "还没有评论或投诉");
	}

}
