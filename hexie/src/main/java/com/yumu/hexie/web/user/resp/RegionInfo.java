/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.user.resp;

import java.io.Serializable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RegionInfo.java, v 0.1 2016年5月23日 下午5:29:17  Exp $
 */
public class RegionInfo implements Serializable {

    private static final long serialVersionUID = -2642514567982327974L;

    public RegionInfo(){}
    public RegionInfo(String name,long id) {
        this.name = name;
        this.value = id;
    }
    private String name;
    private long value;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
    
}
