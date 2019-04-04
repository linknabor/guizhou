package com.yumu.hexie.model.distribution.region;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;
import com.yumu.hexie.model.ModelConstant;

//房产
@Entity
public class Region extends BaseModel{

	private static final long serialVersionUID = 4808669460780339640L;
	private int regionType;//0全国 1 省 2 市 3县区 4小区 以ModelConstant为准
	private long parentId;
	private String parentName;
	private String name;
	@Column(nullable=true)
	private Double longitude;
	@Column(nullable=true)
	private Double latitude;	
	private String description;
	@Column(nullable = true)
	private Long amapId;
	
    public Region(long parentId,String parentName,String name){
    	this.regionType = ModelConstant.REGION_XIAOQU;
    	this.parentId = parentId;
    	this.parentName = parentName;
    	this.name = name;
    }
	public Region(){
		
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRegionType() {
		return regionType;
	}
	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
	    
		this.longitude = longitude;
		if(longitude == null) {
            longitude = 0d;
        }
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
        if(latitude == null) {
            latitude = 0d;
        }
	}
    public Long getAmapId() {
        return amapId;
    }
    public void setAmapId(Long amapId) {
        this.amapId = amapId;
    }
	
}
