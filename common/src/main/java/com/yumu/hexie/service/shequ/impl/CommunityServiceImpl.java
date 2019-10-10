/**
 * 
 */
package com.yumu.hexie.service.shequ.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.model.community.Annoucement;
import com.yumu.hexie.model.community.AnnoucementRepository;
import com.yumu.hexie.model.community.CommunityInfo;
import com.yumu.hexie.model.community.CommunityInfoRepository;
import com.yumu.hexie.model.community.Thread;
import com.yumu.hexie.model.community.ThreadComment;
import com.yumu.hexie.model.community.ThreadCommentRepository;
import com.yumu.hexie.model.community.ThreadRepository;
import com.yumu.hexie.model.user.User;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.shequ.CommunityService;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService {

	@Inject
	private ThreadRepository threadRepository;
	
	@Inject
	private ThreadCommentRepository threadCommentRepository;
	
	@Inject
	private CommunityInfoRepository communityInfoRepository;
	
	@Inject
	private AnnoucementRepository annoucementRepository;
	
	@Override
	public List<Thread> getThreadList(long userSectId, Pageable page) {
		
		return threadRepository.findByThreadStatusAndUserSectId(ModelConstant.THREAD_STATUS_NORMAL, userSectId, page);
		
	}
	
	@Override
	public List<Thread> getThreadListByUserId(long userId, Pageable page) {
		
		return threadRepository.findByThreadStatusAndUserId(ModelConstant.THREAD_STATUS_NORMAL, userId, page);
		
	}
	
	@Override
	public List<Thread> getThreadList(Pageable page) {
		
		return threadRepository.findByThreadStatus(ModelConstant.THREAD_STATUS_NORMAL, page);
	}

	@Override
	public List<Thread> getThreadListByCategory(String category, long userSectId, Pageable page) {

		return threadRepository.getThreadListByCategory(ModelConstant.THREAD_STATUS_NORMAL, userSectId, category, page);
	}

	@Override
	public List<Thread> getThreadListByCategory(String category, Pageable page) {
		
		return threadRepository.getThreadListByCategory(ModelConstant.THREAD_STATUS_NORMAL, category, page);
	}

	@Override
	public Thread addThread(User user, Thread thread) {
		
		thread.setCreateDateTime(System.currentTimeMillis());
		thread.setCreateDate(DateUtil.dtFormat(new Date(), "yyyyMMdd"));
		thread.setCreateTime(DateUtil.dtFormat(new Date().getTime(), "HHMMss"));
		thread.setThreadStatus(ModelConstant.THREAD_STATUS_NORMAL);
		thread.setUserHead(user.getHeadimgurl());
		thread.setUserId(user.getId());
		thread.setUserName(user.getNickname());
		thread.setUserSectId(Long.parseLong(user.getSectId()));
		thread.setUserSectName(user.getXiaoquName());
		thread.setStickPriority("0");	//默认优先级0，为最低
		threadRepository.save(thread);
		
		return thread;
	}

	@Override
	public void deleteThread(User user, long threadId) {

		Thread thread = threadRepository.findOne(threadId);
		if (thread == null) {
			throw new BizValidateException("帖子不存在。");
		}
		
		if (ModelConstant.THREAD_STATUS_DELETED.equals(thread.getThreadStatus())) {
			throw new BizValidateException("帖子已删除。");
		}
		
		if (thread.getUserId()!=user.getId()) {
			throw new BizValidateException("用户无权限删除帖子。");
		}
		
		thread.setThreadStatus(ModelConstant.THREAD_STATUS_DELETED);	//"0"正常，"1"废弃
		threadRepository.save(thread);
	}

	@Override
	public void updateThread(Thread thread) {

		Thread t = threadRepository.findOne(thread.getThreadId());
		if (t == null) {
			throw new BizValidateException("帖子不存在。");
		}
		threadRepository.save(thread);
	}

	@Override
	public ThreadComment addComment(User user, ThreadComment comment) {
	
		comment.setCommentDateTime(System.currentTimeMillis());
		comment.setCommentDate(DateUtil.dtFormat(new Date(), "yyyyMMdd"));
		comment.setCommentTime(DateUtil.dtFormat(new Date().getTime(), "HHMMss"));
		comment.setCommentUserHead(user.getHeadimgurl());
		comment.setCommentUserId(user.getId());
		comment.setCommentUserName(user.getNickname());
		
		threadCommentRepository.save(comment);
		return comment;
		
	}
	
	@Override
	public List<ThreadComment> getCommentListByThreadId(long threadId) {
		
		return threadCommentRepository.findByThreadId(threadId);
	
	}

	@Override
	public Thread getThreadByTreadId(long threadId) {
	
		return threadRepository.findOne(threadId);

	}

	@Override
	public void deleteComment(User user, long threadCommentId) {
		
		
		ThreadComment comment = threadCommentRepository.findOne(threadCommentId);
		if (comment == null) {
			throw new BizValidateException("评论不存在。");
		}
		
		if (comment.getCommentUserId()!=user.getId()) {
			throw new BizValidateException("用户无权限删除帖子。");
		}
		
		threadCommentRepository.delete(threadCommentId);
		
	}

	@Override
	public List<Thread> getThreadListByUserId(long userId, Sort sort) {
		
		return threadRepository.findByThreadStatusAndUserId(ModelConstant.THREAD_STATUS_NORMAL, userId, sort);
	}

	@Override
	public List<CommunityInfo> getCommunityInfoBySectId(long sectId, Sort sort) {
		
		return communityInfoRepository.findBySectId(sectId, sort);
	}

	@Override
	public List<CommunityInfo> getCommunityInfoByCityIdAndInfoType(long cityId, String infoType, Sort sort) {
		
		return communityInfoRepository.findByCityIdAndInfoType(cityId, infoType, sort);
		
	}
	
	@Override
	public List<CommunityInfo> getCommunityInfoByRegionId(long regionId, Sort sort) {
		
		return communityInfoRepository.findByRegionId(regionId, sort);
	}

	@Override
	public List<Annoucement> getAnnoucementList(Sort sort) {
		
		return annoucementRepository.findAll(sort);
	}

	@Override
	public Annoucement getAnnoucementById(long annoucementId) {

		return annoucementRepository.findOne(annoucementId);
	}

	@Override
	public int getUnreadCommentsCount(String threadStatus, long toUserId){
		
		Integer i = threadCommentRepository.getUnreadCommentsCount(threadStatus, toUserId);
		return i;
	}
	
	@Override
	public void updateCommentReaded(long toUserId, long threadId){
	
		threadCommentRepository.updateCommentReaded(toUserId, threadId);
	}

	@Override
	public List<Thread> getThreadListByNewCategory(String category,
			long userSectId, Pageable page) {
		
		return threadRepository.getThreadListByNewCategory(ModelConstant.THREAD_STATUS_NORMAL, userSectId, category, page); 
	
	}

	@Override
	public List<Thread> getThreadListByNewCategory(String category, Pageable page) {

		return threadRepository.getThreadListByNewCategory(ModelConstant.THREAD_STATUS_NORMAL, category, page);
	}

	@Override
	public Page<Thread> getThreadList(String nickName, String createDate,String sectId, List<String> sectIds,Pageable pageable) {
		return threadRepository.getThreadList(nickName,createDate,sectId,sectIds,pageable);
	}

	@Override
	@Transactional
	public void deleteThread(String[] threadIds) {
		threadRepository.deleteThread(threadIds);
	}

	@Override
	@Transactional
	public void saveThreadComment(Long threadId, String content, Long userId, String userName) {
		Thread thread=threadRepository.findOne(threadId);
		Long toUserId =thread.getUserId();
		String toUserName=thread.getUserName();
		
		ThreadComment comment=new ThreadComment();
		comment.setCommentDateTime(System.currentTimeMillis());
		comment.setCommentDate(DateUtil.dtFormat(new Date(), "yyyyMMdd"));
		comment.setCommentTime(DateUtil.dtFormat(new Date().getTime(), "HHMMss"));
		comment.setToUserId(toUserId);
		comment.setToUserName(toUserName);
		comment.setCommentUserId(userId);
		comment.setCommentUserName(userName);
		comment.setThreadId(threadId);
		comment.setCommentContent(content);
		threadCommentRepository.save(comment);
		
		thread.setCommentsCount(thread.getCommentsCount()+1); 
		thread.setHasUnreadComment("true");
		thread.setLastCommentTime(System.currentTimeMillis());
		threadRepository.save(thread);
		
	}

	@Override
	public ThreadComment getThreadCommentByTreadId(long threadCommentId) {
		return threadCommentRepository.findOne(threadCommentId);
	}
	
	@Override
	public void updateThreadComment(ThreadComment thread) {

		ThreadComment t = threadCommentRepository.findOne(thread.getCommentId());
		if (t == null) {
			throw new BizValidateException("帖子不存在。");
		}
		threadCommentRepository.save(thread);
	}
	
}
