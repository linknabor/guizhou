package com.yumu.hexie.model.user;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//文章回复
@Entity
public class Feedback  extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long userId;
	private String content;
	private String userName;
	private String userHeader;
	
	private long articleId;

	public Feedback(){}
	public Feedback(long userId, String userName,String userHeader, long messageId, String content) {
		this.userId = userId;
		this.userHeader = userHeader;
		this.userName = userName;
		this.articleId = messageId;
		this.content = content;
	}
	
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getUserHeader() {
		return userHeader;
	}
	public void setUserHeader(String userHeader) {
		this.userHeader = userHeader;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
