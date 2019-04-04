/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.user.req;

import java.io.Serializable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SimpleRegisterReq.java, v 0.1 2016年3月15日 下午3:14:05  Exp $
 */
public class SimpleRegisterReq implements Serializable{
    private static final long serialVersionUID = -2090643413772467559L;
    private String mobile;
    private String yzm;
    private String name;
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getYzm() {
        return yzm;
    }
    public void setYzm(String yzm) {
        this.yzm = yzm;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
