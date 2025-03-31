package com.example.movies_api.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.movies_api.controller..*(..))")
    public void controllerMethods() {}
    
    @Pointcut("execution(* com.example.movies_api.service..*(..))")
    public void serviceMethods() {}

    @Around("controllerMethods()")
    public Object logControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        logger.info("CONTROLLER: {} executed in {} ms", joinPoint.getSignature(), executionTime);

        return proceed;
    }
    
    @Around("serviceMethods()")
    public Object logServiceMethodCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = signature.getDeclaringType().getSimpleName();
        
        logger.info("SERVICE METHOD CALL: {}.{} with args: {}", 
                className, 
                methodName, 
                Arrays.toString(joinPoint.getArgs()));
                
        Object result = joinPoint.proceed();
        
        logger.info("SERVICE METHOD RETURN: {}.{} completed", className, methodName);
        return result;
    }
}
