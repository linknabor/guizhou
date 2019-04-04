/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.o2o;

import java.util.List;

import com.yumu.hexie.model.localservice.ServiceOperator;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: OperatorService.java, v 0.1 2016年4月8日 下午8:07:14  Exp $
 */
public interface OperatorService {
    
    public ServiceOperator findByTypeAndUserId(int type,long userId);
    public boolean isOperator(int type,long userId);
    public List<ServiceOperator> findByIds(List<Long> ids);

    public List<ServiceOperator> findByType(int type);
}
