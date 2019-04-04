/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import javax.persistence.Entity;

import com.yumu.hexie.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairProject.java, v 0.1 2016年1月1日 上午9:14:35  Exp $
 */
@Entity
public class RepairProject  extends BaseModel {

    private static final long serialVersionUID = 2682534187089240867L;
    private int repairType;
    private int status = RepairConstant.PROJECT_STATUS_AVALIBLE;
    private String name;
    private boolean publicProject;
    public int getRepairType() {
        return repairType;
    }
    public void setRepairType(int repairType) {
        this.repairType = repairType;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isPublicProject() {
        return publicProject;
    }
    public void setPublicProject(boolean publicProject) {
        this.publicProject = publicProject;
    }
    
}
