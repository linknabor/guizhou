/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service;

import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.model.system.SystemConfig;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SharedSysConfigService.java, v 0.1 2016年5月9日 下午9:33:31  Exp $
 */
public interface SharedSysConfigService {

    public void saveAccessToken(AccessToken at);
    public void saveJsToken(String jsToken);
    
}
