package com.yumu.hexie.model.promotion.coupon;

import java.util.Date;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class CouponCombination extends BaseModel {

	private static final long serialVersionUID = 5288510512518844706L;
	
	private long seedId;
	
	private String seedStr;
	
	private Date startDate;
	
	private Date endDate;
	
	private int status;
	
	private int combinationType;

	public long getSeedId() {
		return seedId;
	}

	public void setSeedId(long seedId) {
		this.seedId = seedId;
	}

	public String getSeedStr() {
		return seedStr;
	}

	public void setSeedStr(String seedStr) {
		this.seedStr = seedStr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCombinationType() {
		return combinationType;
	}

	public void setCombinationType(int combinationType) {
		this.combinationType = combinationType;
	}


}
