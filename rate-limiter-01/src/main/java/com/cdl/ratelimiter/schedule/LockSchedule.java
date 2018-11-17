package com.cdl.ratelimiter.schedule;

import com.cdl.ratelimiter.annotation.CacheLock;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableScheduling
@Component
public class LockSchedule {

    @CacheLock(prefix = "limit")
    //每2秒执行一次
//    @Scheduled(cron = "*/7 * * * * ?")
    public void printSay() throws InterruptedException {
        System.out.println("This is a say method! "+ new Date());
    }

}
