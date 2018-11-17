package com.cdl.ratelimiter.controller;

import com.cdl.ratelimiter.annotation.CacheLock;
import com.cdl.ratelimiter.annotation.CacheParam;
import com.cdl.ratelimiter.annotation.Limit;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/limit")
@RestController
@Slf4j
public class LimiterController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    //每秒向桶中放入两个令牌，请求一次消耗一个令牌
    RateLimiter limiter = RateLimiter.create(2.0) ;

    @Limit(key = "limit", period = 100, count = 10)
    @GetMapping("/limit")
    public int limit() {
        // 意味著 100S 内最多允許訪問10次
        return ATOMIC_INTEGER.incrementAndGet();
    }

    @GetMapping("/guava")
    public String guava() {
        if(limiter.tryAcquire()){
            log.info("等待时间" + limiter.acquire());
            return "success";
        }
        return "fail";
    }

    @CacheLock(prefix = "limit")
    @GetMapping("/lock")
    public String lock(@CacheParam(name = "token") @RequestParam String token) {
        System.out.println("Limiter 2");
        return "success - " + token;
    }

}
