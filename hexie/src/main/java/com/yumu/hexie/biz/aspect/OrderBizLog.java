package com.yumu.hexie.biz.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface OrderBizLog {

	    /** 描述 */
	    String title()  default "";
	    /** 订单操作名 */
	    String action() default "";
	    /** 操作者 */
	    String operator() default "";
}
