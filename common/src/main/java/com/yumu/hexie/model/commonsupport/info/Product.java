package com.yumu.hexie.model.commonsupport.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.yumu.hexie.common.util.DateUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;

@Entity
//商品
public class Product extends BaseModel {

	private static final long serialVersionUID = 4808669460780339640L;
	private long merchantId;
	private String productNo;
	private String merchanProductNo;
	private String  productType;
	private String name;
	private String mainPicture;//封面图片
	private String smallPicture;//商品列表小图
	private String pictures;//逗号分隔
	private float miniPrice;//最低价格（基准价）
	private float oriPrice;//原价（市场价）
	private float singlePrice;//单个商品价格（单买价）

	private String serviceDescMore;
	private String serviceDesc;
	private int totalCount;//(总量)
	private int saledNum;//(已售个数-减库存时删总量)

	private int freezeNum;//冻结数量
	
	private int status;//0.初始化   1. 上架   2.下架  3.删除
	/*@Temporal(TemporalType.TIMESTAMP)*/
	private Date startDate;//生效开始时间
	 /*
	 @DateTimeFormat(pattern="yyyy-MM-dd")*/
	/*@Temporal(TemporalType.TIMESTAMP)*/
	private Date endDate; //生效结束时间

	private float platformFeeRate = 0;

	private int priority = 0;//优先级-暂时不用，后续用来排序
	private String descUrl; //暂时没用 跳页面的url
	private String otherDesc;//暂时没用
	private String otherDesc1;//暂时没用
	private String serviceTime;//暂时用不到，纯商品功能。   逗号分隔，一天分48个片段
	private String serviceDays;//暂时用不到，后续本地服务需要放开。 逗号分隔，分周一到日
	
	private String showTemplate = "goodDetail";//暂时没用
	private String orderTemplate = "orderDetail";//暂时没用
	

	private Date updateDate;
	private String updateUser;
	private int provenance;//1 进口 2 国产
	
	//新增字段保持与后台一致
	private String shortName;//商品简称
	private String titleName;//商品标题名称
	private String firstType = "00";//一级类目
	private String secondType = "00";//二级类目
	private float postageFee = 0;//快递费
	
	
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
	@Transient
	public String[] getPictureList(){
		if(StringUtils.isEmpty(pictures)) {
			return new String[0];
		}
		else {
			return pictures.trim().split(",");
		}
	}
	@Transient
	public int getCanSaleNum(){
		return totalCount - saledNum - freezeNum;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public float getMiniPrice() {
		return miniPrice;
	}
	public void setMiniPrice(float miniPrice) {
		this.miniPrice = miniPrice;
	}
	public float getOriPrice() {
		return oriPrice;
	}
	public void setOriPrice(float oriPrice) {
		this.oriPrice = oriPrice;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSaledNum() {
		return saledNum;
	}
	public void setSaledNum(int saledNum) {
		this.saledNum = saledNum;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
			this.startDate =DateUtil.getSqlDateFromString(startDate);
	}
	/*public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}*/

	public Date getEndDate() {
		return endDate;
	}
	
	/*public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}*/
	public void setEndDate(String endDate) {
		this.endDate =DateUtil.getSqlDateFromString(endDate);
	}
	public String getServiceDesc() {
		if(StringUtil.isNotEmpty(serviceDesc)){
			return serviceDesc.replaceAll("\n", "<br/>");
		}
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getOtherDesc() {
		return otherDesc;
	}
	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	public String getOtherDesc1() {
		return otherDesc1;
	}
	public void setOtherDesc1(String otherDesc1) {
		this.otherDesc1 = otherDesc1;
	}
	public String getDescUrl() {
		return descUrl;
	}
	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getServiceDays() {
		return serviceDays;
	}
	public void setServiceDays(String serviceDays) {
		this.serviceDays = serviceDays;
	}
	public String getShowTemplate() {
		return showTemplate;
	}
	public void setShowTemplate(String showTemplate) {
		this.showTemplate = showTemplate;
	}
	public String getOrderTemplate() {
		return orderTemplate;
	}
	public void setOrderTemplate(String orderTemplate) {
		this.orderTemplate = orderTemplate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public float getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(float singlePrice) {
		this.singlePrice = singlePrice;
	}
	public float getPlatformFeeRate() {
		return platformFeeRate;
	}
	public void setPlatformFeeRate(float platformFeeRate) {
		this.platformFeeRate = platformFeeRate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getServiceDescMore() {
		if(StringUtil.isNotEmpty(serviceDescMore)){
			return serviceDescMore.replaceAll("\n", "<br/>");
		}
		return serviceDescMore;
	}
	public void setServiceDescMore(String serviceDescMore) {
		this.serviceDescMore = serviceDescMore;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getMerchanProductNo() {
		return merchanProductNo;
	}
	public void setMerchanProductNo(String merchanProductNo) {
		this.merchanProductNo = merchanProductNo;
	}
	public int getProvenance() {
		return provenance;
	}
	public void setProvenance(int provenance) {
		this.provenance = provenance;
	}
	public int getFreezeNum() {
		return freezeNum;
	}
	public void setFreezeNum(int freezeNum) {
		this.freezeNum = freezeNum;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getFirstType() {
		return firstType;
	}
	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}
	public String getSecondType() {
		return secondType;
	}
	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}
	public float getPostageFee() {
		return postageFee;
	}
	public void setPostageFee(float postageFee) {
		this.postageFee = postageFee;
	}
}
