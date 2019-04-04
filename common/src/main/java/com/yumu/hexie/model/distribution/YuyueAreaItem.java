package com.yumu.hexie.model.distribution;

import javax.persistence.Entity;

//管理到家服务
@Entity
public class YuyueAreaItem extends RuleDistribution {
	private static final long serialVersionUID = 4808669460780339640L;
	
	private boolean featured = false;
	
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
}
