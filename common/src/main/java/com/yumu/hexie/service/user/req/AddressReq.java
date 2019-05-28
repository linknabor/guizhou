/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.user.req;

import java.io.Serializable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: AddressReq.java, v 0.1 2016年5月23日 下午2:56:00  Exp $
 */
public class AddressReq implements Serializable {
    
	private static final long serialVersionUID = -3522389483000401026L;
	
	private Long addrId;
    private long xiaoquId;
    private String tel;
    private String name;
    private String detailAddr;
	private boolean main;//是否是默认地址
    



	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDetailAddr() {
        return detailAddr;
    }
    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }
    public Long getAddrId() {
        return addrId;
    }
    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
   
    public long getXiaoquId() {
		return xiaoquId;
	}
	public void setXiaoquId(long xiaoquId) {
		this.xiaoquId = xiaoquId;
	}
	public boolean isMain() {
        return main;
    }
    public void setMain(boolean main) {
        this.main = main;
    }
    
}
