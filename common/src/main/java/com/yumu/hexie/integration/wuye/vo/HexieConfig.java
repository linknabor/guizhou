package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;
import java.util.Map;

public class HexieConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1046757213613373576L;
	private String infoId;
	private String infoType;
	private Map<String, String> paramMap;
	
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	
	
}
