package com.yumu.hexie.web.user.req;

import java.io.Serializable;

public class ReplyReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private String content;
	private long messageId;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	
}
