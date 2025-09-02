package com.sky.aspect;

import com.sky.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {


    @Before("@annotation(logAnnotation)")
    public void logBefore(JoinPoint joinPoint,Log logAnnotation) {
        log.info(logAnnotation.value());
    }
}
