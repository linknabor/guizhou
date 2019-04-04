package com.yumu.hexie.service.shequ;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.yumu.hexie.model.community.Annoucement;
import com.yumu.hexie.model.community.CommunityInfo;
import com.yumu.hexie.model.community.Thread;
import com.yumu.hexie.model.community.ThreadComment;
import com.yumu.hexie.model.user.User;

public interface CommunityService {
	
	/**
	 * 0.获取帖子列表
	 * @param userSectId 用户所在小区
	 * @param sort 排序
	 * @return
	 */
	public List<Thread> getThreadList(long userSectId, Pageable page);	
	
	/**
	 * 0 A)获取帖子列表,查看所有小区的
	 * @param sort
	 * @return
	 */
	public List<Thread> getThreadList(Pageable page);
	
	/**
	 * 1.根据分类获取帖子列表
	 * @param catagory	分类
	 * @param userSectId	用户所在小区
	 * @param sort	排序
	 * @return
	 */
	public List<Thread> getThreadListByCategory(String category, long userSectId, Pageable page);
	
	/**
	 * 1 A)根据分类获取帖子列表，查看所有小区的
	 * @param category
	 * @param sort
	 * @return
	 */
	public List<Thread> getThreadListByCategory(String category, Pageable page);
	
	/**
	 * 2.添加新帖子
	 * @param thread
	 * @return
	 */
	public Thread addThread(User user, Thread thread);
	
	/**
	 * 3.删除帖子（实际更改帖子状态）
	 * @param user
	 * @param threadId
	 * @return
	 */
	public void deleteThread(User user, long threadId);
	
	/**
	 * 4.编辑帖子
	 * @param user
	 * @param thread
	 * @return
	 */
	public void updateThread(Thread thread);
	
	
	/**
	 * 5.添加评论
	 * @param user
	 * @param threadComment
	 * @return 
	 */
	public ThreadComment addComment(User user, ThreadComment threadComment);
	
	/**
	 * 6.根据帖子ID获取具体的帖子信息
	 * @param user
	 * @param ThreadId
	 * @return
	 */
	public Thread getThreadByTreadId(long threadId);
	
	/**
	 * 7.根据帖子获取其相关评论
	 * @param user
	 * @param threadId
	 * @return
	 */
	public List<ThreadComment>getCommentListByThreadId(long threadId);
	
	/**
	 * 8.删除评论
	 * @param user
	 * @param threadCommentId
	 */
	public void deleteComment(User user, long threadCommentId);
	
	/**
	 * 9.获取我的发布
	 * @param userSectId	用户ID
	 * @param sort	排序
	 * @return
	 */
	public List<Thread> getThreadListByUserId(long userId, Sort sort);
	
	/**
	 * 9.获取我的发布
	 * @param userSectId	用户ID
	 * @param sort	排序
	 * @param page 	分页
	 * @return
	 */
	public List<Thread> getThreadListByUserId(long userId, Pageable page);
	
	/**
	 * 9.1.获取我的发布,按分类
	 * @param userSectId	用户ID
	 * @param sort	排序
	 * @param page 	分页
	 * @return
	 */
	public List<Thread> getThreadListByUserId(long userId, String category, Pageable page);
	
	
	/**
	 * 10.获取社区百事通信息
	 */
	public List<CommunityInfo> getCommunityInfoBySectId(long sectId, Sort sort);
	
	/**
	 * 11.获取社区百事通信息
	 */
	public List<CommunityInfo> getCommunityInfoByRegionId(long regionId, Sort sort);
	
	/**
	 * 12.获取社区百事通信息
	 */
	public List<CommunityInfo> getCommunityInfoByCityIdAndInfoType(long cityId, String infoType, Sort sort);
	
	/**
	 * 13.获取社区公告信息
	 */
	public List<Annoucement> getAnnoucementList(Sort sort);
	
	/**
	 * 14.获取社区详细信息
	 * @param annoucement
	 * @return
	 */
	public Annoucement getAnnoucementById(long annoucement);
	
	/**
	 * 15.获取未评论帖子的数量
	 * @param threadStatus
	 * @param toUserId
	 * @return
	 */
	public int getUnreadCommentsCount(String threadStatus, long toUserId);
	
	/**
	 * 16.更新评论状态
	 * @param threadId
	 */
	public void updateCommentReaded(long toUserId, long threadId);
	
	/**
	 * 获取帖子列表（新分类:即叽歪和二手）
	 * @param category
	 * @param userSectId
	 * @param sort
	 * @return
	 */
	public List<Thread> getThreadListByNewCategory(String category, long userSectId, Pageable page);
	
	/**
	 * 获取帖子列表，全部小区（新分类:即叽歪和二手）
	 * @param category
	 * @param userSectId
	 * @param sort
	 * @return
	 */
	public List<Thread> getThreadListByNewCategory(String category, Pageable page);
	
	/**
	 * 根据用户ID获取帖子列表
	 * @param category
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<Thread> getThreadListByUserId(String category, long userId, Pageable page);
	
}
