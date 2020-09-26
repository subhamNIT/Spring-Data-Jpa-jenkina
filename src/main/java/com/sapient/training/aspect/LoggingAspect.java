package com.sapient.training.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for logging execution of service and repository Spring components.
 * @author Sapient
 *  springBeanPointcut() - Pointcut that matches all repositories, services and Web REST endpoints.
	applicationPackagePointcut() - Pointcut that matches all Spring beans in the application's main packages.
	logAfterThrowing() - Advice that logs methods throwing exceptions.
	logAround() - Advice that logs when a method is entered and exited.
 *
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {
	
	/**
	 * Pointcut that matches all Spring beans in the application's main packages.
	 */
//	@Pointcut("within(com.sapient.training.data.*)"+
//			" || within(com.sapient.training.service.*)"+
//			" || within(ccom.sapient.training.controller.*)")
	
	@Pointcut("within(com.sapient.training.data.*)"+
			" || within(com.sapient.training.service.*)")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @param e exception
	 */
	@AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
	}

	/**
	 * Advice that logs when a method is entered and exited.
	 *
	 * @param joinPoint join point for advice
	 * @return result
	 * @throws Throwable throws IllegalArgumentException
	 */
	@Around("applicationPackagePointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
			}
			try {
				Object result = joinPoint.proceed();
				if (log.isDebugEnabled()) {
					log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
							joinPoint.getSignature().getName(), result);
				}
				return result;
			} catch (Exception e) {
				log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
						joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
				throw e;
			}
		}catch(Exception e) {
				throw e;
		}
	}
}
