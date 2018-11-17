package com.cdl.ratelimiter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author dongli.chi
 * @description 分布式Session，使用spring.session.store-type=redis自动配置
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
public class RedisSessionConfig {

//    @Bean
//    public HttpSessionStrategy httpSessionStrategy() {
//        return new HeaderHttpSessionStrategy();
//    }

}
