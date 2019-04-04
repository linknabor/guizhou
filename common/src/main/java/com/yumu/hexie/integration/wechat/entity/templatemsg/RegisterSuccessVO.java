/**
 * 
 */
package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author HuYM
 *
 */
public class RegisterSuccessVO implements Serializable {

	private static final long serialVersionUID = 7225243307278447899L;

	private TemplateItem first;
	
	@JsonProperty("keyword1")
	private TemplateItem userName;
	
	@JsonProperty("keyword2")
	private TemplateItem registerDateTime;
	
	private TemplateItem remark;

	public TemplateItem getFirst() {
		return first;
	}

	public void setFirst(TemplateItem first) {
		this.first = first;
	}

	public TemplateItem getUserName() {
		return userName;
	}

	public void setUserName(TemplateItem userName) {
		this.userName = userName;
	}

	public TemplateItem getRegisterDateTime() {
		return registerDateTime;
	}

	public void setRegisterDateTime(TemplateItem registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	public TemplateItem getRemark() {
		return remark;
	}

	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}
	
	
	
}
