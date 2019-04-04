/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;
import java.util.List;

import com.yumu.hexie.model.localservice.ServiceItem;
import com.yumu.hexie.model.localservice.ServiceType;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: XiyiItemsVO.java, v 0.1 2016年4月12日 上午6:40:43  Exp $
 */
public class XiyiItemsVO implements Serializable {

    private static final long serialVersionUID = 1537922238779129898L;
    private List<ServiceItem> items;
    private ServiceType type;
    public List<ServiceItem> getItems() {
        return items;
    }
    public void setItems(List<ServiceItem> items) {
        this.items = items;
    }
    public ServiceType getType() {
        return type;
    }
    public void setType(ServiceType type) {
        this.type = type;
    }
    
}
