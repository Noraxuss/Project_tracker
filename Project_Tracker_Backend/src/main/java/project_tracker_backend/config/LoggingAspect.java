package project_tracker_backend.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String EXECUTION_PROJECT_TRACKER_BACKEND = "execution(* project_tracker_backend..*(..))";

    // Before method execution to log method entry
    @Before(EXECUTION_PROJECT_TRACKER_BACKEND)
    public void logAnnotations(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (method.isAnnotationPresent(Transactional.class)) {
            logger.info("Method {} is annotated with @Transactional", method.getName());
        }
    }

    // After method execution to log method exit
    @After(EXECUTION_PROJECT_TRACKER_BACKEND)
    public void logMethodExit(JoinPoint joinPoint) {
        logger.info("Exiting method: {} ", joinPoint.getSignature().getName());
    }

    // Around method execution to log execution time and return value
    @Around(EXECUTION_PROJECT_TRACKER_BACKEND)
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("Method {} executed in {} ms", joinPoint.getSignature().getName(), executionTime);
        return result;
    }

    // After returning from method execution to log return value
    @AfterReturning(value = EXECUTION_PROJECT_TRACKER_BACKEND, returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    // After throwing an exception
    @AfterThrowing(value = EXECUTION_PROJECT_TRACKER_BACKEND, throwing = "e")
    public void logValidationErrors(JoinPoint joinPoint, Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            logger.error("Validation failed in method {}: {}", joinPoint.getSignature().getName(), e.getMessage());
        }
    }
}
