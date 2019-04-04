package com.yumu.hexie.aop;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.system.SystemConfig;

@Aspect 
@Configuration
public class AopUtil {
	
	
	private static final Logger log = LoggerFactory.getLogger("com.yumu.hexie.schedule");

	/** 
	 * 切面 
	 * @author Bird 
	 * 
	 */  
	 
    @Pointcut("execution(* com.yumu.hexie.model.redis.RedisRepository.setSystemConfig(..))")  
    private void anyMethod(){}//定义一个切入点  
      
    @Before("anyMethod()")  
    public void before(JoinPoint joinPoint){
    	
		try {
			
			RedisRepository rr = (RedisRepository)joinPoint.getTarget();
	    	Field f = null;
			String key = (String)joinPoint.getArgs()[0];
			SystemConfig systemConfig = (SystemConfig)joinPoint.getArgs()[1];
	    	if (!key.contains("TOKEN")) {
				return ;
			}
			f = RedisRepository.class.getDeclaredField("systemConfigRedisTemplate");
			f.setAccessible(true);
			RedisTemplate tttt= (RedisTemplate)f.get(rr);
//			log.warn("before:" + joinPoint.getSourceLocation().getFileName()+","+ joinPoint.getSourceLocation().getLine());
			
			StackTraceElement[] stlist = Thread.currentThread().getStackTrace();
	    	StringBuilder sb=new StringBuilder();
	    	for (StackTraceElement stackTraceElement : stlist) {
				sb.append(stackTraceElement.toString() + "\n");
			}
			
	    	log.warn("##############[aop]###############, key:"  +  systemConfig.getSysKey() + ", value:" + systemConfig.getSysValue() +" \r\n value serilaizer:" + tttt.getValueSerializer().toString() +", \r\n stack :" + sb.toString());
		} catch (Exception e) {
			
			log.warn("aop", e);
		}
     	
    	
	} 
      
	
	
}
