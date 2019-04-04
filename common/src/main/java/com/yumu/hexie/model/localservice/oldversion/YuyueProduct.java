package com.yumu.hexie.model.localservice.oldversion;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

@Entity
public class YuyueProduct extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long merchantId;
	private String name;
	private String mainPicture;//封面图片
	private String smallPicture;//商品列表小图
	private String pictures;//逗号分隔
	
	private String thirdPartyMerchantNo;//第三方商品编号
	private String thirdPartyMerchantName;
	
	
	private int status;//0. 初始化   1. 下架   2. ONSALE  3. 删除

	private String serviceDesc;


	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public String getSmallPicture() {
		return smallPicture;
	}

	public void setSmallPicture(String smallPicture) {
		this.smallPicture = smallPicture;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getThirdPartyMerchantNo() {
		return thirdPartyMerchantNo;
	}

	public void setThirdPartyMerchantNo(String thirdPartyMerchantNo) {
		this.thirdPartyMerchantNo = thirdPartyMerchantNo;
	}

	public String getThirdPartyMerchantName() {
		return thirdPartyMerchantName;
	}

	public void setThirdPartyMerchantName(String thirdPartyMerchantName) {
		this.thirdPartyMerchantName = thirdPartyMerchantName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
}
