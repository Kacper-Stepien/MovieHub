package com.example.movies_api.config;

import com.example.movies_api.stats.StatsCollector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

@Aspect
@Component
public class RequestMonitoringAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestMonitoringAspect.class);

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerClass() {}

    @Pointcut("execution(* com.example.movies_api.controller.*.*(..))")
    public void controllerMethod() {}

    @Pointcut("restControllerClass() && controllerMethod()")
    public void restControllerMethod() {}

    @Around("restControllerMethod()")
    public Object monitorApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // Uzyskaj nazwę endpointu i metodę HTTP
        String endpoint = determineEndpoint(joinPoint);
        String httpMethod = determineHttpMethod(joinPoint);
        String key = httpMethod + " " + endpoint;
        
        // Zarejestruj wywołanie w StatsCollector
        StatsCollector collector = StatsCollector.getInstance();
        collector.recordMethodCall(key);
        
        try {
            // Wykonaj oryginalną metodę
            Object result = joinPoint.proceed();
            
            // Rejestracja pomyślnego wywołania
            collector.recordSuccessfulCall(key);
            
            return result;
        } catch (Exception e) {
            // Rejestracja nieudanego wywołania
            collector.recordFailedCall(key);
            throw e;
        }
    }
    
    private String determineEndpoint(ProceedingJoinPoint joinPoint) {
        try {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            
            // Znajdujemy metodę przez refleksję
            Method method = findMethod(joinPoint);
            
            if (method == null) {
                return className + "." + methodName;
            }
            
            // Szukamy adnotacji RequestMapping na metodzie
            String path = extractPathFromMethodAnnotations(method);
            
            // Jeśli nie ma na metodzie, szukamy na klasie
            if (path == null || path.isEmpty()) {
                path = extractPathFromClassAnnotations(joinPoint.getTarget().getClass());
            }
            
            if (path == null || path.isEmpty()) {
                return className + "." + methodName;
            }
            
            return path;
        } catch (Exception e) {
            logger.warn("Nie można określić endpointu dla: " + joinPoint.getSignature().toShortString(), e);
            return joinPoint.getSignature().toShortString();
        }
    }
    
    private String determineHttpMethod(ProceedingJoinPoint joinPoint) {
        try {
            Method method = findMethod(joinPoint);
            
            if (method == null) {
                return "UNKNOWN";
            }
            
            if (method.isAnnotationPresent(GetMapping.class)) {
                return "GET";
            } else if (method.isAnnotationPresent(PostMapping.class)) {
                return "POST";
            } else if (method.isAnnotationPresent(PutMapping.class)) {
                return "PUT";
            } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                return "DELETE";
            } else if (method.isAnnotationPresent(PatchMapping.class)) {
                return "PATCH";
            } else if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMethod[] methods = method.getAnnotation(RequestMapping.class).method();
                if (methods.length > 0) {
                    return methods[0].name();
                }
            }
            
            return "UNKNOWN";
        } catch (Exception e) {
            logger.warn("Nie można określić metody HTTP dla: " + joinPoint.getSignature().toShortString(), e);
            return "UNKNOWN";
        }
    }
    
    private Method findMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Object[] args = joinPoint.getArgs();
        
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                // Prosta weryfikacja czy liczba parametrów się zgadza
                if (method.getParameterCount() == args.length) {
                    return method;
                }
            }
        }
        return null;
    }
    
    private String extractPathFromMethodAnnotations(Method method) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            String[] paths = method.getAnnotation(RequestMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        if (method.isAnnotationPresent(GetMapping.class)) {
            String[] paths = method.getAnnotation(GetMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        if (method.isAnnotationPresent(PostMapping.class)) {
            String[] paths = method.getAnnotation(PostMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        if (method.isAnnotationPresent(PutMapping.class)) {
            String[] paths = method.getAnnotation(PutMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        if (method.isAnnotationPresent(DeleteMapping.class)) {
            String[] paths = method.getAnnotation(DeleteMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        if (method.isAnnotationPresent(PatchMapping.class)) {
            String[] paths = method.getAnnotation(PatchMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        
        return "";
    }
    
    private String extractPathFromClassAnnotations(Class<?> clazz) {
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            String[] paths = clazz.getAnnotation(RequestMapping.class).value();
            if (paths.length > 0) return paths[0];
        }
        return "";
    }
}
