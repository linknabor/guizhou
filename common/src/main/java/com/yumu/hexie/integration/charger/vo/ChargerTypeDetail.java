package com.yumu.hexie.integration.charger.vo;

import java.io.Serializable;

public class ChargerTypeDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	private String type_id;
	private String charger_amt;
	
	private boolean selected =false;//为了展示用
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getCharger_amt() {
		return charger_amt;
	}
	public void setCharger_amt(String charger_amt) {
		this.charger_amt = charger_amt;
	}
	
}