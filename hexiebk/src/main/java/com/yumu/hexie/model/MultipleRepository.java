package com.yumu.hexie.model;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yumu.hexie.model.redis.Keys;
import com.yumu.hexie.model.system.SystemConfig;

@Component(value = "multipleRepository")
public class MultipleRepository {

    private static final Logger SCHEDULE_LOG = LoggerFactory.getLogger("com.yumu.hexie.schedule");

    @Inject
    @Named("systemConfigRedisTemplate")
    private RedisTemplate<String, SystemConfig> systemConfigRedisTemplate;
    
    public void setSystemConfig(String key,SystemConfig value) {

        SCHEDULE_LOG.warn("update cache:" + key + "["+value+"]");
        
        String sysKey = Keys.systemConfigKey(key);
        
        systemConfigRedisTemplate.opsForValue().set(sysKey, value, 120, TimeUnit.MINUTES);
        SystemConfig c = systemConfigRedisTemplate.opsForValue().get(sysKey);
        if(c != null) {
            SCHEDULE_LOG.warn("get mainRedis cache:"+c.getSysKey() + "["+c.getSysValue()+"]");
        }
        
        SCHEDULE_LOG.warn("END update cache:" + key + "["+value+"]");
    }
    
    
}
