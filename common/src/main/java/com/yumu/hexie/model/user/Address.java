package com.yumu.hexie.model.user;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.distribution.region.AmapAddress;

//个人地址
@Entity
public class Address  extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private Long amapId;//高德地图中的_id 没有初始化时为空，若空则需要生成一条记录
	private String amapDetailAddr;//小区的地址，对应高德地图中detailAddr
	private long provinceId;
	private String province;
	
	private long cityId;
	private String city;
	
	private long countyId;
	private String county;
	
	private long xiaoquId;
	private String xiaoquName;
	private String xiaoquAddress;

	private double longitude;
    private double latitude;
    
	private long userId;
	private String userName;
	private String receiveName;
	private String detailAddress;
	private String tel;
	private boolean main;//是否是默认地址
	
	public void initAmapInfo(AmapAddress amapAddr){
	    setAmapId(amapAddr.getId());
        setLongitude(amapAddr.getLon()); 
        setLatitude(amapAddr.getLat());
        setAmapDetailAddr(amapAddr.getDetailaddress());
	}
	
	@Transient
	public String getRegionStr(){
		String province = getProvince();
		if (StringUtil.isEmpty(province)) {
			province = "";
		}
		if(province.indexOf("上海")>=0
				||province.indexOf("北京")>=0
				||province.indexOf("重庆")>=0
				||province.indexOf("天津")>=0){
			province = "";
		}
		return province+getCity()+ getCounty()+getXiaoquName();
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public boolean isMain() {
		return main;
	}
	public void setMain(boolean main) {
		this.main = main;
	}
	public long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getCountyId() {
		return countyId;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public long getXiaoquId() {
		return xiaoquId;
	}
	public void setXiaoquId(long xiaoquId) {
		this.xiaoquId = xiaoquId;
	}
	public String getXiaoquName() {
		return xiaoquName;
	}
	public void setXiaoquName(String xiaoquName) {
		this.xiaoquName = xiaoquName;
	}
	public Long getAmapId() {
		return amapId;
	}
	public void setAmapId(Long amapId) {
		this.amapId = amapId;
	}
	public String getAmapDetailAddr() {
		return amapDetailAddr;
	}
	public void setAmapDetailAddr(String amapDetailAddr) {
		this.amapDetailAddr = amapDetailAddr;
	}

	public String getXiaoquAddress() {
		return xiaoquAddress;
	}

	public void setXiaoquAddress(String xiaoquAddress) {
		this.xiaoquAddress = xiaoquAddress;
	}
	
}
