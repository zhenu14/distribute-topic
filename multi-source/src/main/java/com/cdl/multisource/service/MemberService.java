package com.cdl.multisource.service;

import com.cdl.multisource.model.Member;
import com.cdl.multisource.repository.mysql.MysqlMemberRepository;
import com.cdl.multisource.repository.postgresql.PostgreMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MemberService {

    private final PostgreMemberRepository postgreMemberRepository;

    private final MysqlMemberRepository mysqlMemberRepository;

    @Autowired
    public MemberService(MysqlMemberRepository mysqlMemberRepository, PostgreMemberRepository postgreMemberRepository) {
        this.mysqlMemberRepository = mysqlMemberRepository;
        this.postgreMemberRepository = postgreMemberRepository;
    }

    public void saveToMysql(List<Member> memberList) throws Exception{
        mysqlMemberRepository.saveAll(memberList);
    }

    public void saveToPostgresql(List<Member> memberList) throws Exception{
        postgreMemberRepository.saveAll(memberList);
    }
}
