/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.web.repair;

import java.io.Serializable;

import com.yumu.hexie.model.localservice.repair.RepairProject;
import com.yumu.hexie.model.user.Address;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairReqPageVO.java, v 0.1 2016年1月6日 下午11:36:18  Exp $
 */
public class RepairReqPageVO implements Serializable {

    private static final long serialVersionUID = -7255519434880353350L;
    private RepairProject project;
    private Address address;
    public RepairProject getProject() {
        return project;
    }
    public void setProject(RepairProject project) {
        this.project = project;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    
}
