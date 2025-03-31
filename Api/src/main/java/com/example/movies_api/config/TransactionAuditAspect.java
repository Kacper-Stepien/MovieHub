package com.example.movies_api.config;

import com.example.movies_api.events.EventType;
import com.example.movies_api.events.MediatorConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAuditAspect.class);

    @Pointcut("execution(* com.example.movies_api.service.MovieService.addMovie(..))")
    public void movieAddition() {}
    
    @Pointcut("execution(* com.example.movies_api.service.MovieService.deleteMovie(..))")
    public void movieDeletion() {}
    
    @Pointcut("execution(* com.example.movies_api.service.RatingService.addOrUpdateRating(..))")
    public void ratingAddition() {}
    
    @Pointcut("execution(* com.example.movies_api.service.UserService.updateUserDetails(..))")
    public void userUpdate() {}

    @AfterReturning(pointcut = "movieAddition()", returning = "result")
    public void afterMovieAdded(JoinPoint joinPoint, Object result) {
        String user = getCurrentUser();
        String movieInfo = result != null ? result.toString() : "unknown";
        logger.info("TRANSACTION AUDIT: Movie added by {} - Movie: {}", user, movieInfo);
        
        if (MediatorConfig.MEDIATOR != null) {
            MediatorConfig.MEDIATOR.notify(this, EventType.MOVIE_CREATED);
            logger.info("TRANSACTION AUDIT: Event MOVIE_CREATED published to mediator");
        }
    }
    
    @AfterReturning("movieDeletion()")
    public void afterMovieDeleted(JoinPoint joinPoint) {
        String user = getCurrentUser();
        Long movieId = extractMovieId(joinPoint);
        logger.info("TRANSACTION AUDIT: Movie deleted by {} - Movie ID: {}", user, movieId);
        
        if (MediatorConfig.MEDIATOR != null) {
            MediatorConfig.MEDIATOR.notify(this, EventType.MOVIE_DELETED);
            logger.info("TRANSACTION AUDIT: Event MOVIE_DELETED published to mediator");
        }
    }
    
    @AfterReturning("ratingAddition()")
    public void afterRatingAdded(JoinPoint joinPoint) {
        String user = getCurrentUser();
        Object[] args = joinPoint.getArgs();
        String userEmail = args.length > 0 ? args[0].toString() : "unknown";
        Long movieId = args.length > 1 ? (Long) args[1] : 0L;
        Object rating = args.length > 2 ? args[2] : "unknown";
        
        logger.info("TRANSACTION AUDIT: Rating added/updated by {} - User: {}, Movie ID: {}, Rating: {}", 
                user, userEmail, movieId, rating);
        
        if (MediatorConfig.MEDIATOR != null) {
            MediatorConfig.MEDIATOR.notify(this, EventType.RATING_ADDED);
            logger.info("TRANSACTION AUDIT: Event RATING_ADDED published to mediator");
        }
    }
    
    @AfterReturning("userUpdate()")
    public void afterUserUpdated(JoinPoint joinPoint) {
        String user = getCurrentUser();
        Object[] args = joinPoint.getArgs();
        String userEmail = args.length > 0 ? args[0].toString() : "unknown";
        
        logger.info("TRANSACTION AUDIT: User details updated for {} by {}", userEmail, user);
    }
    
    @AfterThrowing(pointcut = "movieAddition() || movieDeletion() || ratingAddition() || userUpdate()", 
                  throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String user = getCurrentUser();
        logger.error("TRANSACTION AUDIT: Exception in {}.{} executed by {}: {}", 
                className,
                methodName, 
                user, 
                exception.getMessage());
    }
    
    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "anonymous";
    }
    
    private Long extractMovieId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long) {
            return (Long) args[0];
        }
        return 0L;
    }
}
