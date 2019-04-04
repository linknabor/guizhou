package com.yumu.hexie.web.page.resp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yumu.hexie.model.distribution.OnSaleAreaItem;
import com.yumu.hexie.model.view.Banner;

public class BannerProductVO extends Banner {
	private static final long serialVersionUID = 3617608975817929392L;

	public BannerProductVO(){}
	public BannerProductVO(Banner banner) {
		super();
		BeanUtils.copyProperties(banner, this);
	}
	
	private List<OnSaleAreaItem> icons = new ArrayList<>();

	public List<OnSaleAreaItem> getIcons() {
		return icons;
	}

	public void setIcons(List<OnSaleAreaItem> icons) {
		this.icons = icons;
	}
	
}
