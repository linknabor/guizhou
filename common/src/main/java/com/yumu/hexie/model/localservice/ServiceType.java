/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ServiceType.java, v 0.1 2016年3月28日 上午9:02:30  Exp $
 */
@Entity
public class ServiceType  extends BaseModel {
    private static final long serialVersionUID = -2312170260993749078L;
    
    private String typeName;
    private String typeIcon;
    private String description;
    private Long merchantId;
    private long parentId;
    private Long shipTemplate;
    public ServiceType(){
        
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeIcon() {
        return typeIcon;
    }
    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getParentId() {
        return parentId;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public Long getShipTemplate() {
        return shipTemplate;
    }
    public void setShipTemplate(Long shipTemplate) {
        this.shipTemplate = shipTemplate;
    }

}
