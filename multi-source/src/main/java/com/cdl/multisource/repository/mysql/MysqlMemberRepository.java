package com.cdl.multisource.repository.mysql;

import com.cdl.multisource.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlMemberRepository extends JpaRepository<Member,Long> {
}
