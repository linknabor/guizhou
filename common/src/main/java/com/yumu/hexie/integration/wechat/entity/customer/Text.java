package com.yumu.hexie.integration.wechat.entity.customer;

/**
 * 文本消息对象
 */
public class Text {
	/**
	 * 回复的消息内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Text(String content) {
		super();
		this.content = content;
	}

	public Text() {
		super();
	}
	
}
