package com.yumu.hexie.web.user.req;

import java.io.Serializable;

public class RegisterReq implements Serializable{
	private static final long serialVersionUID = -2090643413772467559L;
	private String name;
	private String realName;
	private Integer sex;
	private String tel;
	private String yzm;
	
	private long provinceId;
	private String province;
	
	private long cityId;
	private String city;
	
	private long countyId;
	private String county;
	
	private long xiaoquId;
	private String xiaoquName;
    
	private String detailAddress;
	private Long amapId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
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

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Long getAmapId() {
		return amapId;
	}

	public void setAmapId(Long amapId) {
		this.amapId = amapId;
	}
	
	

}
