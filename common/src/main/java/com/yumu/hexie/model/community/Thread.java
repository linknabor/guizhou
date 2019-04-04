/**
 * 
 */
package com.yumu.hexie.model.community;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.yumu.hexie.common.util.DateUtil;

/**
 * 帖子信息
 */
@Entity
public class Thread implements Serializable{
	
	private static final long serialVersionUID = 3142239937090179235L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long threadId;
	private String threadTitle;	//抬头
	private String threadContent;	//内容
	private String threadStatus;	//状态
	private String threadCategory;	//分类
	private String createDate;	//创建日期
	private String createTime;	//创建时间
	private long createDateTime;	//13位unixtime
	private long userId;
	private String userName;
	private String userHead;	
	private long userSectId;		//用户所在小区ID
	private String userSectName; 	//用户所在小区名称
	private double userSectLatitude;	//用户所在小区纬度
	private double userSectLongtitude;	//用户所在小区精度
	private String attachmentUrl;	//一对多，逗号分割
	private String uploadPicId;	//上传图片路径，一对多，逗号分隔
	private String imgHeight;	//图片高度 ，一对多，逗号分割
	private String imgWidth;	//图片宽度，一对多，逗号分割
	private long likes;	//赞数量
	private long commentsCount;	//评论数量
	private String stickPriority;	//置顶优先级，数值越大优先级越高
	private long lastCommentTime;	//最后评论时间
	private String hasUnreadComment;	//是否有未读评论
	
	@Transient
	private List<ThreadComment> comments;
	
	@Transient
	private String isThreadOwner;	//是否为帖子的所有人
	
	@Transient 
	private String formattedDateTime;	//时间。格式为：xx秒前，xx分钟前
	
	@Transient
	private List<String> imgUrlLink;	//上传图片的原图链接
	
	@Transient
	private List<String> thumbnailLink;	//上传图片的缩略图
	
	@Transient
	private List<String> previewLink;	//发布首页预览图
	
	@Transient
	private String categoryImgName;	//分类图片名称
	
	@Transient
	private String categoryCnName;	//帖子分类中文名称
	
	public long getThreadId() {
		return threadId;
	}
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}
	public String getThreadTitle() {
		return threadTitle;
	}
	public void setThreadTitle(String threadTitle) {
		this.threadTitle = threadTitle;
	}
	public String getThreadContent() {
		return threadContent;
	}
	public void setThreadContent(String threadContent) {
		this.threadContent = threadContent;
	}
	public String getThreadStatus() {
		return threadStatus;
	}
	public void setThreadStatus(String threadStatus) {
		this.threadStatus = threadStatus;
	}
	public String getThreadCategory() {
		return threadCategory;
	}
	public void setThreadCategory(String threadCategory) {
		this.threadCategory = threadCategory;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserHead() {
		return userHead;
	}
	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public long getLikes() {
		return likes;
	}
	public void setLikes(long likes) {
		this.likes = likes;
	}
	public long getUserSectId() {
		return userSectId;
	}
	public void setUserSectId(long userSectId) {
		this.userSectId = userSectId;
	}
	public List<ThreadComment> getComments() {
		return comments;
	}
	public void setComments(List<ThreadComment> comments) {
		this.comments = comments;
	}
	public long getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(long commentsCount) {
		this.commentsCount = commentsCount;
	}
	public String getUserSectName() {
		return userSectName;
	}
	public void setUserSectName(String userSectName) {
		this.userSectName = userSectName;
	}
	public double getUserSectLatitude() {
		return userSectLatitude;
	}
	public void setUserSectLatitude(double userSectLatitude) {
		this.userSectLatitude = userSectLatitude;
	}
	public double getUserSectLongtitude() {
		return userSectLongtitude;
	}
	public void setUserSectLongtitude(double userSectLongtitude) {
		this.userSectLongtitude = userSectLongtitude;
	}
	
	public String getIsThreadOwner() {
		return isThreadOwner;
	}
	public void setIsThreadOwner(String isThreadOwner) {
		this.isThreadOwner = isThreadOwner;
	}
	public long getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(long createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	public String getFormattedDateTime() {
		
		long time = createDateTime;
		return DateUtil.getSendTime(time);
		
	}
	public String getUploadPicId() {
		return uploadPicId;
	}
	public void setUploadPicId(String uploadPicId) {
		this.uploadPicId = uploadPicId;
	}
	
	
	public List<String> getImgUrlLink() {
		return imgUrlLink;
	}
	public void setImgUrlLink(List<String> imgUrlLink) {
		this.imgUrlLink = imgUrlLink;
	}
	public List<String> getThumbnailLink() {
		return thumbnailLink;
	}
	public void setThumbnailLink(List<String> thumbnailLink) {
		this.thumbnailLink = thumbnailLink;
	}
	public List<String> getPreviewLink() {
		return previewLink;
	}
	public void setPreviewLink(List<String> previewLink) {
		this.previewLink = previewLink;
	}
	public String getCategoryImgName() {
		return categoryImgName;
	}
	public void setCategoryImgName(String categoryImgName) {
		this.categoryImgName = categoryImgName;
	}
	public String getCategoryCnName() {
		return categoryCnName;
	}
	public void setCategoryCnName(String categoryCnName) {
		this.categoryCnName = categoryCnName;
	}
	public String getStickPriority() {
		return stickPriority;
	}
	public void setStickPriority(String stickPriority) {
		this.stickPriority = stickPriority;
	}
	public String getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}
	public String getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}
	public long getLastCommentTime() {
		return lastCommentTime;
	}
	public void setLastCommentTime(long lastCommentTime) {
		this.lastCommentTime = lastCommentTime;
	}
	public String getHasUnreadComment() {
		return hasUnreadComment;
	}
	public void setHasUnreadComment(String hasUnreadComment) {
		this.hasUnreadComment = hasUnreadComment;
	}
	
	public static void main(String[] args) {
		
		
		long time = System.currentTimeMillis();
		try {
			java.lang.Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = DateUtil.getSendTime(time);
		System.out.println(str.toString());
	}
	
	
}
