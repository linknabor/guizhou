/**
 * 
 */
package com.yumu.hexie.model.community;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.yumu.hexie.common.util.DateUtil;

/**
 * @author HuYM
 *	跟帖回复
 */

@Entity
public class ThreadComment implements Serializable {
	
	private static final long serialVersionUID = -2496871534305791447L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	private long threadId;
	private String commentContent;
	private String commentDate;
	private String commentTime;
	private long commentDateTime;
	private long commentUserId;
	private String commentUserName;
	private String commentUserHead;
	private long toUserId;
	private String toUserName;
	private String toUserHead;
	private String toUserReaded;
	
	@Transient
	private String isCommentOwner;
	
	@Transient
	private String fmtCommentDateTime;
	
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getThreadId() {
		return threadId;
	}
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public long getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(long commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCommentUserHead() {
		return commentUserHead;
	}
	public void setCommentUserHead(String commentUserHead) {
		this.commentUserHead = commentUserHead;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public long getToUserId() {
		return toUserId;
	}
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
	public String getToUserHead() {
		return toUserHead;
	}
	public void setToUserHead(String toUserHead) {
		this.toUserHead = toUserHead;
	}
	
	public String getToUserReaded() {
		return toUserReaded;
	}
	public void setToUserReaded(String toUserReaded) {
		this.toUserReaded = toUserReaded;
	}
	public String getIsCommentOwner() {
		return isCommentOwner;
	}
	public void setIsCommentOwner(String isCommentOwner) {
		this.isCommentOwner = isCommentOwner;
	}
	public long getCommentDateTime() {
		return commentDateTime;
	}
	public void setCommentDateTime(long commentDateTime) {
		this.commentDateTime = commentDateTime;
	}
	public String getFmtCommentDateTime() {
		
		long time = commentDateTime;
		return DateUtil.getSendTime(time);
		
	}	

}
