package com.cdl.gateway.config;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //异常过滤器
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() {
        log.info("进入Zuul过滤器");
        log.info("进入Zuul过滤器");
        log.info("进入Zuul过滤器");
        return null;
    }

}
