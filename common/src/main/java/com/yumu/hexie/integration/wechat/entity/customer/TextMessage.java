package com.yumu.hexie.integration.wechat.entity.customer;

/**
 * 文本消息
 */
public class TextMessage extends CustomerBaseMessage{
	
	/**
	 * 文本消息对象
	 */
	private Text text;

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public TextMessage(Text text) {
		super();
		this.text = text;
	}

	public TextMessage() {
		super();
	}
	
}
