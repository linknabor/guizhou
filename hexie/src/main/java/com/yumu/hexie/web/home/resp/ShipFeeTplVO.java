/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.home.resp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ShipFeeTplVO.java, v 0.1 2016年5月27日 上午7:12:00  Exp $
 */
public class ShipFeeTplVO implements Serializable {

    private static final long serialVersionUID = 3373033219696460734L;
    private BigDecimal fee;
    private BigDecimal freeLimit;
    private List<Long> freeRegionIds;
    public ShipFeeTplVO(){}
    public ShipFeeTplVO(ShipFeeTemplate tpl) {
        setFee(tpl.getShipFee());
        setFreeLimit(tpl.getFreeFeeLimit());
        setFreeRegionIds(tpl.extractFreeRegions());
    }
    public BigDecimal getFee() {
        return fee;
    }
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    public BigDecimal getFreeLimit() {
        return freeLimit;
    }
    public void setFreeLimit(BigDecimal freeLimit) {
        this.freeLimit = freeLimit;
    }
    public List<Long> getFreeRegionIds() {
        return freeRegionIds;
    }
    public void setFreeRegionIds(List<Long> freeRegionIds) {
        this.freeRegionIds = freeRegionIds;
    }
    
}
