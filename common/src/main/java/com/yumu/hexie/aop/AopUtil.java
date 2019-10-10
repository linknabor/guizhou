package com.yumu.hexie.aop;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yumu.hexie.common.util.JacksonJsonUtil;
import com.yumu.hexie.integration.wuye.resp.BaseResponseDTO;
import com.yumu.hexie.integration.wuye.vo.BaseRequestDTO;
import com.yumu.hexie.model.redis.RedisRepository;
import com.yumu.hexie.model.system.SystemConfig;
import com.yumu.hexie.service.exception.BizValidateException;
import com.yumu.hexie.service.exception.IntegrationBizException;

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
			@SuppressWarnings("rawtypes")
			RedisTemplate tttt= (RedisTemplate)f.get(rr);
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
    
//    @Pointcut("execution (com.yumu.hexie.integration.wuye.resp.BaseResponseDTO *.*(..))")
//    private void servplatCall() {};
//    
//    @Before("servplatCall()")
//    public void validateSign(JoinPoint joinPoint) {
//    	
//        Object[]oarr = joinPoint.getArgs();
//        BaseRequestDTO<?> baseRequestDTO = new BaseRequestDTO<>();
//        for (Object object : oarr) {
//			if (object instanceof BaseRequestDTO<?>) {
//				baseRequestDTO = (BaseRequestDTO<?>) object;
//				break;
//			}
//		}
//        String requestSign = baseRequestDTO.getSign();
//        if (!"xxxx".equals(requestSign)) {
//			throw new IntegrationBizException("invalid sinagure !");
//		}
//    }
      
	
	
}
