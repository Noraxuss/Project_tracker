package project_tracker_backend.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String EXECUTION_PROJECT_TRACKER_BACKEND = "execution(* project_tracker_backend..*(..))";

    // Before method execution
    @Before(EXECUTION_PROJECT_TRACKER_BACKEND)  // You can adjust the package name as needed
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering method: " + joinPoint.getSignature().getName());
    }

    // After method execution
    @After(EXECUTION_PROJECT_TRACKER_BACKEND)
    public void logMethodExit(JoinPoint joinPoint) {
        logger.info("Exiting method: " + joinPoint.getSignature().getName());
    }

    // After throwing an exception
    @AfterThrowing(value = EXECUTION_PROJECT_TRACKER_BACKEND, throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        logger.error("Error in method: " + joinPoint.getSignature().getName(), e);
    }
}
