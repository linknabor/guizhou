package com.yumu.hexie.model.community;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class MessageSect extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3521771555323850456L;
	
	private long messageId;	//公告ID
	private long sectId;	//小区ID
	
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getSectId() {
		return sectId;
	}
	public void setSectId(long sectId) {
		this.sectId = sectId;
	}
	
	

}
