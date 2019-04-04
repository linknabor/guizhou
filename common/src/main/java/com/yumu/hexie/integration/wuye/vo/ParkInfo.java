package com.yumu.hexie.integration.wuye.vo;

import java.io.Serializable;

public class ParkInfo implements Serializable {

	private static final long serialVersionUID = -1730195214911152006L;
	private String park_type;
    private String carport_type;
    private String cell_no;
    private String car_id;
    private String car_no;
    private String car_colour;
    private String car_type;
    private String car_brand;
	public String getPark_type() {
		return park_type;
	}
	public void setPark_type(String park_type) {
		this.park_type = park_type;
	}
	public String getCarport_type() {
		return carport_type;
	}
	public void setCarport_type(String carport_type) {
		this.carport_type = carport_type;
	}
	public String getCell_no() {
		return cell_no;
	}
	public void setCell_no(String cell_no) {
		this.cell_no = cell_no;
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getCar_no() {
		return car_no;
	}
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}
	public String getCar_colour() {
		return car_colour;
	}
	public void setCar_colour(String car_colour) {
		this.car_colour = car_colour;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getCar_brand() {
		return car_brand;
	}
	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}
    
}
