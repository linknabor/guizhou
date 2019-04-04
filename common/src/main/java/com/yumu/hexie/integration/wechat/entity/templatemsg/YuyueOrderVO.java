/**
 * 
 */
package com.yumu.hexie.integration.wechat.entity.templatemsg;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YuyueOrderVO implements Serializable {
	
	private static final long serialVersionUID = 4594080226499648498L;

	@JsonProperty("first")
	private TemplateItem title;
	
	@JsonProperty("keyword1")
	private TemplateItem requireTime;	//维修单号
	
	@JsonProperty("keyword2")
	private TemplateItem projectName;	//客户姓名
	
	private TemplateItem remark;	//备注

	public TemplateItem getTitle() {
		return title;
	}

	public void setTitle(TemplateItem title) {
		this.title = title;
	}


	public TemplateItem getRemark() {
		return remark;
	}

	public void setRemark(TemplateItem remark) {
		this.remark = remark;
	}

    public TemplateItem getRequireTime() {
        return requireTime;
    }

    public void setRequireTime(TemplateItem requireTime) {
        this.requireTime = requireTime;
    }

    public TemplateItem getProjectName() {
        return projectName;
    }

    public void setProjectName(TemplateItem projectName) {
        this.projectName = projectName;
    }
	
	

}
