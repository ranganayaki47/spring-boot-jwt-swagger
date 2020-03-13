package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	public static Logger logger=LoggerFactory.getLogger(LoggingAspect.class);
	
	@Before("execution (* com.stackroute.keepnote.controller.ReminderController.*(..))")
	public void getBeforeAdvice(JoinPoint joinPoint){
		System.out.println("Executing before :: "+joinPoint.getSignature().getName());
		logger.info("Pradeep Executing before :: "+joinPoint.getSignature().getName());
	}
	
	@After("execution (* com.stackroute.keepnote.controller.ReminderController.*(..))")
	public void getAfterAdvice(JoinPoint joinPoint){
		System.out.println("Executing after :: "+joinPoint.getSignature().getName());
		//logger.info("Executing after :: "+joinPoint.getSignature().getName());
	}
	
	@AfterThrowing("execution (* com.stackroute.keepnote.controller.ReminderController.*(..))")
	public void logExceptions(JoinPoint joinPoint){
		System.out.println("Executing logger exception :: "+joinPoint.getSignature().getName());
		//logger.info("Executing logger exception :: "+joinPoint.getSignature().getName());
	}
	
	@AfterReturning(pointcut="execution (* com.stackroute.keepnote.controller.ReminderController.*(..))", returning="returnString")
	public void getNameReturningAdvice(JoinPoint joinPoint,String returnString){
		System.out.println("Executing return advice :: "+joinPoint.getSignature().getName());
		//logger.info("Executing return advice :: "+joinPoint.getSignature().getName());
	}
}
