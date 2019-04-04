package com.yumu.hexie.integration.wuye.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.integration.wuye.vo.PayWater;

public class PayWaterListVO implements Serializable {

	private static final long serialVersionUID = -2243622197573945599L;
	private List<PayWater> bill_info_water;

	public List<PayWater> getBill_info_water() {
		return bill_info_water;
	}

	public void setBill_info_water(List<PayWater> bill_info_water) {
		this.bill_info_water = bill_info_water;
	}
	
}
