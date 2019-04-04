/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.service.common.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.common.util.StringUtil;
import com.yumu.hexie.integration.systemconfig.SystemConfigUtil;
import com.yumu.hexie.integration.wechat.entity.AccessToken;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.system.SystemConfig;
import com.yumu.hexie.model.system.SystemConfigRepository;
import com.yumu.hexie.service.common.SystemConfigService;
import com.yumu.hexie.service.exception.BizValidateException;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: SystemConfigServiceImpl.java, v 0.1 2016年4月7日 下午4:39:59  Exp $
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService {
	
	private static final Logger log = LoggerFactory.getLogger(SystemConfigServiceImpl.class);

    private static final String JS_TOKEN = "JS_TOKEN";
    private static final String ACC_TOKEN = "ACCESS_TOKEN";
    @Inject
    private SystemConfigRepository systemConfigRepository;
    @Inject
    private RedisRepository redisRepository;
    
    @Override
    public int querySmsChannel() {
    	
        String value = queryValueByKey("SMS_CHANNEL");
        if (!"0".equals(value)) {
            return 1;
        }
        return 0;
        
    }
    
    @Override
    public String[] queryActPeriod() {
    	
    	String datePeriod = queryValueByKey("ACT_PERIOD");
    	if (!StringUtil.isEmpty(datePeriod)) {
			return datePeriod.split(",");
		}else {
			return new String[0];
		}
    	
    }
    
    @Override
    public Set<String> getUnCouponItems() {
        Set<String> res = new HashSet<String>();
        String key = "NOCOUPON_ITEMS";
        String value = queryValueByKey(key);
        if (!StringUtil.isEmpty(value)) {
        	for(String idStr: value.split(",")) {
                res.add(idStr.trim());
            }
		}
        return res;
    }
    
    public SystemConfig getConfigFromCache(String key){
    	
    	SystemConfig systemConfig = redisRepository.getSystemConfig(key);
    	if (systemConfig == null) {
			log.error("could not find key [" + key +"] in cache " );
			int ret = SystemConfigUtil.notifyRefreshing(key);
			log.error("notify refreshing the cache : " + ret);
    	}
    	return systemConfig;
    
    }
    
    public String queryWXAToken() {
    	SystemConfig config = getConfigFromCache(ACC_TOKEN);
        if (config != null) {
            try {
                AccessToken at = (AccessToken) JacksonJsonUtil.jsonToBean(config.getSysValue(), AccessToken.class);
                return at.getToken();
            } catch (JSONException e) {
               log.error("queryWXAccToken failed :", e);
            }
        }
        throw new BizValidateException("微信token没有记录");
    }
    
    @Override
	public String queryJsTickets() {
        String tickets = "";
        SystemConfig config = getConfigFromCache(JS_TOKEN);
        if (config != null) {
            tickets = config.getSysValue();
        }
        return tickets;
    }
   
    @Override
	public String queryValueByKey(String key) {
		
		String ret = "";
		List<SystemConfig> list = systemConfigRepository.findAllBySysKey(key);
		if (list.size()>0) {
			SystemConfig config = list.get(0);
			ret = config.getSysValue();
		}
	
		return ret;
	}
    
}
