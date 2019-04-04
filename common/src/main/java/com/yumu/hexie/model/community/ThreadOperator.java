/**
 * 
 */
package com.yumu.hexie.model.community;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * @author davidhardson
 *
 */
@Entity
public class ThreadOperator extends BaseModel {
	
	private static final long serialVersionUID = -8331455709581509886L;
	
	private long userId;
	private String userName;
	private String tel;
	private String openId;
	private String regionType;	//区域类型 0全平台，1公司，2中心3，小区
	private String regionSectId;//区域ID,对应regionifo中的sect_id而不是id
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRegionSectId() {
		return regionSectId;
	}
	public void setRegionSectId(String regionSectId) {
		this.regionSectId = regionSectId;
	}

	
}
