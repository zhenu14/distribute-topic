package com.cdl.ratelimiter.aspect;

import com.cdl.ratelimiter.annotation.CacheLock;
import com.cdl.ratelimiter.service.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 遇到锁直接返回
 */
@Aspect
@Configuration
public class LockAspect {

    @Autowired
    public LockAspect(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    private final StringRedisTemplate lockRedisTemplate;
    private final CacheKeyGenerator cacheKeyGenerator;


    @Around("execution(public * *(..)) && @annotation(com.cdl.ratelimiter.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        System.out.println("lockKey : " + lockKey);
        try {
            final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "1");
            if (!success) {
                // TODO 按理来说 这里应该抛出一个自定义的 CacheLockException 异常;
                throw new RuntimeException("请勿重复请求");
            }
            lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        } finally {
            // TODO 如果演示的话需要注释该代码;实际应该放开
//             lockRedisTemplate.delete(lockKey);
        }
    }
}
