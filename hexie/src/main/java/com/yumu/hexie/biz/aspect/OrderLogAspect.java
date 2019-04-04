package com.yumu.hexie.biz.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OrderLogAspect {
    @Before(value = "execution(public * com.demo.service..*.*(..))" )
    public void beforeShow() {
         System. out.println("before show." );
   }
   
    @After(value = "execution(public * com.demo.service..*.*(..))" )
    public void afterShow() {
         System. out.println("after show." );
   }
}
