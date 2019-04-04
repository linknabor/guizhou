package com.yumu.hexie.model.distribution;

import javax.persistence.Entity;
import javax.persistence.Transient;

//团购上架管理
@Entity
public class RgroupAreaItem  extends RuleDistribution {
	private static final long serialVersionUID = 4808669460780339640L;

	@Transient
	private int process;//进度
	private boolean featured = false;
	private int productType;

	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	public int getProductType() {
		return productType;
	}
	public void setProductType(int productType) {
		this.productType = productType;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
}
