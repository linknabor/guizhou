package com.yumu.hexie.integration.wechat.entity.customer;

/**
 * 媒体类， 图片/语音可直接用此类
 */
public class MediaMessage extends CustomerBaseMessage{
	
	/**
	 * 媒体对象
	 */
	private Media media;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public MediaMessage(Media media) {
		super();
		this.media = media;
	}

	public MediaMessage() {
		super();
	}
	
}
