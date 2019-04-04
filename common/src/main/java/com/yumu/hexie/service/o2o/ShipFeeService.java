/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.math.BigDecimal;
import java.util.List;

import com.yumu.hexie.model.commonsupport.logistics.ShipFeeTemplate;

/**
 * <pre>
 * 运费计算服务
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: ShipFeeService.java, v 0.1 2016年5月26日 下午5:47:27  Exp $
 */
public interface ShipFeeService {

    /**
     * 查询运费模板
     */
    public ShipFeeTemplate findTemplate(long tplId);
    
    public BigDecimal getShipFee(long tplId, BigDecimal amount, List<Long> regionIds);
    
    public ShipFeeTemplate findTemplateByItem(long itemId);
    
}
