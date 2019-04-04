package com.yumu.hexie.integration.daojia.ayilaile.resp;

import java.util.List;

import com.yumu.hexie.model.localservice.oldversion.thirdpartyorder.AyiServiceOrder;

public class ServiceOrderResp  extends BaseResp{

	private static final long serialVersionUID = 2243368998351186024L;
	private List<AyiServiceOrder> data;
	public List<AyiServiceOrder> getData() {
		return data;
	}
	public void setData(List<AyiServiceOrder> data) {
		this.data = data;
	}
	

}
