package com.yumu.hexie.model.op;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class ScheduleRecord  extends BaseModel{
	private static final long serialVersionUID = 4808669460780339640L;

	
	private int type;//定时任务类型
	
	private String bizIds;//业务ID
	
	private int errorCount = 0;
	
	private String errorBizIds="";//业务ID
	
	public ScheduleRecord(){}
	
	public ScheduleRecord(int type,String bizIds){
		this.type = type;
		this.bizIds = bizIds;
	}
	private Date finishDate;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBizIds() {
		return bizIds;
	}

	public void setBizIds(String bizIds) {
		this.bizIds = bizIds;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public void addErrorCount(String errorId) {
		this.errorCount++;
		this.errorBizIds +=errorId+",";
	}

	public String getErrorBizIds() {
		return errorBizIds;
	}

	public void setErrorBizIds(String errorBizIds) {
		this.errorBizIds = errorBizIds;
	}
	
    
	
}
