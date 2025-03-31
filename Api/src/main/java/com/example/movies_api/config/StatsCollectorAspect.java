package com.example.movies_api.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class StatsCollectorAspect {

    private static final Logger logger = LoggerFactory.getLogger(StatsCollectorAspect.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Pointcut("execution(* com.example.movies_api.stats.StatsCollector.recordCall(..))")
    public void statsCollectorRecordCallMethod() {}
    
    @Pointcut("execution(* com.example.movies_api.stats.StatsCollector.get*(..))")
    public void statsCollectorGetMethods() {}

    @Before("statsCollectorRecordCallMethod()")
    public void beforeRecordCall(JoinPoint joinPoint) {
        logger.info("STATS ASPECT: About to record a new call in StatsCollector at {}", 
                LocalDateTime.now().format(formatter));
    }
    
    @AfterReturning(pointcut = "statsCollectorGetMethods()", returning = "result")
    public void afterGetMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        if (result instanceof Integer) {
            logger.info("STATS ASPECT: StatsCollector.{} returned count: {}", methodName, result);
        } else if (result instanceof LocalDateTime) {
            LocalDateTime time = (LocalDateTime) result;
            logger.info("STATS ASPECT: StatsCollector.{} returned time: {}", 
                    methodName, 
                    time != null ? time.format(formatter) : "null");
        }
    }
}
