package com.yumu.hexie.service.home;

import java.util.Map;

import com.yumu.hexie.integration.wuye.resp.BaseResult;
import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.HaoJiaAnComment;
import com.yumu.hexie.model.user.User;

public interface HaoJiaAnCommentService {

	/**
	 * 保存评论或投诉
	 * @param user
	 * @param comment
	 * @return
	 */
	int saveComment(User user, HaoJiaAnComment comment);

	/**
	 * 获得投诉详情页信息
	 * @param commentId
	 */
	Map<String,Object> getComplainDetail(long commentId);

	/**
	 * 处理投诉
	 * @param user
	 * @param feedBack       投诉反馈
	 * @param complainStatus 投诉状态
	 * @param commentId      处理的投诉id
	 * @return
	 */
	int solveComplain(User user, String feedBack, int complainStatus, long commentId);

	/**
	 * 根据订单id和评论类型查看当前订单是否有被评论和投诉
	 * @param yuyueOrderNo 预约订单
	 * @param commentType  评论类型
	 * @return
	 */
	HaoJiaAnComment getCommentByOrderNoAndType(String yuyueOrderNo, int commentType);
}
