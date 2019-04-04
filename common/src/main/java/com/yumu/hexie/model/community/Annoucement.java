package com.yumu.hexie.model.community;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Annoucement implements Serializable{

	private static final long serialVersionUID = -2973607402666078662L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long annoucementId;
	
	private String title;	//公告抬头
	private String content;	//公告内容
	private String createDate;	//创建日期
	private String createTime;	//创建时间
	private String srcFrom;	//来源
	private String titleImg;	//抬头图片
	private String type;	//公共类型
	private String detailLink;	//详细页面链接
	
	public long getAnnoucementId() {
		return annoucementId;
	}
	public void setAnnoucementId(long annoucementId) {
		this.annoucementId = annoucementId;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSrcFrom() {
		return srcFrom;
	}
	public void setSrcFrom(String srcFrom) {
		this.srcFrom = srcFrom;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetailLink() {
		return detailLink;
	}
	public void setDetailLink(String detailLink) {
		this.detailLink = detailLink;
	}

}
