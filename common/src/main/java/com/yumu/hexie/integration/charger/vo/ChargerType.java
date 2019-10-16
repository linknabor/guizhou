package com.yumu.hexie.integration.charger.vo;

import java.io.Serializable;
import java.util.List;

public class ChargerType implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ChargerTypeDetail> chargerTypeItem;

	public List<ChargerTypeDetail> getChargerTypeItem() {
		return chargerTypeItem;
	}

	public void setChargerTypeItem(List<ChargerTypeDetail> chargerTypeItem) {
		this.chargerTypeItem = chargerTypeItem;
	}
	
}