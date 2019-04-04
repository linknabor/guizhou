package com.yumu.hexie.model.system;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;
import com.yumu.hexie.service.exception.BizValidateException;

//异常信息
@Entity
public class BizError extends BaseModel{
	private static final long serialVersionUID = 7241478759847407303L;
	private int bizType;//内部订单，文章，预约单
	private long bizId;//业务ID
	private String message;
	private int level;
	public BizError(){
		
	}
	public BizError(Exception e) {
		this.bizId = 0;
		this.bizType = ModelConstant.EXCEPTION_BIZ_TYPE_SYSTEM;
		this.message = e.getMessage();
		if(this.message!=null && message.length() > 200) {
		    message = message.substring(200);
		}
		this.level = ModelConstant.EXCEPTION_LEVEL_ERROR;
	}
	public BizError(BizValidateException e) {
		this.bizId = e.getBizId();
		this.bizType = e.getBizType();
		this.message = e.getMessage();
		this.level = e.getLevel();
        if(this.message!=null && message.length() > 200) {
            message = message.substring(200);
        }
	}
	public int getBizType() {
		return bizType;
	}
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
	public long getBizId() {
		return bizId;
	}
	public void setBizId(long bizId) {
		this.bizId = bizId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
