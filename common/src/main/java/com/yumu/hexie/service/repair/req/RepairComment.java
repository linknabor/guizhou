/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.repair.req;

import java.io.Serializable;

/**
 * <pre>
 * 评价
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairComment.java, v 0.1 2016年1月1日 上午7:28:08  Exp $
 */
public class RepairComment implements Serializable {

    private static final long serialVersionUID = -5169464121307541868L;
    private long repairId;
    private int commentQuality;
    private int commentAttitude;
    private int commentService;
    private String comment;
    private String commentImgUrls;
    public int getCommentQuality() {
        return commentQuality;
    }
    public void setCommentQuality(int commentQuality) {
        this.commentQuality = commentQuality;
    }
    public int getCommentAttitude() {
        return commentAttitude;
    }
    public void setCommentAttitude(int commentAttitude) {
        this.commentAttitude = commentAttitude;
    }
    public int getCommentService() {
        return commentService;
    }
    public void setCommentService(int commentService) {
        this.commentService = commentService;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCommentImgUrls() {
        return commentImgUrls;
    }
    public void setCommentImgUrls(String commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }
    public long getRepairId() {
        return repairId;
    }
    public void setRepairId(long repairId) {
        this.repairId = repairId;
    }
    
}
