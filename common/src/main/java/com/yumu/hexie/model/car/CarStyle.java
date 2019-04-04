package com.yumu.hexie.model.car;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**车型信息（来自车大大）
 * 
 * @author hwb_work
 *
 */
@Entity
public class CarStyle extends BaseModel{
	private static final long serialVersionUID = 6673681361717876184L;
	private String styleId; //车型编号
	private String styleName; //车型名称
	private String styleType; //车型分类
	private String brandId; //品牌编号
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public String getStyleType() {
		return styleType;
	}
	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	
}

