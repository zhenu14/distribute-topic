package com.cdl.multisource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionhandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        log.error("AsyncUncaughtException-Exception message - " + throwable.getMessage());
        log.error("AsyncUncaughtException-Method name - " + method.getName());
        for (Object param : objects) {
            log.error("AsyncUncaughtException-Parameter value - " + param);
        }
    }
}
