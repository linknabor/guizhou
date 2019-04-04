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
public class RepairOrderVO implements Serializable {
	
	private static final long serialVersionUID = 4594080226499648498L;

	@JsonProperty("first")
	private TemplateItem title;
	
	@JsonProperty("keyword1")
	private TemplateItem orderNum;	//维修单号
	
	@JsonProperty("keyword2")
	private TemplateItem custName;	//客户姓名
	
	@JsonProperty("keyword3")
	private TemplateItem custMobile;	//客户电话
	
	@JsonProperty("keyword4")
	private TemplateItem custAddr;	//维修地址
	
	private TemplateItem remark;	//备注

	public TemplateItem getTitle() {
		return title;
	}

	public void setTitle(TemplateItem title) {
		this.title = title;
	}

	public TemplateItem getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(TemplateItem orderNum) {
		this.orderNum = orderNum;
	}

	public TemplateItem getCustName() {
		return custName;
	}

	public void setCustName(TemplateItem custName) {
		this.custName = custName;
	}

	public TemplateItem getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(TemplateItem custMobile) {
		this.custMobile = custMobile;
	}

	public TemplateItem getCustAddr() {
		return custAddr;
	}

	public void setCustAddr(TemplateItem custAddr) {
		this.custAddr = custAddr;
	}

	public TemplateItem getRemark() {
		return remark;
	}

	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}
	
	

}
