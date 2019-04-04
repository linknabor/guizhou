package com.yumu.hexie.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;

//消息
@Entity
public class UserNotice extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private long userId;
	private int noticeType;//
	private int subType = 1;//
	private String title;
	private String content;
	
	private String url;
	private long bizId = 0;
	
	private boolean readed = false;//暂时无效
	private boolean removed = false;
	private Date publishDate = new Date();
	
	@Transient
	public String getNoticeTypeStr(){
		switch(noticeType){
			case ModelConstant.NOTICE_TYPE_ORDER:
				return "通知";
			case ModelConstant.NOTICE_TYPE_COMMENT:
				return "评论";
			//case ModelConstant.NOTICE_TYPE_SHEQU:
			//	return "社区资讯";
			default:
				return "通知";
				
		}
	}
	@Transient
	public String getTimeStr(){
		if(publishDate == null) {
			return "一个月前";
		}
		long timeDur = (System.currentTimeMillis() - publishDate.getTime())/60000;
		if(timeDur<30){
			return "刚刚";
		} else if(timeDur<60){
			return "半小时前";
		} else  if(timeDur<120){
			return "1小时前";
		} else  if(timeDur<180){
			return "2小时前";
		} else  if(timeDur<24*60){
			return "近1天";
		} else  if(timeDur<48*60){
			return "1天前";
		} else  if(timeDur<30*24*60){
			return "一个月内";
		} else {
			return "一个月前";
		}
	}

	public UserNotice(){
		
	}
	
	
	public UserNotice(long userId, int noticeType,int subType, String title,
			long bizId){
		this.userId=userId;
		this.noticeType=noticeType;
		this.title=title;
		this.bizId=bizId;
		this.subType = subType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public boolean isRemoved() {
		return removed;
	}
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public long getBizId() {
		return bizId;
	}
	public void setBizId(long bizId) {
		this.bizId = bizId;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
}
