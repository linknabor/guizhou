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
    private static final long serialVersionUID = 4808669460780339640L;
    private Long amapId;//高德地图中的_id 没有初始化时为空，若空则需要生成一条记录
    private String amapDetailAddr;//小区的地址，对应高德地图中detailAddr
    
    private Long id;
    private long countyId;
    private long xiaoquId;
    private String xiaoquName;

    private long userId;
    private String receiveName;
    private String detailAddress;
    private String tel;
    private boolean main;//是否是默认地址
    public Long getAmapId() {
        return amapId;
    }
    public void setAmapId(Long amapId) {
        this.amapId = amapId;
    }
    public String getAmapDetailAddr() {
        return amapDetailAddr;
    }
    public void setAmapDetailAddr(String amapDetailAddr) {
        this.amapDetailAddr = amapDetailAddr;
    }
    public Long getId() {
        if(id == null) return 0l;
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCountyId() {
        return countyId;
    }
    public void setCountyId(long countyId) {
        this.countyId = countyId;
    }
    public long getXiaoquId() {
        return xiaoquId;
    }
    public void setXiaoquId(long xiaoquId) {
        this.xiaoquId = xiaoquId;
    }
    public String getXiaoquName() {
        return xiaoquName;
    }
    public void setXiaoquName(String xiaoquName) {
        this.xiaoquName = xiaoquName;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getReceiveName() {
        return receiveName;
    }
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    public String getDetailAddress() {
        return detailAddress;
    }
    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public boolean isMain() {
        return main;
    }
    public void setMain(boolean main) {
        this.main = main;
    }
    
}
