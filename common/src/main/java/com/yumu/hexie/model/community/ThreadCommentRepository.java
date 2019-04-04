/**
 * 
 */
package com.yumu.hexie.model.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HuYM
 *
 */
public interface ThreadCommentRepository extends JpaRepository<ThreadComment, Long> {

	public List<ThreadComment>findByThreadId(long threadId);
	
//	@Query( "select count(1) from ThreadComment c join thread t on c.threadId = t.threadId where t.threadStatus = ?1 and c.toUserId = ?2 order by c.commentId desc " )
	@Query( "select count(c.toUserReaded) as unread from ThreadComment c, Thread t where c.threadId = t.threadId and t.threadStatus = ?1 and c.toUserId = ?2 and c.toUserReaded = 'false' order by c.commentId desc ")
	public int getUnreadCommentsCount(String threadStatus, long toUserId);
	
	@Modifying
	@Query("update ThreadComment tc set tc.toUserReaded = 'true' where tc.toUserId=?1 and tc.threadId =?2 ")
	@Transactional
	public void updateCommentReaded(long toUserId, long threadId);
	
}
