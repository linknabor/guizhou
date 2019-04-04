/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.basemodel;

import java.util.Date;


public interface HasOperator {

    public Long getOperatorId();
    public void setOperatorId(Long operatorId);
    public String getOperatorCompanyName();
    public void setOperatorCompanyName(String operatorCompanyName) ;

    public String getOperatorName();
    public void setOperatorName(String operatorName);
    public String getOperatorTel();
    public void setOperatorTel(String operatorTel);
    

    public Date getOperatorConfirmTime();
    public void setOperatorConfirmTime(Date operatorConfirmTime);
}
