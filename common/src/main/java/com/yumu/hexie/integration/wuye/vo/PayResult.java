package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayResult implements Serializable {
	
	private static final long serialVersionUID = 1488334606713537518L;

	private String trade_water_id;
	private String merger_status;
	@JsonProperty("package")
	private String pacakge_id;
	
	public String getTrade_water_id() {
		return trade_water_id;
	}
	public void setTrade_water_id(String trade_water_id) {
		this.trade_water_id = trade_water_id;
	}
	public String getMerger_status() {
		return merger_status;
	}
	public void setMerger_status(String merger_status) {
		this.merger_status = merger_status;
	}
	public String getPacakge_id() {
		return pacakge_id;
	}
	public void setPacakge_id(String pacakge_id) {
		this.pacakge_id = pacakge_id;
	}
	

}
