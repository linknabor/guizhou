/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.model.localservice.repair;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: RepairProjectRepository.java, v 0.1 2016年1月1日 上午6:57:22  Exp $
 */
public interface RepairProjectRepository extends JpaRepository<RepairProject, Long> {

    public List<RepairProject> queryByRepairTypeAndStatus(int type,int status);
}
