package com.yumu.hexie.web.page.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.distribution.OnSaleAreaItem;
import com.yumu.hexie.model.distribution.RgroupAreaItem;

public class SaleIndexVO implements Serializable{

	private static final long serialVersionUID = -2676616753491950466L;
	private List<OnSaleAreaItem> temais;
	private List<RgroupAreaItem> tuangous;
	public List<OnSaleAreaItem> getTemais() {
		return temais;
	}
	public void setTemais(List<OnSaleAreaItem> temais) {
		this.temais = temais;
	}
	public List<RgroupAreaItem> getTuangous() {
		return tuangous;
	}
	public void setTuangous(List<RgroupAreaItem> tuangous) {
		this.tuangous = tuangous;
	}
}
