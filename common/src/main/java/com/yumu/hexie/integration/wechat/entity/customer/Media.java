package com.yumu.hexie.integration.wechat.entity.customer;

/**
 * 多媒体类
 */
public class Media {
	/**
	 * 媒体ID
	 */
	private String mediaId;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public Media(String mediaId) {
		super();
		this.mediaId = mediaId;
	}
	
}
