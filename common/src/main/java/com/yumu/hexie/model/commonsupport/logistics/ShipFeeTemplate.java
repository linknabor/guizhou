/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.commonsupport.logistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ShipFeeTemplate.java, v 0.1 2016年5月26日 下午5:56:09  Exp $
 */

@Entity
public class ShipFeeTemplate extends BaseModel {
    public static final String SPLIT = ",";

    private static final long serialVersionUID = -1989732048961885858L;
    
    private BigDecimal shipFee;
    private BigDecimal freeFeeLimit;
    private String name;
    private String freeFeeRegionIds;
    
    private BigDecimal settleAmount;
    private BigDecimal freeSettleLimit;
    
    public List<Long> extractFreeRegions() {
        List<Long> ids = new ArrayList<Long>();
        if(StringUtil.isEmpty(freeFeeRegionIds)) {
            return ids;
        }
        String[] idStrs = freeFeeRegionIds.split(",");
        for(String idStr : idStrs) {
            try{
                long id = Long.getLong(idStr);
                if(id>0) {
                    ids.add(id);
                }
            }catch(Exception e) {}
        }
        return ids;
    }
    
    public BigDecimal getShipFee() {
        return shipFee;
    }
    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }
    public BigDecimal getFreeFeeLimit() {
        return freeFeeLimit;
    }
    public void setFreeFeeLimit(BigDecimal freeFeeLimit) {
        this.freeFeeLimit = freeFeeLimit;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFreeFeeRegionIds() {
        return freeFeeRegionIds;
    }
    public void setFreeFeeRegionIds(String freeFeeRegionIds) {
        this.freeFeeRegionIds = freeFeeRegionIds;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getFreeSettleLimit() {
        return freeSettleLimit;
    }

    public void setFreeSettleLimit(BigDecimal freeSettleLimit) {
        this.freeSettleLimit = freeSettleLimit;
    }

}
