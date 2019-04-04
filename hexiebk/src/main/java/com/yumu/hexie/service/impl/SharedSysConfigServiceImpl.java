/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.model.MultipleRepository;
import com.yumu.hexie.model.system.SystemConfig;
import com.yumu.hexie.model.system.SystemConfigRepository;
import com.yumu.hexie.service.SharedSysConfigService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SharedSysConfigServiceImpl.java, v 0.1 2016年5月9日 下午9:42:55  Exp $
 */
@Service("sharedSysConfigService")
public class SharedSysConfigServiceImpl implements SharedSysConfigService {
	
	private static final Logger log = LoggerFactory.getLogger(SharedSysConfigServiceImpl.class);
	
	private static final String JS_TOKEN = "JS_TOKEN";
	private static final String ACC_TOKEN = "ACCESS_TOKEN";
    public static final String APP_ACC_TOKEN = "APP_TOKEN_%s";
    
    @Inject
    private SystemConfigRepository systemConfigRepository;
    @Inject
    private MultipleRepository multipleRepository;
    
    /**
     * 存ACESS_TOKEN，REDIS与数据库都存。
     * 每个系统都各自存ACCESS_TOKEN，值相同。此TOKEN为合协社区的 ACCESS_TOKEN，用于支付等场景。
     * key: ACCESS_TOKEN, value: xxxx
     * @param at
     */
    public void saveAccessToken(AccessToken at) {
    	
    	if (at == null) {
			log.error("access token is null ");
			return;
    	}
    	
    	try {
			SystemConfig config = null;
			List<SystemConfig> configs = systemConfigRepository.findAllBySysKey(ACC_TOKEN);
			if (configs.size() > 0) {
			    config = configs.get(0);
			    config.setSysValue(JacksonJsonUtil.beanToJson(at));
			} else {
			    config = new SystemConfig(ACC_TOKEN, JacksonJsonUtil.beanToJson(at));
			}
			multipleRepository.setSystemConfig(ACC_TOKEN,config);
			systemConfigRepository.save(config);
		} catch (Exception e) {
			
			log.error("save access token failed \r\n", e);
		}
    }
    
    /**
     * 同ACCESS_TOKEN
     * key: js_token, value: xxxx
     * @param jsToken
     */
    public void saveJsToken(String jsToken) {
    	
    	if (StringUtil.isEmpty(jsToken)) {
			log.error("js token is null ");
			return;
    	}
    	
        SystemConfig config = null;
        List<SystemConfig> configs = systemConfigRepository.findAllBySysKey(JS_TOKEN);
        if (configs.size() > 0) {
            config = configs.get(0);
            config.setSysValue(jsToken);
        } else {
            config = new SystemConfig(JS_TOKEN, jsToken);
        }
        multipleRepository.setSystemConfig(JS_TOKEN,config);
        systemConfigRepository.save(config);
    }
    
    
}
