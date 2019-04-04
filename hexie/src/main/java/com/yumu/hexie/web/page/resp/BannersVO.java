package com.yumu.hexie.web.page.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yumu.hexie.model.view.Banner;

public class BannersVO implements Serializable{
	
	private static final long serialVersionUID = 3154596179087517463L;
	private List<Banner> titles = new ArrayList<>();
	private List<Banner> activities = new ArrayList<>();
	private List<BannerProductVO> brands = new ArrayList<>();
	private List<Banner> product = new ArrayList<>();
	public List<Banner> getTitles() {
		return titles;
	}
	public void setTitles(List<Banner> titles) {
		this.titles = titles;
	}
	public List<Banner> getActivities() {
		return activities;
	}
	public void setActivities(List<Banner> activities) {
		this.activities = activities;
	}
	public List<BannerProductVO> getBrands() {
		return brands;
	}
	public void setBrands(List<BannerProductVO> brands) {
		this.brands = brands;
	}
	public List<Banner> getProduct() {
		return product;
	}
	public void setProduct(List<Banner> product) {
		this.product = product;
	}
}
