package com.cdl.multisource;

import com.cdl.multisource.model.Member;
import com.cdl.multisource.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class MultiSourceApplication {

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @PreDestroy
    public void shutdownThread(){
        threadPoolTaskExecutor.shutdown();
    }

    public static void main(String[] args) {
        SpringApplication.run(MultiSourceApplication.class,args);
    }

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String saveToDB() {
        try{
            memberService.saveToMysql(generateMembers());
            memberService.saveToPostgresql(generateMembers());
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "false : " + e.getMessage();
        }
    }

    private List<Member> generateMembers(){
        List<Member> members = new ArrayList<>();
        String name = "Test_";
        for (int i = 0; i < 13; i++) {
            Member member = new Member();
            member.setName(name+i);
            member.setAge(i);
            members.add(member);
        }
        return members;
    }

}
