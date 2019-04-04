package com.yumu.hexie.model.distribution;

import javax.persistence.MappedSuperclass;

import com.yumu.hexie.model.BaseModel;
@MappedSuperclass
public class Distribution extends BaseModel{
	private static final long serialVersionUID = 4808669460780339640L;
	private long regionId;//区域
	private int regionType;//0 省 1 市 2县区  3全部
	private long productId; //商品

    private String productName;//商品名称
    private String productPic;//商品图片
    
	private int status;//RULE_STATUS_ON

	private String tagUrl;//右上角标签URL

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public int getRegionType() {
		return regionType;
	}

	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTagUrl() {
		return tagUrl;
	}

	public void setTagUrl(String tagUrl) {
		this.tagUrl = tagUrl;
	}
	
}
