package com.yumu.hexie.integration.amap.req;

import java.io.Serializable;

public class DataCreateReq implements Serializable{

	private static final long serialVersionUID = -5968127142709415032L;

	private String _name;
	private String _location;
	private String coordtype;
	private String _address;
	private String city; //城市
	private String region; //区
	private String detailaddress;

	
	public DataCreateReq() {
	}
	
	public DataCreateReq(String _name, String _location, String coordtype,
			String _address, String city, String region,String detailaddress) {
		this._name = _name;
		this._location = _location;
		this.coordtype = coordtype;
		this._address = _address;
		this.city = city;
		this.region = region;
		this.detailaddress = detailaddress;
	}

	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_location() {
		return _location;
	}
	public void set_location(String _location) {
		this._location = _location;
	}
	public String getCoordtype() {
		return coordtype;
	}
	public void setCoordtype(String coordtype) {
		this.coordtype = coordtype;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
}
