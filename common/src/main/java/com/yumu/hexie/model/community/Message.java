package com.yumu.hexie.model.community;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

//资讯
@Entity
public class Message extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	
	private int msgType;	//资讯类型  0. 健康类、1.文化娱乐类、2.城市社区时事、3.教育类、4.居家生活类、5.母婴、6.汽车、7.其他
	private String title;	//资讯抬头
	private String summary;	//资讯摘要	
	private String content;	//资讯内容
	private String fromSite;	//来自网站
	private int regionType;	//区域类型 （0.全部 1.省 2. 市 3. 县  4.小区）
	private String regionId;	//区域ID
	private String publishDate;	//发布日期
	private int status;	//0正常 1失效
	private boolean top;	//是否置顶
	private String image;	//资讯图片
	private String smallImage;	//缩略图


	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getRegionType() {
		return regionType;
	}

	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getFromSite() {
		return fromSite;
	}

	public void setFromSite(String fromSite) {
		this.fromSite = fromSite;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
