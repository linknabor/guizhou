package com.yumu.hexie.model.view;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class QrCode extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4533516247634177772L;
	
	private String fromSys;
	private String qrLink;
	public String getFromSys() {
		return fromSys;
	}
	public void setFromSys(String fromSys) {
		this.fromSys = fromSys;
	}
	public String getQrLink() {
		return qrLink;
	}
	public void setQrLink(String qrLink) {
		this.qrLink = qrLink;
	}
	

}
