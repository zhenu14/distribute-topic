package com.cdl.multisource;

import com.cdl.multisource.model.Member;
import com.cdl.multisource.service.MemberService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiSourceApplicationTests {

    @Autowired
    private MemberService memberService;

    @Test
    @Ignore
    public void saveToDB() throws Exception{
        memberService.saveToMysql(generateMembers());
        memberService.saveToPostgresql(generateMembers());
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
