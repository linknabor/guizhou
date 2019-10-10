package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class HexieUser implements Serializable {

	private static final long serialVersionUID = -8863855555160105591L;
	
	private String user_id;
	
	private String user_name;
	
	private String user_head;
	
	private String user_sex;
	
	private String user_email;
	
	private String email_activie;
	private String user_tel;
	private String is_house;//房屋数
	private String office_tel;//小区电话
	private String cell_addr;//小区地址
	private String sect_name;//小区名字
	private long province_id;//省id
	private String province_name;//省名
	private long city_id;//城市ID
	private String city_name;//城市名称
	private long region_id;//区域ID
	private String region_name;//区域名
	private String sect_addr;
	
	private String sect_id;//小区id
	private String csp_id;//公司
	private String center_id;//中心管理id
	
	public String getSect_id() {
		return sect_id;
	}
	public void setSect_id(String sect_id) {
		this.sect_id = sect_id;
	}
	public String getCsp_id() {
		return csp_id;
	}
	public void setCsp_id(String csp_id) {
		this.csp_id = csp_id;
	}
	public String getCenter_id() {
		return center_id;
	}
	public void setCenter_id(String center_id) {
		this.center_id = center_id;
	}
	public String getSect_addr() {
		return sect_addr;
	}
	public void setSect_addr(String sect_addr) {
		this.sect_addr = sect_addr;
	}
	public long getProvince_id() {
		return province_id;
	}
	public void setProvince_id(long province_id) {
		this.province_id = province_id;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public long getCity_id() {
		return city_id;
	}
	public void setCity_id(long city_id) {
		this.city_id = city_id;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getSect_name() {
		return sect_name;
	}
	public void setSect_name(String sect_name) {
		this.sect_name = sect_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_head() {
		return user_head;
	}
	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getEmail_activie() {
		return email_activie;
	}
	public void setEmail_activie(String email_activie) {
		this.email_activie = email_activie;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getIs_house() {
		return is_house;
	}
	public void setIs_house(String is_house) {
		this.is_house = is_house;
	}
	public String getOffice_tel() {
		return office_tel;
	}
	public void setOffice_tel(String office_tel) {
		this.office_tel = office_tel;
	}
	public String getCell_addr() {
		return cell_addr;
	}
	public void setCell_addr(String cell_addr) {
		this.cell_addr = cell_addr;
	}
	
}
