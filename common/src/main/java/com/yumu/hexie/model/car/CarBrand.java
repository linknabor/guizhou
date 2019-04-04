package com.yumu.hexie.model.car;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * 车辆品牌信息（来自车大大）
 * @author hwb_work
 *
 */
@Entity
public class CarBrand extends BaseModel {
	private static final long serialVersionUID = 5374009375570271895L;
	
	private String brandId; //品牌编号
	private String letter; //首字母
	private String image; //图标
	private String brandName; //名称
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
}

