/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;

import java.math.BigDecimal;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: NeedShip.java, v 0.1 2016年5月27日 上午7:27:21  Exp $
 */
public interface NeedShipFee {

    public BigDecimal getShipFee();
    public long getShipFeeTplId();

    public void setShipFee(BigDecimal fee);
    public void setShipFeeTplId( long tplId);

    public BigDecimal getShipSettleFee();
    public void setShipSettleFee(BigDecimal fee);
    
}
