package com.yumu.hexie.model.localservice.repair;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class ServiceOperatorSect extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2117643880075908961L;
	
	private long operatorId;
	private String sectId;
	public long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	public String getSectId() {
		return sectId;
	}
	public void setSectId(String sectId) {
		this.sectId = sectId;
	}
	

}
