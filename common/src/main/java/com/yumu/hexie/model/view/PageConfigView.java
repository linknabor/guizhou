/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: PageConfigView.java, v 0.1 2016年1月18日 上午9:41:56  Exp $
 */
@Entity
public class PageConfigView extends BaseModel {

    private static final long serialVersionUID = 7403265377382069482L;

    @Column(unique=true,length=100)
    private String tempKey;

    @Column(length=2047)
    private String pageConfig;
    
    private String description;
    
    private boolean available;


    public String getPageConfig() {
        return pageConfig;
    }

    public void setPageConfig(String pageConfig) {
        this.pageConfig = pageConfig;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }
}
